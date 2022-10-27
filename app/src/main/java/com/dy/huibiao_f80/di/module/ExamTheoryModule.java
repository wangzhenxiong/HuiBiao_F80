package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.ExamTheoryContract;
import com.dy.huibiao_f80.mvp.model.ExamTheoryModel;

@Module
public abstract class ExamTheoryModule {

    @Binds
    abstract ExamTheoryContract.Model bindExamTheoryModel(ExamTheoryModel model);
}