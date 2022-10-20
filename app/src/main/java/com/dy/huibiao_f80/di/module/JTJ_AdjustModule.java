package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.JTJ_AdjustContract;
import com.dy.huibiao_f80.mvp.model.JTJ_AdjustModel;

@Module
public abstract class JTJ_AdjustModule {

    @Binds
    abstract JTJ_AdjustContract.Model bindJTJ_AdjustModel(JTJ_AdjustModel model);
}