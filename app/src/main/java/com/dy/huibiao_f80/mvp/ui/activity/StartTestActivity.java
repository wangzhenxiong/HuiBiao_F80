package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.di.component.DaggerStartTestComponent;
import com.dy.huibiao_f80.mvp.contract.StartTestContract;
import com.dy.huibiao_f80.mvp.presenter.StartTestPresenter;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.CharIndexView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class StartTestActivity extends BaseActivity<StartTestPresenter> implements StartTestContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.rv_main)
    RecyclerView mRvMain;
    @BindView(R.id.iv_main)
    CharIndexView mIvMain;
    @BindView(R.id.tv_index)
    TextView mTvIndex;
    @BindView(R.id.layout1)
    LinearLayout mLayout1;
    @BindView(R.id.btn_nextstep)
    Button mBtnNextstep;
    @BindView(R.id.fggd)
    TextView mFggd;
    @BindView(R.id.jtj)
    TextView mJtj;
    @BindView(R.id.ed_seach)
    EditText mEdSeach;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStartTestComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_starttest; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
}