package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.di.component.DaggerChoseGalleryJTJComponent;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.contract.ChoseGalleryJTJContract;
import com.dy.huibiao_f80.mvp.presenter.ChoseGalleryJTJPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ChoseGalleryJTJActivity extends BaseActivity<ChoseGalleryJTJPresenter> implements ChoseGalleryJTJContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.checkbox1)
    CheckBox mCheckbox1;
    @BindView(R.id.background1)
    LinearLayout mBackground1;
    @BindView(R.id.checkbox2)
    CheckBox mCheckbox2;
    @BindView(R.id.background2)
    LinearLayout mBackground2;
    @BindView(R.id.checkall)
    CheckBox mCheckall;
    @BindView(R.id.title_gallery)
    TextView mTitleGallery;
    @BindView(R.id.sampleserial)
    AutoCompleteTextView mSampleserial;
    @BindView(R.id.samplename)
    AutoCompleteTextView mSamplename;
    @BindView(R.id.dr)
    AutoCompleteTextView mDr;
    /*@BindView(R.id.camerasetting)
    Button mCamerasetting;*/
    @BindView(R.id.layout1)
    LinearLayout mLayout1;
    @BindView(R.id.btn_clean)
    Button mBtnClean;
    @BindView(R.id.btn_gallerychange)
    Button mBtnGallerychange;
    @BindView(R.id.btn_starttest)
    Button mBtnStarttest;

    @Inject
    AlertDialog mSportDialog;
    @BindView(R.id.checkbox3)
    CheckBox mCheckbox3;
    @BindView(R.id.background3)
    LinearLayout mBackground3;
    @BindView(R.id.checkbox4)
    CheckBox mCheckbox4;
    @BindView(R.id.background4)
    LinearLayout mBackground4;
    private int nowCheckId = R.id.background1;
    private int nowCheckindex = 1;
    private TestRecord nowCheckGallery;
    private String project;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChoseGalleryJTJComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_chosegalleryjtj; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        project = intent.getStringExtra("project");
        mPresenter.initJTJUSB();
        mCheckall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckbox1.setChecked(isChecked);
                mCheckbox2.setChecked(isChecked);
                mCheckbox3.setChecked(isChecked);
                mCheckbox4.setChecked(isChecked);
            }
        });
        mSampleserial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.d(s);
                String sampleserial = s.toString();
                nowCheckGallery.setSamplenum(sampleserial);
            }
        });
        mSamplename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.d(s);
                String samplename = s.toString();
                nowCheckGallery.setSamplename(samplename);
            }
        });
        //先将所有通道设为未选择
        List<GalleryBean> mJTJGalleryBeanList = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList;
        for (int i = 0; i < mJTJGalleryBeanList.size(); i++) {
            mJTJGalleryBeanList.get(i).setCheckd(false);
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
    public void showSportDialog(String message) {
        mSportDialog.setMessage(message);
        if (!mSportDialog.isShowing()) {
            mSportDialog.show();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void hideSportDialog() {
        if (mSportDialog.isShowing()) {
            mSportDialog.dismiss();
        }
    }

    @Override
    public void buildGalleryView() {
        // TODO: 10/19/22  新建通道
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<GalleryBean> mJTJGalleryBeanList = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList;
                for (int i = 0; i < mJTJGalleryBeanList.size(); i++) {
                    int galleryNum = mJTJGalleryBeanList.get(i).getGalleryNum();
                    if (galleryNum == 1) {
                        mBackground1.setVisibility(View.VISIBLE);
                    }
                    if (galleryNum == 2) {
                        mBackground2.setVisibility(View.VISIBLE);
                    }
                    if (galleryNum == 3) {
                        mBackground3.setVisibility(View.VISIBLE);
                    }
                    if (galleryNum == 4) {
                        mBackground4.setVisibility(View.VISIBLE);
                    }
                }
                refishMessage(1, R.id.background1);
            }
        });


    }

    @Override
    public void reBuildGalleryView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBackground1.setVisibility(View.GONE);
                mBackground2.setVisibility(View.GONE);
                mBackground3.setVisibility(View.GONE);
                mBackground4.setVisibility(View.GONE);
               mPresenter.initJTJUSB();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_clean, R.id.btn_gallerychange, R.id.btn_starttest, R.id.background1, R.id.background2, R.id.background3, R.id.background4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clean:
                clean();
                break;
            case R.id.btn_gallerychange:
                mPresenter.changeGallery();
                break;
            case R.id.btn_starttest:
                nextStep();
                break;
            case R.id.background1:
                refishMessage(1, view.getId());
                break;
            case R.id.background2:
                refishMessage(2, view.getId());
                break;
            case R.id.background3:
                refishMessage(3, view.getId());
                break;
            case R.id.background4:
                refishMessage(4, view.getId());
                break;
        }
    }

    private void nextStep() {
        boolean ischeck=false;
        List<GalleryBean> mJTJGalleryBeanList = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList;
        for (int i = 0; i < mJTJGalleryBeanList.size(); i++) {
            GalleryBean galleryBean = mJTJGalleryBeanList.get(i);
            if (i==0){
                boolean checked = mCheckbox1.isChecked();
                if (checked){
                    ischeck=true;
                }
                galleryBean.setCheckd(checked);

            }
            if (i==1){
                boolean checked = mCheckbox2.isChecked();
                if (checked){
                    ischeck=true;
                }
                galleryBean.setCheckd(checked);
            }
            if (i==2){
                boolean checked = mCheckbox3.isChecked();
                if (checked){
                    ischeck=true;
                }
                galleryBean.setCheckd(checked);
            }
            if (i==3){
                boolean checked = mCheckbox4.isChecked();
                if (checked){
                    ischeck=true;
                }
                galleryBean.setCheckd(checked);
            }
        }
         if (ischeck){
             Intent content1 = new Intent(getActivity(), TestSettingJTJActivity.class);
             content1.putExtra("data", nowCheckindex);
             content1.putExtra("project", project);
             ArmsUtils.startActivity(getActivity(), content1);
         }else {
             ArmsUtils.snackbarText("请选择通道");
         }
    }

    private void clean() {
        LogUtils.d(MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList);

        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.size(); i++) {
            MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(i).removedata();
        }
        refishMessage(nowCheckindex, nowCheckId);
        LogUtils.d(MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList);

    }

    private void refishMessage(int i, int id) {

        findViewById(nowCheckId).setBackground(getResources().getDrawable(R.drawable.background_item_gray));
        findViewById(id).setBackground(getResources().getDrawable(R.drawable.background_item_blue));
        nowCheckId = id;
        nowCheckindex = i;
        mTitleGallery.setText("检测孔：" + i);

        nowCheckGallery = (TestRecord) MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(i - 1);
        mSampleserial.setText(nowCheckGallery.getSamplenum());
        mSamplename.setText(nowCheckGallery.getSamplename());
        //mDr.setText(nowCheckGallery.getDilutionratio() + "");
    }
}