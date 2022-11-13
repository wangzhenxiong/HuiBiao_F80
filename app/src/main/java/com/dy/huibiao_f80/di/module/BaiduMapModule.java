package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.BaiduMapContract;
import com.dy.huibiao_f80.mvp.model.BaiduMapModel;

@Module
public abstract class BaiduMapModule {

    @Binds
    abstract BaiduMapContract.Model bindBaiduMapModel(BaiduMapModel model);
}