package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.ExamAnalyseContract;
import com.dy.huibiao_f80.mvp.model.ExamAnalyseModel;

@Module
public abstract class ExamAnalyseModule {

    @Binds
    abstract ExamAnalyseContract.Model bindExamAnalyseModel(ExamAnalyseModel model);
}