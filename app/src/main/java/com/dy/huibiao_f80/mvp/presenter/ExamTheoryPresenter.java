package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.api.back.BeginTheoryExam_Back;
import com.dy.huibiao_f80.api.back.TheorySubmit_Back;
import com.dy.huibiao_f80.app.utils.NetworkUtils;
import com.dy.huibiao_f80.bean.eventBusBean.NetWorkState;
import com.dy.huibiao_f80.mvp.contract.ExamTheoryContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class ExamTheoryPresenter extends BasePresenter<ExamTheoryContract.Model, ExamTheoryContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private MaterialDialog mWaiteDialog;
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    @Inject
    public ExamTheoryPresenter(ExamTheoryContract.Model model, ExamTheoryContract.View rootView) {
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

    public void beginTheoryExam(String examinationId) {
        mModel.beginTheoryExam(Constants.URL, examinationId)
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BeginTheoryExam_Back>(mErrorHandler) {
                    @Override
                    public void onNext(BeginTheoryExam_Back back) {
                        LogUtils.d(back);
                        if (back.getSuccess()) {
                            mRootView.showExamTitle(back);
                        } else {
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
              submit(examinationId,examinerId,beginTheoryExamBack,false);
          }
        }
    }

    private void makeDialog() {
        mRootView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("??????");
                if (mWaiteDialog == null) {
                    mWaiteDialog = new MaterialDialog.Builder(mRootView.getActivity())
                            .cancelable(false)
                            .canceledOnTouchOutside(false)
                            .positiveText("??????")
                            .neutralText("??????")
                            .contentGravity(GravityEnum.CENTER)
                            .build();
                }
                mWaiteDialog.setContent("??????????????????????????????????????????");
                mWaiteDialog.getActionButton(DialogAction.POSITIVE).setVisibility(View.GONE);
                mWaiteDialog.getActionButton(DialogAction.NEUTRAL).setVisibility(View.GONE);
                mWaiteDialog.show();
            }
        });


    }
    private String examinationId;
    private String examinerId;
    private BeginTheoryExam_Back beginTheoryExamBack;
    public void submit(String examinationId, String examinerId, BeginTheoryExam_Back beginTheoryExamBack, boolean userSubmit) {
        this.examinationId=examinationId;
        this.examinerId=examinerId;
        this.beginTheoryExamBack=beginTheoryExamBack;
        //???????????? ???????????? ??? ????????????
        if (userSubmit) {
            //??????????????????????????????
            if (!NetworkUtils.getNetworkType()) {
                ArmsUtils.snackbarText("??????????????????????????????????????????");
                return;
            }
        } else {
            //???????????????????????????????????????????????????????????????????????????
            if (!NetworkUtils.getNetworkType()) {
                makeDialog();
                return;
            }




        }


        LogUtils.d(beginTheoryExamBack);
        mModel.submit(examinationId, examinerId, beginTheoryExamBack, Constants.URL)
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<TheorySubmit_Back>(mErrorHandler) {
                    @Override
                    public void onNext(TheorySubmit_Back back) {
                        LogUtils.d(back);
                        if (back.getSuccess()) {
                            mRootView.submitSuccess();
                        }

                        ArmsUtils.snackbarText(back.getMessage());

                    }
                });
    }
}