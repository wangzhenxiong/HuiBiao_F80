package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.SystemSetContract;
import com.dy.huibiao_f80.mvp.model.SystemSetModel;

@Module
public abstract class SystemSetModule {

    @Binds
    abstract SystemSetContract.Model bindSystemSetModel(SystemSetModel model);
}