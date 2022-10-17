package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.ChoseGalleryFGGDModule;
import com.dy.huibiao_f80.mvp.contract.ChoseGalleryFGGDContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.ChoseGalleryFGGDActivity;;

@ActivityScope
@Component(modules = ChoseGalleryFGGDModule.class, dependencies = AppComponent.class)
public interface ChoseGalleryFGGDComponent {
    void inject(ChoseGalleryFGGDActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChoseGalleryFGGDComponent.Builder view(ChoseGalleryFGGDContract.View view);

        ChoseGalleryFGGDComponent.Builder appComponent(AppComponent appComponent);

        ChoseGalleryFGGDComponent build();
    }
}