package com.dy.huibiao_f80.app.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.api.back.BeginOperationExam_Back;
import com.dy.huibiao_f80.api.back.GetTestForm_Back;
import com.dy.huibiao_f80.api.back.IsTeacherSubmit_Back;
import com.dy.huibiao_f80.api.back.TestFormSubmit_Back;
import com.dy.huibiao_f80.app.utils.DataUtils;
import com.dy.huibiao_f80.app.utils.NetworkUtils;
import com.dy.huibiao_f80.bean.ReportBean;
import com.dy.huibiao_f80.bean.eventBusBean.NetWorkState;
import com.dy.huibiao_f80.mvp.ui.activity.ExamStateActivity;
import com.google.gson.Gson;
import com.jess.arms.base.BaseService;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private MaterialDialog mWaiteDialog;

    @Override
    public void onCreate() {
        super.onCreate();
        //EventBusManager.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBusManager.getInstance().unregister(this);
    }

    private IBinder bind = new ExamOperationService.MyBinder();
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;
    private int operationExamTime;
    public boolean isTeacherSubmit;
    /**
     * ???????????????
     */
    private BeginOperationExam_Back beginOperationExam_back;
    /**
     * ??????????????????
     */
    private Map<String, GetTestForm_Back> getTestForm_backMap = new HashMap<>();
    /**
     * ????????????????????????
     */
    private Map<String, ReportBean> reportBeanMap = new HashMap<>();
    /**
     * ??????id
     */
    private String examinationId;
    /**
     * ??????id
     */
    private String examinerId;

    /**
     * ?????????????????????
     */
    private BeginOperationExam_Back.EntityBean.OperationPaperListBean nowOperationExam;

    /**
     * ??????????????????
     */
    private boolean isStartExamOperation;

    /**
     * ??????????????????
     *
     * @return
     */
    public boolean isStartExamOperation() {
        return isStartExamOperation;
    }

    /**
     * ????????????
     *
     * @param examTime
     */
    public void startExamOperation(int examTime) {
        LogUtils.d(isStartExamOperation+"  "+operationExamTime);
        isStartExamOperation = true;
        operationExamTime = examTime;
    }


    Runnable command_countdown = new Runnable() {
        @Override
        public void run() {
            //LogUtils.d(operationExamTime+"  "+isStartExamOperation);
            if (operationExamTime>=0&&isStartExamOperation){
                int i = operationExamTime / 60;
                int i1 = operationExamTime % 60;
                ExamOperationServiceEventBean event = new ExamOperationServiceEventBean();
                if (operationExamTime > 0) {
                    event.setTime(operationExamTime);
                    event.setTimestring("????????????  " + (i < 10 ? "0" + i : "" + i) + ":" + (i1 < 10 ? "0" + i1 : "" + i1));
                    EventBus.getDefault().post(event);
                    operationExamTime--;
                } else if (operationExamTime == 0) {
                    event.setTime(operationExamTime);
                    event.setTimestring("????????????  " + (i < 10 ? "0" + i : "" + i) + ":" + (i1 < 10 ? "0" + i1 : "" + i1));
                    EventBus.getDefault().post(event);

                    // TODO: 11/5/22 ????????????????????????????????????????????????
                    forceUploadReport();
                }

            }

        }
    };

    Runnable commandisTeacherSubmit = new Runnable() {
        @Override
        public void run() {
            if (!isTeacherSubmit&&isStartExamOperation) {
                if (!NetworkUtils.getNetworkType()){
                   return;
                }
                RetrofitUrlManager.getInstance().putDomain("xxx", Constants.URL);
                ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this)
                        .repositoryManager().obtainRetrofitService(HuiBiaoService.class)
                        .isTeacherSubmit(examinerId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io()).subscribe(new ErrorHandleSubscriber<IsTeacherSubmit_Back>(ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this).rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull IsTeacherSubmit_Back isTeacherSubmit_back) {
                        LogUtils.d(isTeacherSubmit_back);
                        if (null != isTeacherSubmit_back) {
                            isTeacherSubmit = isTeacherSubmit_back.getSuccess();
                            if (isTeacherSubmit) {
                                // TODO: 11/3/22 ???????????????????????????????????????
                                //ArmsUtils.snackbarText("????????????????????????");
                                //ArmsUtils.startActivity(new Intent());
                            }
                        }
                    }
                });
            }
        }
    };


    /**
     * ????????????
     */
    public void finishOperationExam() {
        operationExamTime=-1;
        isStartExamOperation = false;
        isTeacherSubmit = false;
        beginOperationExam_back = null;
        getTestForm_backMap.clear();
        reportBeanMap.clear();
    }

    public void cleanMaps() {
        getTestForm_backMap.clear();
        reportBeanMap.clear();
    }

    public void addGetTestForm_BackMap(String operationPaperId, GetTestForm_Back back) {
        getTestForm_backMap.put(operationPaperId, back);
    }

    public ReportBean getReportBeanByID(String operationPaperId) {
        return reportBeanMap.get(operationPaperId);
    }

    public void addReportBeanMap(String operationPaperId, ReportBean reportBean) {
        reportBeanMap.put(operationPaperId, reportBean);
    }

    public GetTestForm_Back getGetTestFormByID(String operationPaperId) {
        return getTestForm_backMap.get(operationPaperId);
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
        mScheduledThreadPoolExecutor.scheduleAtFixedRate(command_countdown, 0, 1000, TimeUnit.MILLISECONDS);
        mScheduledThreadPoolExecutor.scheduleAtFixedRate(commandisTeacherSubmit, 0, 5000, TimeUnit.MILLISECONDS);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetWorkState tags) {
        if (tags.isLinkstate()) {
            if (mWaiteDialog!=null&&mWaiteDialog.isShowing()){
                mWaiteDialog.dismiss();
                forceUploadReport();
            }
        }
    }
    Activity currentActivity;
    private void makeDialog() {
        currentActivity = ArmsUtils.obtainAppComponentFromContext(MyAppLocation.myAppLocation).appManager().getCurrentActivity();
        currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mWaiteDialog == null) {
                    mWaiteDialog = new MaterialDialog.Builder(currentActivity)
                            .cancelable(false)
                            .canceledOnTouchOutside(false)
                            .positiveText("??????")
                            .neutralText("??????")
                            .contentGravity(GravityEnum.CENTER)
                            .build();
                }
                //mWaiteDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                mWaiteDialog.setContent("??????????????????????????????????????????");
                mWaiteDialog.getActionButton(DialogAction.POSITIVE).setVisibility(View.GONE);
                mWaiteDialog.getActionButton(DialogAction.NEUTRAL).setVisibility(View.GONE);
                mWaiteDialog.show();
            }
        });


    }

    private void forceUploadReport() {
        if (!NetworkUtils.getNetworkType()) {
            //??????????????????????????????
            makeDialog();
            return;
        }

        Set<String> strings = getTestForm_backMap.keySet();
        for (int i = 0; i < strings.size(); i++) {
            if (strings.iterator().hasNext()) {
                String next = strings.iterator().next();
                uploadReport(next);
            }
        }


    }

    private void uploadReport(String next) {
        RequestBody requestBody;
        ReportBean reportBean = MyAppLocation.myAppLocation.mExamOperationService.getReportBeanByID(next);
        if (null == reportBean) {
            GetTestForm_Back beginTestForm_back = MyAppLocation.myAppLocation.mExamOperationService.getGetTestFormByID(next);
            ReportBean bean = new ReportBean();
            bean.setExaminerId(MyAppLocation.myAppLocation.mExamOperationService.getExaminerId());
            bean.setExaminationId(MyAppLocation.myAppLocation.mExamOperationService.getExaminationId());
            bean.setOperationPaperId(next);
            bean.setCreateTime(DataUtils.getNowtimeyyymmddhhmmss());
            ArrayList<ReportBean.TestFormDetail> testFormDetailList = new ArrayList<>();
            List<GetTestForm_Back.EntityBean.TestFormListBean> testFormList = beginTestForm_back.getEntity().getTestFormList();
            for (int i = 0; i < testFormList.size(); i++) {
                GetTestForm_Back.EntityBean.TestFormListBean testFormListBean = testFormList.get(i);
                List<GetTestForm_Back.EntityBean.TestFormListBean.TestFormDetailListBean> testFormDetailList1 = testFormListBean.getTestFormDetailList();
                for (int i1 = 0; i1 < testFormDetailList1.size(); i1++) {
                    GetTestForm_Back.EntityBean.TestFormListBean.TestFormDetailListBean testFormDetailListBean = testFormDetailList1.get(i1);
                    ReportBean.TestFormDetail detail = new ReportBean.TestFormDetail();
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
            requestBody = RequestBody.create(MediaType.parse("application/json"), s);
        } else {
            String content = new Gson().toJson(reportBean);
            LogUtils.d(content);
            requestBody = RequestBody.create(MediaType.parse("application/json"), content);
        }

        RetrofitUrlManager.getInstance().putDomain("xxx", Constants.URL);
        ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this)
                .repositoryManager().obtainRetrofitService(HuiBiaoService.class)
                .testFormSubmit(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<TestFormSubmit_Back>(ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this).rxErrorHandler()) {
                    @Override
                    public void onNext(@NonNull TestFormSubmit_Back back) {
                        if (null != back) {
                            //isTeacherSubmit = back.isSuccess();
                            getTestForm_backMap.remove(next);
                            if (getTestForm_backMap.keySet().size() == 0) {
                                isStartExamOperation = false;
                                Activity topActivity = ArmsUtils.obtainAppComponentFromContext(ExamOperationService.this).appManager().getTopActivity();
                                Intent content = new Intent(topActivity, ExamStateActivity.class);
                               // content.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                content.putExtra("examinationId", examinationId);
                                content.putExtra("examinerId", examinerId);
                                ArmsUtils.startActivity(content);

                                finishOperationExam();
                                ArmsUtils.snackbarText("???????????????");

                               /* ExamOperationService.ExamOperationServiceEventBean event = new ExamOperationService.ExamOperationServiceEventBean();
                                event.state = -1;
                                EventBus.getDefault().post(event);*/
                                //ArmsUtils.startActivity(ExamStateActivity.class);
                            }
                        } else {
                            uploadReport(next);
                        }
                    }
                });
    }


    public BeginOperationExam_Back getBeginOperationExam_back() {
        return beginOperationExam_back;
    }

    public void setBeginOperationExam_back(BeginOperationExam_Back beginOperationExam_back) {
        this.beginOperationExam_back = beginOperationExam_back;
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

    public Map<String, GetTestForm_Back> getGetTestForm_backMap() {
        return getTestForm_backMap;
    }


    public Map<String, ReportBean> getReportBeanMap() {
        return reportBeanMap;
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


}