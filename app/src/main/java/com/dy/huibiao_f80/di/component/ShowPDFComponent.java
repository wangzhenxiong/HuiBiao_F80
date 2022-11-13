package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.ShowPDFModule;
import com.dy.huibiao_f80.mvp.contract.ShowPDFContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.ShowPDFActivity;;

@ActivityScope
@Component(modules = ShowPDFModule.class, dependencies = AppComponent.class)
public interface ShowPDFComponent {
    void inject(ShowPDFActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ShowPDFComponent.Builder view(ShowPDFContract.View view);

        ShowPDFComponent.Builder appComponent(AppComponent appComponent);

        ShowPDFComponent build();
    }
}