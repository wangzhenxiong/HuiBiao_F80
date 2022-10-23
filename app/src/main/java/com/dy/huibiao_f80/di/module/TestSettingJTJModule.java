package com.dy.huibiao_f80.di.module;

import com.dy.huibiao_f80.mvp.contract.TestSettingJTJContract;
import com.dy.huibiao_f80.mvp.model.TestSettingJTJModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TestSettingJTJModule {

    @Binds
    abstract TestSettingJTJContract.Model bindTestResultJTJModel(TestSettingJTJModel model);
}