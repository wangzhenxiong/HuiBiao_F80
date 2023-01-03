package com.dy.huibiao_f80.mvp.contract;

import android.app.Activity;

import com.dy.huibiao_f80.api.back.AnalyseSubmit_Back;
import com.dy.huibiao_f80.api.back.BeginAnalyseExam_Back;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;

public interface ExamAnalyseContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showExamTitle(BeginAnalyseExam_Back back);

        void submitSuccess();

        Activity getActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BeginAnalyseExam_Back> beginAnalyseExam(String URL, String examinationId, String examinerId);

        Observable<AnalyseSubmit_Back> analyseSubmit(String url, String examinationId, String examinerId, BeginAnalyseExam_Back beginAnalyseExamBack);

    }
}