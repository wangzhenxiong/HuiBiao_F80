package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.app.utils.SPUtils;
import com.dy.huibiao_f80.bean.PrintMessage;
import com.dy.huibiao_f80.bean.UpdateMessage;
import com.dy.huibiao_f80.di.component.DaggerSystemSetComponent;
import com.dy.huibiao_f80.mvp.contract.SystemSetContract;
import com.dy.huibiao_f80.mvp.presenter.SystemSetPresenter;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

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
    @BindView(R.id.versionname)
    TextView mVersionname;
    private AlertDialog mSportDialog;
    private String username;

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
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        if (username.equals("hbadmin")) {
            mPlaurl.setEnabled(true);
            mDevicenumber.setEnabled(true);
        } else {
            mPlaurl.setEnabled(false);
            mDevicenumber.setEnabled(false);
        }
        mDevicenumber.setText(Constants.DEVICENUM);
        mPlaurl.setText(Constants.URL);
        mSportDialog = new SpotsDialog.Builder().setContext(getActivity()).build();
        mVersionname.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    public void showLoading() {
        LogUtils.d("showLoading");
        if (!mSportDialog.isShowing()) {
            mSportDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        LogUtils.d("hideLoading");
        if (mSportDialog.isShowing()) {
            mSportDialog.dismiss();
        }
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
                makePrintItemChoseDialog();
                break;
            case R.id.checknewversion:
                mPresenter.checkNewVersion();
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

    private void makePrintItemChoseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        PrintMessage printMessage = Constants.CheckPrintMessage();

        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.print_itemchose_layout, null);
        CheckBox checkBox_project = (CheckBox) inflate.findViewById(R.id.check_project);
        CheckBox checkBox_gallery = (CheckBox) inflate.findViewById(R.id.check_gallery);
        CheckBox checkBox_samplenum = (CheckBox) inflate.findViewById(R.id.check_samplenum);
        CheckBox checkBox_samplename = (CheckBox) inflate.findViewById(R.id.check_samplename);
        CheckBox checkBox_standname = (CheckBox) inflate.findViewById(R.id.check_standname);
        CheckBox checkBox_resultunit = (CheckBox) inflate.findViewById(R.id.check_resultunit);
        CheckBox checkBox_device = (CheckBox) inflate.findViewById(R.id.check_device);
        CheckBox checkBox_testtime = (CheckBox) inflate.findViewById(R.id.check_testtime);
        CheckBox checkBox_beunit = (CheckBox) inflate.findViewById(R.id.check_beunit);
        CheckBox checkBox_sampleplace = (CheckBox) inflate.findViewById(R.id.check_sampleplace);
        CheckBox checkBox_testpeople = (CheckBox) inflate.findViewById(R.id.check_testpeople);
        CheckBox checkBox_jujdger = (CheckBox) inflate.findViewById(R.id.check_jujdger);
        CheckBox checkBox_all = (CheckBox) inflate.findViewById(R.id.check_all);
        checkBox_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBox_device.setChecked(isChecked);
                checkBox_testtime.setChecked(isChecked);
                checkBox_beunit.setChecked(isChecked);
                checkBox_sampleplace.setChecked(isChecked);
                checkBox_testpeople.setChecked(isChecked);
                checkBox_jujdger.setChecked(isChecked);
            }
        });
        EditText ed_sampleplace = (EditText) inflate.findViewById(R.id.ed_sampleplace);
        EditText ed_testpeople = (EditText) inflate.findViewById(R.id.ed_testpeople);
        EditText ed_jujdger = (EditText) inflate.findViewById(R.id.ed_jujdger);

        checkBox_project.setChecked(printMessage.isCheckBox_project());
        checkBox_gallery.setChecked(printMessage.isCheckBox_gallery());
        checkBox_samplenum.setChecked(printMessage.isCheckBox_samplenum());
        checkBox_samplename.setChecked(printMessage.isCheckBox_samplename());
        checkBox_standname.setChecked(printMessage.isCheckBox_standname());
        checkBox_resultunit.setChecked(printMessage.isCheckBox_resultunit());
        checkBox_device.setChecked(printMessage.isCheckBox_device());
        checkBox_testtime.setChecked(printMessage.isCheckBox_testtime());
        checkBox_beunit.setChecked(printMessage.isCheckBox_beunit());
        checkBox_sampleplace.setChecked(printMessage.isCheckBox_sampleplace());
        checkBox_testpeople.setChecked(printMessage.isCheckBox_testpeople());
        checkBox_jujdger.setChecked(printMessage.isCheckBox_jujdger());
        //checkBox_all.setChecked(printMessage.isCheckBox_all());
        ed_sampleplace.setText(printMessage.getEd_sampleplace());
        ed_testpeople.setText(printMessage.getEd_testpeople());
        ed_jujdger.setText(printMessage.getEd_jujdger());

        builder.setView(inflate);
        AlertDialog alertDialog = builder.create();

        ((Button) inflate.findViewById(R.id.btn_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printMessage.setCheckBox_project(checkBox_project.isChecked());
                printMessage.setCheckBox_gallery(checkBox_gallery.isChecked());
                printMessage.setCheckBox_samplenum(checkBox_samplenum.isChecked());
                printMessage.setCheckBox_samplename(checkBox_samplename.isChecked());
                printMessage.setCheckBox_standname(checkBox_standname.isChecked());
                printMessage.setCheckBox_resultunit(checkBox_resultunit.isChecked());
                printMessage.setCheckBox_testtime(checkBox_testtime.isChecked());
                printMessage.setCheckBox_beunit(checkBox_beunit.isChecked());
                printMessage.setCheckBox_device(checkBox_device.isChecked());
                printMessage.setCheckBox_sampleplace(checkBox_sampleplace.isChecked());
                printMessage.setCheckBox_testpeople(checkBox_testpeople.isChecked());
                printMessage.setCheckBox_jujdger(checkBox_jujdger.isChecked());
                //printMessage.setCheckBox_all(checkBox_jujdger.isChecked());
                printMessage.setCheckBox_device(checkBox_all.isChecked());
                printMessage.setEd_sampleplace(ed_sampleplace.getText().toString());
                printMessage.setEd_testpeople(ed_testpeople.getText().toString());
                printMessage.setEd_jujdger(ed_jujdger.getText().toString());
                FileUtils.saveAsFileWriter(new Gson().toJson(printMessage), Constants.PATH_PRINTMESSAGEBEAN);
                alertDialog.dismiss();
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
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

    @Override
    public Activity getActivity() {
        return this;
    }
}