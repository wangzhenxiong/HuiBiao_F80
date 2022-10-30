package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.api.back.GetExamPage_Back;
import com.dy.huibiao_f80.di.component.DaggerExamStateComponent;
import com.dy.huibiao_f80.mvp.contract.ExamStateContract;
import com.dy.huibiao_f80.mvp.presenter.ExamStatePresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ExamStateActivity extends BaseActivity<ExamStatePresenter> implements ExamStateContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.time1)
    TextView mTime1;
    @BindView(R.id.startexam1)
    Button mStartexam1;
    @BindView(R.id.time2)
    TextView mTime2;
    @BindView(R.id.startexa2)
    Button mStartexa2;
    @BindView(R.id.time3)
    TextView mTime3;
    @BindView(R.id.startexam3)
    Button mStartexam3;
    private String examinationId;
    private String examinerId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExamStateComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_examstate; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        examinationId = intent.getStringExtra("examinationId");
        examinerId = intent.getStringExtra("examinerId");
        mPresenter.getExamPage(examinerId, examinationId);
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

    @Override
    public void showState(GetExamPage_Back back) {
        GetExamPage_Back.EntityBean entity = back.getEntity();
        GetExamPage_Back.EntityBean.ExaminationBean examination = entity.getExamination();
        GetExamPage_Back.EntityBean.ExaminerBean examiner = entity.getExaminer();
        //理论考试状态：1未考2已考
        Integer examStatus = examiner.getExamStatus();
        //分析题考试状态：1未考2已考
        Integer analyseStatus = examiner.getAnalyseStatus();
        //实操题考试状态：1未考2进行中3已结束
        Integer operationStatus = examiner.getOperationStatus();
        //时间
        Integer theoryExamTime = examination.getTheoryExamTime();
        Integer analyseExamTime = examination.getAnalyseExamTime();
        Integer operationExamTime = examination.getOperationExamTime();

        if (examStatus == 1) {
            mStartexam1.setClickable(true);
            mStartexam1.setBackground(getResources().getDrawable(R.drawable.btn_background_blue));
        } else if (examStatus == 2) {

            mStartexam1.setClickable(false);
            if (true){
                mStartexam1.setClickable(true);
            }
            mStartexam1.setBackground(getResources().getDrawable(R.drawable.btn_background_gray));
        }

        if (analyseStatus == 1) {
            mStartexa2.setClickable(true);
            mStartexa2.setBackground(getResources().getDrawable(R.drawable.btn_background_blue));
        } else if (analyseStatus == 2) {
            mStartexa2.setClickable(false);
            if (true){
                mStartexa2.setClickable(true);
            }
            mStartexa2.setBackground(getResources().getDrawable(R.drawable.btn_background_gray));
        }
        if (operationStatus == 1) {
            mStartexam3.setClickable(true);
            mStartexam3.setBackground(getResources().getDrawable(R.drawable.btn_background_blue));
        } else if (operationStatus == 2) {
            mStartexam3.setClickable(false);
            if (true){
                mStartexam3.setClickable(true);
            }
            mStartexam3.setBackground(getResources().getDrawable(R.drawable.btn_background_gray));
        }

        if (examination.getIsShowScore() == 1) {
            mTime1.setVisibility(View.VISIBLE);
            mTime2.setVisibility(View.VISIBLE);
            mTime3.setVisibility(View.VISIBLE);

            mTime1.setText("考试时间：" + theoryExamTime + "分钟");
            mTime2.setText("考试时间：" + analyseExamTime + "分钟");
            mTime3.setText("考试时间：" + operationExamTime + "分钟");
        } else {
            mTime1.setVisibility(View.GONE);
            mTime2.setVisibility(View.GONE);
            mTime3.setVisibility(View.GONE);
        }


    }

    @OnClick({R.id.startexam1, R.id.startexa2, R.id.startexam3})
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("examinationId",examinationId);
        intent.putExtra("examinerId",examinerId);
        switch (view.getId()) {
            case R.id.startexam1:
                intent.setClass(this,ExamTheoryActivity.class);
                ArmsUtils.startActivity(intent);
                finish();
                break;
            case R.id.startexa2:
                intent.setClass(this,ExamAnalyseActivity.class);
                ArmsUtils.startActivity(intent);
                finish();
                break;
            case R.id.startexam3:
                intent.setClass(this,ExamOperationActivity.class);
                ArmsUtils.startActivity(intent);
                finish();
                break;
        }
    }
}