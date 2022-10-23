package com.dy.huibiao_f80.di.module;

import android.app.AlertDialog;

import com.dy.huibiao_f80.mvp.contract.TestResultJTJContract;
import com.dy.huibiao_f80.mvp.model.TestResultJTJModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dmax.dialog.SpotsDialog;

@Module
public abstract class TestResultJTJModule {

    @Binds
    abstract TestResultJTJContract.Model bindTestResultJTJModel(TestResultJTJModel model);
    @ActivityScope
    @Provides
    static AlertDialog getSportDialog(TestResultJTJContract.View view) {
        return new SpotsDialog.Builder().setContext(view.getActivity()).setCancelable(true).build();
    }
}