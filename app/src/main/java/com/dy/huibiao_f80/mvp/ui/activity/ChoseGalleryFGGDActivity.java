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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerChoseGalleryFGGDComponent;
import com.dy.huibiao_f80.mvp.contract.ChoseGalleryFGGDContract;
import com.dy.huibiao_f80.mvp.presenter.ChoseGalleryFGGDPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ChoseGalleryFGGDActivity extends BaseActivity<ChoseGalleryFGGDPresenter> implements ChoseGalleryFGGDContract.View {

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
    Map<Integer, Boolean> layoutMap = new HashMap<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChoseGalleryFGGDComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_chosegalleryfggd; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        layoutMap.put(R.id.background1, false);
        layoutMap.put(R.id.background2, false);
        layoutMap.put(R.id.background3, false);
        layoutMap.put(R.id.background4, false);
        layoutMap.put(R.id.background5, false);
        layoutMap.put(R.id.background6, false);
        layoutMap.put(R.id.background7, false);
        layoutMap.put(R.id.background8, false);
        layoutMap.put(R.id.background9, false);
        layoutMap.put(R.id.background10, false);
        layoutMap.put(R.id.background11, false);
        layoutMap.put(R.id.background12, false);
        layoutMap.put(R.id.background13, false);
        layoutMap.put(R.id.background14, false);
        layoutMap.put(R.id.background15, false);
        layoutMap.put(R.id.background16, false);
        mCheckall1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check01(isChecked);
            }
        });
        mCheckall2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check02(isChecked);
            }
        });
    }

    private void check01(boolean isChecked) {
        if (isChecked) {
            mBackground1.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground2.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground3.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground4.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground5.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground6.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground7.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground8.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            layoutMap.put(R.id.background1, true);
            layoutMap.put(R.id.background2, true);
            layoutMap.put(R.id.background3, true);
            layoutMap.put(R.id.background4, true);
            layoutMap.put(R.id.background5, true);
            layoutMap.put(R.id.background6, true);
            layoutMap.put(R.id.background7, true);
            layoutMap.put(R.id.background8, true);

        } else {
            mBackground1.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground2.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground3.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground4.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground5.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground6.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground7.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground8.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            layoutMap.put(R.id.background1, false);
            layoutMap.put(R.id.background2, false);
            layoutMap.put(R.id.background3, false);
            layoutMap.put(R.id.background4, false);
            layoutMap.put(R.id.background5, false);
            layoutMap.put(R.id.background6, false);
            layoutMap.put(R.id.background7, false);
            layoutMap.put(R.id.background8, false);
        }
    }

    private void check02(boolean isChecked) {
        if (isChecked) {
            mBackground9.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground10.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground11.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground12.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground13.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground14.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground15.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            mBackground16.setBackground(getResources().getDrawable(R.drawable.background_item_blue));
            layoutMap.put(R.id.background9, true);
            layoutMap.put(R.id.background10, true);
            layoutMap.put(R.id.background11, true);
            layoutMap.put(R.id.background12, true);
            layoutMap.put(R.id.background13, true);
            layoutMap.put(R.id.background14, true);
            layoutMap.put(R.id.background15, true);
            layoutMap.put(R.id.background16, true);
        } else {
            mBackground9.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground10.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground11.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground12.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground13.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground14.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground15.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            mBackground16.setBackground(getResources().getDrawable(R.drawable.background_item_gray));
            layoutMap.put(R.id.background9, false);
            layoutMap.put(R.id.background10, false);
            layoutMap.put(R.id.background11, false);
            layoutMap.put(R.id.background12, false);
            layoutMap.put(R.id.background13, false);
            layoutMap.put(R.id.background14, false);
            layoutMap.put(R.id.background15, false);
            layoutMap.put(R.id.background16, false);
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

    @OnClick({R.id.background1, R.id.background2, R.id.background3, R.id.background4, R.id.background5, R.id.background6, R.id.background7, R.id.background8, R.id.background9, R.id.background10, R.id.background11, R.id.background12, R.id.background13, R.id.background14, R.id.background15, R.id.background16, R.id.btn_clean, R.id.btn_nextstep})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.background1:
                refishMessage();
            case R.id.background2:
            case R.id.background3:
            case R.id.background4:
            case R.id.background5:
            case R.id.background6:
            case R.id.background7:
            case R.id.background8:
            case R.id.background9:
            case R.id.background10:
            case R.id.background11:
            case R.id.background12:
            case R.id.background13:
            case R.id.background14:
            case R.id.background15:
            case R.id.background16:
                if (layoutMap.get(view.getId())){
                    findViewById(view.getId()).setBackground(getResources().getDrawable(R.drawable.background_item_gray));
                    layoutMap.put(view.getId(),false);
                }else {
                    findViewById(view.getId()).setBackground(getResources().getDrawable(R.drawable.background_item_blue));
                    layoutMap.put(view.getId(),true);
                }
                break;
            case R.id.btn_clean:
                break;
            case R.id.btn_nextstep:
                break;
        }
    }

    private void refishMessage() {

    }
}