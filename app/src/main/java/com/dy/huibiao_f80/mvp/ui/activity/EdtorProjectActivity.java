package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.di.component.DaggerEdtorProjectComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.mvp.contract.EdtorProjectContract;
import com.dy.huibiao_f80.mvp.presenter.EdtorProjectPresenter;
import com.dy.huibiao_f80.mvp.ui.fragment.NewProjectFGGDFragment;
import com.dy.huibiao_f80.mvp.ui.fragment.NewProjectJTJFragment;
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

public class EdtorProjectActivity extends BaseActivity<EdtorProjectPresenter> implements EdtorProjectContract.View {


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
    @BindView(R.id.recylerview)
    RecyclerView mRecycleview;
    @BindView(R.id.charIndexView)
    CharIndexView mCharIndexView;
    @BindView(R.id.tv_index)
    TextView mTvIndex;
    @BindView(R.id.moudle_layout)
    LinearLayout mMoudleLayout;
    @BindView(R.id.newproject)
    Button mNewproject;
    @BindView(R.id.deleteproject)
    Button mDeleteproject;
    @BindView(R.id.new_curve)
    ImageButton mNewCurve;
    @BindView(R.id.sp_curegroup)
    Spinner mSpCuregroup;
    @BindView(R.id.frame)
    FrameLayout mFrame;
    @BindView(R.id.filter_edit)
    ClearEditText mFilterEdit;
    @Inject
    List<BaseProjectMessage> mDateList;
    @Inject
    private SortAdapter mAdapter;
    private int checkmoudle = 1;
    private SparseArray<String> mSparseTags = new SparseArray<>();
    private BaseProjectMessage chosedProject;
    private PinyinComparator mComparator;
    private TitleItemDecoration mDecoration;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEdtorProjectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_edtorproject; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initCharIndex();
        initsparr();
        checkFGGD();
    }

    private void initCharIndex() {
        mComparator = new PinyinComparator();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //mDateList = filledData(getResources().getStringArray(R.array.methods_fggd));
        // 根据a-z进行排序源数据
        Collections.sort(mDateList, mComparator);
        mDecoration = new TitleItemDecoration(this, mDateList);
        mCharIndexView.setOnCharIndexChangedListener(new CharIndexView.OnCharIndexChangedListener() {
            @Override
            public void onCharIndexChanged(char currentIndex) {

            }

            @Override
            public void onCharIndexSelected(String currentIndex) {
                if (currentIndex == null) {
                    mTvIndex.setVisibility(View.INVISIBLE);
                } else {
                    mTvIndex.setVisibility(View.VISIBLE);
                    mTvIndex.setText(currentIndex);
                }
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(currentIndex.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        mAdapter = new SortAdapter(this,mDateList);
        mRecycleview.setLayoutManager(manager);
        mRecycleview.addItemDecoration(mDecoration);
        mRecycleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecycleview.setAdapter(mAdapter);

        //根据输入框输入值的改变来过滤搜索
        mFilterEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    /**
     * 为RecyclerView填充数据
     *
     * @param date
     * @return
     *//*
    private List<BaseProjectMessage> filledData(String[] date) {
        List<BaseProjectMessage> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            BaseProjectMessage sortModel = new BaseProjectMessage();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }*/

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

    private void initsparr() {
        mSparseTags.put(R.id.fggd, "fggd");
        mSparseTags.put(R.id.jtj, "jtj");
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

    @OnClick({R.id.fggd, R.id.jtj, R.id.new_curve, R.id.newproject, R.id.deleteproject})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fggd:
                checkFGGD();
                break;
            case R.id.jtj:
                checkJTJ();
                break;
            case R.id.new_curve:

                break;
            case R.id.newproject:
                makeeDialog();
                break;
            case R.id.deleteproject:
                if (null == chosedProject) {
                    ArmsUtils.snackbarText("请选择需要删除的检测项目");
                    return;
                }
                // TODO: 10/13/22 删除检测项目
                break;
        }
    }

    private void makeeDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("请输入检测项目名称");
        EditText view = new EditText(EdtorProjectActivity.this);

        alertDialog.setView(view);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String projectname = view.getText().toString();
                if (projectname.isEmpty()) {
                    ArmsUtils.snackbarText("请输入检测项目名称");
                    return;
                }
                if (checkmoudle == 1) {
                    ProjectFGGD entity = new ProjectFGGD();
                    entity.setProjectName(projectname);
                    entity.setId(null);
                    DBHelper.getProjectFGGDDao().insert(entity);
                    mPresenter.getFGGDProject(null);
                } else if (checkmoudle == 2) {
                    ProjectJTJ entity = new ProjectJTJ();
                    entity.setProjectName(projectname);
                    entity.setId(null);
                    DBHelper.getProjectJTJDao().insert(entity);
                    mPresenter.getJTJProject(null);
                }


            }
        });
        alertDialog.show();
    }

    private void checkJTJ() {
        mJtj.setBackground(getResources().getDrawable(R.color.BAC0));
        mFggd.setBackground(null);
        checkmoudle = 2;
        mPresenter.getJTJProject(null);
        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.frame, NewProjectJTJFragment.newInstance(), mSparseTags.get(R.id.jtj));
    }

    private void checkFGGD() {
        mFggd.setBackground(getResources().getDrawable(R.color.BAC0));
        mJtj.setBackground(null);
        checkmoudle = 1;
        mPresenter.getFGGDProject(null);
        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.frame, NewProjectFGGDFragment.newInstance(), mSparseTags.get(R.id.fggd));

    }

    @Override
    public Activity getActivity() {
        return this;
    }
}