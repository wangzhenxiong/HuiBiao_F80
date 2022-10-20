package com.dy.huibiao_f80.di.module;

import android.app.AlertDialog;

import com.dy.huibiao_f80.mvp.contract.ChoseGalleryJTJContract;
import com.dy.huibiao_f80.mvp.model.ChoseGalleryJTJModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dmax.dialog.SpotsDialog;

@Module
public abstract class ChoseGalleryJTJModule {

    @Binds
    abstract ChoseGalleryJTJContract.Model bindChoseGalleryJTJModel(ChoseGalleryJTJModel model);
    @ActivityScope
    @Provides
    static AlertDialog getSportDialog(ChoseGalleryJTJContract.View view) {
        return new SpotsDialog.Builder().setContext(view.getActivity()).setCancelable(true).build();
    }
}