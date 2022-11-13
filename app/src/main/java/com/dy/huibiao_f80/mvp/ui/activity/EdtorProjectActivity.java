package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.DataUtils;
import com.dy.huibiao_f80.app.utils.JxlUtils;
import com.dy.huibiao_f80.bean.UpdateFileMessage;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.di.component.DaggerEdtorProjectComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.contract.EdtorProjectContract;
import com.dy.huibiao_f80.mvp.presenter.EdtorProjectPresenter;
import com.dy.huibiao_f80.mvp.ui.fragment.FGGDProjectFragment;
import com.dy.huibiao_f80.mvp.ui.fragment.JTJProjectFragment;
import com.dy.huibiao_f80.mvp.ui.fragment.NewProjectFGGDFragment;
import com.dy.huibiao_f80.mvp.ui.fragment.NewProjectJTJFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

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
    @BindView(R.id.moudle_layout)
    LinearLayout mMoudleLayout;
    @BindView(R.id.newproject)
    Button mNewproject;
    @BindView(R.id.deleteproject)
    Button mDeleteproject;

    @BindView(R.id.frame)
    FrameLayout mFrame;
    @Named("fggd")
    @Inject
    List<BaseProjectMessage> mDateList_fggd;
    @Named("jtj")
    @Inject
    List<BaseProjectMessage> mDateList_jtj;
    @BindView(R.id.project_fram)
    FrameLayout mProjectFram;
    @Inject
    AlertDialog mSportDialog;
    @BindView(R.id.synchronous)
    Button mSynchronous;
    private int checkmoudle = 1;
    private SparseArray<String> mSparseTags = new SparseArray<>();
    private SparseArray<String> mSparseTags_Project = new SparseArray<>();
    private NewProjectFGGDFragment newProjectFGGDFragment;
    private NewProjectJTJFragment newProjectJTJFragment;

    private FGGDProjectFragment fggdProjectFragment;
    private JTJProjectFragment jtjProjectFragment;


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
    protected void onResume() {
        super.onResume();
        mPresenter.loadData(null);

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        newProjectJTJFragment = NewProjectJTJFragment.newInstance();
        newProjectFGGDFragment = NewProjectFGGDFragment.newInstance();

        fggdProjectFragment = FGGDProjectFragment.newInstance();
        jtjProjectFragment = JTJProjectFragment.newInstance();

        initsparr();
        checkFGGD();

        mToolbarTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                makeExportProjectDialog();
                return true;
            }
        });
    }

    /**
     * 当前所选中的项目
     */
    BaseProjectMessage projectMessage_fggd;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent1(FGGDProjectFragment.FGGDProjectFragmentEvent tags) {
        LogUtils.d(tags);
        projectMessage_fggd = tags.getProjectFGGD();
        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.frame, newProjectFGGDFragment, mSparseTags.get(R.id.fggd));
        newProjectFGGDFragment.setData(projectMessage_fggd);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent1(NewProjectFGGDFragment.FGGDProjectFragmentEvent tags) {
        if (tags.getPersion() == -1) {
            mDateList_fggd.remove(projectMessage_fggd);
            projectMessage_fggd = null;
            fggdProjectFragment.setData(mDateList_fggd);
        }
    }

    BaseProjectMessage projectMessage_jtj;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent2(JTJProjectFragment.JTJProjectFragmentEvent tags) {
        LogUtils.d(tags);
        projectMessage_jtj = tags.getProjectJTJ();
        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.frame, newProjectJTJFragment, mSparseTags.get(R.id.jtj));
        newProjectJTJFragment.setData(projectMessage_jtj);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent1(NewProjectJTJFragment.JTJProjectFragmentEvent tags) {
        if (tags.getPersion() == -1) {
            mDateList_jtj.remove(projectMessage_jtj);
            projectMessage_jtj = null;
            jtjProjectFragment.setData(mDateList_jtj);
        }
    }


    private void makeExportProjectDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("请选择导入或者导出");
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.expor_data_item, null);
        alertDialog.setView(inflate);
        inflate.findViewById(R.id.out_fggd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JxlUtils.openFilechose_Folder(EdtorProjectActivity.this, 4);
                alertDialog.dismiss();
            }
        });
        inflate.findViewById(R.id.out_jtj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JxlUtils.openFilechose_Folder(EdtorProjectActivity.this, 3);
                alertDialog.dismiss();
            }
        });
        inflate.findViewById(R.id.in_fggd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JxlUtils.openFilechose_File(EdtorProjectActivity.this, 2);
                alertDialog.dismiss();
            }
        });
        inflate.findViewById(R.id.in_jtj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JxlUtils.openFilechose_File(EdtorProjectActivity.this, 1);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                //文件选择模式，需要获取选择的所有文件的路径集合
                List<String> list = data.getStringArrayListExtra("paths");
                mPresenter.inputJTJProject(list);


            } else if (requestCode == 2) {
                //文件选择模式，需要获取选择的所有文件的路径集合
                List<String> list = data.getStringArrayListExtra("paths");
                mPresenter.inputFGGDProject(list);

            } else if (requestCode == 3) {
                //文件夹选择模式，需要获取选择的文件夹路径
                String path = data.getStringExtra("path");
                mPresenter.outPutItem(path, "胶体金检测项目" + DataUtils.getFIleNameNowtimeyyymmddhhmmss() + ".xls", "胶体金检测项目", 3);

            } else if (requestCode == 4) {
                //文件夹选择模式，需要获取选择的文件夹路径
                String path = data.getStringExtra("path");
                mPresenter.outPutItem(path, "分光光度检测项目" + DataUtils.getFIleNameNowtimeyyymmddhhmmss() + ".xls", "分光光度检测项目", 4);


            }
        }
    }

    private void initsparr() {
        mSparseTags.put(R.id.fggd, "fggd");
        mSparseTags.put(R.id.jtj, "jtj");

        mSparseTags_Project.put(R.id.fggd, "fggd_project");
        mSparseTags_Project.put(R.id.jtj, "jtj_project");

    }

    @Override
    public void showLoading() {
        if (!mSportDialog.isShowing()) {
            LogUtils.d("show");
            mSportDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mSportDialog.isShowing()) {
            LogUtils.d("hide");
            mSportDialog.dismiss();
        }
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

    @OnClick({R.id.fggd, R.id.jtj, R.id.newproject, R.id.deleteproject,R.id.synchronous})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fggd:
                checkFGGD();
                break;
            case R.id.jtj:
                checkJTJ();
                break;
            case R.id.newproject:
                makeeDialog();
                break;
            case R.id.deleteproject:
                deleteProject();
                break;
            case R.id.synchronous:
                mPresenter.checkNewVersion(checkmoudle);
                break;
        }
    }


    private void deleteProject() {
        if (checkmoudle == 1) {
            if (null == projectMessage_fggd) {
                ArmsUtils.snackbarText("请选择需要删除的检测项目");
                return;
            }
        }

        if (checkmoudle == 2) {
            if (null == projectMessage_jtj) {
                ArmsUtils.snackbarText("请选择需要删除的检测项目");
                return;
            }
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
                if (checkmoudle == 1) {
                    List<ProjectFGGD> list = DBHelper.getProjectFGGDDao().queryBuilder().where(ProjectFGGDDao.Properties.ProjectName.eq(projectMessage_fggd.getPjName())).list();
                    DBHelper.getProjectFGGDDao().deleteInTx(list);
                    mDateList_fggd.remove(projectMessage_fggd);
                    fggdProjectFragment.setData(mDateList_fggd);
                    fggdProjectFragment.lastCheck=-1;
                    projectMessage_fggd = null;
                    //删除肯定是选中的项目
                    newProjectFGGDFragment.setData(projectMessage_fggd);

                } else if (checkmoudle == 2) {
                    List<ProjectJTJ> list = DBHelper.getProjectJTJDao().queryBuilder().where(ProjectJTJDao.Properties.ProjectName.eq(projectMessage_jtj.getPjName())).list();
                    DBHelper.getProjectJTJDao().deleteInTx(list);
                    mDateList_jtj.remove(projectMessage_jtj);
                    jtjProjectFragment.setData(mDateList_jtj);
                    jtjProjectFragment.lastCheck=-1;
                    projectMessage_jtj = null;

                    //删除肯定是选中的项目
                    newProjectJTJFragment.setData(projectMessage_jtj);
                }

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
                    List<ProjectFGGD> list = DBHelper.getProjectFGGDDao().queryBuilder().where(ProjectFGGDDao.Properties.ProjectName.eq(projectname)).list();
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
                    entity.setCreator(1);
                    DBHelper.getProjectFGGDDao().insert(entity);
                    mPresenter.loadData(null);
                } else if (checkmoudle == 2) {
                    if (DBHelper.getProjectJTJDao().queryBuilder().where(ProjectJTJDao.Properties.ProjectName.eq(projectname)).count() > 0) {
                        ArmsUtils.snackbarText("该检测项目已经存在，请勿重复新建");
                        return;
                    }
                    ProjectJTJ entity = new ProjectJTJ();
                    entity.setProjectName(projectname);
                    entity.setCurveName("曲线1");
                    entity.setIsdefault(true);
                    entity.setId(null);
                    entity.setCurveOrder(0);
                    entity.setCreator(1);
                    DBHelper.getProjectJTJDao().insert(entity);
                    mPresenter.loadData(null);
                }


            }
        });
        alertDialog.show();
    }

    private void checkJTJ() {

        mJtj.setBackground(getResources().getDrawable(R.color.BAC0));
        mFggd.setBackground(null);
        checkmoudle = 2;
        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.project_fram, jtjProjectFragment, mSparseTags_Project.get(R.id.jtj));


        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.frame, newProjectJTJFragment, mSparseTags.get(R.id.jtj));
        newProjectJTJFragment.setData(projectMessage_jtj);
    }

    private void checkFGGD() {
        mFggd.setBackground(getResources().getDrawable(R.color.BAC0));
        mJtj.setBackground(null);
        checkmoudle = 1;
        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.project_fram, fggdProjectFragment, mSparseTags_Project.get(R.id.fggd));


        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.frame, newProjectFGGDFragment, mSparseTags.get(R.id.fggd));
        newProjectFGGDFragment.setData(projectMessage_fggd);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showSportDialog(String title) {
        //尝试先消除上次的dialog 避免弹框不消失
        if (!mSportDialog.isShowing()) {
            mSportDialog.show();
        }

    }

    @Override
    public void hideSportDialog() {
        if (mSportDialog.isShowing()) {
            mSportDialog.dismiss();
        }
    }

    @Override
    public void RefreshList() {
        if (checkmoudle == 1) {
            checkFGGD();
        } else {
            checkJTJ();
        }
    }

    @Override
    public void loadJTJFinish() {
        jtjProjectFragment.setData(mDateList_jtj);
    }

    @Override
    public void loadFGGDFinish() {
        fggdProjectFragment.setData(mDateList_fggd);
    }

    @Override
    public void makeDialogNewVersion(String filename, String local, int checkmoudle, String linkurl, UpdateFileMessage message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("makeDialogNewVersion");
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setPositiveButton(getString(R.string.download), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.downLoadProject(filename, checkmoudle, linkurl);

                    }
                });
                builder.setNeutralButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setTitle(getString(R.string.projectversionnmessage))
                        .setMessage(getString(R.string.serviceversion) + filename + "\r\n" + getString(R.string.localversion) + (local.equals("") ? getString(R.string.whihoutnewversion) : local))
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false);
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);//设置弹出框失去焦点是否隐藏
                dialog.show();
            }
        });
    }

    @Override
    public void inputProject(File file, String filename, int checkmoudle) {
        List<String> list = new ArrayList<>();
        list.add(file.getAbsolutePath());
        if (1==checkmoudle) {
            mPresenter.inputFGGDProject(list);
        } else if (2==checkmoudle) {
            mPresenter.inputJTJProject(list);
        }
    }


}