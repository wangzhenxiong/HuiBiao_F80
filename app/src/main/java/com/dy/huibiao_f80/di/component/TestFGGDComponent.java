package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.TestFGGDModule;
import com.dy.huibiao_f80.mvp.contract.TestFGGDContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.TestFGGDActivity;;

@ActivityScope
@Component(modules = TestFGGDModule.class, dependencies = AppComponent.class)
public interface TestFGGDComponent {
    void inject(TestFGGDActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TestFGGDComponent.Builder view(TestFGGDContract.View view);

        TestFGGDComponent.Builder appComponent(AppComponent appComponent);

        TestFGGDComponent build();
    }
}