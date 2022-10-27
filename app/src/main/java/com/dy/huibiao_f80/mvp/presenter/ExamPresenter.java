package com.dy.huibiao_f80.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.api.back.CheckExaminer_Back;
import com.dy.huibiao_f80.mvp.contract.ExamContract;
import com.dy.huibiao_f80.mvp.ui.activity.ExamHintsActivity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class ExamPresenter extends BasePresenter<ExamContract.Model, ExamContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ExamPresenter(ExamContract.Model model, ExamContract.View rootView) {
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

    public void checkExaminer(String id, String name, String number, int personTestMethod) {
        mModel.checkExaminer(Constants.URL,id,name,number,personTestMethod)
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CheckExaminer_Back>(mErrorHandler) {
                    @Override
                    public void onNext(CheckExaminer_Back back) {
                        LogUtils.d(back);
                        if (back.getSuccess()){
                            Intent content = new Intent(mRootView.getActivity(), ExamHintsActivity.class);
                            CheckExaminer_Back.EntityBean.ExaminationBean examination = back.getEntity().getExamination();
                            if (examination==null){
                                ArmsUtils.snackbarText("checkExaminer 未按照协议返回信息，请联系考培云系统");
                                return;
                            }
                            content.putExtra("examinationId", examination.getId());
                            CheckExaminer_Back.EntityBean.ExaminerBean examiner = back.getEntity().getExaminer();
                            if (examiner==null){
                                ArmsUtils.snackbarText("checkExaminer 未按照协议返回信息，请联系考培云系统");
                                return;
                            }
                            content.putExtra("examinerId", examiner.getId());
                            content.putExtra("name",examiner.getName());
                            content.putExtra("cardnumber",examiner.getIdNumber());
                            content.putExtra("school",examination.getSchoolName());
                            content.putExtra("examname",examination.getName());
                            ArmsUtils.startActivity(content);
                        }else {
                            ArmsUtils.snackbarText(back.getMessage());
                        }
                    }
                });

    }
}