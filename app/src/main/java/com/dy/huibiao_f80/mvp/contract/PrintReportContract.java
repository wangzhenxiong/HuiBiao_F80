package com.dy.huibiao_f80.mvp.contract;

import com.dy.huibiao_f80.api.back.BeginTestForm_Back;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;

public interface PrintReportContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showReports(BeginTestForm_Back back);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BeginTestForm_Back> beginTestForm(String url, String examinationId);

    }
}