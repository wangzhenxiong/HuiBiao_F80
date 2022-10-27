package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.ExamHintsModule;
import com.dy.huibiao_f80.mvp.contract.ExamHintsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.ExamHintsActivity;;

@ActivityScope
@Component(modules = ExamHintsModule.class, dependencies = AppComponent.class)
public interface ExamHintsComponent {
    void inject(ExamHintsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExamHintsComponent.Builder view(ExamHintsContract.View view);

        ExamHintsComponent.Builder appComponent(AppComponent appComponent);

        ExamHintsComponent build();
    }
}