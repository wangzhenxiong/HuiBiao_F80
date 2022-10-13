package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.SetingModule;
import com.dy.huibiao_f80.mvp.contract.SetingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.SetingActivity;;

@ActivityScope
@Component(modules = SetingModule.class, dependencies = AppComponent.class)
public interface SetingComponent {
    void inject(SetingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SetingComponent.Builder view(SetingContract.View view);

        SetingComponent.Builder appComponent(AppComponent appComponent);

        SetingComponent build();
    }
}