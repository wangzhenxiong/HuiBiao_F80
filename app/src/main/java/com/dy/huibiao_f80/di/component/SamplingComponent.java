package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.SamplingModule;
import com.dy.huibiao_f80.mvp.contract.SamplingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.SamplingActivity;;

@ActivityScope
@Component(modules = SamplingModule.class, dependencies = AppComponent.class)
public interface SamplingComponent {
    void inject(SamplingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SamplingComponent.Builder view(SamplingContract.View view);

        SamplingComponent.Builder appComponent(AppComponent appComponent);

        SamplingComponent build();
    }
}