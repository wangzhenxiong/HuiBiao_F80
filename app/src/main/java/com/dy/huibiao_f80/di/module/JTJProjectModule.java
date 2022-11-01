package com.dy.huibiao_f80.di.module;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.mvp.contract.JTJProjectContract;
import com.dy.huibiao_f80.mvp.model.JTJProjectModel;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinComparator;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.adapter.SortAdapter;
import com.jess.arms.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class JTJProjectModule {

    @Binds
    abstract JTJProjectContract.Model bindJTJProjectModel(JTJProjectModel model);
    @FragmentScope
    @Provides
    static PinyinComparator pinyinComparator(){
        return  new PinyinComparator();
    }
    @FragmentScope
    @Provides
    static List<BaseProjectMessage> projectMessageListfggd(){
        return new ArrayList<>();
    }
    @FragmentScope
    @Provides
    static SortAdapter contactAdapterfggd(List< BaseProjectMessage> list){
        return new SortAdapter(R.layout.project_item_layout,list);
    }
}