package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.TestJTJModule;
import com.dy.huibiao_f80.mvp.contract.TestJTJContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.TestJTJActivity;;

@ActivityScope
@Component(modules = TestJTJModule.class, dependencies = AppComponent.class)
public interface TestJTJComponent {
    void inject(TestJTJActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TestJTJComponent.Builder view(TestJTJContract.View view);

        TestJTJComponent.Builder appComponent(AppComponent appComponent);

        TestJTJComponent build();
    }
}