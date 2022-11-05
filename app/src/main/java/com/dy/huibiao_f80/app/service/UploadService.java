package com.dy.huibiao_f80.app.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.api.HuiBiaoService;
import com.dy.huibiao_f80.api.back.OperationTestRecord_Back;
import com.dy.huibiao_f80.api.back.UploadBean;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.greendao.daos.TestRecordDao;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploadService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.dy.huibiao_f80.app.service.action.FOO";
    private static final String ACTION_BAZ = "com.dy.huibiao_f80.app.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.dy.huibiao_f80.app.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.dy.huibiao_f80.app.service.extra.PARAM2";

    public UploadService() {
        super("UploadService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startUpLoad(Context context, String param1) {
        Intent intent = new Intent(context, UploadService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UploadService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionUpload(param1);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpload(String param1) {
        List<TestRecord> list = DBHelper.getTestRecordDao().queryBuilder().where(TestRecordDao.Properties.SysCode.eq(param1)).list();
        if (list.size()>0) {
            TestRecord testRecord = list.get(0);
            RetrofitUrlManager.getInstance().putDomain("xxx", Constants.URL);
            RxErrorHandler handler = ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler();
            ArmsUtils.obtainAppComponentFromContext(this).repositoryManager()
                    .obtainRetrofitService(HuiBiaoService.class)
                    .operationTestRecord(getoperationTestRecordBody(testRecord))
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ErrorHandleSubscriber<OperationTestRecord_Back>(handler) {
                        @Override
                        public void onNext(OperationTestRecord_Back upLoadBack) {
                            LogUtils.d(upLoadBack);
                             if (upLoadBack.isSuccess()){
                                 testRecord.setIsupload(2);
                                 DBHelper.getTestRecordDao().update(testRecord);
                             }
                             ArmsUtils.snackbarText(upLoadBack.getMessage());

                        }
                    });
        }
    }

    private RequestBody getoperationTestRecordBody(TestRecord testRecord) {
        List<UploadBean> list=new ArrayList<>() ;
        UploadBean uploadBean = new UploadBean();
        uploadBean.setExaminationId(testRecord.getExaminationId());
        uploadBean.setExaminerId(testRecord.getExaminerId());
        uploadBean.setOperationPaperId(testRecord.getExam_id());
        uploadBean.setSampleCode(testRecord.getSamplenum());
        uploadBean.setSampleName(testRecord.getSamplename());
        uploadBean.setDetectionMode(testRecord.getTest_Moudle());
        uploadBean.setProjectName(testRecord.getTest_project());
        uploadBean.setDetectionResult(testRecord.getTestresult());
        uploadBean.setCompany(testRecord.getCov_unit());
        uploadBean.setDetermine(testRecord.getDecisionoutcome().equals("合格")?"1":"2");
        uploadBean.setDetectionTime(testRecord.getdfTestingtimeyy_mm_dd_hh_mm_ss());
        uploadBean.setTestWay(testRecord.getGalleryNum()+"");
        uploadBean.setUploadStatus("");
        list.add(uploadBean) ;
        String content = new Gson().toJson(list);
        LogUtils.d(content);
        return RequestBody.create(MediaType.parse("application/json"), content);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}