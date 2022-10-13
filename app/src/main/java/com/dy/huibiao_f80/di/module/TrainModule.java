package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.TrainContract;
import com.dy.huibiao_f80.mvp.model.TrainModel;

@Module
public abstract class TrainModule {

    @Binds
    abstract TrainContract.Model bindTrainModel(TrainModel model);
}