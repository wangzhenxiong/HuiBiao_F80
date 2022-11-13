package com.dy.huibiao_f80.mvp.model;

import android.app.Application;

import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.api.back.GetTestForm_Back;
import com.dy.huibiao_f80.mvp.contract.PrintReportContract;
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
public class PrintReportModel extends BaseModel implements PrintReportContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PrintReportModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GetTestForm_Back> getTestForm(String url, String operationPaperId) {
        RetrofitUrlManager.getInstance().putDomain("xxx", url);
        return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                .getTestForm(operationPaperId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

   /* @Override
    public Observable<TestFormSubmit_Back> submitOperation(String operationPaperId) {
        RetrofitUrlManager.getInstance().putDomain("xxx", Constants.URL);
        return mRepositoryManager.obtainRetrofitService(HuiBiaoService.class)
                .testFormSubmit(getTestFormSubmit(operationPaperId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }*/

    /*private RequestBody getTestFormSubmit(String operationPaperId) {
        ReportBean reportBean = MyAppLocation.myAppLocation.mExamOperationService.getReportBeanList();
        if (null==reportBean) {
            GetTestForm_Back beginTestForm_back = MyAppLocation.myAppLocation.mExamOperationService.getGetTestFormBackList();
            ReportBean bean=new ReportBean();
            bean.setExaminerId(MyAppLocation.myAppLocation.mExamOperationService.getExaminerId());
            bean.setOperationPaperId(operationPaperId);
            bean.setExaminationId(MyAppLocation.myAppLocation.mExamOperationService.getExaminationId());
            bean.setCreateTime(DataUtils.getNowtimeyyymmddhhmmss());
            ArrayList<ReportBean.TestFormDetail> testFormDetailList = new ArrayList<>();
            List<GetTestForm_Back.EntityBean.TestFormListBean> testFormList = beginTestForm_back.getEntity().getTestFormList();
            for (int i = 0; i < testFormList.size(); i++) {
                GetTestForm_Back.EntityBean.TestFormListBean testFormListBean = testFormList.get(i);
                List<GetTestForm_Back.EntityBean.TestFormListBean.TestFormDetailListBean> testFormDetailList1 = testFormListBean.getTestFormDetailList();
                for (int i1 = 0; i1 < testFormDetailList1.size(); i1++) {
                    GetTestForm_Back.EntityBean.TestFormListBean.TestFormDetailListBean testFormDetailListBean = testFormDetailList1.get(i1);
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

    }*/
}