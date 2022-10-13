package com.dy.huibiao_f80.di.component;

import com.dy.huibiao_f80.di.module.ExamModule;
import com.dy.huibiao_f80.mvp.contract.ExamContract;
import com.dy.huibiao_f80.mvp.ui.activity.ExamActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = ExamModule.class, dependencies = AppComponent.class)
public interface ExamComponent {
    void inject(ExamActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExamComponent.Builder view(ExamContract.View view);

        ExamComponent.Builder appComponent(AppComponent appComponent);

        ExamComponent build();
    }
}