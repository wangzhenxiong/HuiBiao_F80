package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.ExamTheoryModule;
import com.dy.huibiao_f80.mvp.contract.ExamTheoryContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.ExamTheoryActivity;;

@ActivityScope
@Component(modules = ExamTheoryModule.class, dependencies = AppComponent.class)
public interface ExamTheoryComponent {
    void inject(ExamTheoryActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExamTheoryComponent.Builder view(ExamTheoryContract.View view);

        ExamTheoryComponent.Builder appComponent(AppComponent appComponent);

        ExamTheoryComponent build();
    }
}