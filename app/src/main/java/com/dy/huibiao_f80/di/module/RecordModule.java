package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.RecordContract;
import com.dy.huibiao_f80.mvp.model.RecordModel;

@Module
public abstract class RecordModule {

    @Binds
    abstract RecordContract.Model bindRecordModel(RecordModel model);
}