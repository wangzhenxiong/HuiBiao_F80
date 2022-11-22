package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.mvp.contract.ShowPDFContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.utils.LogUtils;

import java.net.URLEncoder;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@ActivityScope
public class ShowPDFModel extends BaseModel implements ShowPDFContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ShowPDFModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override
    public Observable<ResponseBody> downLoadPDF(String type, String s) {
        String[] split = s.split("/");

        if ("1".equals(type)){

            String paramas = split[split.length - 1];
            String encode = URLEncoder.encode(paramas);
            LogUtils.debugInfo(paramas);
            LogUtils.debugInfo(encode);
            return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                    .getLaws(paramas)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io());
        }else {
            String paramas =  split[split.length - 1];
            String encode = URLEncoder.encode(paramas);
            LogUtils.debugInfo(paramas);
            LogUtils.debugInfo(encode);
            return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                    .getStandard(paramas)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io());
        }


    }

}