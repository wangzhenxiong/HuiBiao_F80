package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerPracticeComponent;
import com.dy.huibiao_f80.mvp.contract.PracticeContract;
import com.dy.huibiao_f80.mvp.presenter.PracticePresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class PracticeActivity extends BaseActivity<PracticePresenter> implements PracticeContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.samplingofsingle)
    TextView mSamplingofsingle;
    @BindView(R.id.startingtest)
    TextView mStartingtest;
    @BindView(R.id.record)
    TextView mRecord;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPracticeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_practice; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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

    @OnClick({R.id.samplingofsingle, R.id.startingtest, R.id.record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.samplingofsingle:
                ArmsUtils.startActivity(new Intent(getActivity(),SamplingActivity.class));
                break;
            case R.id.startingtest:
                ArmsUtils.startActivity(new Intent(getActivity(),StartTestActivity.class));
                break;
            case R.id.record:
                ArmsUtils.startActivity(new Intent(getActivity(),RecordActivity.class));
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}