package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.api.back.AnalyseSubmit_Back;
import com.dy.huibiao_f80.api.back.BeginAnalyseExam_Back;
import com.dy.huibiao_f80.app.utils.DataUtils;
import com.dy.huibiao_f80.mvp.contract.ExamAnalyseContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;

@ActivityScope
public class ExamAnalyseModel extends BaseModel implements ExamAnalyseContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ExamAnalyseModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BeginAnalyseExam_Back> beginAnalyseExam(String url, String examinationId, String examinerId) {
        RetrofitUrlManager.getInstance().putDomain("xxx", url);
        return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                .beginAnalyseExam(examinationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<AnalyseSubmit_Back> analyseSubmit(String url, String examinationId, String examinerId, BeginAnalyseExam_Back beginAnalyseExamBack) {
        RetrofitUrlManager.getInstance().putDomain("xxx", url);
        return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                .analyseSubmit(getAnalyseSubmitBody(examinationId, examinerId, beginAnalyseExamBack))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    private RequestBody getAnalyseSubmitBody(String examinationId, String examinerId, BeginAnalyseExam_Back beginAnalyseExamBack) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("examinationId", examinationId);
            obj.put("examinerId", examinerId);
            obj.put("analysePapaerId", beginAnalyseExamBack.getEntity().getAnalysePaper().getId());
            obj.put("createTime", DataUtils.getNowtimeyyymmddhhmmss());

            JSONArray value = new JSONArray();
            List<BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean> theoryQuestionRadioList = beginAnalyseExamBack.getEntity().getAnalysePaperList();
            for (int i = 0; i < theoryQuestionRadioList.size(); i++) {
                BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean theoryQuestionRadioListBean = theoryQuestionRadioList.get(i);
                String answer = theoryQuestionRadioListBean.getStudentAnswer();
                JSONObject value1 = new JSONObject();
                value1.put("analysePaperDetailId",theoryQuestionRadioListBean.getId());
                value1.put("answer",answer);
                value1.put("status", answer.isEmpty()?"3":"4");
                value.put(value1);
            }


            obj.put("analysePaperDetailList", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String object = obj.toString();
        LogUtils.d(object);
        return RequestBody.create(MediaType.parse("application/json"), object);

    }

}