package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerExamComponent;
import com.dy.huibiao_f80.mvp.contract.ExamContract;
import com.dy.huibiao_f80.mvp.presenter.ExamPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ExamActivity extends BaseActivity<ExamPresenter> implements ExamContract.View {

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
    @BindView(R.id.realname)
    AutoCompleteTextView mRealname;
    @BindView(R.id.cardnumber)
    AutoCompleteTextView mCardnumber;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private String id;
    private int personTestMethod;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExamComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_exam; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        personTestMethod = intent.getIntExtra("personTestMethod",1);
        if (BuildConfig.DEBUG){
            mRealname.setText("小虎1");
            mCardnumber.setText("43072420001010113X");
        }
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

    @OnClick({R.id.cardnumber, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardnumber:
                break;
            case R.id.btn_login:
                String name = mRealname.getText().toString();
                String number = mCardnumber.getText().toString();
                if (name.isEmpty()){
                    ArmsUtils.snackbarText("请输入真实姓名");
                    return;
                }
                if (number.isEmpty()){
                    ArmsUtils.snackbarText("请输入准考证号");
                    return;
                }
                mPresenter.checkExaminer(id,name,number,personTestMethod);
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}