package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.mvp.contract.StepContract;
import com.dy.huibiao_f80.mvp.ui.adapter.LocalFileAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class StepPresenter extends BasePresenter<StepContract.Model, StepContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    List<File> mFiles;
    @Inject
    LocalFileAdapter mAdapter;

    private int lastUserId = 1;
    private boolean isFirst = true;
    private int preEndIndex;
    private String path;

    @Inject
    public StepPresenter(StepContract.Model model, StepContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        this.mFiles = null;
        this.mAdapter = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void oncreat() {
        int data = mRootView.getActivity().getIntent().getIntExtra("data", -1);
        switch (data) {
            case -1:
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/Step";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("?????????????????????????????????");
                }
                break;
            case 1:
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/FengGuangStep";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("?????????????????????????????????");
                }

                break;
            case 2:
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/NongCanStep";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("?????????????????????????????????");
                }
                break;
            case 3:
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/JiaoTiJinStep";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("?????????????????????????????????");
                }
                break;
            case 4:
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/ZhongJinShuStep";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("?????????????????????????????????");
                }
                break;
            case 5:
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/MianYiYingGuangStep";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("?????????????????????????????????");
                }
                break;
        }


    }

    public void requestfile(boolean b) {
        //????????????????????????????????????android6.0?????????????????????
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
                requestFromModel(b);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("????????????????????????");
                mRootView.hideLoading();//??????????????????????????????
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showMessage("???????????????????????????");
                mRootView.hideLoading();//??????????????????????????????
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }

    public void requestFromModel(boolean p) {


        mModel.getfiles(path)
                .subscribeOn(Schedulers.io())
                //.retryWhen(new RetryWithDelay(3, 2))//?????????????????????,??????????????????????????????,?????????????????????????????????
                .doOnSubscribe(disposable -> {
                    if (p) {
                        mRootView.showLoading();//??????????????????????????????
                    } else {
                        mRootView.startLoadMore();//????????????????????????????????????
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (p) {
                        mRootView.hideLoading();//??????????????????????????????
                    } else {
                        mRootView.endLoadMore();//????????????????????????????????????
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//?????? Rxlifecycle,??? Disposable ??? Activity ????????????
                .subscribe(new ErrorHandleSubscriber<List<File>>(mErrorHandler) {
                    @Override
                    public void onNext(List<File> files) {
                        // if (p)
                        mFiles.clear();//????????????????????????????????????
                        preEndIndex = mFiles.size();//???????????????????????????,???????????????????????????????????????
                        mFiles.addAll(files);
                        //if (p)
                        mAdapter.notifyDataSetChanged();
                        /*else
                            mAdapter.notifyItemRangeInserted(preEndIndex, files.size());*/
                    }


                });
    }
}