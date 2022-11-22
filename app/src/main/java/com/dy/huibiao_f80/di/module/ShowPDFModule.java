package com.dy.huibiao_f80.di.module;

import android.app.AlertDialog;

import com.dy.huibiao_f80.mvp.contract.ShowPDFContract;
import com.dy.huibiao_f80.mvp.model.ShowPDFModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dmax.dialog.SpotsDialog;

@Module
public abstract class ShowPDFModule {

    @Binds
    abstract ShowPDFContract.Model bindShowPDFModel(ShowPDFModel model);
    @ActivityScope
    @Provides
    static AlertDialog getSportDialog(ShowPDFContract.View view) {
        return new SpotsDialog.Builder().setContext(view.getActivity()).setCancelable(true).build();
    }
}