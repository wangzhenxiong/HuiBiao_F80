package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.PracticeModule;
import com.dy.huibiao_f80.mvp.contract.PracticeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.PracticeActivity;;

@ActivityScope
@Component(modules = PracticeModule.class, dependencies = AppComponent.class)
public interface PracticeComponent {
    void inject(PracticeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PracticeComponent.Builder view(PracticeContract.View view);

        PracticeComponent.Builder appComponent(AppComponent appComponent);

        PracticeComponent build();
    }
}