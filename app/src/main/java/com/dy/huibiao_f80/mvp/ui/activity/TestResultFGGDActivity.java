package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.bean.eventBusBean.FGTestMessageBean;
import com.dy.huibiao_f80.di.component.DaggerTestResultFGGDComponent;
import com.dy.huibiao_f80.mvp.contract.TestResultFGGDContract;
import com.dy.huibiao_f80.mvp.presenter.TestResultFGGDPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.FGGDTestResultAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class TestResultFGGDActivity extends BaseActivity<TestResultFGGDPresenter> implements TestResultFGGDContract.View {

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
    @BindView(R.id.controvalue)
    TextView mControvalue;
    @BindView(R.id.recylerview)
    RecyclerView mRecylerview;
    @BindView(R.id.btn_retest)
    Button mBtnRetest;
    @BindView(R.id.btn_restart)
    Button mBtnRestart;
    @BindView(R.id.btn_record)
    Button mBtnRecord;
    @BindView(R.id.choseall)
    CheckBox mChoseall;
    private List<GalleryBean> dataList = new ArrayList<>();
    private FGGDTestResultAdapter fggdAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTestResultFGGDComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_testresultfggd; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getdata();
        ArmsUtils.configRecyclerView(mRecylerview, new GridLayoutManager(this, 1));
        fggdAdapter = new FGGDTestResultAdapter(R.layout.layout_fggftestresult_item, dataList);
        fggdAdapter.setEmptyView(R.layout.emptyview, (ViewGroup) mRecylerview.getParent());
        mRecylerview.setAdapter(fggdAdapter);
    }

    private void getdata() {
        dataList.clear();
        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.size(); i++) {
            GalleryBean galleryBean = MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i);
            if (galleryBean.isCheckd()) {
                dataList.add(galleryBean);
            }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FGTestMessageBean tags) {
        switch (tags.tag) {
            case 0:
                fggdAdapter.notifyDataSetChanged();

                break;
        }
    }

    @OnClick({R.id.btn_retest, R.id.btn_restart, R.id.btn_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_retest:
                break;
            case R.id.btn_restart:
                break;
            case R.id.btn_record:
                ArmsUtils.startActivity(new Intent(this,RecordActivity.class));
                break;
        }
    }
}