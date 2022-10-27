package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerExamHintsComponent;
import com.dy.huibiao_f80.mvp.contract.ExamHintsContract;
import com.dy.huibiao_f80.mvp.presenter.ExamHintsPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ExamHintsActivity extends BaseActivity<ExamHintsPresenter> implements ExamHintsContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.studentname)
    TextView mStudentname;
    @BindView(R.id.cardname)
    TextView mCardname;
    @BindView(R.id.schoolname)
    TextView mSchoolname;
    @BindView(R.id.examname)
    TextView mExamname;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.startexam)
    Button mStartexam;
    private String examinationId;
    private String examinerId;
    private String name;
    private String cardnumber;
    private String school;
    private String examname;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExamHintsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_examhints; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        examinationId = getIntent().getStringExtra("examinationId");
        examinerId = getIntent().getStringExtra("examinerId");
        name = getIntent().getStringExtra("name");
        cardnumber = getIntent().getStringExtra("cardnumber");
        school = getIntent().getStringExtra("school");
        examname = getIntent().getStringExtra("examname");
        mStudentname.setText(name);
        mCardname.setText(cardnumber);
        mSchoolname.setText(school);
        mExamname.setText(examname);
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

    @OnClick({R.id.examname, R.id.startexam})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.examname:
                break;
            case R.id.startexam:
                if (!mCheckbox.isChecked()) {
                    ArmsUtils.snackbarText("请阅读并同意考试注意事项！");
                    return;
                }
                Intent content = new Intent(this, ExamStateActivity.class);
                content.putExtra("examinationId", examinationId);
                content.putExtra("examinerId", examinerId);
                ArmsUtils.startActivity(content);
                break;
        }
    }
}