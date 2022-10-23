package com.dy.huibiao_f80.di.module;

import android.app.AlertDialog;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.contract.RecordContract;
import com.dy.huibiao_f80.mvp.model.RecordModel;
import com.dy.huibiao_f80.mvp.ui.adapter.TestRecrdAdapter;
import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dmax.dialog.SpotsDialog;

@Module
public abstract class RecordModule {

    @Binds
    abstract RecordContract.Model bindRecordModel(RecordModel model);
    @ActivityScope
    @Provides
    static List<TestRecord> SamplingList(){
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static TestRecrdAdapter projectMessageList(List<TestRecord> list){
        return new TestRecrdAdapter(R.layout.record_item_layou, list);
    }
    @ActivityScope
    @Provides
    static AlertDialog getSportDialog(RecordContract.View view) {
        return new SpotsDialog.Builder().setContext(view.getActivity()).setCancelable(true).build();
    }
}