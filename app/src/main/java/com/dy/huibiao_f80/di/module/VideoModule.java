package com.dy.huibiao_f80.di.module;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dy.huibiao_f80.mvp.contract.VideoContract;
import com.dy.huibiao_f80.mvp.model.VideoModel;
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
public abstract class VideoModule {

    @Binds
    abstract VideoContract.Model bindVideoModel(VideoModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(VideoContract.View view) {
        return new GridLayoutManager(view.getActivity(), 6);
    }
    @ActivityScope
    @Provides
    static RxPermissions providesrxpermissions(VideoContract.View view){
        return new RxPermissions((FragmentActivity) view.getActivity());
    }
    @ActivityScope
    @Provides
    static List<File> provideUserList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static LocalFileAdapter provideUserAdapter(List<File> list, VideoContract.View view){
        return new LocalFileAdapter(list,view.getActivity(),"video");
    }
}