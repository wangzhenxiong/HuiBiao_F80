package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.api.back.ExistExam_Back;
import com.dy.huibiao_f80.bean.UpdateMessage;
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
                            //ArmsUtils.snackbarText("请求错误");
                            return;
                        }
                        UpdateMessage.ResultBean result = message.getResult();
                        if (null != result) {
                            String appversion = result.getAppversion();
                            Integer integer = Integer.valueOf(appversion.replace(".", ""));
                            LogUtils.d(integer);
                            if (integer > BuildConfig.VERSION_CODE) {
                                //有新版本
                                mRootView.makeDialogNewVersion(message);

                                return;
                            }
                            // ArmsUtils.snackbarText("当前已是最新版本");
                            return;
                        }
                        //ArmsUtils.snackbarText("请稍后重试");
                    }
                });

    }

    public void downLoadAPK(String link) {
        //创建request对象
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link));
        //设置什么网络情况下可以下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏的标题
        request.setTitle("软件更新");
        //设置通知栏的message
        request.setDescription(BuildConfig.FLAVOR + "正在下载.....");
        //设置漫游状态下是否可以下载
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(mRootView.getActivity(), Environment.DIRECTORY_DOWNLOADS, "update" + BuildConfig.DEVICE_UPDATA_NAME + ".apk");
        //获取系统服务
        DownloadManager downloadManager = (DownloadManager) mRootView.getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        //进行下载
        long id = downloadManager.enqueue(request);
    }
}