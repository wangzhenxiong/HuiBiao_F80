package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerRecordDetailComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.Sampling;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.contract.RecordDetailContract;
import com.dy.huibiao_f80.mvp.presenter.RecordDetailPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class RecordDetailActivity extends BaseActivity<RecordDetailPresenter> implements RecordDetailContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.samplename)
    TextView mSamplename;
    @BindView(R.id.testproject)
    TextView mTestproject;
    @BindView(R.id.testunit)
    TextView mTestunit;
    @BindView(R.id.testmoudle)
    TextView mTestmoudle;
    @BindView(R.id.testresult)
    TextView mTestresult;
    @BindView(R.id.judge)
    TextView mJudge;
    @BindView(R.id.standnum)
    TextView mStandnum;
    @BindView(R.id.samplingnumber)
    TextView mSamplingnumber;
    @BindView(R.id.samplingname)
    TextView mSamplingname;
    @BindView(R.id.beunits)
    TextView mBeunits;
    @BindView(R.id.samplingtime)
    TextView mSamplingtime;
    @BindView(R.id.btn_chosesampling)
    Button mBtnGallerychange;
    @BindView(R.id.btn_save)
    Button mBtnStarttest;
    @BindView(R.id.methodlimit)
    TextView mMethodlimit;

    private TestRecord testRecord;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRecordDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recorddetail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0L);
        testRecord = DBHelper.getTestRecordDao().load(id);
        initMessage(testRecord);
    }

    private void initMessage(TestRecord testRecord) {
        if (null == testRecord) {
            ArmsUtils.snackbarText("数据加载错误");
            finish();
            return;
        }
        Long samplingID = testRecord.getSamplingID();
        Sampling load = DBHelper.getSamplingDao().load(samplingID);
        if (null!=load){
            mSamplingname.setText(load.getSamplingName());
            mSamplingnumber.setText(load.getSamplingNumber());
            mBeunits.setText(load.getUnitDetected());
            mSamplingtime.setText(load.getCreationTimeyymmddhhssmm());
        }
        mSamplename.setText(testRecord.getSamplename());
        mTestproject.setText(testRecord.getTest_project());
        mTestunit.setText(testRecord.getCov_unit());
        mTestmoudle.setText(testRecord.getTest_Moudle());
        mTestresult.setText(testRecord.getTestresult());
        mJudge.setText(testRecord.getDecisionoutcome());
        mStandnum.setText(testRecord.getStand_num());
        mMethodlimit.setText(testRecord.getMethodsDetectionLimit());

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
    Sampling sampling;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if (resultCode== Activity.RESULT_OK){
           long id = data.getLongExtra("id", 0L);
           sampling = DBHelper.getSamplingDao().load(id);
           if (null==sampling){
               return;
           }
           mSamplingname.setText(sampling.getSamplingName());
           mSamplingnumber.setText(sampling.getSamplingNumber());
           mBeunits.setText(sampling.getUnitDetected());
           mSamplingtime.setText(sampling.getCreationTimeyymmddhhssmm());
       }
    }
    public static int  REQUESTCODE=100;
    @OnClick({R.id.btn_chosesampling, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chosesampling:
                Intent intent = new Intent(this, SamplingActivity.class);
                intent.putExtra("requestcode",REQUESTCODE);
                startActivityForResult(intent,REQUESTCODE);
                break;
            case R.id.btn_save:
                if (null!=sampling){
                    testRecord.setSamplingID(sampling.getId());
                    sampling.setTestingTime(testRecord.getTestingtime());
                    sampling.setTestResult(testRecord.getDecisionoutcome());
                    DBHelper.getSamplingDao().update(sampling);
                    ArmsUtils.snackbarText("已将检测信息同步至采样单");
                }


                break;
        }
    }
}