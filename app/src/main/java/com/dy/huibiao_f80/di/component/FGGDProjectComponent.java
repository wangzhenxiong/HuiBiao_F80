package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.FGGDProjectModule;
import com.dy.huibiao_f80.mvp.contract.FGGDProjectContract;

import com.jess.arms.di.scope.FragmentScope;
import com.dy.huibiao_f80.mvp.ui.fragment.FGGDProjectFragment;;

@FragmentScope
@Component(modules = FGGDProjectModule.class, dependencies = AppComponent.class)
public interface FGGDProjectComponent {
    void inject(FGGDProjectFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FGGDProjectComponent.Builder view(FGGDProjectContract.View view);

        FGGDProjectComponent.Builder appComponent(AppComponent appComponent);

        FGGDProjectComponent build();
    }
}