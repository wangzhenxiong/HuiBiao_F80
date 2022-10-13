package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.SamplingContract;
import com.dy.huibiao_f80.mvp.model.SamplingModel;

@Module
public abstract class SamplingModule {

    @Binds
    abstract SamplingContract.Model bindSamplingModel(SamplingModel model);
}