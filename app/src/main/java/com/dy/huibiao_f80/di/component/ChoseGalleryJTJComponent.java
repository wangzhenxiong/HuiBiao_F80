package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.ChoseGalleryJTJModule;
import com.dy.huibiao_f80.mvp.contract.ChoseGalleryJTJContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.ChoseGalleryJTJActivity;;

@ActivityScope
@Component(modules = ChoseGalleryJTJModule.class, dependencies = AppComponent.class)
public interface ChoseGalleryJTJComponent {
    void inject(ChoseGalleryJTJActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChoseGalleryJTJComponent.Builder view(ChoseGalleryJTJContract.View view);

        ChoseGalleryJTJComponent.Builder appComponent(AppComponent appComponent);

        ChoseGalleryJTJComponent build();
    }
}