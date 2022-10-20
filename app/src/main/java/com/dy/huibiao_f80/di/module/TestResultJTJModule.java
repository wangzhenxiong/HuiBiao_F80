package com.dy.huibiao_f80.di.module;

import com.dy.huibiao_f80.mvp.contract.TestResultJTJContract;
import com.dy.huibiao_f80.mvp.model.TestResultJTJModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TestResultJTJModule {

    @Binds
    abstract TestResultJTJContract.Model bindTestResultJTJModel(TestResultJTJModel model);
}