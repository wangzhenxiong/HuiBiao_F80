package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.PrintReportContract;
import com.dy.huibiao_f80.mvp.model.PrintReportModel;

@Module
public abstract class PrintReportModule {

    @Binds
    abstract PrintReportContract.Model bindPrintReportModel(PrintReportModel model);
}