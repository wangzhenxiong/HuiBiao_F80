package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.SetingContract;
import com.dy.huibiao_f80.mvp.model.SetingModel;

@Module
public abstract class SetingModule {

    @Binds
    abstract SetingContract.Model bindSetingModel(SetingModel model);
}