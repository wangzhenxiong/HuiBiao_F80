package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.TestFGGDContract;
import com.dy.huibiao_f80.mvp.model.TestFGGDModel;

@Module
public abstract class TestFGGDModule {

    @Binds
    abstract TestFGGDContract.Model bindTestFGGDModel(TestFGGDModel model);
}