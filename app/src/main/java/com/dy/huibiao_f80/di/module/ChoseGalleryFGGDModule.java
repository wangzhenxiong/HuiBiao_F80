package com.dy.huibiao_f80.di.module;

import dagger.Binds;
import dagger.Module;

import com.dy.huibiao_f80.mvp.contract.ChoseGalleryFGGDContract;
import com.dy.huibiao_f80.mvp.model.ChoseGalleryFGGDModel;

@Module
public abstract class ChoseGalleryFGGDModule {

    @Binds
    abstract ChoseGalleryFGGDContract.Model bindChoseGalleryFGGDModel(ChoseGalleryFGGDModel model);
}