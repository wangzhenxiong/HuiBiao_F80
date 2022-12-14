package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.api.back.BeginTheoryExam_Back;
import com.dy.huibiao_f80.di.component.DaggerExamTheoryComponent;
import com.dy.huibiao_f80.mvp.contract.ExamTheoryContract;
import com.dy.huibiao_f80.mvp.presenter.ExamTheoryPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.TheoryQuestionAdapter;
import com.dy.huibiao_f80.mvp.ui.adapter.TheoryQuestionJudgeAdapter;
import com.dy.huibiao_f80.mvp.ui.adapter.TheoryQuestionMultipleAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ExamTheoryActivity extends BaseActivity<ExamTheoryPresenter> implements ExamTheoryContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;

    @BindView(R.id.exam_title)
    LinearLayout mExamTitle;
    @BindView(R.id.titletype)
    TextView mTitletype;
    @BindView(R.id.itemcontent)
    TextView mItemcontent;

    @BindView(R.id.group_checksingle)
    RadioGroup mGroupChecksingle;

    @BindView(R.id.group_multiple)
    RadioGroup mGroupMultiple;
    @BindView(R.id.radio_right)
    RadioButton mRadioRight;
    @BindView(R.id.radio_error)
    RadioButton mRadioError;
    @BindView(R.id.group_judger)
    RadioGroup mGroupJudger;
    @BindView(R.id.btn_up)
    Button mBtnUp;
    @BindView(R.id.btn_down)
    Button mBtnDown;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.toolbar_time)
    TextView mToolbarTime;
    @BindView(R.id.radio1)
    RadioButton mRadio1;
    @BindView(R.id.radio2)
    RadioButton mRadio2;
    @BindView(R.id.radio3)
    RadioButton mRadio3;
    @BindView(R.id.radio4)
    RadioButton mRadio4;
    @BindView(R.id.checkbox1)
    CheckBox mCheckbox1;
    @BindView(R.id.checkbox2)
    CheckBox mCheckbox2;
    @BindView(R.id.checkbox3)
    CheckBox mCheckbox3;
    @BindView(R.id.checkbox4)
    CheckBox mCheckbox4;

    @Inject
    AlertDialog mSportDialog;
    private String examinationId;
    private String examinerId;
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    private int theoryExamTime;
    private boolean runflag = true;

    private List<BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean> theoryQuestionRadioList;
    private List<BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean> theoryQuestionMultipleList;
    private List<BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean> theoryQuestionJudgeList;
    private int singleList_p = -1;
    private int multipleList_p = -1;
    private int judgeList_p = -1;

    private View theoryQuestionRadio_layout;
    private View theoryQuestionMultiple_layout;
    private View theoryQuestionJudge_layout;

    private Object nowShowTitle;

    private String title1 = "???????????????";
    private TheoryQuestionAdapter adapter1;
    private String title2 = "???????????????";
    private TheoryQuestionMultipleAdapter adapter2;
    private String title3 = "???????????????";
    private TheoryQuestionJudgeAdapter adapter3;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExamTheoryComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_examtheory; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        WindowManager windowManager = this.getActivity().getWindowManager();
        examinationId = intent.getStringExtra("examinationId");
        examinerId = intent.getStringExtra("examinerId");
        mPresenter.beginTheoryExam(examinationId);
        mBtnUp.setText("<< ?????????");
        mBtnDown.setText("????????? >>");
        mScheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) ArmsUtils.obtainAppComponentFromContext(this).executorService();
        mRadio1.setChecked(false);
        mRadio2.setChecked(false);
        mRadio3.setChecked(false);
        mRadio4.setChecked(false);
        mRadioRight.setChecked(false);
        mRadioError.setChecked(false);
        mRadio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadio1.isChecked()) {
                    checked("A");

                }
            }
        });

        mRadio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadio2.isChecked()) {
                    checked("B");
                }

            }
        });

        mRadio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadio3.isChecked()) {
                    checked("C");
                }

            }
        });

        mRadio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadio4.isChecked()) {
                    checked("D");
                }

            }
        });

        mRadioRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioRight.isChecked()) {
                    checked("A");
                }

            }
        });

        mRadioError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioError.isChecked()) {
                    checked("B");
                }

            }
        });


        mCheckbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckbox1.isChecked()) {

                    multipleCheck("A");
                }else {
                    multipleUnCheck("A");

                }

            }
        });

        mCheckbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckbox2.isChecked()) {

                    multipleCheck("B");
                }else {
                    multipleUnCheck("B");

                }

            }
        });
        mCheckbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckbox3.isChecked()) {

                    multipleCheck("C");
                }else {
                    multipleUnCheck("C");

                }

            }
        });
        mCheckbox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckbox4.isChecked()) {

                    multipleCheck("D");
                }else {
                    multipleUnCheck("D");

                }

            }
        });




    }

    private void checked(String a) {
        LogUtils.d("?????????"+a);
        if (nowShowTitle instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean) {
            theoryQuestionRadioList.get(singleList_p).setStudentAnswer(a);
        } else if (nowShowTitle instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean) {
            theoryQuestionMultipleList.get(multipleList_p).setStudentAnswer(a);
        } else if (nowShowTitle instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean) {
            theoryQuestionJudgeList.get(judgeList_p).setStudentAnswer(a);
        }
    }

    private void multipleUnCheck(String c) {
        if (nowShowTitle instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean theoryQuestionRadioListBean = theoryQuestionRadioList.get(singleList_p);
            String answer = theoryQuestionRadioListBean.getStudentAnswer();
            if (answer.contains(c)) {
                theoryQuestionRadioListBean.setStudentAnswer(answer.replace(c,""));
            }

        } else if (nowShowTitle instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean theoryQuestionMultipleListBean = theoryQuestionMultipleList.get(multipleList_p);
            String answer = theoryQuestionMultipleListBean.getStudentAnswer();
            if (answer.contains(c)) {
                theoryQuestionMultipleListBean.setStudentAnswer(answer.replace(c,""));
            }
        } else if (nowShowTitle instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean theoryQuestionJudgeListBean = theoryQuestionJudgeList.get(judgeList_p);
            String answer = theoryQuestionJudgeListBean.getStudentAnswer();
            if (answer.contains(c)) {
                theoryQuestionJudgeListBean.setStudentAnswer(answer.replace(c,""));
            }
        }
    }

    private void multipleCheck(String c) {
        LogUtils.d(c+"_check  " );
        if (nowShowTitle instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean theoryQuestionRadioListBean = theoryQuestionRadioList.get(singleList_p);
            String answer = theoryQuestionRadioListBean.getStudentAnswer();
            if (answer.isEmpty()) {
                theoryQuestionRadioListBean.setStudentAnswer(c+"");
            } else {
                if (!answer.contains(c+"")) {
                    theoryQuestionRadioListBean.setStudentAnswer(answer + c);
                }
            }

        } else if (nowShowTitle instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean theoryQuestionMultipleListBean = theoryQuestionMultipleList.get(multipleList_p);

            String answer = theoryQuestionMultipleListBean.getStudentAnswer();
            if (answer.isEmpty()) {
                theoryQuestionMultipleListBean.setStudentAnswer(c+"");
            } else {
                if (!answer.contains(c+"")) {
                    theoryQuestionMultipleListBean.setStudentAnswer(answer + c+"");
                }
            }
        } else if (nowShowTitle instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean theoryQuestionJudgeListBean = theoryQuestionJudgeList.get(judgeList_p);
            String answer = theoryQuestionJudgeListBean.getStudentAnswer();
            if (answer.isEmpty()) {
                theoryQuestionJudgeListBean.setStudentAnswer(c+"");
            } else {
                if (!answer.contains(c+"")) {
                    theoryQuestionJudgeListBean.setStudentAnswer(answer + c+"");
                }
            }
        }
    }

    @Override
    public void showLoading() {
        if (!mSportDialog.isShowing()) {
           mSportDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mSportDialog.isShowing()) {
            mSportDialog.dismiss();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

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
   private BeginTheoryExam_Back beginTheoryExamBack;
    /**
     * @param back ??????????????????
     */
    @Override
    public void showExamTitle(BeginTheoryExam_Back back) {
        beginTheoryExamBack=back;
        BeginTheoryExam_Back.EntityBean entity = back.getEntity();
        BeginTheoryExam_Back.EntityBean.TheoryPaperBean theoryPaper = entity.getTheoryPaper();
        theoryExamTime = theoryPaper.getTheoryExamTime() * 60;
        if (BuildConfig.DEBUG){
            theoryExamTime=10;
        }
        mScheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (runflag) {
                    if (theoryExamTime > 0) {
                        int i = theoryExamTime / 60;
                        int i1 = theoryExamTime % 60;
                        String text = "????????????  " + (i < 10 ? "0" + i : "" + i) + ":" + (i1 < 10 ? "0" + i1 : "" + i1);
                        theoryExamTime--;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null != mToolbarTime) {
                                    mToolbarTime.setText(text);
                                }
                            }
                        });

                    } else {
                        mPresenter.submit(examinationId, examinerId, beginTheoryExamBack,false);
                        runflag = false;
                    }
                }


            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        //mExamname.setText(theoryPaper.getName() + "(??????" + theoryPaper.getQuestionNum() + "????????????" + theoryPaper.getAllScore() + "???)");

        //?????????
        theoryQuestionRadioList = entity.getTheoryQuestionRadioList();
        theoryQuestionRadio_layout = LayoutInflater.from(getActivity()).inflate(R.layout.exam_layout, null);
        initTheoryQuestionRadio();
        mExamTitle.addView(theoryQuestionRadio_layout);
        //?????????
        theoryQuestionMultipleList = entity.getTheoryQuestionMultipleList();
        theoryQuestionMultiple_layout = LayoutInflater.from(getActivity()).inflate(R.layout.exam_layout, null);
        initTheoryQuestionMultiple();
        mExamTitle.addView(theoryQuestionMultiple_layout);
        //?????????
        theoryQuestionJudgeList = entity.getTheoryQuestionJudgeList();
        theoryQuestionJudge_layout = LayoutInflater.from(getActivity()).inflate(R.layout.exam_layout, null);
        initTheoryQuestionJudge();
        mExamTitle.addView(theoryQuestionJudge_layout);
    }

    private void initTheoryQuestionRadio() {
        TextView examtype = (TextView) theoryQuestionRadio_layout.findViewById(R.id.exam_type);
        examtype.setText(title1);

        LinearLayout parent = (LinearLayout) theoryQuestionRadio_layout.findViewById(R.id.parent);
        parent.setBackgroundColor(getResources().getColor(R.color.EXAMBAC01));

        RecyclerView recyclerView = (RecyclerView) theoryQuestionRadio_layout.findViewById(R.id.recycleview);
        ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 5));

        adapter1 = new TheoryQuestionAdapter(R.layout.exam_title_number_layout, theoryQuestionRadioList);
        recyclerView.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                singleList_p = position;
                multipleList_p = -1;
                judgeList_p = -1;
                check1(title1, position);
                LogUtils.d(singleList_p + "  " + multipleList_p + "  " + judgeList_p);

            }
        });
        //????????????
        BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean object = theoryQuestionRadioList.get(0);
        showExamMessage(title1, object, 0);
        nowShowTitle = object;
        singleList_p = 0;
        multipleList_p = -1;
        judgeList_p = -1;
        theoryQuestionRadioList.get(0).setCheck(true);
        adapter1.notifyDataSetChanged();
        LogUtils.d(singleList_p + "  " + multipleList_p + "  " + judgeList_p);

    }

    private void initTheoryQuestionMultiple() {
        TextView examtype = (TextView) theoryQuestionMultiple_layout.findViewById(R.id.exam_type);
        LinearLayout parent = (LinearLayout) theoryQuestionMultiple_layout.findViewById(R.id.parent);
        parent.setBackgroundColor(getResources().getColor(R.color.EXAMBAC2));

        examtype.setText(title2);
        RecyclerView recyclerView = (RecyclerView) theoryQuestionMultiple_layout.findViewById(R.id.recycleview);
        ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 5));
        adapter2 = new TheoryQuestionMultipleAdapter(R.layout.exam_title_number_layout, theoryQuestionMultipleList);
        recyclerView.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //???????????????
                singleList_p = theoryQuestionRadioList.size() - 1;
                multipleList_p = position;
                judgeList_p = -1;
                check2(title2, position);
                LogUtils.d(singleList_p + "  " + multipleList_p + "  " + judgeList_p);

            }
        });
    }

    private void initTheoryQuestionJudge() {
        TextView examtype = (TextView) theoryQuestionJudge_layout.findViewById(R.id.exam_type);
        LinearLayout parent = (LinearLayout) theoryQuestionJudge_layout.findViewById(R.id.parent);
        parent.setBackgroundColor(getResources().getColor(R.color.EXAMBAC3));

        examtype.setText(title3);
        RecyclerView recyclerView = (RecyclerView) theoryQuestionJudge_layout.findViewById(R.id.recycleview);
        ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 5));
        adapter3 = new TheoryQuestionJudgeAdapter(R.layout.exam_title_number_layout, theoryQuestionJudgeList);
        recyclerView.setAdapter(adapter3);
        adapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                singleList_p = theoryQuestionRadioList.size() - 1;
                multipleList_p = theoryQuestionMultipleList.size() - 1;
                judgeList_p = position;
                check3(title3, position);
                LogUtils.d(singleList_p + "  " + multipleList_p + "  " + judgeList_p);

            }
        });
    }

    private void check1(String title, int position) {
        BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean object = theoryQuestionRadioList.get(position);
        showExamMessage(title, object, position);
        nowShowTitle = object;

        for (int i = 0; i < theoryQuestionRadioList.size(); i++) {
            theoryQuestionRadioList.get(i).setCheck(false);
            if (i == position) {
                theoryQuestionRadioList.get(i).setCheck(true);
            }
        }

        for (int i = 0; i < theoryQuestionMultipleList.size(); i++) {
            theoryQuestionMultipleList.get(i).setCheck(false);
        }

        for (int i = 0; i < theoryQuestionJudgeList.size(); i++) {
            theoryQuestionJudgeList.get(i).setCheck(false);
        }
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
    }

    private void check2(String title, int position) {
        BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean object = theoryQuestionMultipleList.get(position);
        showExamMessage(title, object, position);
        nowShowTitle = object;

        for (int i = 0; i < theoryQuestionRadioList.size(); i++) {
            theoryQuestionRadioList.get(i).setCheck(false);
        }

        for (int i = 0; i < theoryQuestionMultipleList.size(); i++) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean theoryQuestionJudgeListBean = theoryQuestionMultipleList.get(i);
            theoryQuestionJudgeListBean.setCheck(false);
            if (i == position) {
                theoryQuestionJudgeListBean.setCheck(true);
            }
        }

        for (int i = 0; i < theoryQuestionJudgeList.size(); i++) {
            theoryQuestionJudgeList.get(i).setCheck(false);
        }

        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
    }

    private void check3(String title, int position) {
        BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean object = theoryQuestionJudgeList.get(position);
        showExamMessage(title, object, position);
        nowShowTitle = object;

        for (int i = 0; i < theoryQuestionRadioList.size(); i++) {
            theoryQuestionRadioList.get(i).setCheck(false);
        }
        for (int i = 0; i < theoryQuestionMultipleList.size(); i++) {
            theoryQuestionMultipleList.get(i).setCheck(false);
        }
        for (int i = 0; i < theoryQuestionJudgeList.size(); i++) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean theoryQuestionJudgeListBean = theoryQuestionJudgeList.get(i);
            theoryQuestionJudgeListBean.setCheck(false);
            if (i == position) {
                theoryQuestionJudgeListBean.setCheck(true);
            }
        }


        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
    }


    /**
     * @param title   ??????
     * @param object  BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean
     *                BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean
     *                BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean
     * @param persion
     */
    private void showExamMessage(String title, Object object, int persion) {

        //????????????????????????
        if (object instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean object1 = (BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean) object;
            Double score = object1.getScore();
            mTitletype.setText(title + "(" + score + "???)");
            mItemcontent.setText((persion + 1) + "???" + object1.getContent());
            mGroupChecksingle.setVisibility(View.VISIBLE);
            mGroupMultiple.setVisibility(View.GONE);
            mGroupJudger.setVisibility(View.GONE);
            String options = object1.getOptions();
            String[] split = options.split("#option#");
            for (int i = 0; i < split.length; i++) {
                String text = split[i].replace("<p>", "").replace("</p>", "");
                if (i == 0) {
                    mRadio1.setText(text);
                } else if (i == 1) {
                    mRadio2.setText(text);
                } else if (i == 2) {
                    mRadio3.setText(text);
                } else if (i == 3) {
                    mRadio4.setText(text);
                }
            }
            mGroupChecksingle.clearCheck();
            String studentAnswer = object1.getStudentAnswer();
            LogUtils.d(studentAnswer);
            if (studentAnswer.equals("A")) {
                mGroupChecksingle.check(R.id.radio1);
            } else if (studentAnswer.equals("B")) {
                mGroupChecksingle.check(R.id.radio2);
            } else if (studentAnswer.equals("C")) {
                mGroupChecksingle.check(R.id.radio3);
            } else if (studentAnswer.equals("D")) {
                mGroupChecksingle.check(R.id.radio4);
            }

        } else if (object instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean object1 = (BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean) object;
            Double score = object1.getScore();
            mTitletype.setText(title + "(" + score + "???)");
            mItemcontent.setText((persion + 1) + "???" + object1.getContent());
            mGroupChecksingle.setVisibility(View.GONE);
            mGroupMultiple.setVisibility(View.VISIBLE);
            mGroupJudger.setVisibility(View.GONE);
            String options = object1.getOptions();
            String[] split = options.split("#option#");
            for (int i = 0; i < split.length; i++) {
                String text = split[i].replace("<p>", "").replace("</p>", "");
                if (i == 0) {
                    mCheckbox1.setText(text);
                } else if (i == 1) {
                    mCheckbox2.setText(text);
                } else if (i == 2) {
                    mCheckbox3.setText(text);
                } else if (i == 3) {
                    mCheckbox4.setText(text);
                }
            }
            mCheckbox1.setChecked(false);
            mCheckbox2.setChecked(false);
            mCheckbox3.setChecked(false);
            mCheckbox4.setChecked(false);
            String studentAnswer = object1.getStudentAnswer();
            LogUtils.d(studentAnswer);
            if (studentAnswer.contains("A")) {
                mCheckbox1.setChecked(true);
            } else {
                mCheckbox1.setChecked(false);
            }
            if (studentAnswer.contains("B")) {
                mCheckbox2.setChecked(true);
            } else {
                mCheckbox2.setChecked(false);
            }
            if (studentAnswer.contains("C")) {
                mCheckbox3.setChecked(true);
            } else {
                mCheckbox3.setChecked(false);
            }
            if (studentAnswer.contains("D")) {
                mCheckbox4.setChecked(true);
            } else {
                mCheckbox4.setChecked(false);
            }

        } else if (object instanceof BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean object1 = (BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean) object;
            Double score = object1.getScore();
            mTitletype.setText(title + "(" + score + "???)");
            mItemcontent.setText((persion + 1) + "???" + object1.getContent());
            mGroupChecksingle.setVisibility(View.GONE);
            mGroupMultiple.setVisibility(View.GONE);
            mGroupJudger.setVisibility(View.VISIBLE);
            mGroupJudger.clearCheck();
            String studentAnswer = object1.getStudentAnswer();
            LogUtils.d(studentAnswer);
            if (studentAnswer.equals("A")) {
                mGroupJudger.check(R.id.radio_right);
            } else if (studentAnswer.equals("B")) {
                mGroupJudger.check(R.id.radio_error);
            }
        }


    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void submitSuccess() {
        runflag=false;
        finish();
        Intent content = new Intent(getActivity(),ExamStateActivity.class);
        content.putExtra("examinationId", examinationId);
        content.putExtra("examinerId", examinerId);
        ArmsUtils.startActivity(content);
    }

    boolean isup = false;
    boolean isdown = false;

    @OnClick({R.id.btn_up, R.id.btn_down, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_up:
                up();
                isup = true;
                isdown = false;
                break;
            case R.id.btn_down:
                down();
                isdown = true;
                isup = false;
                break;
            case R.id.btn_submit:
                 makeDialog();
                break;
        }
    }
    private void makeDialog() {
        int noanswer = 0;
        BeginTheoryExam_Back.EntityBean entity = beginTheoryExamBack.getEntity();
        List<BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean> analysePaperList = entity.getTheoryQuestionJudgeList();
        for (int i = 0; i < analysePaperList.size(); i++) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionJudgeListBean analysePaperListBean = analysePaperList.get(i);
            if (analysePaperListBean.getStudentAnswer().isEmpty()) {
                noanswer++;
            }
        }
        List<BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean> theoryQuestionMultipleList = entity.getTheoryQuestionMultipleList();
        for (int i = 0; i < theoryQuestionMultipleList.size(); i++) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionMultipleListBean analysePaperListBean = theoryQuestionMultipleList.get(i);
            if (analysePaperListBean.getStudentAnswer().isEmpty()) {
                noanswer++;
            }
        }
        List<BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean> theoryQuestionRadioList = entity.getTheoryQuestionRadioList();
        for (int i = 0; i < theoryQuestionRadioList.size(); i++) {
            BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean analysePaperListBean = theoryQuestionRadioList.get(i);
            if (analysePaperListBean.getStudentAnswer().isEmpty()) {
                noanswer++;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.submithint_layout, null);
        TextView title = (TextView) inflate.findViewById(R.id.title);
        TextView message = (TextView) inflate.findViewById(R.id.message);
        Button confirm = (Button) inflate.findViewById(R.id.confirm);
        Button cancle = (Button) inflate.findViewById(R.id.cancle);
        title.setText("??????");
        builder.setView(inflate);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        String content = " <font style=\"font-size:16dp\" color=\"#3856FC\">"+"??????"+(analysePaperList.size()+theoryQuestionMultipleList.size()+theoryQuestionRadioList.size())
                +"?????????????????????"+(analysePaperList.size()+theoryQuestionMultipleList.size()+theoryQuestionRadioList.size()-noanswer)+"???????????????</font>\n" +
                " <font style=\"font-size:16dp\" color=\"#FF0000\">" +noanswer + "</font>" +
                " <font style=\"font-size:16dp\" color=\"#3856FC\">???????????????????????????</font>\n";
        //"????????????...??????????????? "+message.num + " ??????"
        message.setText(Html.fromHtml(content));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mPresenter.submit(examinationId,examinerId,beginTheoryExamBack,true);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });




    }
    //?????????
    private void down() {
        LogUtils.d(singleList_p + "  " + multipleList_p + "  " + judgeList_p);

        //39 0 0
        //?????????????????????????????????

        singleList_p++;
        if (singleList_p > theoryQuestionRadioList.size() - 1) {
            singleList_p--;

            multipleList_p++;
            if (multipleList_p > theoryQuestionMultipleList.size() - 1) {
                multipleList_p--;
                judgeList_p++;
                if (judgeList_p > theoryQuestionJudgeList.size() - 1) {
                    judgeList_p--;
                    ArmsUtils.snackbarText("?????????????????????");
                } else {
                    check3(title3, judgeList_p);
                }
            } else {
                check2(title2, multipleList_p);
            }
        } else {
            check1(title1, singleList_p);
        }

        LogUtils.d(singleList_p + "  " + multipleList_p + "  " + judgeList_p);

    }

    //?????????
    private void up() {
        LogUtils.d(singleList_p + "  " + multipleList_p + "  " + judgeList_p);
        //?????????????????????????????????
        // ??????ls3??????????????????0??????

        if (judgeList_p < 0) {

            if (multipleList_p < 0) {
                if (singleList_p <= 0) {
                    ArmsUtils.snackbarText("?????????????????????");
                } else {
                    singleList_p--;
                    if (singleList_p == -1) {
                        check1(title1, 0);
                    } else {
                        check1(title1, singleList_p);
                    }

                }
            } else {
                multipleList_p--;
                if (multipleList_p == -1) {
                    check1(title1, singleList_p);
                } else {
                    check2(title2, multipleList_p);
                }

            }
        } else {
            judgeList_p--;
            if (judgeList_p == -1) {
                check2(title2, multipleList_p);
            } else {
                check3(title3, judgeList_p);
            }

        }


        LogUtils.d(singleList_p + "  " + multipleList_p + "  " + judgeList_p);

    }

    @Override
    public void onBackPressed() {
        ArmsUtils.snackbarText("????????????????????????");
    }
}