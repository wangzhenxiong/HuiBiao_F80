package com.dy.huibiao_f80.mvp.presenter;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.api.back.BeginOperationExam_Back;
import com.dy.huibiao_f80.api.back.GetTestForm_Back;
import com.dy.huibiao_f80.api.back.TestFormSubmit_Back;
import com.dy.huibiao_f80.bean.ReportBean;
import com.dy.huibiao_f80.mvp.contract.ExamOperationContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class ExamOperationPresenter extends BasePresenter<ExamOperationContract.Model, ExamOperationContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ExamOperationPresenter(ExamOperationContract.Model model, ExamOperationContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void beginOperationExam(String examinationId, String examinerId) {
        mModel.beginOperationExam(Constants.URL,examinationId,examinerId)
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BeginOperationExam_Back>(mErrorHandler) {
                    @Override
                    public void onNext(BeginOperationExam_Back back) {
                        LogUtils.d(back);
                        if (back.getSuccess()){
                            mRootView.showExamTitle(back);
                            getAllReportMessage(back);
                        }else {
                            ArmsUtils.snackbarText(back.getMessage());
                        }
                    }
                });
    }

    private void getAllReportMessage(BeginOperationExam_Back back) {
        List<BeginOperationExam_Back.EntityBean.OperationPaperListBean> operationPaperList = back.getEntity().getOperationPaperList();
        for (int i = 0; i < operationPaperList.size(); i++) {
            BeginOperationExam_Back.EntityBean.OperationPaperListBean operationPaperListBean = operationPaperList.get(i);
            String id = operationPaperListBean.getId();
            getReportMessage(id);
        }
    }

    private void getReportMessage(String id) {
        mModel.getReportMessage(id)
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GetTestForm_Back>(mErrorHandler) {
                    @Override
                    public void onNext(GetTestForm_Back back) {
                        LogUtils.d(back);
                        if (back.getSuccess()){
                            MyAppLocation.myAppLocation.mExamOperationService.addGetTestForm_BackMap(id,back);
                        }else {
                            getReportMessage(id);
                        }



                    }
                });
    }


    public void submitOperation() {
        if (MyAppLocation.myAppLocation.mExamOperationService.isTeacherSubmit){
            Map<String, GetTestForm_Back> getTestForm_backMap = MyAppLocation.myAppLocation.mExamOperationService.getGetTestForm_backMap();
            Map<String, ReportBean> reportBeanMap = MyAppLocation.myAppLocation.mExamOperationService.getReportBeanMap();
            Set<String>  getTestForm_backMapkeys= getTestForm_backMap.keySet();
            Set<String>  reportBeanMapkeys= reportBeanMap.keySet();
            LogUtils.d(getTestForm_backMap);
            LogUtils.d(reportBeanMap);
            int i = getTestForm_backMapkeys.size() - reportBeanMapkeys.size();
            if (i >0) {
                makeDialog(i);
            }else{
                makeDialog(0);
            }
        }else {
          ArmsUtils.snackbarText("请联系考评员评分后提交");
        }



    }
    private void submit(){
        Map<String, GetTestForm_Back> getTestForm_backMap = MyAppLocation.myAppLocation.mExamOperationService.getGetTestForm_backMap();
        Set<String> strings = getTestForm_backMap.keySet();
        for (String string : strings) {
            GetTestForm_Back getTestForm_back = getTestForm_backMap.get(string);
            submitone(string);
        }

    }



    private void submitone(String string) {
        mModel.submitOperation(string)
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<TestFormSubmit_Back>(mErrorHandler) {
                    @Override
                    public void onNext(TestFormSubmit_Back back) {
                        LogUtils.d(back);
                        if (back.isSuccess()){

                            Map<String, GetTestForm_Back> getTestForm_backMap = MyAppLocation.myAppLocation.mExamOperationService.getGetTestForm_backMap();
                            getTestForm_backMap.remove(string);
                            if (getTestForm_backMap.keySet().size()==0){
                                mRootView.submitSuccess();
                                ArmsUtils.snackbarText("考试已结束");
                            }


                        }else {
                            submitone(string);
                        }

                        ArmsUtils.snackbarText(back.getMessage());

                    }
                });
    }

    private void makeDialog(int i) {
        AlertDialog.Builder builder=new AlertDialog.Builder(mRootView.getActivity());
        builder.setTitle("提示");
        if (i==0){
            builder.setMessage("确定要提交吗？");
        }else {
            builder.setMessage("还有"+i+"道题实验报告还未填写完成，确定要提交吗？");
        }

        builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
               submit();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }) ;
        builder.show();
    }
}