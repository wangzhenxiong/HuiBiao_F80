package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.ExamOperationModule;
import com.dy.huibiao_f80.mvp.contract.ExamOperationContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.ExamOperationActivity;;

@ActivityScope
@Component(modules = ExamOperationModule.class, dependencies = AppComponent.class)
public interface ExamOperationComponent {
    void inject(ExamOperationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExamOperationComponent.Builder view(ExamOperationContract.View view);

        ExamOperationComponent.Builder appComponent(AppComponent appComponent);

        ExamOperationComponent build();
    }
}