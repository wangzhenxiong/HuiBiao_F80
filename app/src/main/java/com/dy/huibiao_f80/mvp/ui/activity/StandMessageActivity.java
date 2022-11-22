package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.di.component.DaggerStandMessageComponent;
import com.dy.huibiao_f80.mvp.contract.StandMessageContract;
import com.dy.huibiao_f80.mvp.presenter.StandMessagePresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.LocalFileAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class StandMessageActivity extends BaseActivity<StandMessagePresenter> implements StandMessageContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    RxPermissions mRxPermissions;

    @Inject
    LocalFileAdapter mAdapter;
    /* @Inject
     Standard_LawsAdapter mStandardAdapter;*/
    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.showpage)
    TextView mShowpage;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;
    @BindView(R.id.toolbar_container)
    FrameLayout mToolbarContainer;
    private boolean isLoadingMore;
    private Paginate mPaginate;
    private boolean hasLoadedAllItems;
    private RecyclerView.LayoutManager mLayoutManager;
    public boolean isSeaching = false;
    public String keyWord;
    private int mData;
    private String path;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStandMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_standmessage; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mData = getIntent().getIntExtra("data", -1);
        mLayoutManager = new GridLayoutManager(getActivity(), 5);
        initRecyclerView();
        mSwipeRefreshLayout.setEnabled(false);
        mAdapter.setEmptyView(R.layout.emptyview, (ViewGroup) mRecyclerView.getParent());
        mRecyclerView.setAdapter(mAdapter);
        boolean exists = FileUtils.isFileExists(ArmsUtils.getString(getActivity(), R.string.app_localdata_address) + "/standard");
        path = ArmsUtils.getString(getActivity(), R.string.app_localdata_address) + "/standard";
        setTitle("检测标准");
        if (exists) {
            mPresenter.requestfile(true, path);
        } else {
            killMyself();
            ArmsUtils.snackbarText("未找到相关标准文件");
        }
    }


    private void initRecyclerView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
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


    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {

        isLoadingMore = true;
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public void setShowPgeText(String s) {

        mShowpage.setText(s + "");
    }

    @Override
    public void sethasLoadedAllItemstrue() {
        hasLoadedAllItems = true;
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.footview_layout, null);
        TextView id = (TextView) inflate.findViewById(R.id.showmessage);
        id.setText("没有更多数据了...");
        mAdapter.removeAllFooterView();
        mAdapter.addFooterView(inflate);
        // hideLoading();
    }

    @Override
    public void sethasLoadedAllItemsfase() {
        hasLoadedAllItems = false;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.stand_toobar_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        //item.setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);

        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (isSeaching) {
            isSeaching = false;
            keyWord = "";
            /*if (mData == 1) {
                mPresenter.loadStandardMore(true);
                mToolbarTitle.setText("检测标准");
            } else if (mData == 2) {
                mPresenter.loadLawsMore(true);
                mToolbarTitle.setText("法律法规");
            }*/


        } else {
            if (mSearchView.isSearchOpen()) {
                mSearchView.closeSearch();
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public void onRefresh() {

    }
}