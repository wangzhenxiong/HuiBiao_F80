package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.service.ExamOperationService;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.bean.eventBusBean.FGTestMessageBean;
import com.dy.huibiao_f80.di.component.DaggerTestResultFGGDComponent;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.contract.TestResultFGGDContract;
import com.dy.huibiao_f80.mvp.presenter.TestResultFGGDPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.FGGDTestResultAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class TestResultFGGDActivity extends BaseActivity<TestResultFGGDPresenter> implements TestResultFGGDContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.controvalue)
    TextView mControvalue;
    @BindView(R.id.choseall)
    CheckBox mChoseall;
    @BindView(R.id.recylerview)
    RecyclerView mRecylerview;
    @BindView(R.id.btn_retest)
    Button mBtnRetest;
    @BindView(R.id.btn_restart)
    Button mBtnRestart;
    @BindView(R.id.btn_record)
    Button mBtnRecord;
    @BindView(R.id.btn_backhome)
    Button mBtnBackhome;
    @BindView(R.id.btn_writereport)
    Button mBtnWritereport;
    @BindView(R.id.toolbar_time)
    TextView mToolbarTime;
    private List<GalleryBean> dataList = new ArrayList<>();
    private FGGDTestResultAdapter fggdAdapter;
    String pjName;
    //private MaterialDialog mUploadDialog;

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent2(ExamOperationService.ExamOperationServiceEventBean tags) {

        if (tags.getTime() == 0) {
            if (null != mToolbarTime) {
                mToolbarTime.setText("正在提交考试结果");
            }
            return;
        }
        String timestring = tags.getTimestring();
        if (null != mToolbarTime) {
            mToolbarTime.setText(timestring);
        }


    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTestResultFGGDComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_testresultfggd; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getdata();
        if (dataList.size() > 0) {
            pjName = dataList.get(0).getmProjectMessage().getPjName();
            mTitle.setText("分光光度检测——" + pjName);
        } else {
            mTitle.setText("分光光度检测");
        }
        ArmsUtils.configRecyclerView(mRecylerview, new GridLayoutManager(this, 1));
        fggdAdapter = new FGGDTestResultAdapter(R.layout.layout_fggftestresult_item, dataList);
        fggdAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.checkbox) {
                    GalleryBean galleryBean = dataList.get(position);
                    if (galleryBean.isCheckd()) {
                        galleryBean.setCheckd(false);
                        if (mChoseall.isChecked()) {
                            mChoseall.setChecked(false);
                        }
                    } else {
                        galleryBean.setCheckd(true);
                    }
                }
            }
        });
        fggdAdapter.setEmptyView(R.layout.emptyview, (ViewGroup) mRecylerview.getParent());
        mRecylerview.setAdapter(fggdAdapter);
        mChoseall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = mChoseall.isChecked();
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).setCheckd(checked);
                }
                fggdAdapter.notifyDataSetChanged();
            }
        });

        if (MyAppLocation.myAppLocation.mExamOperationService.isStartExamOperation()) {
            mBtnWritereport.setVisibility(View.VISIBLE);
            mBtnBackhome.setVisibility(View.VISIBLE);
        } else {
            mBtnWritereport.setVisibility(View.GONE);
            mBtnBackhome.setVisibility(View.GONE);
        }
        makeDialog();
    }


    private void getdata() {
        dataList.clear();
        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.size(); i++) {
            GalleryBean galleryBean = MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i);
            if (galleryBean.getState() != 0) {
                dataList.add(galleryBean);
            }
        }
       /* for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.size(); i++) {
            GalleryBean galleryBean = MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i);
            if (galleryBean.isCheckd()) {
                galleryBean.setCheckd(false);
            }
        }*/

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FGTestMessageBean tags) {

        switch (tags.tag) {
            case 0:
                fggdAdapter.notifyDataSetChanged();
                //LogUtils.d(MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList);

                if (dataList.size() > 0) {
                    BaseProjectMessage baseProjectMessage = dataList.get(0).getmProjectMessage();

                    TestRecord testRecord = (TestRecord) dataList.get(0);
                    int remainingtime = testRecord.getRemainingtime();

                    if (remainingtime <= 0) {
                        ((TextView) inflate.findViewById(R.id.message)).setText("检测完成");
                        Button cancle=((Button) inflate.findViewById(R.id.cancle));
                        cancle.setVisibility(View.GONE);
                        Button confirm=((Button) inflate.findViewById(R.id.confirm));
                        confirm.setVisibility(View.VISIBLE);
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               alertDialog.dismiss();
                            }
                        });


                        initControValue(baseProjectMessage.getMethod_sp());

                    } else {
                        ((TextView) inflate.findViewById(R.id.message)).setText("倒计时："+remainingtime+"S");
                        Button cancle=((Button) inflate.findViewById(R.id.cancle));
                        cancle.setVisibility(View.VISIBLE);
                        Button confirm=((Button) inflate.findViewById(R.id.confirm));
                        confirm.setVisibility(View.GONE);
                        cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                for (int i = 0; i < dataList.size(); i++) {
                                    GalleryBean galleryBean = dataList.get(i);
                                    galleryBean.stopFGGDTest();
                                }
                            }
                        });

                        /*mUploadDialog.setContent("正在检测：" + remainingtime + "S");
                        mUploadDialog.getActionButton(DialogAction.POSITIVE).setVisibility(View.GONE);
                        mUploadDialog.getActionButton(DialogAction.NEUTRAL).setVisibility(View.VISIBLE);*/
                    }
                }
                break;
        }
    }

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private View inflate;
    private void makeDialog() {
        builder = new AlertDialog.Builder(this);
        inflate = LayoutInflater.from(this).inflate(R.layout.fggd_test_dialog_layout, null);
        builder.setView(inflate);
        builder.setTitle("正在检测");
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        /*if (mUploadDialog == null) {
            mUploadDialog = new MaterialDialog.Builder(this)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .positiveText("确定")
                    .neutralText("取消")
                    .contentGravity(GravityEnum.CENTER)
                    .build();
        }
        mUploadDialog.setTitle("正在测试");
        mUploadDialog.getActionButton(DialogAction.POSITIVE).setVisibility(View.GONE);
        mUploadDialog.getActionButton(DialogAction.NEUTRAL).setVisibility(View.VISIBLE);
        mUploadDialog.show();*/
    }

    private void initControValue(int method_sp) {
        if (method_sp == 0) {
            float controValue0 = Constants.getControValue0();
            if (controValue0==-1){
                mControvalue.setText(getString(R.string.sampleerro_message1));
            }else if (controValue0==-2){
                mControvalue.setText(getString(R.string.sampleerro_message2));
            }else {
                mControvalue.setText("当前对照:" + Constants.getControValue0());
            }
            mControvalue.setVisibility(View.VISIBLE);
        } else if (method_sp == 1) {
            float controValue1 = Constants.getControValue1();
            if (controValue1==-1){
                mControvalue.setText(getString(R.string.sampleerro_message1));
            }else {
                mControvalue.setText("当前对照:" + Constants.getControValue1());
            }
            mControvalue.setVisibility(View.VISIBLE);
        } else {
            mControvalue.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_retest, R.id.btn_restart, R.id.btn_record, R.id.btn_backhome, R.id.btn_writereport})
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("project", pjName);
        switch (view.getId()) {
            case R.id.btn_backhome:
                ArmsUtils.startActivity(new Intent(this, ExamOperationActivity.class));

                break;
            case R.id.btn_writereport:
                Intent intent1 = new Intent(this, PrintReportActivity.class);
                intent1.putExtra("examinationId", MyAppLocation.myAppLocation.mExamOperationService.getExaminationId());
                intent1.putExtra("examinerId", MyAppLocation.myAppLocation.mExamOperationService.getExaminerId());
                intent1.putExtra("operationPaperId", MyAppLocation.myAppLocation.mExamOperationService.getNowOperationExam().getId());
                startActivity(intent1);

                break;
            case R.id.btn_retest://复检
                for (int i = 0; i < dataList.size(); i++) {
                    GalleryBean galleryBean = dataList.get(i);
                    if (galleryBean.checkd) {
                        intent.setClass(TestResultFGGDActivity.this, TestFGGDActivity.class);
                        ArmsUtils.startActivity(intent);
                        this.finish();
                        return;

                    }
                }
                ArmsUtils.snackbarText("请选择需要复检的数据");

                break;
            case R.id.btn_restart:
                intent.setClass(TestResultFGGDActivity.this, ChoseGalleryFGGDActivity.class);
                ArmsUtils.startActivity(intent);
                this.finish();
                break;
            case R.id.btn_record:
                intent.setClass(TestResultFGGDActivity.this, RecordActivity.class);

                ArmsUtils.startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ArmsUtils.startActivity(this, StartTestActivity.class);
    }
}