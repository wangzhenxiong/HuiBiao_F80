package com.dy.huibiao_f80.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface ShowPDFContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Context getActivity();
        void success();

        void fail();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<ResponseBody> downLoadPDF(String type, String s);
    }
}