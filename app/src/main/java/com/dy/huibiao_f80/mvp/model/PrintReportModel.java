package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.api.back.BeginTestForm_Back;
import com.dy.huibiao_f80.mvp.contract.PrintReportContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

@ActivityScope
public class PrintReportModel extends BaseModel implements PrintReportContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PrintReportModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BeginTestForm_Back> beginTestForm(String url, String examinationId) {
        RetrofitUrlManager.getInstance().putDomain("xxx", url);
        return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                .beginTestForm(examinationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}