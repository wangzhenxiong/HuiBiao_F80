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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.di.component.DaggerEdtorProjectComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.contract.EdtorProjectContract;
import com.dy.huibiao_f80.mvp.presenter.EdtorProjectPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.MySpinnerAdapter;
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

public class EdtorProjectActivity extends BaseActivity<EdtorProjectPresenter> implements EdtorProjectContract.View, NewProjectJTJFragment.OnEventJTJ, NewProjectFGGDFragment.OnEventFGGD {
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
    @BindView(R.id.filter_edit)
    ClearEditText mFilterEdit;
    @BindView(R.id.recylerview)
    RecyclerView mRecylerview;
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

    @Inject
    List<BaseProjectMessage> mDateList;
    @Inject
    SortAdapter mAdapter;
    @Inject
    PinyinComparator mComparator;

    private int checkmoudle = 1;
    private SparseArray<String> mSparseTags = new SparseArray<>();
    private BaseProjectMessage chosedProject;

    private TitleItemDecoration mDecoration;
    private NewProjectFGGDFragment newProjectFGGDFragment;
    private NewProjectJTJFragment newProjectJTJFragment;
    private ProjectFGGDDao projectFGGDDao;
    private ProjectJTJDao projectJTJDao;


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
        projectFGGDDao = DBHelper.getProjectFGGDDao();
        projectJTJDao = DBHelper.getProjectJTJDao();

        newProjectJTJFragment = NewProjectJTJFragment.newInstance();
        newProjectJTJFragment.setOnEventListenner(this);
        newProjectFGGDFragment = NewProjectFGGDFragment.newInstance();
        newProjectFGGDFragment.setOnEventListenner(this);


        initCharIndex();
        initsparr();
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
                //查找所选中的检测项目的曲线信息，有多个曲线的情况，需要进行切换
                String cvName = mDateList.get(position).getCVName();
                initCurve(cvName);
                //更新选中的检测项目信息
                initProjectMessage(position);


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

    private void initCurve(String curvename) {
        if (null == curvename) {
            mSpCuregroup.setVisibility(View.GONE);
            mNewCurve.setVisibility(View.GONE);
            return;
        } else {
            mSpCuregroup.setVisibility(View.VISIBLE);
            mNewCurve.setVisibility(View.VISIBLE);
        }
        List<BaseProjectMessage> list = new ArrayList<>();
        if (null != chosedProject) {
            if (checkmoudle == 1) {
                list.addAll(projectFGGDDao.queryBuilder().where(ProjectFGGDDao.Properties.ProjectName.eq(chosedProject.getPjName()))
                        .build().list());
            } else if (checkmoudle == 2) {
                list.addAll(projectJTJDao.queryBuilder().where(ProjectJTJDao.Properties.ProjectName.eq(chosedProject.getPjName()))
                        .list());
            }
        }
        LogUtils.d(list);
        LogUtils.d(curvename);
        MySpinnerAdapter adapter = new MySpinnerAdapter(list, getActivity());


        mSpCuregroup.setAdapter(adapter);
        mSpCuregroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BaseProjectMessage object = list.get(position);
                LogUtils.d(object);
                if (checkmoudle == 1) {
                    newProjectFGGDFragment.setData(object);
                } else if (checkmoudle == 2) {
                    newProjectJTJFragment.setData(object);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCVName().equals(curvename)) {
                mSpCuregroup.setSelection(i);
                LogUtils.d(i);
            }
        }

    }

    private void initProjectMessage(int position) {
        BaseProjectMessage baseProjectMessage = mDateList.get(position);
        if (checkmoudle == 1) {
            newProjectFGGDFragment.setData(baseProjectMessage);
        } else if (checkmoudle == 2) {
            newProjectJTJFragment.setData(baseProjectMessage);
        }

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
                newCurve();
                break;
            case R.id.newproject:
                makeeDialog();
                break;
            case R.id.deleteproject:

                deleteProject();
                break;
        }
    }

    private void newCurve() {
        if (null == chosedProject) {
            ArmsUtils.snackbarText("请先选择检测项目");
            return;
        }
        String pjName = chosedProject.getPjName();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("请输入新建曲线的名称");
        EditText view = new EditText(EdtorProjectActivity.this);
        long count = projectFGGDDao.queryBuilder().where(ProjectFGGDDao.Properties.ProjectName.eq(pjName)).count();
        view.setText("曲线" + (count + 1));

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
                String curvename = view.getText().toString();
                if (curvename.isEmpty()) {
                    ArmsUtils.snackbarText("请输入新建曲线的名称");
                    return;
                }
                if (checkmoudle == 1) {
                    ProjectFGGD projectFGGD = new ProjectFGGD();
                    projectFGGD.setId(null);
                    projectFGGD.setProjectName(pjName);
                    projectFGGD.setCurveName(curvename);
                    projectFGGD.setCurveOrder((int) count);
                    projectFGGDDao.insert(projectFGGD);
                    initCurve(curvename);
                } else if (checkmoudle == 2) {
                    ProjectJTJ projectJTJ = new ProjectJTJ();
                    projectJTJ.setId(null);
                    projectJTJ.setProjectName(pjName);
                    projectJTJ.setCurveName(curvename);
                    projectJTJ.setCurveOrder((int) count);
                    projectJTJDao.insert(projectJTJ);
                    initCurve(curvename);
                }


            }
        });
        alertDialog.show();


    }

    private void deleteProject() {
        if (null == chosedProject) {
            ArmsUtils.snackbarText("请选择需要删除的检测项目");
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("确定要删除该检测项目吗");

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (chosedProject instanceof ProjectFGGD) {
                    ProjectFGGD chosedProject = (ProjectFGGD) EdtorProjectActivity.this.chosedProject;
                    List<ProjectFGGD> list = DBHelper.getProjectFGGDDao().queryBuilder().where(ProjectFGGDDao.Properties.ProjectName.eq(chosedProject.getPjName())).list();
                    DBHelper.getProjectFGGDDao().deleteInTx(list);
                    newProjectFGGDFragment=NewProjectFGGDFragment.newInstance();
                    mPresenter.replaceFragment(getSupportFragmentManager(), R.id.frame, newProjectFGGDFragment, mSparseTags.get(R.id.fggd));
                    newProjectFGGDFragment.setData(null);
                } else if (chosedProject instanceof ProjectJTJ) {
                    ProjectJTJ chosedProject = (ProjectJTJ) EdtorProjectActivity.this.chosedProject;
                    List<ProjectJTJ> list = DBHelper.getProjectJTJDao().queryBuilder().where(ProjectJTJDao.Properties.ProjectName.eq(chosedProject.getPjName())).list();
                    DBHelper.getProjectJTJDao().deleteInTx(list);
                    newProjectJTJFragment=NewProjectJTJFragment.newInstance();
                    mPresenter.replaceFragment(getSupportFragmentManager(), R.id.frame, newProjectJTJFragment, mSparseTags.get(R.id.jtj));
                    newProjectJTJFragment.setData(null);
                }
                mDateList.remove(chosedProject);
                chosedProject = null;
                mAdapter.notifyDataSetChanged();
            }
        });
        alertDialog.show();
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
                    List<ProjectFGGD> list = projectFGGDDao.queryBuilder().where(ProjectFGGDDao.Properties.ProjectName.eq(projectname)).list();
                    LogUtils.d(list);
                    if (list.size() > 0) {
                        ArmsUtils.snackbarText("该检测项目已经存在，请勿重复新建");
                        return;
                    }
                    ProjectFGGD entity = new ProjectFGGD();
                    entity.setProjectName(projectname);
                    entity.setCurveName("曲线1");
                    entity.setIsdefault(true);
                    entity.setId(null);
                    entity.setCurveOrder(0);
                    projectFGGDDao.insert(entity);
                    mPresenter.getFGGDProject(null);
                } else if (checkmoudle == 2) {
                    if (projectJTJDao.queryBuilder().where(ProjectJTJDao.Properties.ProjectName.eq(projectname)).count() > 0) {
                        ArmsUtils.snackbarText("该检测项目已经存在，请勿重复新建");
                        return;
                    }
                    ProjectJTJ entity = new ProjectJTJ();
                    entity.setProjectName(projectname);
                    entity.setCurveName("曲线1");
                    entity.setIsdefault(true);
                    entity.setId(null);
                    entity.setCurveOrder(0);
                    projectJTJDao.insert(entity);
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

        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.frame, newProjectJTJFragment, mSparseTags.get(R.id.jtj));
        chosedProject = null;
        initCurve(null);
    }

    private void checkFGGD() {
        mFggd.setBackground(getResources().getDrawable(R.color.BAC0));
        mJtj.setBackground(null);
        checkmoudle = 1;
        mPresenter.getFGGDProject(null);

        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.frame, newProjectFGGDFragment, mSparseTags.get(R.id.fggd));
        chosedProject = null;
        initCurve(null);
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    public void onEventJTJ(int what, Object o) {
     initCurve(null);
    }

    @Override
    public void onEventFGGD(int what, Object o) {
        initCurve(null);
    }
}