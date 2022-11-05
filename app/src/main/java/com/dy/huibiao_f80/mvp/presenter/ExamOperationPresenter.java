package com.dy.huibiao_f80.mvp.presenter;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.api.back.BeginOperationExam_Back;
import com.dy.huibiao_f80.api.back.TestFormSubmit_Back;
import com.dy.huibiao_f80.mvp.contract.ExamOperationContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

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
                        }else {
                            ArmsUtils.snackbarText(back.getMessage());
                        }
                    }
                });
    }



    public void submitOperation() {
        if (null==MyAppLocation.myAppLocation.mExamOperationService.getBeginTestForm_back()) {
            ArmsUtils.snackbarText("请先填写实验报告");
            return;
        }
        if (null==MyAppLocation.myAppLocation.mExamOperationService.getReportBean()){
          makeDialog();
        }else {
            submit();
        }

    }
    private void submit(){
        mModel.submitOperation()
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
                            mRootView.submitSuccess();
                        }

                        ArmsUtils.snackbarText(back.getMessage());

                    }
                });
    }

    private void makeDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(mRootView.getActivity());
        builder.setTitle("提示");
        builder.setMessage("实验报告还未填写，确定要提交吗？");
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