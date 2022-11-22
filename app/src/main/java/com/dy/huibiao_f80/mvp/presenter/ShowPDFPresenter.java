package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.mvp.contract.ShowPDFContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.ResponseBody;

@ActivityScope
public class ShowPDFPresenter extends BasePresenter<ShowPDFContract.Model, ShowPDFContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ShowPDFPresenter(ShowPDFContract.Model model, ShowPDFContract.View rootView) {
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

    public void downLoadPDF(String type, String from) {
        if ("1".equals(type)){
            RetrofitUrlManager.getInstance().putDomain("xxx", BuildConfig.PDF_URL_LAWS);
        }else if ("2".equals(type)){
            RetrofitUrlManager.getInstance().putDomain("xxx", BuildConfig.PDF_URL_STANDARD);
        }

        mModel.downLoadPDF(type, from)
                .subscribeOn(Schedulers.io())
                //.retryWhen(new RetryWithDelay(2, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mRootView.killMyself();
                    }
                })
                .subscribe(new ErrorHandleSubscriber<ResponseBody>(mErrorHandler) {
                    @Override
                    public void onNext(ResponseBody body) {
                        LogUtils.d(body);
                        LogUtils.d(body.charStream()+"--");
                        //LogUtils.d(body.bytes()+"--");
                        try {
                            File futureStudioIconFile;
                            futureStudioIconFile = new File("/mnt/internal_sd/dayuan/stand_laws.pdf");
                            InputStream inputStream = null;
                            OutputStream outputStream = null;
                            byte[] fileReader = new byte[4096];

                            long fileSize = body.contentLength();
                            long fileSizeDownloaded = 0;

                            inputStream = body.byteStream();
                            try {
                                outputStream = new FileOutputStream(futureStudioIconFile);
                                while (true) {
                                    int read = inputStream.read(fileReader);

                                    if (read == -1) {
                                        break;
                                    }

                                    outputStream.write(fileReader, 0, read);

                                    fileSizeDownloaded += read;

                                    LogUtils.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                                }
                                outputStream.flush();
                                mRootView.success();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                ArmsUtils.snackbarText("");
                                mRootView.fail();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (inputStream != null) {
                                    inputStream.close();
                                }

                                if (outputStream != null) {
                                    outputStream.close();
                                }

                            }
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }

                    }
                });
    }
}