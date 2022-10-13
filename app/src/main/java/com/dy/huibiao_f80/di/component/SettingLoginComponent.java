package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.SettingLoginModule;
import com.dy.huibiao_f80.mvp.contract.SettingLoginContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.SettingLoginActivity;;

@ActivityScope
@Component(modules = SettingLoginModule.class, dependencies = AppComponent.class)
public interface SettingLoginComponent {
    void inject(SettingLoginActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SettingLoginComponent.Builder view(SettingLoginContract.View view);

        SettingLoginComponent.Builder appComponent(AppComponent appComponent);

        SettingLoginComponent build();
    }
}