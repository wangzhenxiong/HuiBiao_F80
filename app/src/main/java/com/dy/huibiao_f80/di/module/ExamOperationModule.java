package com.dy.huibiao_f80.di.module;

import android.app.AlertDialog;

import com.dy.huibiao_f80.mvp.contract.ExamOperationContract;
import com.dy.huibiao_f80.mvp.model.ExamOperationModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dmax.dialog.SpotsDialog;

@Module
public abstract class ExamOperationModule {

    @Binds
    abstract ExamOperationContract.Model bindExamOperationModel(ExamOperationModel model);
    @ActivityScope
    @Provides
    static AlertDialog getSportDialog(ExamOperationContract.View view) {
        return new SpotsDialog.Builder().setContext(view.getActivity()).setCancelable(true).build();
    }
}