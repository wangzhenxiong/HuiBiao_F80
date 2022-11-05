package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.PrintReportModule;
import com.dy.huibiao_f80.mvp.contract.PrintReportContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.PrintReportActivity;;

@ActivityScope
@Component(modules = PrintReportModule.class, dependencies = AppComponent.class)
public interface PrintReportComponent {
    void inject(PrintReportActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrintReportComponent.Builder view(PrintReportContract.View view);

        PrintReportComponent.Builder appComponent(AppComponent appComponent);

        PrintReportComponent build();
    }
}