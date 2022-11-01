package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.app.utils.JxlUtils;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.mvp.contract.EdtorProjectContract;
import com.dy.huibiao_f80.mvp.ui.widget.OutMoudle;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinComparator;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
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
    PinyinComparator mComparator;
    @Named("fggd")
    @Inject
    List<BaseProjectMessage> mDateList_fggd;
    @Named("jtj")
    @Inject
    List<BaseProjectMessage> mDateList_jtj;

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

   /* public void getFGGDProject(String keyword) {
        LogUtils.d("开始加载");
        mModel.getFGGDProject(keyword)
                .map(new Function<List<ProjectFGGD>, List<ProjectFGGD>>() {
                    @Override
                    public List<ProjectFGGD> apply(@NonNull List<ProjectFGGD> list) throws Exception {
                        Collections.sort(list, mComparator);
                        return list;
                    }
                }).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<ProjectFGGD>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<ProjectFGGD> list) {
                        LogUtils.d("排序完成");
                        mDateList_fggd.clear();
                        mDateList_fggd.addAll(list);
                        mAdapter_fggd.notifyDataSetChanged();


                    }
                });
    }


    public void getJTJProject(String keyword) {
        LogUtils.d("开始加载");
        mModel.getJTJProject(keyword)
                .map(new Function<List<ProjectJTJ>, List<ProjectJTJ>>() {
                    @Override
                    public List<ProjectJTJ> apply(@NonNull List<ProjectJTJ> list) throws Exception {
                        Collections.sort(list, mComparator);
                        return list;
                    }
                }).doOnSubscribe(disposable -> {
            mRootView.showLoading();
        }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<ProjectJTJ>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull List<ProjectJTJ> list) {
                        LogUtils.d("排序完成");
                        //LogUtils.d(list);
                        mDateList_jtj.clear();
                        mDateList_jtj.addAll(list);
                        mAdapter_jtj.notifyDataSetChanged();


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


    private void loadFGGD(String keyword) {
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