package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.PracticeContract;
import com.dy.huibiao_f80.mvp.model.PracticeModel;

@Module
public abstract class PracticeModule {

    @Binds
    abstract PracticeContract.Model bindPracticeModel(PracticeModel model);
}