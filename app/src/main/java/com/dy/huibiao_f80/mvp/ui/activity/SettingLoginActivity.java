package com.dy.huibiao_f80.mvp.ui.activity;

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
import com.dy.huibiao_f80.di.component.DaggerSettingLoginComponent;
import com.dy.huibiao_f80.mvp.contract.SettingLoginContract;
import com.dy.huibiao_f80.mvp.presenter.SettingLoginPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class SettingLoginActivity extends BaseActivity<SettingLoginPresenter> implements SettingLoginContract.View {

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
    @BindView(R.id.username)
    AutoCompleteTextView mUsername;
    @BindView(R.id.password)
    AutoCompleteTextView mPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_settinglogin; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
       if (BuildConfig.DEBUG){
           mUsername.setText("admin");
           mPassword.setText("123456");
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

    @OnClick({R.id.password, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.password:
                break;
            case R.id.btn_login:
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                if (username.isEmpty()||password.isEmpty()){
                    ArmsUtils.snackbarText("请输入用户名密码");
                    return;
                }
                if ((username.equals("admin")&&password.equals("admin"))||(username.equals("hbadmin")&&password.equals("hbadmin"))){

                    Intent content = new Intent(this, SetingActivity.class);
                    content.putExtra("username",username);
                    ArmsUtils.startActivity(content);
                }else {
                    ArmsUtils.snackbarText("用户名密码错误");
                }
                break;
        }
    }
}