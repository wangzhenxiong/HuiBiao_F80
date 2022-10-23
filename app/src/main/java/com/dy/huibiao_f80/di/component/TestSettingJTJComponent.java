package com.dy.huibiao_f80.di.component;

import com.dy.huibiao_f80.di.module.TestSettingJTJModule;
import com.dy.huibiao_f80.mvp.contract.TestSettingJTJContract;
import com.dy.huibiao_f80.mvp.ui.activity.TestSettingJTJActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = TestSettingJTJModule.class, dependencies = AppComponent.class)
public interface TestSettingJTJComponent {
    void inject(TestSettingJTJActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TestSettingJTJComponent.Builder view(TestSettingJTJContract.View view);

        TestSettingJTJComponent.Builder appComponent(AppComponent appComponent);

        TestSettingJTJComponent build();
    }
}