package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.DataBaseUtil;
import com.dy.huibiao_f80.app.utils.NetworkUtils;
import com.dy.huibiao_f80.app.utils.SPUtils;
import com.dy.huibiao_f80.bean.UpdateMessage;
import com.dy.huibiao_f80.di.component.DaggerHomeComponent;
import com.dy.huibiao_f80.mvp.contract.HomeContract;
import com.dy.huibiao_f80.mvp.presenter.HomePresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

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
    private AlertDialog sportDialog;

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
        sportDialog = new SpotsDialog.Builder().setContext(this).setCancelable(true).build();
        mPresenter.checkNewVersion();
    }

    @Override
    public void showLoading() {
        if (null==sportDialog){
            return;
        }
        if (!sportDialog.isShowing()) {
            sportDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (null==sportDialog){
            return;
        }
        if (sportDialog.isShowing()) {
            sportDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean database = (boolean) SPUtils.get(getActivity(), "database", false);
        if (!database) {
            getmessageFromAssets();
            SPUtils.put(getActivity(), "database", true);
        }
    }
    private void getmessageFromAssets() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //设置农残模块流水号
                    DataBaseUtil util = new DataBaseUtil(getActivity(), BuildConfig.APPLICATION_ID);
                    //用户列表
                    util.copyDataBase(R.raw.f80, "f80.db");



                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


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

    @Override
    public void makeDialogNewVersion(UpdateMessage message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("makeDialogNewVersion");
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setPositiveButton(getString(R.string.download), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.downLoadAPK(message.getResult().getLink());

                        String string = getString(R.string.toasmessage_download);
                        ArmsUtils.snackbarText(string);
                    }
                });
                builder.setNeutralButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setTitle(getString(R.string.newversionmessage))
                        .setMessage(message.getResult().getDesc())
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false);
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);//设置弹出框失去焦点是否隐藏
                dialog.show();
            }
        });
    }

    @OnClick({R.id.practice, R.id.train, R.id.exam})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.practice:
                ArmsUtils.startActivity(new Intent(getActivity(),PracticeActivity.class));
                break;
            case R.id.train:
                jumpUriToBrowser(this,Constants.URL_TRAINING);
                //ArmsUtils.startActivity(new Intent(getActivity(),TrainActivity.class));
                break;
            case R.id.exam:
                if (!NetworkUtils.getNetworkType()){
                    ArmsUtils.snackbarText("当前无网络连接，请检查后重试");
                  return;
                }
                mPresenter.existExam(Constants.URL,Constants.DEVICENUM);
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

    public static void jumpUriToBrowser(Context context, String url) {
        if (url.startsWith("www.")) {
            url = "http://" + url;
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            Toast.makeText(context, "网址错误", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        // 设置意图动作为打开浏览器
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        // 声明一个Uri
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.location:
                launchActivity(new Intent(getActivity(), BaiduMapActivity.class));
                break;
            case R.id.balance:
                launchActivity(new Intent(getActivity(), BalanceCalibrationActivity.class));
                break;
            case R.id.setting:
                ArmsUtils.startActivity(new Intent(getActivity(),SettingLoginActivity.class));
                break;
        }
        return true;
    }
}