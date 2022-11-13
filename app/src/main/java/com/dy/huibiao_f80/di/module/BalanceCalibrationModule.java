package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.BalanceCalibrationContract;
import com.dy.huibiao_f80.mvp.model.BalanceCalibrationModel;

@Module
public abstract class BalanceCalibrationModule {

    @Binds
    abstract BalanceCalibrationContract.Model bindBalanceCalibrationModel(BalanceCalibrationModel model);
}