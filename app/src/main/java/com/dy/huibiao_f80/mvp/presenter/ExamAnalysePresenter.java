package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.api.back.AnalyseSubmit_Back;
import com.dy.huibiao_f80.api.back.BeginAnalyseExam_Back;
import com.dy.huibiao_f80.mvp.contract.ExamAnalyseContract;
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
public class ExamAnalysePresenter extends BasePresenter<ExamAnalyseContract.Model, ExamAnalyseContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ExamAnalysePresenter(ExamAnalyseContract.Model model, ExamAnalyseContract.View rootView) {
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

    public void beginAnalyseExam(String examinationId, String examinerId) {

        mModel.beginAnalyseExam(Constants.URL,examinationId,examinerId)
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BeginAnalyseExam_Back>(mErrorHandler) {
                    @Override
                    public void onNext(BeginAnalyseExam_Back back) {
                        LogUtils.d(back);
                        if (back.getSuccess()){
                            mRootView.showExamTitle(back);
                        }else {
                            ArmsUtils.snackbarText(back.getMessage());
                        }
                    }
                });
    }

    public void submit(String examinationId, String examinerId, BeginAnalyseExam_Back beginAnalyseExamBack) {
       mModel.analyseSubmit(Constants.URL,examinationId,examinerId,beginAnalyseExamBack)
               .doOnSubscribe(disposable -> {
                   mRootView.showLoading();
               }).subscribeOn(AndroidSchedulers.mainThread())
               .doFinally(() -> {
                   mRootView.hideLoading();
               }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
               .subscribe(new ErrorHandleSubscriber<AnalyseSubmit_Back>(mErrorHandler) {
                   @Override
                   public void onNext(AnalyseSubmit_Back back) {
                       LogUtils.d(back);
                       if (back.getSuccess()){
                           mRootView.submitSuccess();
                       }

                       ArmsUtils.snackbarText(back.getMessage());

                   }
               });
    }
}