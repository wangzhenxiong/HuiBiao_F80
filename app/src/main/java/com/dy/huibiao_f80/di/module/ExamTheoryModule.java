package com.dy.huibiao_f80.di.module;

import android.app.AlertDialog;

import com.dy.huibiao_f80.mvp.contract.ExamTheoryContract;
import com.dy.huibiao_f80.mvp.model.ExamTheoryModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dmax.dialog.SpotsDialog;

@Module
public abstract class ExamTheoryModule {

    @Binds
    abstract ExamTheoryContract.Model bindExamTheoryModel(ExamTheoryModel model);

    @ActivityScope
    @Provides
    static AlertDialog getSportDialog(ExamTheoryContract.View view) {
        return new SpotsDialog.Builder().setContext(view.getActivity()).setCancelable(true).build();
    }
}