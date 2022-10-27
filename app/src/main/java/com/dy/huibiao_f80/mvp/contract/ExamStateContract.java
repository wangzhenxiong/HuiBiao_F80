package com.dy.huibiao_f80.mvp.contract;

import com.dy.huibiao_f80.api.back.GetExamPage_Back;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;

public interface ExamStateContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showState(GetExamPage_Back back);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<GetExamPage_Back> getExamPage(String url,String examinerId, String examinationId);
    }
}