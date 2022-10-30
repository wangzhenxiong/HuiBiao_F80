package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.app.utils.JxlUtils;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.mvp.contract.EdtorProjectContract;
import com.dy.huibiao_f80.mvp.ui.widget.OutMoudle;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinComparator;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.adapter.SortAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
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
    List<BaseProjectMessage> mDateList;
    @Inject
    SortAdapter mAdapter;
    @Inject
    PinyinComparator mComparator;

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


    public void getJTJProject(String keyword) {
        mModel.getJTJProject(keyword).doOnSubscribe(disposable -> {
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


    public void inputJTJProject(List<String> path) {
        JxlUtils.inToXlsJtj(path)
                .doOnSubscribe(disposable -> {
                    mRootView.showSportDialog("正在导入数据");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideSportDialog();
                    mRootView.RefreshList();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<String>>(mErrorHandler) {
                    @Override
                    public void onNext(List<String> strings) {
                        String s = "";
                        for (int i = 0; i < strings.size(); i++) {
                            s = s + strings.get(i) + "\r\n";
                        }
                        mRootView.hideSportDialog();
                        ArmsUtils.snackbarText(s);
                    }
                });
    }

    public void inputFGGDProject(List<String> path) {
        JxlUtils.inToXlsFggd(path)
                .doOnSubscribe(disposable -> {
                    mRootView.showSportDialog("正在导入数据");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideSportDialog();
                    mRootView.RefreshList();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<String>>(mErrorHandler) {
                    @Override
                    public void onNext(List<String> strings) {
                        String s = "";
                        for (int i = 0; i < strings.size(); i++) {
                            s = s + strings.get(i) + "\r\n";
                        }
                        mRootView.hideSportDialog();
                        ArmsUtils.snackbarText(s);
                    }

                });
    }

    public void outPutItem(String path, String filename, String sheetname, int i) {

        if (i == 3) { //胶体金
            mModel.getJTJJXLs()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> {
                        mRootView.showSportDialog("正在准备数据");
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> {
                        mRootView.hideSportDialog();
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<List<OutMoudle>>(mErrorHandler) {
                        @Override
                        public void onNext(List<OutMoudle> moudles) {
                            if (moudles.size() <= 1) {
                                mRootView.showSportDialog("没有需要导出的数据");
                            } else {
                                out(path, filename, sheetname, moudles);
                            }
                        }

                    });
        } else if (i == 4) { //分光
            mModel.getFGGDJXLs()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> {
                        mRootView.showSportDialog("正在准备数据");
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> {
                        mRootView.hideSportDialog();
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<List<OutMoudle>>(mErrorHandler) {
                        @Override
                        public void onNext(List<OutMoudle> moudles) {
                            if (moudles.size() <= 1) {
                                ArmsUtils.snackbarText("没有需要导出的数据");
                            }
                            out(path, filename, sheetname, moudles);
                        }

                    });
        }
    }

    private void out(String path, String filename, String sheetname, List<OutMoudle> moudles) {
        JxlUtils.outTo_xls_1Sheet(path, filename, sheetname, moudles).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showMessage("正在导出数据");
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideSportDialog();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        mRootView.hideLoading();
                        //mRootView.RefreshList();

                    }

                });
    }
}