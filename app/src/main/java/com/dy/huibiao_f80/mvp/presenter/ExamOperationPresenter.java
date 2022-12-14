package com.dy.huibiao_f80.mvp.presenter;

import android.app.AlertDialog;
import android.app.Application;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.api.back.BeginOperationExam_Back;
import com.dy.huibiao_f80.api.back.GetTestForm_Back;
import com.dy.huibiao_f80.api.back.TestFormSubmit_Back;
import com.dy.huibiao_f80.app.utils.NetworkUtils;
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
        if (!NetworkUtils.getNetworkType()) {
            ArmsUtils.snackbarText("??????????????????????????????????????????");
            return;
        }
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
          ArmsUtils.snackbarText("?????????????????????????????????");
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
                                ArmsUtils.snackbarText("???????????????");
                            }


                        }else {
                            submitone(string);
                        }

                        ArmsUtils.snackbarText(back.getMessage());

                    }
                });
    }

    private void makeDialog(int i) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mRootView.getActivity());
        View inflate = LayoutInflater.from(mRootView.getActivity()).inflate(R.layout.submithint_layout, null);
        TextView title = (TextView) inflate.findViewById(R.id.title);
        TextView message = (TextView) inflate.findViewById(R.id.message);
        Button confirm = (Button) inflate.findViewById(R.id.confirm);
        Button cancle = (Button) inflate.findViewById(R.id.cancle);
        title.setText("??????");
        builder.setView(inflate);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        if (i==0){
            message.setText("?????????????????????");
        }else {
            String content = " <font style=\"font-size:16dp\" color=\"#3856FC\">??????</font>\n" +
                    " <font style=\"font-size:16dp\" color=\"#FF0000\">" + i + "</font>" +
                    " <font style=\"font-size:16dp\" color=\"#3856FC\">????????????????????????????????????????????????????????????</font>\n";
            message.setText(Html.fromHtml(content));
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                submit();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



       /* AlertDialog.Builder builder=new AlertDialog.Builder(mRootView.getActivity());
        builder.setTitle("??????");
        if (i==0){
            builder.setMessage("?????????????????????");
        }else {
            builder.setMessage("??????"+i+"????????????????????????????????????????????????????????????");
        }

        builder.setNeutralButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
               submit();
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }) ;
        builder.show();*/
    }
}