package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.ChoseGalleryJTJContract;
import com.dy.huibiao_f80.mvp.model.ChoseGalleryJTJModel;

@Module
public abstract class ChoseGalleryJTJModule {

    @Binds
    abstract ChoseGalleryJTJContract.Model bindChoseGalleryJTJModel(ChoseGalleryJTJModel model);
}