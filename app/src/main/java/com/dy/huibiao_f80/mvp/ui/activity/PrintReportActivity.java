package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.api.back.BeginTestForm_Back;
import com.dy.huibiao_f80.app.utils.DataUtils;
import com.dy.huibiao_f80.bean.ReportBean;
import com.dy.huibiao_f80.di.component.DaggerPrintReportComponent;
import com.dy.huibiao_f80.mvp.contract.PrintReportContract;
import com.dy.huibiao_f80.mvp.presenter.PrintReportPresenter;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class PrintReportActivity extends BaseActivity<PrintReportPresenter> implements PrintReportContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.parent_ll)
    LinearLayout mParentLl;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    private String examinationId;
    private String examinerId;
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPrintReportComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_printreport; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        examinationId = intent.getStringExtra("examinationId");
        examinerId = intent.getStringExtra("examinerId");
        mScheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) ArmsUtils.obtainAppComponentFromContext(this).executorService();
        mPresenter.getReportMoudle(examinerId, examinationId);
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
    public void showReports(BeginTestForm_Back back) {
        MyAppLocation.myAppLocation.mExamOperationService.setBeginTestForm_back(back);
        
        BeginTestForm_Back.EntityBean entity = back.getEntity();
        List<BeginTestForm_Back.EntityBean.TestFormListBean> testFormList = entity.getTestFormList();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < testFormList.size(); i++) {
            LinearLayout linearLayout1 = new LinearLayout(this);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params1.setMargins(0, 20, 0, 0);
            linearLayout1.setLayoutParams(params1);
            BeginTestForm_Back.EntityBean.TestFormListBean testFormListBean = testFormList.get(i);
            String name = testFormListBean.getName();
            TextView textView = new TextView(PrintReportActivity.this);
            textView.setText(name);
            textView.setLayoutParams(params);
            textView.setTextSize(24);
            linearLayout1.addView(textView);
            List<BeginTestForm_Back.EntityBean.TestFormListBean.TestFormDetailListBean> testFormDetailList = testFormListBean.getTestFormDetailList();
            ReportBean reportBean = MyAppLocation.myAppLocation.mExamOperationService.getReportBean();

            for (int i1 = 0; i1 < testFormDetailList.size(); i1++) {
                BeginTestForm_Back.EntityBean.TestFormListBean.TestFormDetailListBean testFormDetailListBean = testFormDetailList.get(i1);
                View inflate = LayoutInflater.from(this).inflate(R.layout.layout1, null);
                inflate.setTag("parent");

                TextInputLayout textInputLayout = (TextInputLayout) inflate.findViewById(R.id.answerlayout);
                AutoCompleteTextView child = (AutoCompleteTextView) inflate.findViewById(R.id.answer1);

                textInputLayout.setTag(testFormDetailListBean.getTestFormId());
                textInputLayout.setHint(testFormDetailListBean.getFieldName());
                textInputLayout.setLayoutParams(params);
                String id = testFormDetailListBean.getId();
                child.setTag(id);
                if (null != reportBean) {
                    List<ReportBean.TestFormDetail> testFormDetailList1 = reportBean.getTestFormDetailList();
                    for (int i2 = 0; i2 < testFormDetailList1.size(); i2++) {
                        ReportBean.TestFormDetail testFormDetail = testFormDetailList1.get(i2);
                        if (testFormDetail.getTestFormDetailId().equals(id)) {
                            child.setText(testFormDetail.getAnswer());
                        }
                    }
                }
                linearLayout1.addView(inflate);
            }


            mParentLl.addView(linearLayout1);

        }


    }

    private void saveReport() {
        ReportBean reportBean = new ReportBean();
        reportBean.setCreateTime(DataUtils.getNowtimeyyymmddhhmmss());
        reportBean.setExaminationId(examinationId);
        reportBean.setExaminerId(examinerId);
        ArrayList<ReportBean.TestFormDetail> testFormDetailList = new ArrayList<>();
        int childCount = mParentLl.getChildCount();
        LogUtils.d(childCount);
        for (int i = 0; i < childCount; i++) {

            View childAt = mParentLl.getChildAt(i);
            LinearLayout childAt1 = (LinearLayout) childAt;
            int childCount1 = childAt1.getChildCount();
            LogUtils.d(childCount1);
            for (int i1 = 0; i1 < childCount1; i1++) {
                View childAt2 = childAt1.getChildAt(i1);
                LogUtils.d(childAt2);
                Object tag = childAt2.getTag();
                if (null != tag) {
                    TextInputLayout answerlayout = (TextInputLayout) childAt2.findViewById(R.id.answerlayout);
                    AutoCompleteTextView answer = (AutoCompleteTextView) childAt2.findViewById(R.id.answer1);
                    LogUtils.d(answerlayout);
                    LogUtils.d(answer);
                    String testFormId = (String) answerlayout.getTag();
                    String fieldName = answerlayout.getHint().toString();
                    LogUtils.d(fieldName);
                    String answers = answer.getText().toString();
                    String testFormDetailId = (String) answer.getTag();
                    ReportBean.TestFormDetail bean = new ReportBean.TestFormDetail();
                    bean.setAnswer(answers);
                    bean.setFieldName(fieldName);
                    bean.setTestFormId(testFormId);
                    bean.setTestFormDetailId(testFormDetailId);
                    LogUtils.d(testFormId + " " + testFormDetailId + " " + fieldName + " " + answer);
                    testFormDetailList.add(bean);

                }
            }


        }
        reportBean.setTestFormDetailList(testFormDetailList);
        String s = new Gson().toJson(reportBean);
        LogUtils.d(s);
        MyAppLocation.myAppLocation.mExamOperationService.setReportBean(reportBean);
        killMyself();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.toolbar, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.btn_save:
                saveReport();
                break;
        }
    }
}