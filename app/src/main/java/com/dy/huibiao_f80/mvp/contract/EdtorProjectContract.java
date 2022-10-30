package com.dy.huibiao_f80.mvp.contract;

import android.app.Activity;

import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.mvp.ui.widget.OutMoudle;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import io.reactivex.Observable;

public interface EdtorProjectContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        void showSportDialog(String s);

        void hideSportDialog();

        void RefreshList();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<List<?extends BaseProjectMessage>> getFGGDProject(String keyword);

        Observable<List<?extends BaseProjectMessage>> getJTJProject(String keyword);

        Observable<List<OutMoudle>> getJTJJXLs();

        Observable<List<OutMoudle>> getFGGDJXLs();


    }
}