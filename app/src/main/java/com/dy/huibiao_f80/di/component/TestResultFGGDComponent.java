package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.TestResultFGGDModule;
import com.dy.huibiao_f80.mvp.contract.TestResultFGGDContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.TestResultFGGDActivity;;

@ActivityScope
@Component(modules = TestResultFGGDModule.class, dependencies = AppComponent.class)
public interface TestResultFGGDComponent {
    void inject(TestResultFGGDActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TestResultFGGDComponent.Builder view(TestResultFGGDContract.View view);

        TestResultFGGDComponent.Builder appComponent(AppComponent appComponent);

        TestResultFGGDComponent build();
    }
}