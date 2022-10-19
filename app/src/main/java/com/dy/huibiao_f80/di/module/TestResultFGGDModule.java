package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.TestResultFGGDContract;
import com.dy.huibiao_f80.mvp.model.TestResultFGGDModel;

@Module
public abstract class TestResultFGGDModule {

    @Binds
    abstract TestResultFGGDContract.Model bindTestResultFGGDModel(TestResultFGGDModel model);
}