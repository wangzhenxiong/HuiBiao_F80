package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.ShowPDFContract;
import com.dy.huibiao_f80.mvp.model.ShowPDFModel;

@Module
public abstract class ShowPDFModule {

    @Binds
    abstract ShowPDFContract.Model bindShowPDFModel(ShowPDFModel model);
}