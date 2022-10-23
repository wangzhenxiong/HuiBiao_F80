package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.RecordDetailModule;
import com.dy.huibiao_f80.mvp.contract.RecordDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.RecordDetailActivity;;

@ActivityScope
@Component(modules = RecordDetailModule.class, dependencies = AppComponent.class)
public interface RecordDetailComponent {
    void inject(RecordDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RecordDetailComponent.Builder view(RecordDetailContract.View view);

        RecordDetailComponent.Builder appComponent(AppComponent appComponent);

        RecordDetailComponent build();
    }
}