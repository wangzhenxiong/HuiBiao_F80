package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.api.back.AnalyseSubmit_Back;
import com.dy.huibiao_f80.api.back.BeginAnalyseExam_Back;
import com.dy.huibiao_f80.app.utils.NetworkUtils;
import com.dy.huibiao_f80.bean.eventBusBean.NetWorkState;
import com.dy.huibiao_f80.mvp.contract.ExamAnalyseContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class ExamAnalysePresenter extends BasePresenter<ExamAnalyseContract.Model, ExamAnalyseContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private MaterialDialog mWaiteDialog;

    @Inject
    public ExamAnalysePresenter(ExamAnalyseContract.Model model, ExamAnalyseContract.View rootView) {
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

    public void beginAnalyseExam(String examinationId, String examinerId) {

        mModel.beginAnalyseExam(Constants.URL,examinationId,examinerId)
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BeginAnalyseExam_Back>(mErrorHandler) {
                    @Override
                    public void onNext(BeginAnalyseExam_Back back) {
                        LogUtils.d(back);
                        if (back.getSuccess()){
                            mRootView.showExamTitle(back);
                        }else {
                            ArmsUtils.snackbarText(back.getMessage());
                        }
                    }
                });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetWorkState tags) {
        if (tags.isLinkstate()) {
            if (mWaiteDialog!=null&&mWaiteDialog.isShowing()){
                mWaiteDialog.dismiss();
                submit(examinationId,examinerId,beginAnalyseExamBack,false);
            }
        }
    }

    private void makeDialog() {
        mRootView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mWaiteDialog == null) {
                    mWaiteDialog = new MaterialDialog.Builder(mRootView.getActivity())
                            .cancelable(false)
                            .canceledOnTouchOutside(false)
                            .positiveText("确定")
                            .neutralText("取消")
                            .contentGravity(GravityEnum.CENTER)
                            .build();
                }
                mWaiteDialog.setContent("网络连接失败，请检查网络连接");
                mWaiteDialog.getActionButton(DialogAction.POSITIVE).setVisibility(View.GONE);
                mWaiteDialog.getActionButton(DialogAction.NEUTRAL).setVisibility(View.GONE);
                mWaiteDialog.show();
            }
        });


    }

    private String examinationId;
    private String examinerId;
    private BeginAnalyseExam_Back beginAnalyseExamBack;

    public void submit(String examinationId, String examinerId, BeginAnalyseExam_Back beginAnalyseExamBack,boolean userSubmit) {
        this.examinationId=examinationId;
        this.examinerId=examinerId;
        this.beginAnalyseExamBack=beginAnalyseExamBack;
        //需要区分 手动提交 和 自动提交
        if (userSubmit) {
            //手动提交提示重试即可
            if (!NetworkUtils.getNetworkType()) {
                ArmsUtils.snackbarText("当前无网络连接，请检查后重试");
                return;
            }
        } else {
            //自动提交需要启动网络监控，在网络连接后再次自动提交
            if (!NetworkUtils.getNetworkType()) {
                makeDialog();
                return;
            }




        }
       mModel.analyseSubmit(Constants.URL,examinationId,examinerId,beginAnalyseExamBack)
               .doOnSubscribe(disposable -> {
                   mRootView.showLoading();
               }).subscribeOn(AndroidSchedulers.mainThread())
               .doFinally(() -> {
                   mRootView.hideLoading();
               }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
               .subscribe(new ErrorHandleSubscriber<AnalyseSubmit_Back>(mErrorHandler) {
                   @Override
                   public void onNext(AnalyseSubmit_Back back) {
                       LogUtils.d(back);
                       if (back.getSuccess()){
                           mRootView.submitSuccess();
                       }

                       ArmsUtils.snackbarText(back.getMessage());

                   }
               });
    }
}