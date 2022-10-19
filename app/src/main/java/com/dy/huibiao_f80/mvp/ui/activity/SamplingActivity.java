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

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerSamplingComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.Sampling;
import com.dy.huibiao_f80.mvp.contract.SamplingContract;
import com.dy.huibiao_f80.mvp.presenter.SamplingPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.SamplingAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class SamplingActivity extends BaseActivity<SamplingPresenter> implements SamplingContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.chose_time)
    Button mChoseTime;
    @BindView(R.id.chose_result)
    Button mChoseResult;
    @BindView(R.id.seach)
    Button mSeach;
    @BindView(R.id.choseall)
    CheckBox mChoseall;
    @BindView(R.id.recylerview)
    RecyclerView mRecylerview;
    @BindView(R.id.newsampling)
    Button mNewsampling;
    @BindView(R.id.editor)
    Button mEditor;
    @BindView(R.id.delete)
    Button mDelete;
    private List<Sampling> samplingList;
    private SamplingAdapter samplingAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSamplingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sampling; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getdata();
    }

    private void getdata() {
        samplingList = DBHelper.getSamplingDao().loadAll();
        ArmsUtils.configRecyclerView(mRecylerview, new GridLayoutManager(this, 1));
        samplingAdapter = new SamplingAdapter(R.layout.layout_fggftest_item, samplingList);
        samplingAdapter.setEmptyView(R.layout.emptyview, (ViewGroup) mRecylerview.getParent());
        mRecylerview.setAdapter(samplingAdapter);
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

    @OnClick({R.id.chose_time, R.id.chose_result, R.id.seach, R.id.newsampling, R.id.editor, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chose_time:
                break;
            case R.id.chose_result:
                break;
            case R.id.seach:
                break;
            case R.id.newsampling:
                break;
            case R.id.editor:
                break;
            case R.id.delete:
                break;
        }
    }
}