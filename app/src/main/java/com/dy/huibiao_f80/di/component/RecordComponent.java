package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.RecordModule;
import com.dy.huibiao_f80.mvp.contract.RecordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.RecordActivity;;

@ActivityScope
@Component(modules = RecordModule.class, dependencies = AppComponent.class)
public interface RecordComponent {
    void inject(RecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RecordComponent.Builder view(RecordContract.View view);

        RecordComponent.Builder appComponent(AppComponent appComponent);

        RecordComponent build();
    }
}