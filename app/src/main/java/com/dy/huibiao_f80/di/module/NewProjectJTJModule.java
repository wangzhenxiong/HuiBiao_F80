package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.NewProjectJTJContract;
import com.dy.huibiao_f80.mvp.model.NewProjectJTJModel;

@Module
public abstract class NewProjectJTJModule {

    @Binds
    abstract NewProjectJTJContract.Model bindNewProjectJTJModel(NewProjectJTJModel model);
}