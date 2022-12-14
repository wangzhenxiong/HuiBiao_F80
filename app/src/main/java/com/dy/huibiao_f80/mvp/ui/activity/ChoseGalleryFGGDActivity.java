package com.dy.huibiao_f80.mvp.ui.activity;

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

import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.service.ExamOperationService;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.di.component.DaggerChoseGalleryFGGDComponent;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.contract.ChoseGalleryFGGDContract;
import com.dy.huibiao_f80.mvp.presenter.ChoseGalleryFGGDPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ChoseGalleryFGGDActivity extends BaseActivity<ChoseGalleryFGGDPresenter> implements ChoseGalleryFGGDContract.View, CompoundButton.OnCheckedChangeListener {

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
    @BindView(R.id.checkbox3)
    CheckBox mCheckbox3;
    @BindView(R.id.background3)
    LinearLayout mBackground3;
    @BindView(R.id.checkbox4)
    CheckBox mCheckbox4;
    @BindView(R.id.background4)
    LinearLayout mBackground4;
    @BindView(R.id.checkbox5)
    CheckBox mCheckbox5;
    @BindView(R.id.background5)
    LinearLayout mBackground5;
    @BindView(R.id.checkbox6)
    CheckBox mCheckbox6;
    @BindView(R.id.background6)
    LinearLayout mBackground6;
    @BindView(R.id.checkbox7)
    CheckBox mCheckbox7;
    @BindView(R.id.background7)
    LinearLayout mBackground7;
    @BindView(R.id.checkbox8)
    CheckBox mCheckbox8;
    @BindView(R.id.background8)
    LinearLayout mBackground8;
    @BindView(R.id.checkall1)
    CheckBox mCheckall1;
    @BindView(R.id.checkbox9)
    CheckBox mCheckbox9;
    @BindView(R.id.background9)
    LinearLayout mBackground9;
    @BindView(R.id.checkbox10)
    CheckBox mCheckbox10;
    @BindView(R.id.background10)
    LinearLayout mBackground10;
    @BindView(R.id.checkbox11)
    CheckBox mCheckbox11;
    @BindView(R.id.background11)
    LinearLayout mBackground11;
    @BindView(R.id.checkbox12)
    CheckBox mCheckbox12;
    @BindView(R.id.background12)
    LinearLayout mBackground12;
    @BindView(R.id.checkbox13)
    CheckBox mCheckbox13;
    @BindView(R.id.background13)
    LinearLayout mBackground13;
    @BindView(R.id.checkbox14)
    CheckBox mCheckbox14;
    @BindView(R.id.background14)
    LinearLayout mBackground14;
    @BindView(R.id.checkbox15)
    CheckBox mCheckbox15;
    @BindView(R.id.background15)
    LinearLayout mBackground15;
    @BindView(R.id.checkbox16)
    CheckBox mCheckbox16;
    @BindView(R.id.background16)
    LinearLayout mBackground16;
    @BindView(R.id.checkall2)
    CheckBox mCheckall2;
    @BindView(R.id.sampleserial)
    AutoCompleteTextView mSampleserial;
    @BindView(R.id.samplename)
    AutoCompleteTextView mSamplename;
    @BindView(R.id.layout1)
    LinearLayout mLayout1;
    @BindView(R.id.btn_clean)
    Button mBtnClean;
    @BindView(R.id.btn_nextstep)
    Button mBtnNextstep;
    @BindView(R.id.title_gallery)
    TextView mTitleGallery;
    @BindView(R.id.checkbox_duizhao)
    CheckBox mCheckboxDuizhao;
    @BindView(R.id.dr)
    AutoCompleteTextView mDr;
    @BindView(R.id.toolbar_time)
    TextView mToolbarTime;

    private int nowCheckId = R.id.background1;
    private int nowCheckindex = 1;
    private TestRecord nowCheckGallery;
    private String projectname;

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent2(ExamOperationService.ExamOperationServiceEventBean tags) {

        if (tags.getTime() == 0) {
            if (null != mToolbarTime) {
                mToolbarTime.setText("????????????????????????");
            }
            return;
        }
        String timestring = tags.getTimestring();
        if (null != mToolbarTime) {
            mToolbarTime.setText(timestring);
        }


    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChoseGalleryFGGDComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_chosegalleryfggd; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.size(); i++) {
            GalleryBean galleryBean = MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i);
            ((TestRecord) galleryBean).setSamplename(null);
            ((TestRecord) galleryBean).setSamplenum(null);
            ((TestRecord) galleryBean).setDilutionratio(1);
            ((TestRecord) galleryBean).setEveryresponse(1);
        }
        Intent intent = getIntent();
        projectname = intent.getStringExtra("project");
        refishMessage(1, R.id.background1);
        mCheckall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = mCheckall1.isChecked();
                check01(checked);
            }
        });

        mCheckall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = mCheckall2.isChecked();
                check02(checked);
            }
        });
        
        mCheckboxDuizhao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nowCheckGallery.setDowhat(2);
                } else {
                    nowCheckGallery.setDowhat(1);
                }

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
                String samplename = s.toString();
                nowCheckGallery.setSamplename(samplename);
            }
        });
        mDr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String dr = s.toString();
                if (dr.isEmpty()) {
                    dr = "1";
                    //mDr.setText("1");
                }

                nowCheckGallery.setDilutionratio(Double.parseDouble(dr));
            }
        });

        //?????????????????????????????????
        List<GalleryBean> mFGGDGalleryBeanList = MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList;
        for (int i = 0; i < mFGGDGalleryBeanList.size(); i++) {
            mFGGDGalleryBeanList.get(i).setCheckd(false);
        }
        initCheckBox();
    }

    private void initCheckBox() {
        mCheckbox1.setOnCheckedChangeListener(this);
        mCheckbox2.setOnCheckedChangeListener(this);
        mCheckbox3.setOnCheckedChangeListener(this);
        mCheckbox4.setOnCheckedChangeListener(this);
        mCheckbox5.setOnCheckedChangeListener(this);
        mCheckbox6.setOnCheckedChangeListener(this);
        mCheckbox7.setOnCheckedChangeListener(this);
        mCheckbox8.setOnCheckedChangeListener(this);
        mCheckbox9.setOnCheckedChangeListener(this);
        mCheckbox10.setOnCheckedChangeListener(this);
        mCheckbox11.setOnCheckedChangeListener(this);
        mCheckbox12.setOnCheckedChangeListener(this);
        mCheckbox13.setOnCheckedChangeListener(this);
        mCheckbox14.setOnCheckedChangeListener(this);
        mCheckbox15.setOnCheckedChangeListener(this);
        mCheckbox16.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkbox1:
            case R.id.checkbox2:
            case R.id.checkbox3:
            case R.id.checkbox4:
            case R.id.checkbox5:
            case R.id.checkbox6:
            case R.id.checkbox7:
            case R.id.checkbox8:
                if (!isChecked){
                    if (mCheckall1.isChecked()) {
                        mCheckall1.setChecked(false);
                    }
                }
                break;
            case R.id.checkbox9:
            case R.id.checkbox10:
            case R.id.checkbox11:
            case R.id.checkbox12:
            case R.id.checkbox13:
            case R.id.checkbox14:
            case R.id.checkbox15:
            case R.id.checkbox16:
                if (!isChecked){
                    if (mCheckall2.isChecked()) {
                        mCheckall2.setChecked(false);
                    }
                }
                break;


        }
    }

    private void check01(boolean isChecked) {
        mCheckbox1.setChecked(isChecked);
        mCheckbox2.setChecked(isChecked);
        mCheckbox3.setChecked(isChecked);
        mCheckbox4.setChecked(isChecked);
        mCheckbox5.setChecked(isChecked);
        mCheckbox6.setChecked(isChecked);
        mCheckbox7.setChecked(isChecked);
        mCheckbox8.setChecked(isChecked);
    }

    private void check02(boolean isChecked) {
        mCheckbox9.setChecked(isChecked);
        mCheckbox10.setChecked(isChecked);
        mCheckbox11.setChecked(isChecked);
        mCheckbox12.setChecked(isChecked);
        mCheckbox13.setChecked(isChecked);
        mCheckbox14.setChecked(isChecked);
        mCheckbox15.setChecked(isChecked);
        mCheckbox16.setChecked(isChecked);
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


    @OnClick({R.id.background1, R.id.background2, R.id.background3, R.id.background4, R.id.background5, R.id.background6, R.id.background7, R.id.background8, R.id.background9, R.id.background10, R.id.background11, R.id.background12, R.id.background13, R.id.background14, R.id.background15, R.id.background16, R.id.btn_clean, R.id.btn_nextstep})
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.background5:
                refishMessage(5, view.getId());
                break;
            case R.id.background6:
                refishMessage(6, view.getId());
                break;
            case R.id.background7:
                refishMessage(7, view.getId());
                break;
            case R.id.background8:
                refishMessage(8, view.getId());
                break;
            case R.id.background9:
                refishMessage(9, view.getId());
                break;
            case R.id.background10:
                refishMessage(10, view.getId());
                break;
            case R.id.background11:
                refishMessage(11, view.getId());
                break;
            case R.id.background12:
                refishMessage(12, view.getId());
                break;
            case R.id.background13:
                refishMessage(13, view.getId());
                break;
            case R.id.background14:
                refishMessage(14, view.getId());
                break;
            case R.id.background15:
                refishMessage(15, view.getId());
                break;
            case R.id.background16:
                refishMessage(16, view.getId());
                break;
            case R.id.btn_clean:
                clean();
                break;
            case R.id.btn_nextstep:
                nextStep();
                break;
        }
    }

    private void nextStep() {
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(0).setCheckd(mCheckbox1.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(1).setCheckd(mCheckbox2.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(2).setCheckd(mCheckbox3.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(3).setCheckd(mCheckbox4.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(4).setCheckd(mCheckbox5.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(5).setCheckd(mCheckbox6.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(6).setCheckd(mCheckbox7.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(7).setCheckd(mCheckbox8.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(8).setCheckd(mCheckbox9.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(9).setCheckd(mCheckbox10.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(10).setCheckd(mCheckbox11.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(11).setCheckd(mCheckbox12.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(12).setCheckd(mCheckbox13.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(13).setCheckd(mCheckbox14.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(14).setCheckd(mCheckbox15.isChecked());
        MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(15).setCheckd(mCheckbox16.isChecked());
        boolean check = false;
        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.size(); i++) {
            if (MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i).isCheckd()) {
                check = true;
                break;
            }
        }
        if (check) {
            Intent content = new Intent(this, TestFGGDActivity.class);
            content.putExtra("projectname", projectname);
            ArmsUtils.startActivity(content);
        } else {
            ArmsUtils.snackbarText("???????????????");
        }

    }

    private void clean() {
        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.size(); i++) {
            MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i).removedata();
        }
        refishMessage(nowCheckindex, nowCheckId);
    }

    private void refishMessage(int i, int id) {

        findViewById(nowCheckId).setBackground(getResources().getDrawable(R.drawable.background_item_gray));
        findViewById(id).setBackground(getResources().getDrawable(R.drawable.background_item_blue));
        nowCheckId = id;
        nowCheckindex = i;
        mTitleGallery.setText("????????????" + i);

        nowCheckGallery = (TestRecord) MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i - 1);
        mSampleserial.setText(nowCheckGallery.getSamplenum());
        mSamplename.setText(nowCheckGallery.getSamplename());
        mDr.setText(nowCheckGallery.getDilutionratio() + "");
        mCheckboxDuizhao.setChecked(nowCheckGallery.getDowhat() == 2 ? true : false);
    }


}