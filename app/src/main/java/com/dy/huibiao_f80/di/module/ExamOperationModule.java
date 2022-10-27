package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.ExamOperationContract;
import com.dy.huibiao_f80.mvp.model.ExamOperationModel;

@Module
public abstract class ExamOperationModule {

    @Binds
    abstract ExamOperationContract.Model bindExamOperationModel(ExamOperationModel model);
}