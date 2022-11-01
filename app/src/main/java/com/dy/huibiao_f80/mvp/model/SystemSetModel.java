package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.bean.UpdateMessage;
import com.dy.huibiao_f80.mvp.contract.SystemSetContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

@ActivityScope
public class SystemSetModel extends BaseModel implements SystemSetContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SystemSetModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<UpdateMessage> checkNewVision(String deviceUpdataName, String url) {
        RetrofitUrlManager.getInstance().putDomain("upgradeVersion", url);
        return mRepositoryManager
                .obtainRetrofitService(HuiBiaoService.class)
                .checkNewVersion(deviceUpdataName)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());

    }
}