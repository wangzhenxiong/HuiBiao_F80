package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.service.ExamOperationService;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.di.component.DaggerStartTestComponent;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.contract.StartTestContract;
import com.dy.huibiao_f80.mvp.presenter.StartTestPresenter;
import com.dy.huibiao_f80.mvp.ui.fragment.FGGDProjectFragment;
import com.dy.huibiao_f80.mvp.ui.fragment.JTJProjectFragment;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.TitleItemDecoration;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

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

    @BindView(R.id.btn_nextstep)
    Button mBtnNextstep;

    @Named("fggd")
    @Inject
    List<BaseProjectMessage> mDateList_fggd;
    @Named("jtj")
    @Inject
    List<BaseProjectMessage> mDateList_jtj;
    @Inject
    AlertDialog mSportDialog;
    @BindView(R.id.toolbar_time)
    TextView mToolbarTime;
    @BindView(R.id.project_fram)
    FrameLayout mProjectFram;
    @BindView(R.id.layout1)
    LinearLayout mLayout1;
    private SparseArray<String> mSparseTags_Project = new SparseArray<>();
    private ProjectFGGDDao projectFGGDDao;
    private ProjectJTJDao projectJTJDao;

    private TitleItemDecoration mDecoration;
    private int checkmoudle = 1;
    private BaseProjectMessage chosedProject_FGGD;
    private BaseProjectMessage chosedProject_JTJ;
    private FGGDProjectFragment fggdProjectFragment;
    private JTJProjectFragment jtjProjectFragment;


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
            if (null!=mToolbarTime){
                mToolbarTime.setText("正在提交考试结果");
            }
            return;
        }
        String timestring = tags.getTimestring();
        if (null!=mToolbarTime){
            mToolbarTime.setText(timestring);
        }


    }

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

    private void initsparr() {

        mSparseTags_Project.put(R.id.fggd, "fggd_project");
        mSparseTags_Project.put(R.id.jtj, "jtj_project");

    }



    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        fggdProjectFragment = FGGDProjectFragment.newInstance();
        jtjProjectFragment = JTJProjectFragment.newInstance();
        initsparr();
        checkFGGD();
        mPresenter.loadData(null);
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
            if (null == chosedProject_FGGD) {
                ArmsUtils.snackbarText("请选择检测项目");
                return;
            }
            Intent content = new Intent(this, ChoseGalleryFGGDActivity.class);
            content.putExtra("project", chosedProject_FGGD.getPjName());
            ArmsUtils.startActivity(content);
        } else if (checkmoudle == 2) {
            if (null == chosedProject_JTJ) {
                ArmsUtils.snackbarText("请选择检测项目");
                return;
            }
            Intent content = new Intent(this, ChoseGalleryJTJActivity.class);
            content.putExtra("project", chosedProject_JTJ.getPjName());
            ArmsUtils.startActivity(content);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent1(FGGDProjectFragment.FGGDProjectFragmentEvent tags) {
        chosedProject_FGGD = tags.getProjectFGGD();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent2(JTJProjectFragment.JTJProjectFragmentEvent tags) {
        chosedProject_JTJ = tags.getProjectJTJ();
    }

    private void checkJTJ() {

        mJtj.setBackground(getResources().getDrawable(R.color.BAC0));
        mFggd.setBackground(null);
        checkmoudle = 2;

        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.project_fram, jtjProjectFragment, mSparseTags_Project.get(R.id.jtj));

    }

    private void checkFGGD() {
        mFggd.setBackground(getResources().getDrawable(R.color.BAC0));
        mJtj.setBackground(null);
        checkmoudle = 1;
        mPresenter.replaceFragment(getSupportFragmentManager(), R.id.project_fram, fggdProjectFragment, mSparseTags_Project.get(R.id.fggd));

    }

    @Override
    public void loadJTJFinish() {
        jtjProjectFragment.setData(mDateList_jtj);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void loadFGGDFinish() {
        fggdProjectFragment.setData(mDateList_fggd);
    }
}