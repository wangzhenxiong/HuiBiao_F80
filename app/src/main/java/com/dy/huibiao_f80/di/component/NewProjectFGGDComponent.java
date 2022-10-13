package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.NewProjectFGGDModule;
import com.dy.huibiao_f80.mvp.contract.NewProjectFGGDContract;

import com.jess.arms.di.scope.FragmentScope;
import com.dy.huibiao_f80.mvp.ui.fragment.NewProjectFGGDFragment;;

@FragmentScope
@Component(modules = NewProjectFGGDModule.class, dependencies = AppComponent.class)
public interface NewProjectFGGDComponent {
    void inject(NewProjectFGGDFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NewProjectFGGDComponent.Builder view(NewProjectFGGDContract.View view);

        NewProjectFGGDComponent.Builder appComponent(AppComponent appComponent);

        NewProjectFGGDComponent build();
    }
}