package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.mvp.contract.VideoContract;
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
public class VideoPresenter extends BasePresenter<VideoContract.Model, VideoContract.View> {
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
    private String path;
    private int preEndIndex;

    @Inject
    public VideoPresenter(VideoContract.Model model, VideoContract.View rootView) {
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
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/Video";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("???????????????????????????");
                }
                break;
            case 1:

                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/FengGuangVideo";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("???????????????????????????");
                }

                break;
            case 2:
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/NongCanVideo";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("???????????????????????????");
                }
                break;
            case 3:
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/JiaoTiJinVideo";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("???????????????????????????");
                }
                break;
            case 4:
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/ZhongJinShuVideo";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("???????????????????????????");
                }
                break;
            case 5:
                path = ArmsUtils.getString(mRootView.getActivity(), R.string.app_localdata_address) + "/MianYiYingGuangVideo";
                if (FileUtils.isFileExists(path)) {
                    requestfile(true);
                } else {
                    mRootView.killMyself();
                    ArmsUtils.snackbarText("???????????????????????????");
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