package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.mvp.contract.StartTestContract;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinComparator;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.adapter.SortAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class StartTestPresenter extends BasePresenter<StartTestContract.Model, StartTestContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    List<BaseProjectMessage> mDateList;
    @Inject
    SortAdapter mAdapter;
    @Inject
    PinyinComparator mComparator;
    @Inject
    public StartTestPresenter(StartTestContract.Model model, StartTestContract.View rootView) {
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

    public void getJTJProject(String keyword) {
        mModel.getJTJProject(keyword).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<? extends BaseProjectMessage>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<? extends BaseProjectMessage> list) {


                        Collections.sort(list, mComparator);
                        LogUtils.d(list);
                        mDateList.clear();
                        mDateList.addAll(list);
                        mAdapter.notifyDataSetChanged();


                    }
                });
    }

    public void getFGGDProject(String keyword) {
        mModel.getFGGDProject(keyword).doOnSubscribe(disposable -> {
            mRootView.showLoading();
        }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<? extends BaseProjectMessage>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<? extends BaseProjectMessage> list) {

                        Collections.sort(list, mComparator);
                        LogUtils.d(list);
                        mDateList.clear();
                        mDateList.addAll(list);
                        mAdapter.notifyDataSetChanged();


                    }
                });
    }
}