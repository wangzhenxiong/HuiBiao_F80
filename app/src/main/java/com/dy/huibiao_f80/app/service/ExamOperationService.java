package com.dy.huibiao_f80.app.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.api.back.BeginOperationExam_Back;
import com.dy.huibiao_f80.api.back.BeginTestForm_Back;
import com.dy.huibiao_f80.api.back.IsTeacherSubmit_Back;
import com.dy.huibiao_f80.api.back.TestFormSubmit_Back;
import com.dy.huibiao_f80.app.utils.DataUtils;
import com.dy.huibiao_f80.bean.ReportBean;
import com.dy.huibiao_f80.mvp.ui.activity.ExamStateActivity;
import com.google.gson.Gson;
import com.jess.arms.base.BaseService;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ExamOperationService extends BaseService {
    private IBinder bind = new ExamOperationService.MyBinder();
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;
    private Integer operationExamTime;
    private boolean isTeacherSubmit;
    /**
     * 实操题实体
     */
    private BeginOperationExam_Back beginOperationExam_back;
    /**
     * 实验报告实体
     */
    private BeginTestForm_Back beginTestForm_back;
    private ReportBean reportBean;

    /**
     * 考试id
     */
    private String examinationId;
    /**
     * 考生id
     */
    private String examinerId;

    /**
     * 当前考试的题目
     */
    private BeginOperationExam_Back.EntityBean.OperationPaperListBean nowOperationExam;

    /**
     * 是否开始考试
     *
     * @return
     */
    public boolean isStartExamOperation() {
        return isStartExamOperation;
    }

    /**
     * 开始考试
     *
     * @param startExamOperation
     * @param examTime
     */
    public void startExamOperation(boolean startExamOperation, int examTime) {
        isStartExamOperation = startExamOperation;
        operationExamTime = examTime;
        if (startExamOperation) {
            countDownOperationExamTime(examTime);
            isTeacherSubmit();
            getReportData();
        }
    }

    private void getReportData() {
        RetrofitUrlManager.getInstance().putDomain("xxx", Constants.URL);
        ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this)
                .repositoryManager().obtainRetrofitService(HuiBiaoService.class)
                .beginTestForm(examinationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe(new ErrorHandleSubscriber<BeginTestForm_Back>(ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this).rxErrorHandler()) {
            @Override
            public void onNext(@NonNull BeginTestForm_Back back) {
                if (null != back) {
                    isTeacherSubmit = back.getSuccess();
                    if (isTeacherSubmit) {
                        beginTestForm_back= back;
                    }
                }
            }
        });
    }

    /**
     * 5秒一次轮询考评员是否提交分数
     */
    private void isTeacherSubmit() {
        mScheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!isTeacherSubmit) {
                    RetrofitUrlManager.getInstance().putDomain("xxx", Constants.URL);
                    ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this)
                            .repositoryManager().obtainRetrofitService(HuiBiaoService.class)
                            .isTeacherSubmit(examinerId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .unsubscribeOn(Schedulers.io()).subscribe(new ErrorHandleSubscriber<IsTeacherSubmit_Back>(ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this).rxErrorHandler()) {
                        @Override
                        public void onNext(@NonNull IsTeacherSubmit_Back isTeacherSubmit_back) {
                            if (null != isTeacherSubmit_back) {
                                isTeacherSubmit = isTeacherSubmit_back.getSuccess();
                                if (isTeacherSubmit) {
                                    // TODO: 11/3/22 考评员分数已提交，结束考试
                                    ArmsUtils.snackbarText("考试已结束");
                                    ArmsUtils.startActivity(ExamStateActivity.class);
                                }
                            }
                        }
                    });
                }
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }

    /**
     * 开始考试
     */
    private boolean isStartExamOperation;

    /**
     * 结束考试
     */
    public void finishOperationExam() {
        isStartExamOperation = false;
        beginOperationExam_back = null;
        beginTestForm_back = null;
        reportBean = null;
    }

    public class MyBinder extends Binder {
        public ExamOperationService getService() {
            return ExamOperationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return bind;
    }

    @Override
    public void init() {
        mScheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) ArmsUtils.obtainAppComponentFromContext(this).executorService();
    }

    private void countDownOperationExamTime(Integer time) {
        mScheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isStartExamOperation) {
                    if (operationExamTime > 0) {

                        int i = operationExamTime / 60;
                        int i1 = operationExamTime % 60;

                        // mToolbarTime.setText("剩余时间  " + (i < 10 ? "0" + i : "" + i) + ":" + (i1 < 10 ? "0" + i1 : "" + i1));
                        ExamOperationServiceEventBean event = new ExamOperationServiceEventBean();
                        event.setTime(operationExamTime);
                        event.setTimestring((i < 10 ? "0" + i : "" + i) + ":" + (i1 < 10 ? "0" + i1 : "" + i1));
                        EventBus.getDefault().post(event);
                        operationExamTime--;
                    } else {
                        //mPresenter.submit(examinationId, examinerId, beginAnalyseExamBack);
                        isStartExamOperation = false;
                        // TODO: 11/5/22 倒计时结束，需要强制上传实验报告
                        forceUploadReport();
                    }
                }
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void forceUploadReport() {
        RetrofitUrlManager.getInstance().putDomain("xxx", Constants.URL);
        ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this)
                .repositoryManager().obtainRetrofitService(HuiBiaoService.class)
                .testFormSubmit(getTestFormSubmit())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<TestFormSubmit_Back>(ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this).rxErrorHandler()) {
            @Override
            public void onNext(@NonNull TestFormSubmit_Back back) {
                if (null != back) {
                    isTeacherSubmit = back.isSuccess();
                    if (isTeacherSubmit) {
                        ArmsUtils.snackbarText("考试已结束");
                        ArmsUtils.startActivity(ExamStateActivity.class);
                    }else {
                        forceUploadReport();
                    }
                }
            }
        });
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


    public BeginOperationExam_Back getBeginOperationExam_back() {
        return beginOperationExam_back;
    }

    public void setBeginOperationExam_back(BeginOperationExam_Back beginOperationExam_back) {
        this.beginOperationExam_back = beginOperationExam_back;
    }

    public BeginTestForm_Back getBeginTestForm_back() {
        return beginTestForm_back;
    }

    public void setBeginTestForm_back(BeginTestForm_Back beginTestForm_back) {
        this.beginTestForm_back = beginTestForm_back;
    }

    public String getExaminationId() {
        return examinationId == null ? "" : examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId == null ? "" : examinationId;
    }

    public String getExaminerId() {
        return examinerId == null ? "" : examinerId;
    }

    public void setExaminerId(String examinerId) {
        this.examinerId = examinerId == null ? "" : examinerId;
    }

    public BeginOperationExam_Back.EntityBean.OperationPaperListBean getNowOperationExam() {
        return nowOperationExam;
    }

    public void setNowOperationExam(BeginOperationExam_Back.EntityBean.OperationPaperListBean nowOperationExam) {
        this.nowOperationExam = nowOperationExam;
    }

    public class ExamOperationServiceEventBean {
        private int state;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        private int time;

        public String getTimestring() {
            return timestring;
        }

        public void setTimestring(String timestring) {
            this.timestring = timestring;
        }

        private String timestring;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    public ReportBean getReportBean() {
        return reportBean;
    }

    public void setReportBean(ReportBean reportBean) {
        this.reportBean = reportBean;
    }
}