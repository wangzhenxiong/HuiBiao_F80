package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.api.back.BeginOperationExam_Back;
import com.dy.huibiao_f80.api.back.BeginTestForm_Back;
import com.dy.huibiao_f80.api.back.TestFormSubmit_Back;
import com.dy.huibiao_f80.app.utils.DataUtils;
import com.dy.huibiao_f80.bean.ReportBean;
import com.dy.huibiao_f80.mvp.contract.ExamOperationContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;

@ActivityScope
public class ExamOperationModel extends BaseModel implements ExamOperationContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ExamOperationModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BeginOperationExam_Back> beginOperationExam(String URL, String examinationId, String examinerId) {
        RetrofitUrlManager.getInstance().putDomain("xxx", URL);
        return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                .getOperationExam(examinationId,examinerId, Constants.DEVICENUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<TestFormSubmit_Back> submitOperation() {
        RetrofitUrlManager.getInstance().putDomain("xxx", Constants.URL);
        return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                .testFormSubmit(getTestFormSubmit())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    private RequestBody getTestFormSubmit() {
        ReportBean reportBean = MyAppLocation.myAppLocation.mExamOperationService.getReportBean();
        if (null==reportBean) {
            BeginTestForm_Back beginTestForm_back = MyAppLocation.myAppLocation.mExamOperationService.getBeginTestForm_back();
            ReportBean bean=new ReportBean();
            bean.setExaminerId(MyAppLocation.myAppLocation.mExamOperationService.getExaminerId());
            bean.setExaminationId(MyAppLocation.myAppLocation.mExamOperationService.getExaminationId());
            bean.setCreateTime(DataUtils.getNowtimeyyymmddhhmmss());
            ArrayList<ReportBean.TestFormDetail> testFormDetailList = new ArrayList<>();
            List<BeginTestForm_Back.EntityBean.TestFormListBean> testFormList = beginTestForm_back.getEntity().getTestFormList();
            for (int i = 0; i < testFormList.size(); i++) {
                BeginTestForm_Back.EntityBean.TestFormListBean testFormListBean = testFormList.get(i);
                List<BeginTestForm_Back.EntityBean.TestFormListBean.TestFormDetailListBean> testFormDetailList1 = testFormListBean.getTestFormDetailList();
                for (int i1 = 0; i1 < testFormDetailList1.size(); i1++) {
                    BeginTestForm_Back.EntityBean.TestFormListBean.TestFormDetailListBean testFormDetailListBean = testFormDetailList1.get(i1);
                    ReportBean.TestFormDetail detail=new ReportBean.TestFormDetail();
                    detail.setTestFormDetailId(testFormDetailListBean.getId());
                    detail.setTestFormId(testFormDetailListBean.getTestFormId());
                    detail.setFieldName(testFormDetailListBean.getFieldName());
                    detail.setAnswer("");
                    testFormDetailList.add(detail);
                }
            }
            bean.setTestFormDetailList(testFormDetailList);
            String s = new Gson().toJson(bean);
            LogUtils.d(s);
            return RequestBody.create(MediaType.parse("application/json"), s);
        }else {
            String content = new Gson().toJson(reportBean);
            LogUtils.d(content);
            return RequestBody.create(MediaType.parse("application/json"), content);
        }

    }
}