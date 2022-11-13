package com.dy.huibiao_f80.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.dy.huibiao_f80.di.module.BaiduMapModule;
import com.dy.huibiao_f80.mvp.contract.BaiduMapContract;

import com.jess.arms.di.scope.ActivityScope;
import com.dy.huibiao_f80.mvp.ui.activity.BaiduMapActivity;;

@ActivityScope
@Component(modules = BaiduMapModule.class, dependencies = AppComponent.class)
public interface BaiduMapComponent {
    void inject(BaiduMapActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BaiduMapComponent.Builder view(BaiduMapContract.View view);

        BaiduMapComponent.Builder appComponent(AppComponent appComponent);

        BaiduMapComponent build();
    }
}