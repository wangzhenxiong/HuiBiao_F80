package com.dy.huibiao_f80.di.module;

import android.app.AlertDialog;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.greendao.Sampling;
import com.dy.huibiao_f80.mvp.contract.SamplingContract;
import com.dy.huibiao_f80.mvp.model.SamplingModel;
import com.dy.huibiao_f80.mvp.ui.adapter.SamplingAdapter;
import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dmax.dialog.SpotsDialog;

@Module
public abstract class SamplingModule {

    @Binds
    abstract SamplingContract.Model bindSamplingModel(SamplingModel model);

    @ActivityScope
    @Provides
    static List<Sampling> SamplingList(){
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static SamplingAdapter projectMessageList(List<Sampling> list){
        return new SamplingAdapter(R.layout.layout_sampling_item, list);
    }

    @ActivityScope
    @Provides
    static AlertDialog getSportDialog(SamplingContract.View view) {
        return new SpotsDialog.Builder().setContext(view.getActivity()).setCancelable(true).build();
    }
}