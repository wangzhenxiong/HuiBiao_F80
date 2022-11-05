package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.AutoCurveModule;
import com.dy.huibiao_f80.mvp.contract.AutoCurveContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.AutoCurveActivity;;

@ActivityScope
@Component(modules = AutoCurveModule.class, dependencies = AppComponent.class)
public interface AutoCurveComponent {
    void inject(AutoCurveActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AutoCurveComponent.Builder view(AutoCurveContract.View view);

        AutoCurveComponent.Builder appComponent(AppComponent appComponent);

        AutoCurveComponent build();
    }
}