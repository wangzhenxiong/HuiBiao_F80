package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.ExamStateContract;
import com.dy.huibiao_f80.mvp.model.ExamStateModel;

@Module
public abstract class ExamStateModule {

    @Binds
    abstract ExamStateContract.Model bindExamStateModel(ExamStateModel model);
}