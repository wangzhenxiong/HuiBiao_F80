package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.RecordDetailContract;
import com.dy.huibiao_f80.mvp.model.RecordDetailModel;

@Module
public abstract class RecordDetailModule {

    @Binds
    abstract RecordDetailContract.Model bindRecordDetailModel(RecordDetailModel model);
}