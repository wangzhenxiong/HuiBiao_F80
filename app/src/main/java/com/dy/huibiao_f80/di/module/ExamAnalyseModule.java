package com.dy.huibiao_f80.di.module;

import android.app.AlertDialog;

import com.dy.huibiao_f80.mvp.contract.ExamAnalyseContract;
import com.dy.huibiao_f80.mvp.model.ExamAnalyseModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dmax.dialog.SpotsDialog;

@Module
public abstract class ExamAnalyseModule {

    @Binds
    abstract ExamAnalyseContract.Model bindExamAnalyseModel(ExamAnalyseModel model);

    @ActivityScope
    @Provides
    static AlertDialog getSportDialog(ExamAnalyseContract.View view) {
        return new SpotsDialog.Builder().setContext(view.getActivity()).setCancelable(true).build();
    }
}