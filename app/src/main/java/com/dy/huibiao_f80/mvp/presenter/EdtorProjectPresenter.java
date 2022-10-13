package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.mvp.contract.EdtorProjectContract;
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
public class EdtorProjectPresenter extends BasePresenter<EdtorProjectContract.Model, EdtorProjectContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    public EdtorProjectPresenter(EdtorProjectContract.Model model, EdtorProjectContract.View rootView) {
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

    public void getFGGDProject(String keyword) {
        mModel.getFGGDProject(keyword).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<? extends BaseProjectMessage>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<? extends BaseProjectMessage> list) {
                        LogUtils.d(list);


                    }
                });
    }



    public void getJTJProject(String keyword) {
        mModel.getJTJProject(keyword).doOnSubscribe(disposable -> {
            mRootView.showLoading();
        }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<? extends BaseProjectMessage>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<? extends BaseProjectMessage> list) {
                        LogUtils.d(list);


                    }
                });
    }

    public void replaceFragment(FragmentManager manager, int containerViewId, Fragment fragment, String tag) {
        if (manager.findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            //fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            manager.popBackStack(tag, 0);
        }
    }


}