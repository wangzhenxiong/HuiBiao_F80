package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.StartTestModule;
import com.dy.huibiao_f80.mvp.contract.StartTestContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.StartTestActivity;;

@ActivityScope
@Component(modules = StartTestModule.class, dependencies = AppComponent.class)
public interface StartTestComponent {
    void inject(StartTestActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StartTestComponent.Builder view(StartTestContract.View view);

        StartTestComponent.Builder appComponent(AppComponent appComponent);

        StartTestComponent build();
    }
}