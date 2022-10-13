package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.ExamContract;
import com.dy.huibiao_f80.mvp.model.ExamModel;

@Module
public abstract class ExamModule {

    @Binds
    abstract ExamContract.Model bindExamModel(ExamModel model);
}