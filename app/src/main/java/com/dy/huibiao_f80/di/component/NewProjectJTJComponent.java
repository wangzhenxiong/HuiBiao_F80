package com.dy.huibiao_f80.di.component;

import com.dy.huibiao_f80.di.module.NewProjectJTJModule;
import com.dy.huibiao_f80.mvp.contract.NewProjectJTJContract;
import com.dy.huibiao_f80.mvp.ui.fragment.NewProjectJTJFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;

;

@FragmentScope
@Component(modules = NewProjectJTJModule.class, dependencies = AppComponent.class)
public interface NewProjectJTJComponent {
    void inject(NewProjectJTJFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NewProjectJTJComponent.Builder view(NewProjectJTJContract.View view);

        NewProjectJTJComponent.Builder appComponent(AppComponent appComponent);

        NewProjectJTJComponent build();
    }
}