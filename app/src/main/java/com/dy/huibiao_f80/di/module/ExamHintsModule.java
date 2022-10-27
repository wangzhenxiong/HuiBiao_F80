package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.ExamHintsContract;
import com.dy.huibiao_f80.mvp.model.ExamHintsModel;

@Module
public abstract class ExamHintsModule {

    @Binds
    abstract ExamHintsContract.Model bindExamHintsModel(ExamHintsModel model);
}