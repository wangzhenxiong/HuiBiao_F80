package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.StepModule;
import com.dy.huibiao_f80.mvp.contract.StepContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.StepActivity;;

@ActivityScope
@Component(modules = StepModule.class, dependencies = AppComponent.class)
public interface StepComponent {
    void inject(StepActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StepComponent.Builder view(StepContract.View view);

        StepComponent.Builder appComponent(AppComponent appComponent);

        StepComponent build();
    }
}