package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.api.back.GetExamPage_Back;
import com.dy.huibiao_f80.app.utils.NetworkUtils;
import com.dy.huibiao_f80.di.component.DaggerExamStateComponent;
import com.dy.huibiao_f80.mvp.contract.ExamStateContract;
import com.dy.huibiao_f80.mvp.presenter.ExamStatePresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ExamStateActivity extends BaseActivity<ExamStatePresenter> implements ExamStateContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.time1)
    TextView mTime1;
    @BindView(R.id.startexam1)
    Button mStartexam1;
    @BindView(R.id.time2)
    TextView mTime2;
    @BindView(R.id.startexa2)
    Button mStartexa2;
    @BindView(R.id.time3)
    TextView mTime3;
    @BindView(R.id.startexam3)
    Button mStartexam3;
    @BindView(R.id.layout1)
    LinearLayout mLayout1;
    @BindView(R.id.finish_hint)
    TextView mFinishHint;
    private String examinationId;
    private String examinerId;
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExamStateComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_examstate; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        runFlag = false;
        Intent intent = new Intent(ExamStateActivity.this, ExamActivity.class);
        startActivity(intent);
        finish();
    }
     /*@Override
    public void onBackPressed() {

        runFlag=false;
        Intent intent = new Intent(ExamStateActivity.this, ExamActivity.class);
        startActivity(intent);

    }*/


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        examinationId = intent.getStringExtra("examinationId");
        examinerId = intent.getStringExtra("examinerId");
        if (examinationId == null) {
            examinationId = MyAppLocation.myAppLocation.mExamOperationService.getExaminationId();
        }
        if (examinerId == null) {
            examinerId = MyAppLocation.myAppLocation.mExamOperationService.getExaminerId();
        }
        mPresenter.getExamPage(examinerId, examinationId);
        mScheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) ArmsUtils.obtainAppComponentFromContext(this).executorService();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private int finishTime;
    boolean runFlag;
    /**
     *?????????????????????1??????2??????
     */
    Integer examStatus;
    /**
     * ????????????????????????1??????2??????
     */
    Integer analyseStatus;
    /**
     * ????????????????????????1??????2?????????3?????????
     */
    Integer operationStatus;
    @Override
    public void showState(GetExamPage_Back back) {
        runFlag = true;
        finishTime = 30;
        GetExamPage_Back.EntityBean entity = back.getEntity();
        GetExamPage_Back.EntityBean.ExaminationBean examination = entity.getExamination();
        GetExamPage_Back.EntityBean.ExaminerBean examiner = entity.getExaminer();

         examStatus = examiner.getExamStatus();
         analyseStatus = examiner.getAnalyseStatus();
         operationStatus = examiner.getOperationStatus();
        Integer theoryExamTime = examination.getTheoryExamTime();
        Integer analyseExamTime = examination.getAnalyseExamTime();
        Integer operationExamTime = examination.getOperationExamTime();

        if (examStatus == 1) {
            mStartexam1.setBackground(getResources().getDrawable(R.drawable.btn_background_blue));
        } else  {
            mStartexam1.setText(">>?????????>>");
            mStartexam1.setBackground(getResources().getDrawable(R.drawable.btn_background_gray));
        }

        if (analyseStatus == 1) {
            mStartexa2.setBackground(getResources().getDrawable(R.drawable.btn_background_blue));
        } else  {
            mStartexa2.setText(">>?????????>>");
            mStartexa2.setBackground(getResources().getDrawable(R.drawable.btn_background_gray));
        }
        if (operationStatus == 1) {
            mStartexam3.setBackground(getResources().getDrawable(R.drawable.btn_background_blue));
        } else {
            mStartexam3.setText(">>?????????>>");
            mStartexam3.setBackground(getResources().getDrawable(R.drawable.btn_background_gray));
        }

        mTime1.setText("???????????????" + theoryExamTime + "??????");
        mTime2.setText("???????????????" + analyseExamTime + "??????");
        mTime3.setText("???????????????" + operationExamTime + "??????");
        /*//?????????????????????1??????2??????
        Integer examStatus = examiner.getExamStatus();
        //????????????????????????1??????2??????
        Integer analyseStatus = examiner.getAnalyseStatus();
        //????????????????????????1??????2?????????3?????????
        Integer operationStatus = examiner.getOperationStatus();*/
        if (examStatus != 1 && analyseStatus != 1 && operationStatus != 1) {
            //?????????30????????????

            mScheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    if (runFlag) {
                        if (finishTime == 0) {
                            //????????????
                            runFlag = false;
                            Intent intent = new Intent(ExamStateActivity.this, ExamActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            finishTime--;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (null != mFinishHint) {
                                        mFinishHint.setVisibility(View.VISIBLE);
                                        mFinishHint.setText("?????????????????????????????????" + finishTime + "????????????");
                                    }

                                }
                            });
                        }
                    }

                }
            }, 0, 1000, TimeUnit.MILLISECONDS);
        }


    }


    @OnClick({R.id.startexam1, R.id.startexa2, R.id.startexam3})
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("examinationId", examinationId);
        intent.putExtra("examinerId", examinerId);
        switch (view.getId()) {
            case R.id.startexam1:
                if (BuildConfig.DEBUG){
                    intent.setClass(this, ExamTheoryActivity.class);
                    ArmsUtils.startActivity(intent);
                    finish();
                    return;
                }
                if (!NetworkUtils.getNetworkType()) {
                    ArmsUtils.snackbarText("??????????????????????????????????????????");
                    return;
                }
                if (examStatus == 1) {
                    intent.setClass(this, ExamTheoryActivity.class);
                    ArmsUtils.startActivity(intent);
                    finish();
                }else {
                   ArmsUtils.snackbarText("?????????????????????");
                }

                break;
            case R.id.startexa2:
                if (BuildConfig.DEBUG){
                    intent.setClass(this, ExamAnalyseActivity.class);
                    ArmsUtils.startActivity(intent);
                    finish();
                    return;
                }
                if (!NetworkUtils.getNetworkType()) {
                    ArmsUtils.snackbarText("??????????????????????????????????????????");
                    return;
                }
                if (analyseStatus == 1) {
                    if (examStatus == 1){
                        ArmsUtils.snackbarText("????????????????????????");
                        return;
                    }
                    intent.setClass(this, ExamAnalyseActivity.class);
                    ArmsUtils.startActivity(intent);
                    finish();
                }else {
                    ArmsUtils.snackbarText("????????????????????????");
                }

                break;
            case R.id.startexam3:
                if (BuildConfig.DEBUG){
                    intent.setClass(this, ExamOperationActivity.class);
                    ArmsUtils.startActivity(intent);
                    finish();
                    return;
                }
                if (!NetworkUtils.getNetworkType()) {
                    ArmsUtils.snackbarText("??????????????????????????????????????????");
                    return;
                }
                if (operationStatus == 1) {
                    /*if (analyseStatus == 1) {
                        ArmsUtils.snackbarText("???????????????????????????");
                        return;
                    }*/
                    intent.setClass(this, ExamOperationActivity.class);
                    ArmsUtils.startActivity(intent);
                    finish();
                }else {
                    ArmsUtils.snackbarText("????????????????????????");
                }

                break;
        }
    }
}