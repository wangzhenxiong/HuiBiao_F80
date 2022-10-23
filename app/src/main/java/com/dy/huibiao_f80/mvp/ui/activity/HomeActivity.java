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
import android.widget.TextView;
import android.widget.Toast;

import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerHomeComponent;
import com.dy.huibiao_f80.mvp.contract.HomeContract;
import com.dy.huibiao_f80.mvp.presenter.HomePresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.practice)
    TextView mPractice;
    @BindView(R.id.train)
    TextView mTrain;
    @BindView(R.id.exam)
    TextView mExam;
    @BindView(R.id.seting)
    Button mSeting;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.setTime();

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

    @Override
    public void setDataTime(String s) {
        mToolbarTitle.setText(s);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.practice, R.id.train, R.id.exam, R.id.seting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.practice:
                ArmsUtils.startActivity(new Intent(getActivity(),PracticeActivity.class));
                break;
            case R.id.train:
                ArmsUtils.startActivity(new Intent(getActivity(),TrainActivity.class));
                break;
            case R.id.exam:
                mPresenter.existExam(Constants.URL,Constants.DEVICENUM);
                break;
            case R.id.seting:
                ArmsUtils.startActivity(new Intent(getActivity(),SettingLoginActivity.class));
                break;
        }
    }
    private long mExitTime = 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            ArmsUtils.exitApp();
        }
    }
}