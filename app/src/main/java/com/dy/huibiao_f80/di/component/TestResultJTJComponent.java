package com.dy.huibiao_f80.di.component;

import com.dy.huibiao_f80.di.module.TestResultJTJModule;
import com.dy.huibiao_f80.mvp.contract.TestResultJTJContract;
import com.dy.huibiao_f80.mvp.ui.activity.TestResultJTJActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = TestResultJTJModule.class, dependencies = AppComponent.class)
public interface TestResultJTJComponent {
    void inject(TestResultJTJActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TestResultJTJComponent.Builder view(TestResultJTJContract.View view);

        TestResultJTJComponent.Builder appComponent(AppComponent appComponent);

        TestResultJTJComponent build();
    }
}