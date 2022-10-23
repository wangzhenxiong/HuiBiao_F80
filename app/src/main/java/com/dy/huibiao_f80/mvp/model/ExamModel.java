package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.api.back.CheckExaminer_Back;
import com.dy.huibiao_f80.mvp.contract.ExamContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

@ActivityScope
public class ExamModel extends BaseModel implements ExamContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ExamModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CheckExaminer_Back> checkExaminer(String url, int id, String name, String number, int personTestMethod) {
        RetrofitUrlManager.getInstance().putDomain("xxx", url);
        return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                .checkExaminer(mGson.toJson(new QueryExaminer(id + "", name, number, personTestMethod + "")))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    class QueryExaminer {
        public QueryExaminer(String examinationId, String name, String idNumber, String personTestMethod) {
            this.examinationId = examinationId;
            this.name = name;
            this.idNumber = idNumber;
            this.personTestMethod = personTestMethod;
        }

        private String examinationId;
        private String name;
        private String idNumber;
        private String personTestMethod;
    }
}