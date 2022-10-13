package com.dy.huibiao_f80.di.component;

import com.dy.huibiao_f80.di.module.EdtorProjectModule;
import com.dy.huibiao_f80.mvp.contract.EdtorProjectContract;
import com.dy.huibiao_f80.mvp.ui.activity.EdtorProjectActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

;

@ActivityScope
@Component(modules = EdtorProjectModule.class, dependencies = AppComponent.class)
public interface EdtorProjectComponent {
    void inject(EdtorProjectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EdtorProjectComponent.Builder view(EdtorProjectContract.View view);

        EdtorProjectComponent.Builder appComponent(AppComponent appComponent);

        EdtorProjectComponent build();
    }
}