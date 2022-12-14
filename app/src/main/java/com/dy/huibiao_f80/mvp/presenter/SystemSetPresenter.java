package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.bean.UpdateMessage;
import com.dy.huibiao_f80.mvp.contract.SystemSetContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class SystemSetPresenter extends BasePresenter<SystemSetContract.Model, SystemSetContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SystemSetPresenter(SystemSetContract.Model model, SystemSetContract.View rootView) {
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

    public void checkNewVersion() {
        String url = BuildConfig.DEVICE_UPDATA_URL;
        LogUtils.d(url);

        mModel.checkNewVision(BuildConfig.DEVICE_UPDATA_NAME,url)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<UpdateMessage>(mErrorHandler) {
                    @Override
                    public void onNext(UpdateMessage message) {
                        mRootView.hideLoading();
                        LogUtils.d(message);
                        String code = message.getResultCode();
                        if (!"success1".equals(code)) {
                            //ArmsUtils.snackbarText("????????????");
                            return;
                        }
                        UpdateMessage.ResultBean result = message.getResult();
                        if (null != result) {
                            String appversion = result.getAppversion();
                            Integer integer = Integer.valueOf(appversion.replace(".", ""));
                            LogUtils.d(integer);
                            if (integer > BuildConfig.VERSION_CODE) {
                                //????????????
                                mRootView.makeDialogNewVersion(message);

                                return;
                            }
                             ArmsUtils.snackbarText("????????????????????????");
                            return;
                        }
                        ArmsUtils.snackbarText("???????????????");
                    }
                });
    }

    public void downLoadAPK(String link) {
        //??????request??????
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link));
        //???????????????????????????????????????
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //????????????????????????
        request.setTitle("????????????");
        //??????????????????message
        request.setDescription(BuildConfig.FLAVOR + "????????????.....");
        //???????????????????????????????????????
        request.setAllowedOverRoaming(false);
        //????????????????????????
        request.setDestinationInExternalFilesDir(mRootView.getActivity(), Environment.DIRECTORY_DOWNLOADS, "update" + BuildConfig.DEVICE_UPDATA_NAME + ".apk");
        //??????????????????
        DownloadManager downloadManager = (DownloadManager) mRootView.getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        //????????????
        long id = downloadManager.enqueue(request);
    }
}