package com.dy.huibiao_f80.mvp.contract;

import android.app.Activity;

import com.dy.huibiao_f80.api.back.BeginTheoryExam_Back;
import com.dy.huibiao_f80.api.back.TheorySubmit_Back;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;

public interface ExamTheoryContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showExamTitle(BeginTheoryExam_Back back);
        Activity getActivity();

        void submitSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BeginTheoryExam_Back> beginTheoryExam(String url, String examinationId);

        Observable<TheorySubmit_Back> submit(String examinationId, String examinerId, BeginTheoryExam_Back beginTheoryExamBack, String url);
    }
}