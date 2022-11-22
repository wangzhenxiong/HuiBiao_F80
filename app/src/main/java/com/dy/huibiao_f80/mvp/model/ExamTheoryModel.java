package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.api.back.BeginTheoryExam_Back;
import com.dy.huibiao_f80.api.back.TheorySubmit_Back;
import com.dy.huibiao_f80.app.utils.DataUtils;
import com.dy.huibiao_f80.mvp.contract.ExamTheoryContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;

@ActivityScope
public class ExamTheoryModel extends BaseModel implements ExamTheoryContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ExamTheoryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BeginTheoryExam_Back> beginTheoryExam(String url, String examinationId) {
        RetrofitUrlManager.getInstance().putDomain("xxx", url);
        return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                .beginTheoryExam(examinationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<TheorySubmit_Back> submit(String examinationId, String examinerId, BeginTheoryExam_Back beginTheoryExamBack, String url) {

        RetrofitUrlManager.getInstance().putDomain("xxx", url);
        return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                .theorySubmit(getTheorySubmitBody(examinationId, examinerId, beginTheoryExamBack))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    private RequestBody getTheorySubmitBody(String examinationId, String examinerId, BeginTheoryExam_Back beginTheoryExamBack) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("examinationId", examinationId);
            obj.put("examinerId", examinerId);
            obj.put("theoryPaperId", beginTheoryExamBack.getEntity().getTheoryPaper().getId());
            obj.put("createTime", DataUtils.getNowtimeyyymmddhhmmss());

            JSONArray value = new JSONArray();
            List<BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean> theoryQuestionRadioList = beginTheoryExamBack.getEntity().getTheoryQuestionRadioList();
            for (int i = 0; i < theoryQuestionRadioList.size(); i++) {
                BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean theoryQuestionRadioListBean = theoryQuestionRadioList.get(i);
                String answer = answerSore(theoryQuestionRadioListBean.getStudentAnswer());
                JSONObject value1 = new JSONObject();
                value1.put("theoryQuestionId",theoryQuestionRadioListBean.getId());
                value1.put("examinerAnswer",answer);
                value1.put("status", answer.isEmpty()?"3":"4");
                value.put(value1);
            }
            List<BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean> theoryQuestionMultipleList = beginTheoryExamBack.getEntity().getTheoryQuestionMultipleList();
            for (int i = 0; i < theoryQuestionMultipleList.size(); i++) {
                BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean theoryQuestionRadioListBean = theoryQuestionMultipleList.get(i);
                String answer = answerSore(theoryQuestionRadioListBean.getStudentAnswer());
                JSONObject value1 = new JSONObject();
                value1.put("theoryQuestionId",theoryQuestionRadioListBean.getId());
                value1.put("examinerAnswer",answer);
                value1.put("status", answer.isEmpty()?"3":"4");
                value.put(value1);
            }
            List<BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean> theoryQuestionJudgeList = beginTheoryExamBack.getEntity().getTheoryQuestionJudgeList();
            for (int i = 0; i < theoryQuestionJudgeList.size(); i++) {
                BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean theoryQuestionRadioListBean = theoryQuestionJudgeList.get(i);
                String answer = answerSore(theoryQuestionRadioListBean.getStudentAnswer());
                JSONObject value1 = new JSONObject();
                value1.put("theoryQuestionId",theoryQuestionRadioListBean.getId());
                value1.put("examinerAnswer",answer);
                value1.put("status", answer.isEmpty()?"3":"4");
                value.put(value1);
            }

            obj.put("questionAnswerList", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String object = obj.toString();
        LogUtils.d(object);
        return RequestBody.create(MediaType.parse("application/json"), object);

    }

    private String answerSore(String answer) {
        char[] chars = answer.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }
}