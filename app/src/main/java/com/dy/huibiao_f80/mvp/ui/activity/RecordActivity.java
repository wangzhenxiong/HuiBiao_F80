package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.service.ExamOperationService;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.di.component.DaggerRecordComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.contract.RecordContract;
import com.dy.huibiao_f80.mvp.presenter.RecordPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.TestRecrdAdapter;
import com.dy.huibiao_f80.printer.MyPrinterIntentService;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
    @Inject
    List<TestRecord> testRecordList;
    @Inject
    TestRecrdAdapter testRecrdAdapter;
    @Inject
    AlertDialog sportDialog;
    @BindView(R.id.toolbar_time)
    TextView mToolbarTime;
    private boolean isSeaching = false;
    private String examinationId = "";
    private String examinerId = "";
    private String examId = "";
    @Override
    public boolean useEventBus() {
        return false;
    }
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
        Intent intent = getIntent();
        String id = intent.getStringExtra("examId");
        //examId = id == null ? "" : id;
        if (MyAppLocation.myAppLocation.mExamOperationService.isStartExamOperation()) {
            examinationId = MyAppLocation.myAppLocation.mExamOperationService.getExaminationId();
            examinerId = MyAppLocation.myAppLocation.mExamOperationService.getExaminerId();
            examId = MyAppLocation.myAppLocation.mExamOperationService.getNowOperationExam().getId();
        }
        ArmsUtils.configRecyclerView(mRecylerview, new GridLayoutManager(this, 1));
        testRecrdAdapter.setEmptyView(R.layout.emptyview, (ViewGroup) mRecylerview.getParent());
        testRecrdAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.checkbox) {
                    GalleryBean galleryBean = testRecordList.get(position);
                    if (galleryBean.isCheckd()) {
                        galleryBean.setCheckd(false);
                    } else {
                        galleryBean.setCheckd(true);
                    }
                }
            }
        });
        mRecylerview.setAdapter(testRecrdAdapter);
        mChoseall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < testRecordList.size(); i++) {
                    testRecordList.get(i).setCheckd(isChecked);
                }
                testRecrdAdapter.notifyDataSetChanged();
            }
        });
        testRecrdAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TestRecord testRecord = testRecordList.get(position);
                Intent content = new Intent(getActivity(), RecordDetailActivity.class);
                content.putExtra("id", testRecord.getId());
                ArmsUtils.startActivity(content);
            }
        });
        mPresenter.load(examinationId, examinerId, this.examId);
    }


    @Override
    public void showLoading() {
        if (null != sportDialog) {
            if (!sportDialog.isShowing()) {
                sportDialog.show();
            }
        }
    }

    @Override
    public void hideLoading() {
        if (null != sportDialog) {
            if (sportDialog.isShowing()) {
                sportDialog.dismiss();
            }
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

    @OnClick({R.id.testmoudle, R.id.testprojectname, R.id.jujdger, R.id.seach, R.id.print, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.testmoudle:
                makeDialogChoseMoudle();
                break;
            case R.id.testprojectname:
                mPresenter.makeDialogChoseProject();
                break;
            case R.id.jujdger:
                makeDialogChoseJujdger();
                break;
            case R.id.seach:
                if (isSeaching) {
                    isSeaching = false;
                    mSeach.setText("查询");
                    mTestmoudle.setText("选择检测方式");
                    mTestprojectname.setText("选择检测项目");
                    mJujdger.setText("选择判定结果");
                    mPresenter.load(examinationId, examinerId, examId);
                } else {
                    String testmoudle = mTestmoudle.getText().toString();
                    String testproject = mTestprojectname.getText().toString();
                    String jujdger = mJujdger.getText().toString();
                    if (testmoudle.equals("选择检测方式")
                            && testproject.equals("选择检测项目")
                            && jujdger.equals("选择判定结果")) {
                        ArmsUtils.snackbarText("请选择查询条件");
                        return;
                    }
                    isSeaching = true;
                    mSeach.setText("取消");
                    mPresenter.seach(testmoudle, testproject, jujdger, examinationId, examinerId, examId);
                }
                break;
            case R.id.print:
                int checkNum_print = 0;
                List<String> checkSamples_print = new ArrayList<>();
                for (int i = 0; i < testRecordList.size(); i++) {
                    TestRecord sampling = testRecordList.get(i);
                    boolean check = sampling.isCheckd();
                    if (check) {
                        checkNum_print++;
                        checkSamples_print.add(sampling.getSysCode());
                    }
                }
                if (checkNum_print == 0) {
                    ArmsUtils.snackbarText("请选择需要打印的检测记录");
                } else {
                    // TODO: 10/21/22 打印
                    MyPrinterIntentService.startActionToDeailPrint(getActivity(), checkSamples_print);
                }
                break;
            case R.id.delete:
                int checkNum_delete = 0;
                List<TestRecord> checkSamples = new ArrayList<>();
                for (int i = 0; i < testRecordList.size(); i++) {
                    TestRecord sampling = testRecordList.get(i);
                    boolean check = sampling.isCheckd();
                    if (check) {
                        checkNum_delete++;
                        checkSamples.add(sampling);
                    }
                }
                if (checkNum_delete == 0) {
                    ArmsUtils.snackbarText("请选择需要删除的检测记录");
                } else {
                    makeDeleteDialog(checkSamples);

                }
                break;
        }
    }

    private void makeDeleteDialog(List<TestRecord> checkSamples) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("提示");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setMessage("确定要删除所选择的检测记录吗？");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper.getTestRecordDao().deleteInTx(checkSamples);
                ArmsUtils.snackbarText("删除成功");
                if (isSeaching) {
                    mPresenter.seach(mTestmoudle.getText().toString(), mTestprojectname.getText().toString(), mJujdger.getText().toString(), examinationId, examinerId, examId);
                } else {
                    mPresenter.load(examinationId, examinerId, examId);
                }
                dialog.dismiss();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void makeDialogChoseJujdger() {
        String[] strings = new String[]{"合格", "不合格", "可疑", "--"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("请选择判断结果");
        builder.setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String string = strings[which];
                mJujdger.setText(string);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        // dialog弹出后，点击界面其他部分dialog消失
        dialog.setCanceledOnTouchOutside(true);
    }


    private void makeDialogChoseMoudle() {
        String[] strings = new String[]{"分光光度", "胶体金"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("请选择检测模块");
        builder.setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String string = strings[which];
                mTestmoudle.setText(string);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        // dialog弹出后，点击界面其他部分dialog消失
        dialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setChosedProject(String s) {
        mTestprojectname.setText(s);
    }


}