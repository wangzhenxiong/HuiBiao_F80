package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.api.back.BeginTheoryExam_Back;
import com.dy.huibiao_f80.api.back.TheorySubmit_Back;
import com.dy.huibiao_f80.mvp.contract.ExamTheoryContract;
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
public class ExamTheoryPresenter extends BasePresenter<ExamTheoryContract.Model, ExamTheoryContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ExamTheoryPresenter(ExamTheoryContract.Model model, ExamTheoryContract.View rootView) {
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

    public void beginTheoryExam(String examinationId) {
        mModel.beginTheoryExam(Constants.URL,examinationId)
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BeginTheoryExam_Back>(mErrorHandler) {
                    @Override
                    public void onNext(BeginTheoryExam_Back back) {
                        LogUtils.d(back);
                        if (back.getSuccess()){
                            mRootView.showExamTitle(back);
                        }else {
                            ArmsUtils.snackbarText(back.getMessage());
                        }
                    }
                });
    }

    public void submit(String examinationId, String examinerId, BeginTheoryExam_Back beginTheoryExamBack) {
        LogUtils.d(beginTheoryExamBack);
      mModel.submit(examinationId,examinerId,beginTheoryExamBack,Constants.URL)
              .doOnSubscribe(disposable -> {
                  mRootView.showLoading();
              }).subscribeOn(AndroidSchedulers.mainThread())
              .doFinally(() -> {
                  mRootView.hideLoading();
              }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
              .subscribe(new ErrorHandleSubscriber<TheorySubmit_Back>(mErrorHandler) {
                  @Override
                  public void onNext(TheorySubmit_Back back) {
                      LogUtils.d(back);
                      if (back.getSuccess()){
                        mRootView.submitSuccess();
                      }

                      ArmsUtils.snackbarText(back.getMessage());

                  }
              });
    }
}