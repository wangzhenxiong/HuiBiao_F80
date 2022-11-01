package com.dy.huibiao_f80.di.module;

import android.app.AlertDialog;

import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.mvp.contract.EdtorProjectContract;
import com.dy.huibiao_f80.mvp.model.EdtorProjectModel;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinComparator;
import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dmax.dialog.SpotsDialog;

@Module
public abstract class EdtorProjectModule {

    @Binds
    abstract EdtorProjectContract.Model bindEdtorProjectModel(EdtorProjectModel model);


    @ActivityScope
    @Provides
    static AlertDialog getSportDialog(EdtorProjectContract.View view) {
        return new SpotsDialog.Builder().setContext(view.getActivity()).setCancelable(true).build();
    }

    @ActivityScope
    @Named("jtj")
    @Provides
    static List<BaseProjectMessage> getSportDialog1() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Named("fggd")
    @Provides
    static List<BaseProjectMessage> getSportDialog2() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static PinyinComparator pinyinComparator(){
        return  new PinyinComparator();
    }
}
