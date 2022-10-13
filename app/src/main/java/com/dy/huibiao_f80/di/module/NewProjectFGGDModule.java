package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.NewProjectFGGDContract;
import com.dy.huibiao_f80.mvp.model.NewProjectFGGDModel;

@Module
public abstract class NewProjectFGGDModule {

    @Binds
    abstract NewProjectFGGDContract.Model bindNewProjectFGGDModel(NewProjectFGGDModel model);
}