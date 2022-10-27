package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.SPUtils;
import com.dy.huibiao_f80.di.component.DaggerSystemSetComponent;
import com.dy.huibiao_f80.mvp.contract.SystemSetContract;
import com.dy.huibiao_f80.mvp.presenter.SystemSetPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class SystemSetActivity extends BaseActivity<SystemSetPresenter> implements SystemSetContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.devicenumber)
    EditText mDevicenumber;
    @BindView(R.id.edtmoudle)
    Button mEdtmoudle;
    @BindView(R.id.plaurl)
    EditText mPlaurl;
    @BindView(R.id.checknewversion)
    Button mChecknewversion;
    @BindView(R.id.save)
    Button mSave;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSystemSetComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_systemset; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mDevicenumber.setText(Constants.DEVICENUM);
        mPlaurl.setText(Constants.URL);
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

    @OnClick({R.id.edtmoudle, R.id.checknewversion, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtmoudle:
                break;
            case R.id.checknewversion:
                break;
            case R.id.save:
                String devicenum = mDevicenumber.getText().toString();
                String plaurl = mPlaurl.getText().toString();
                if (!URLUtil.isNetworkUrl(plaurl)) {
                    ArmsUtils.snackbarText("请输入合法平台地址");
                    return;
                }
                SPUtils.put(this, Constants.KEY_DEVICENUM, devicenum);
                Constants.DEVICENUM = devicenum;
                SPUtils.put(this, Constants.KEY_URL, plaurl);
                Constants.URL = plaurl;
                ArmsUtils.snackbarText("当前内容已保存");
                break;
        }
    }
}