package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.AutoCurveContract;
import com.dy.huibiao_f80.mvp.model.AutoCurveModel;

@Module
public abstract class AutoCurveModule {

    @Binds
    abstract AutoCurveContract.Model bindAutoCurveModel(AutoCurveModel model);
}