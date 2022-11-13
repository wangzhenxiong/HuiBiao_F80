package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.StandMessageModule;
import com.dy.huibiao_f80.mvp.contract.StandMessageContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.StandMessageActivity;;

@ActivityScope
@Component(modules = StandMessageModule.class, dependencies = AppComponent.class)
public interface StandMessageComponent {
    void inject(StandMessageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StandMessageComponent.Builder view(StandMessageContract.View view);

        StandMessageComponent.Builder appComponent(AppComponent appComponent);

        StandMessageComponent build();
    }
}