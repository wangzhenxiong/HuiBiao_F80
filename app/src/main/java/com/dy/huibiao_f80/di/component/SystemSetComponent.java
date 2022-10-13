package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.SystemSetModule;
import com.dy.huibiao_f80.mvp.contract.SystemSetContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.SystemSetActivity;;

@ActivityScope
@Component(modules = SystemSetModule.class, dependencies = AppComponent.class)
public interface SystemSetComponent {
    void inject(SystemSetActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SystemSetComponent.Builder view(SystemSetContract.View view);

        SystemSetComponent.Builder appComponent(AppComponent appComponent);

        SystemSetComponent build();
    }
}