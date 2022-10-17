package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.di.component.DaggerStartTestComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.contract.StartTestContract;
import com.dy.huibiao_f80.mvp.presenter.StartTestPresenter;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.CharIndexView;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.ClearEditText;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinComparator;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinUtils;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.TitleItemDecoration;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.adapter.SortAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class StartTestActivity extends BaseActivity<StartTestPresenter> implements StartTestContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.fggd)
    TextView mFggd;
    @BindView(R.id.jtj)
    TextView mJtj;
    @BindView(R.id.ed_seach)
    ClearEditText mFilterEdit;
    @BindView(R.id.recycleview)
    RecyclerView mRecylerview;
    @BindView(R.id.charIndexView)
    CharIndexView mCharIndexView;
    @BindView(R.id.tv_index)
    TextView mTvIndex;
    @BindView(R.id.layout1)
    LinearLayout mLayout1;
    @BindView(R.id.btn_nextstep)
    Button mBtnNextstep;
    private ProjectFGGDDao projectFGGDDao;
    private ProjectJTJDao projectJTJDao;
    @Inject
    List<BaseProjectMessage> mDateList;
    @Inject
    SortAdapter mAdapter;
    @Inject
    PinyinComparator mComparator;
    private TitleItemDecoration mDecoration;
    private int checkmoudle = 1;
    private BaseProjectMessage chosedProject;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStartTestComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_starttest; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        projectFGGDDao = DBHelper.getProjectFGGDDao();
        projectJTJDao = DBHelper.getProjectJTJDao();
        initCharIndex();
        checkFGGD();
    }

    private void initCharIndex() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        // 根据a-z进行排序源数据
        mDecoration = new TitleItemDecoration(this, mDateList);
        mCharIndexView.setOnCharIndexChangedListener(new CharIndexView.OnCharIndexChangedListener() {
            @Override
            public void onCharIndexChanged(char currentIndex) {

            }

            @Override
            public void onCharIndexSelected(String currentIndex) {
                LogUtils.d(currentIndex);
                if (currentIndex == null) {
                    mTvIndex.setVisibility(View.INVISIBLE);
                } else {
                    mTvIndex.setVisibility(View.VISIBLE);
                    mTvIndex.setText(currentIndex);
                    //该字母首次出现的位置
                    int position = mAdapter.getPositionForSection(currentIndex.charAt(0));
                    if (position != -1) {
                        manager.scrollToPositionWithOffset(position, 0);
                    }
                }

            }
        });

        mRecylerview.setLayoutManager(manager);
        mRecylerview.addItemDecoration(mDecoration);
        mRecylerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecylerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //选中状态处理
                checkProject(position);


            }
        });
        //根据输入框输入值的改变来过滤搜索
        mFilterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                if (s.toString().isEmpty()) {
                    if (checkmoudle == 1) {
                        checkFGGD();
                    } else {
                        checkJTJ();
                    }
                } else {
                    filterData(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<BaseProjectMessage> filterDateList = new ArrayList<>();

        filterDateList.clear();
        for (BaseProjectMessage sortModel : mDateList) {
            String name = sortModel.getPjName();
            if (name.indexOf(filterStr.toString()) != -1 ||
                    PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                    //不区分大小写
                    || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                    || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
            ) {
                filterDateList.add(sortModel);
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, mComparator);
        mDateList.clear();
        mDateList.addAll(filterDateList);
        mAdapter.notifyDataSetChanged();
    }

    private void checkProject(int position) {
        for (int i = 0; i < mDateList.size(); i++) {
            BaseProjectMessage baseProjectMessage = mDateList.get(i);
            if (i == position) {
                chosedProject = baseProjectMessage;
                baseProjectMessage.check = true;
            } else {
                baseProjectMessage.check = false;
            }

        }
        mAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.fggd, R.id.jtj, R.id.btn_nextstep})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fggd:
                checkFGGD();
                break;
            case R.id.jtj:
                checkJTJ();
                break;
            case R.id.btn_nextstep:
                startTest();
                break;
        }
    }

    private void startTest() {
        if (checkmoudle == 1) {
            Intent content = new Intent(this, ChoseGalleryFGGDActivity.class);
            content.putExtra("projectname",chosedProject.getPjName());
            ArmsUtils.startActivity(content);
        } else if (checkmoudle == 2) {
            Intent content = new Intent(this, ChoseGalleryJTJActivity.class);
            content.putExtra("projectname",chosedProject.getPjName());
            ArmsUtils.startActivity(content);
        }
    }

    private void checkJTJ() {
        mJtj.setBackground(getResources().getDrawable(R.color.BAC0));
        mFggd.setBackground(null);
        checkmoudle = 2;
        mPresenter.getJTJProject(null);

        chosedProject = null;
    }

    private void checkFGGD() {
        mFggd.setBackground(getResources().getDrawable(R.color.BAC0));
        mJtj.setBackground(null);
        checkmoudle = 1;
        mPresenter.getFGGDProject(null);

        chosedProject = null;
    }
}