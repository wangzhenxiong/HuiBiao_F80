package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.ExamStateModule;
import com.dy.huibiao_f80.mvp.contract.ExamStateContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.ExamStateActivity;;

@ActivityScope
@Component(modules = ExamStateModule.class, dependencies = AppComponent.class)
public interface ExamStateComponent {
    void inject(ExamStateActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExamStateComponent.Builder view(ExamStateContract.View view);

        ExamStateComponent.Builder appComponent(AppComponent appComponent);

        ExamStateComponent build();
    }
}