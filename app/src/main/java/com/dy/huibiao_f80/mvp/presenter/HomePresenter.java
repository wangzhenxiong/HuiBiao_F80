package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.api.back.ExistExam_Back;
import com.dy.huibiao_f80.mvp.contract.HomeContract;
import com.dy.huibiao_f80.mvp.ui.activity.ExamActivity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
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

    public void setTime() {
        mModel.getTime().compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<String>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull String s) {
                      mRootView.setDataTime(s);
                    }
                });
    }

    public void existExam(String url,String devicenum) {
        mModel.existExam(url,devicenum).subscribeOn(Schedulers.io())
                //.retryWhen(new RetryWithDelay(2, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<ExistExam_Back>(mErrorHandler) {
                    @Override
                    public void onNext(ExistExam_Back back) {
                        LogUtils.d(back);
                        if (back.getSuccess()){
                            ExistExam_Back.EntityBean entity = back.getEntity();
                            if (null==entity){
                                ArmsUtils.snackbarText("existExam 未按照协议返回信息，请联系考培云系统");
                                return;
                            }
                            ExistExam_Back.EntityBean.ExaminationBean examination = entity.getExamination();
                            if (null==entity){
                                ArmsUtils.snackbarText("existExam 未按照协议返回信息，请联系考培云系统");
                                return;
                            }
                            Intent content = new Intent(mRootView.getActivity(), ExamActivity.class);
                            content.putExtra("id", examination.getId());
                            content.putExtra("personTestMethod", examination.getPersonTestMethod());
                            ArmsUtils.startActivity(content);
                        }else {
                            ArmsUtils.snackbarText(back.getMessage());
                        }
                    }
                });
    }
}