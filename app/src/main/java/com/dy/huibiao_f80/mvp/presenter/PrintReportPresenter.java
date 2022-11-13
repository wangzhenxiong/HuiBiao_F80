package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.api.back.GetTestForm_Back;
import com.dy.huibiao_f80.mvp.contract.PrintReportContract;
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
public class PrintReportPresenter extends BasePresenter<PrintReportContract.Model, PrintReportContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PrintReportPresenter(PrintReportContract.Model model, PrintReportContract.View rootView) {
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

    public void getReportMoudle(String examinerId,String examinationId,String operationPaperId) {
      mModel.getTestForm(Constants.URL,operationPaperId)
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
                          mRootView.showReports(back);
                      }else {
                          ArmsUtils.snackbarText(back.getMessage());
                      }
                  }
              });
    }

    /*public void submit(String operationPaperId) {
        if (null== MyAppLocation.myAppLocation.mExamOperationService.getGetTestFormBackList()) {
            ArmsUtils.snackbarText("请先填写实验报告");
            return;
        }
        submits(operationPaperId);
    }*/

   /* private void submits(String operationPaperId){
        mModel.submitOperation(operationPaperId)
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
    }*/
}