package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.JTJProjectModule;
import com.dy.huibiao_f80.mvp.contract.JTJProjectContract;

import com.jess.arms.di.scope.FragmentScope;
import com.dy.huibiao_f80.mvp.ui.fragment.JTJProjectFragment;;

@FragmentScope
@Component(modules = JTJProjectModule.class, dependencies = AppComponent.class)
public interface JTJProjectComponent {
    void inject(JTJProjectFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        JTJProjectComponent.Builder view(JTJProjectContract.View view);

        JTJProjectComponent.Builder appComponent(AppComponent appComponent);

        JTJProjectComponent build();
    }
}