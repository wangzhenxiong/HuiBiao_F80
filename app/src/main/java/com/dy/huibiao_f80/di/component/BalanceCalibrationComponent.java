package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.BalanceCalibrationModule;
import com.dy.huibiao_f80.mvp.contract.BalanceCalibrationContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.BalanceCalibrationActivity;;

@ActivityScope
@Component(modules = BalanceCalibrationModule.class, dependencies = AppComponent.class)
public interface BalanceCalibrationComponent {
    void inject(BalanceCalibrationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BalanceCalibrationComponent.Builder view(BalanceCalibrationContract.View view);

        BalanceCalibrationComponent.Builder appComponent(AppComponent appComponent);

        BalanceCalibrationComponent build();
    }
}