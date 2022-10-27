package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.ExamAnalyseModule;
import com.dy.huibiao_f80.mvp.contract.ExamAnalyseContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.ExamAnalyseActivity;;

@ActivityScope
@Component(modules = ExamAnalyseModule.class, dependencies = AppComponent.class)
public interface ExamAnalyseComponent {
    void inject(ExamAnalyseActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExamAnalyseComponent.Builder view(ExamAnalyseContract.View view);

        ExamAnalyseComponent.Builder appComponent(AppComponent appComponent);

        ExamAnalyseComponent build();
    }
}