package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.TestJTJContract;
import com.dy.huibiao_f80.mvp.model.TestJTJModel;

@Module
public abstract class TestJTJModule {

    @Binds
    abstract TestJTJContract.Model bindTestJTJModel(TestJTJModel model);
}