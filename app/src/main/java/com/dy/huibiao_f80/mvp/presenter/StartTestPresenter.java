package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.mvp.contract.StartTestContract;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinComparator;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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
    @Named("fggd")
    @Inject
    List<BaseProjectMessage> mDateList_fggd;
    @Named("jtj")
    @Inject
    List<BaseProjectMessage> mDateList_jtj;
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

    /*public void getJTJProject(String keyword) {
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
                        *//*mDateList.clear();
                        mDateList.addAll(list);
                        mAdapter.notifyDataSetChanged();*//*


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
                       *//* mDateList.clear();
                        mDateList.addAll(list);
                        mAdapter.notifyDataSetChanged();*//*


                    }
                });
    }*/

    public void replaceFragment(FragmentManager manager, int containerViewId, Fragment fragment, String tag) {
        if (manager.findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            // fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            manager.popBackStack(tag, 0);
        }
    }

    public void loadFGGD(String keyword) {
        LogUtils.d("开始加载");
        mModel.getFGGDProject(keyword)
                .map(new Function<List<ProjectFGGD>, List<ProjectFGGD>>() {
                    @Override
                    public List<ProjectFGGD> apply(@NonNull List<ProjectFGGD> list) throws Exception {
                        Collections.sort(list, mComparator);
                        return list;
                    }
                }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<ProjectFGGD>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<ProjectFGGD> list) {
                        LogUtils.d("排序完成");
                        mDateList_fggd.clear();
                        mDateList_fggd.addAll(list);
                        //mAdapter.notifyDataSetChanged();
                        mRootView.loadFGGDFinish();
                        mRootView.hideLoading();
                    }
                });
    }

    public void loadData(String keyword) {
        LogUtils.d("开始加载");
        mModel.getJTJProject(keyword)
                .map(new Function<List<ProjectJTJ>, List<ProjectJTJ>>() {
                    @Override
                    public List<ProjectJTJ> apply(@NonNull List<ProjectJTJ> list) throws Exception {
                        Collections.sort(list, mComparator);
                        return list;
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<ProjectJTJ>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<ProjectJTJ> list) {
                        LogUtils.d("排序完成");
                        //LogUtils.d(list);
                        mDateList_jtj.clear();
                        mDateList_jtj.addAll(list);
                        //mAdapter.notifyDataSetChanged();
                        mRootView.loadJTJFinish();
                        loadFGGD(null);
                    }
                });
    }
}