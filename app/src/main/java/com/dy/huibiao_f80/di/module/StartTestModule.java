package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.StartTestContract;
import com.dy.huibiao_f80.mvp.model.StartTestModel;

@Module
public abstract class StartTestModule {

    @Binds
    abstract StartTestContract.Model bindStartTestModel(StartTestModel model);
}