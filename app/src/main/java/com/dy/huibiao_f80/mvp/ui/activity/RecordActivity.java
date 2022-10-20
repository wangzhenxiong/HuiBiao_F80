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
import com.dy.huibiao_f80.di.component.DaggerRecordComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.contract.RecordContract;
import com.dy.huibiao_f80.mvp.presenter.RecordPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.TestRecrdAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class RecordActivity extends BaseActivity<RecordPresenter> implements RecordContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.testmoudle)
    Button mTestmoudle;
    @BindView(R.id.testprojectname)
    Button mTestprojectname;
    @BindView(R.id.jujdger)
    Button mJujdger;
    @BindView(R.id.seach)
    Button mSeach;
    @BindView(R.id.choseall)
    CheckBox mChoseall;
    @BindView(R.id.recylerview)
    RecyclerView mRecylerview;
    @BindView(R.id.print)
    Button mPrint;
    @BindView(R.id.delete)
    Button mDelete;
    private List<TestRecord> testRecordList=new ArrayList<>();
    private TestRecrdAdapter testRecrdAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_record; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getData();

    }

    private void getData() {
        testRecordList.clear();
        testRecordList.addAll(DBHelper.getTestRecordDao().loadAll());
        ArmsUtils.configRecyclerView(mRecylerview, new GridLayoutManager(this, 1));
        testRecrdAdapter = new TestRecrdAdapter(R.layout.record_item_layou, testRecordList);
        testRecrdAdapter.setEmptyView(R.layout.emptyview, (ViewGroup) mRecylerview.getParent());
        mRecylerview.setAdapter(testRecrdAdapter);
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

    @OnClick({R.id.testmoudle, R.id.testprojectname, R.id.jujdger, R.id.seach, R.id.print, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.testmoudle:
                break;
            case R.id.testprojectname:
                break;
            case R.id.jujdger:
                break;
            case R.id.seach:
                break;
            case R.id.print:
                break;
            case R.id.delete:
                break;
        }
    }
}