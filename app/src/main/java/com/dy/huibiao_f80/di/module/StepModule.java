package com.dy.huibiao_f80.di.module;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dy.huibiao_f80.mvp.contract.StepContract;
import com.dy.huibiao_f80.mvp.model.StepModel;
import com.dy.huibiao_f80.mvp.ui.adapter.LocalFileAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class StepModule {

    @Binds
    abstract StepContract.Model bindStepModel(StepModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(StepContract.View view) {
        return new GridLayoutManager(view.getActivity(), 6);
    }
    @ActivityScope
    @Provides
    static RxPermissions providesrxpermissions(StepContract.View view){
        return new RxPermissions((FragmentActivity) view.getActivity());
    }
    @ActivityScope
    @Provides
    static List<File> provideUserList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static LocalFileAdapter provideUserAdapter(List<File> list, StepContract.View view){
        return new LocalFileAdapter(list,view.getActivity(),"step");
    }
}