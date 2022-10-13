package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.SettingLoginContract;
import com.dy.huibiao_f80.mvp.model.SettingLoginModel;

@Module
public abstract class SettingLoginModule {

    @Binds
    abstract SettingLoginContract.Model bindSettingLoginModel(SettingLoginModel model);
}