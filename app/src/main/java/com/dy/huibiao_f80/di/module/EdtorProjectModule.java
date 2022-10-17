package com.dy.huibiao_f80.di.module;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.mvp.contract.EdtorProjectContract;
import com.dy.huibiao_f80.mvp.model.EdtorProjectModel;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinComparator;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.adapter.SortAdapter;
import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class EdtorProjectModule {

    @Binds
    abstract EdtorProjectContract.Model bindEdtorProjectModel(EdtorProjectModel model);

    @ActivityScope
    @Provides
    static List< BaseProjectMessage> projectMessageList(){
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static SortAdapter contactAdapter(List<BaseProjectMessage> list){
        return new SortAdapter(R.layout.project_item_layout,list);
    }
    @ActivityScope
    @Provides
    static PinyinComparator pinyinComparator(){
        return  new PinyinComparator();
    }
}