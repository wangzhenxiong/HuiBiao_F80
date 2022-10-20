package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.JTJ_AdjustModule;
import com.dy.huibiao_f80.mvp.contract.JTJ_AdjustContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.JTJ_AdjustActivity;;

@ActivityScope
@Component(modules = JTJ_AdjustModule.class, dependencies = AppComponent.class)
public interface JTJ_AdjustComponent {
    void inject(JTJ_AdjustActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        JTJ_AdjustComponent.Builder view(JTJ_AdjustContract.View view);

        JTJ_AdjustComponent.Builder appComponent(AppComponent appComponent);

        JTJ_AdjustComponent build();
    }
}