package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;

import com.dy.huibiao_f80.greendao.Sampling;
import com.dy.huibiao_f80.greendao.daos.SamplingDao;
import com.dy.huibiao_f80.mvp.contract.SamplingContract;
import com.dy.huibiao_f80.mvp.ui.adapter.SamplingAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class SamplingPresenter extends BasePresenter<SamplingContract.Model, SamplingContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    SamplingAdapter samplingAdapter;
    @Inject
    List<Sampling> samplingList;
    private SamplingDao samplingDao;

    @Inject
    public SamplingPresenter(SamplingContract.Model model, SamplingContract.View rootView) {
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

    public void seach(String starttime, String stoptime, String testResult, boolean b) {

        mModel.seach(starttime,stoptime,testResult,b)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<Sampling>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<Sampling> list) {
                        samplingList.clear();
                        samplingList.addAll(list);
                        samplingAdapter.notifyDataSetChanged();
                    }
                });


    }


    public void load(boolean b) {
        mModel.load(b).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<Sampling>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<Sampling> list) {
                        samplingList.clear();
                        samplingList.addAll(list);
                        samplingAdapter.notifyDataSetChanged();
                    }
                });
    }
}