package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.TrainModule;
import com.dy.huibiao_f80.mvp.contract.TrainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.TrainActivity;;

@ActivityScope
@Component(modules = TrainModule.class, dependencies = AppComponent.class)
public interface TrainComponent {
    void inject(TrainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrainComponent.Builder view(TrainContract.View view);

        TrainComponent.Builder appComponent(AppComponent appComponent);

        TrainComponent build();
    }
}