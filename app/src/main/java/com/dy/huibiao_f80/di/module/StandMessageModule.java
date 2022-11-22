package com.dy.huibiao_f80.di.module;

import android.support.v4.app.FragmentActivity;

import com.dy.huibiao_f80.bean.base.BaseStandard_LawsMessage;
import com.dy.huibiao_f80.mvp.contract.StandMessageContract;
import com.dy.huibiao_f80.mvp.model.StandMessageModel;
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
public abstract class StandMessageModule {

    @Binds
    abstract StandMessageContract.Model bindStandMessageModel(StandMessageModel model);
    @ActivityScope
    @Provides
    static RxPermissions providesrxpermissions(StandMessageContract.View view){
        return new RxPermissions((FragmentActivity) view.getActivity());
    }
    @ActivityScope
    @Provides
    static List<File> provideUserList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static LocalFileAdapter provideUserAdapter(StandMessageContract.View view, List<File> list){
        return new LocalFileAdapter(list,view.getActivity(),"standard");
    }

    /*@ActivityScope
    @Provides
    static Standard_YQWLWDao provideStandard_YQWLWDao(StandMessageContract.View view){
        return DBHelper.getStandard_YQWLWDao(view.getActivity());
    }
    @ActivityScope
    @Provides
    static Standard_KJFWDao provideStandard_KJFWDao(StandMessageContract.View view){
        return DBHelper.getStandard_KJFWDao(view.getActivity());
    }*/
    @ActivityScope
    @Provides
    static List<BaseStandard_LawsMessage> provideStandardList(){
        return new ArrayList<>();
    }
   /* @ActivityScope
    @Provides
    static Standard_LawsAdapter provideStandardAdapter(StandMessageContract.View view, List<BaseStandard_LawsMessage>list){
        return new Standard_LawsAdapter(R.layout.standard_item_layout,list,view.getActivity());
    }*/
}