package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.api.back.BeginAnalyseExam_Back;
import com.dy.huibiao_f80.di.component.DaggerExamAnalyseComponent;
import com.dy.huibiao_f80.mvp.contract.ExamAnalyseContract;
import com.dy.huibiao_f80.mvp.presenter.ExamAnalysePresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ExamAnalyseActivity extends BaseActivity<ExamAnalysePresenter> implements ExamAnalyseContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.toolbar_time)
    TextView mToolbarTime;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;

    @BindView(R.id.exam_title)
    LinearLayout mExamTitle;
    @BindView(R.id.ed_answer)
    EditText mEdAnswer;
    @BindView(R.id.title_answer)
    TextView mTitleAnswer;
    @Inject
    AlertDialog mSportDialog;
    private String examinationId;
    private String examinerId;
    private int theoryExamTime;
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;
    private boolean runflag = true;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExamAnalyseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_examanalyse; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        examinationId = intent.getStringExtra("examinationId");
        examinerId = intent.getStringExtra("examinerId");
        mScheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) ArmsUtils.obtainAppComponentFromContext(this).executorService();
        mPresenter.beginAnalyseExam(examinationId, examinerId);

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
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @OnClick({R.id.toolbar_title, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title:
                break;
            case R.id.btn_submit:
                makeDialog();


                break;
        }
    }

    private void makeDialog() {
        int noanswer = 0;
        List<BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean> analysePaperList = beginAnalyseExamBack.getEntity().getAnalysePaperList();
        for (int i = 0; i < analysePaperList.size(); i++) {
            BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean analysePaperListBean = analysePaperList.get(i);
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
        title.setText("提示");
        builder.setView(inflate);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        String content = " <font style=\"font-size:16dp\" color=\"#3856FC\">"+"一共"+(analysePaperList.size())
                +"道题，已经完成"+(analysePaperList.size()-noanswer)+"</font>\n" +
                " <font style=\"font-size:16dp\" color=\"#FF0000\">" + ",未完成"+noanswer+"道" + "</font>" +
                " <font style=\"font-size:16dp\" color=\"#3856FC\">确定要交卷吗？</font>\n";
        //"正在检测...富集倒计时 "+message.num + " 秒！"
        message.setText(Html.fromHtml(content));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mPresenter.submit(examinationId, examinerId, beginAnalyseExamBack,true);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("一共" + analysePaperList.size()
                + "道题，已经完成" + (analysePaperList.size() - noanswer) + ",未完成" + noanswer + "道\r\n" + "确定要交卷吗？");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mPresenter.submit(examinationId, examinerId, beginAnalyseExamBack);
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();*/

    }

    @Override
    public void killMyself() {
        finish();
    }

    private BeginAnalyseExam_Back beginAnalyseExamBack;
    private List<BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean> analysePaperList;

    @Override
    public void showExamTitle(BeginAnalyseExam_Back back) {
        beginAnalyseExamBack = back;
        BeginAnalyseExam_Back.EntityBean entity = back.getEntity();
        BeginAnalyseExam_Back.EntityBean.AnalysePaperBean analysePaper = entity.getAnalysePaper();
        theoryExamTime = analysePaper.getAnalyseExamTime() * 60;
        LogUtils.d(theoryExamTime);
        mScheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (runflag) {
                    if (theoryExamTime > 0) {
                        int i = theoryExamTime / 60;
                        int i1 = theoryExamTime % 60;
                        String text = "剩余时间  " + (i < 10 ? "0" + i : "" + i) + ":" + (i1 < 10 ? "0" + i1 : "" + i1);
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
                        mPresenter.submit(examinationId, examinerId, beginAnalyseExamBack,false);
                        runflag = false;
                    }
                }
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
        analysePaperList = entity.getAnalysePaperList();
        for (int i = 0; i < analysePaperList.size(); i++) {
            BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean analysePaperListBean = analysePaperList.get(i);
            View inflate = LayoutInflater.from(this).inflate(R.layout.analyse_title_item, null);
            TextView title_number = (TextView) inflate.findViewById(R.id.title_number);
            TextView viewById = (TextView) inflate.findViewById(R.id.title);
            TextView starttest = (TextView) inflate.findViewById(R.id.startTest);
            starttest.setId(i);
            starttest.setOnClickListener(chardClick());
            String content = analysePaperListBean.getContent();
            content = content.replaceAll("\\\\", "");
            LogUtils.d(content);
            CharSequence spanned = Html.fromHtml(content, new URLImageGetter(viewById), null);
            viewById.setText(spanned);

            title_number.setText("第" + (i + 1) + "题" + "(共" + analysePaperListBean.getScore() + "分)");
            mExamTitle.addView(inflate);

        }
        initExamCont(analysePaperList.get(0), 0);
    }

    @Override
    public void submitSuccess() {
        runflag = false;
        finish();
        Intent content = new Intent(this, ExamStateActivity.class);
        content.putExtra("examinationId", examinationId);
        content.putExtra("examinerId", examinerId);
        ArmsUtils.startActivity(content);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    private View.OnClickListener chardClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean analysePaperListBean = analysePaperList.get(v.getId());
                initExamCont(analysePaperListBean,v.getId());
            }
        };
    }

    BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean nowAnalysePaper;

    private void initExamCont(BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean analysePaperListBean, int id) {
        mTitleAnswer.setText("第"+(id+1)+"题作答处");
        nowAnalysePaper = analysePaperListBean;
        mEdAnswer.removeTextChangedListener(answerWatcher);
        mEdAnswer.setText("");
        String studentAnswer = analysePaperListBean.getStudentAnswer();
        mEdAnswer.setText(studentAnswer);
        mEdAnswer.addTextChangedListener(answerWatcher);
    }

    private TextWatcher answerWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            LogUtils.d(s.toString());
            nowAnalysePaper.setStudentAnswer(s.toString());
        }
    };


    public class URLDrawable extends BitmapDrawable {

        protected Bitmap bitmap;

        @Override

        public void draw(Canvas canvas) {

            if (bitmap != null) {

                canvas.drawBitmap(bitmap, 0, 0, getPaint());

            }

        }

    }

    public class URLImageGetter implements Html.ImageGetter {

        TextView textView;

        public URLImageGetter(TextView textView) {

            this.textView = textView;

        }

        @Override

        public Drawable getDrawable(String paramString) {

            final URLDrawable urlDrawable = new URLDrawable();

            ImageLoader.getInstance().loadImage(paramString, new SimpleImageLoadingListener() {

                @Override

                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    LogUtils.d(imageUri);
                    int width = loadedImage.getWidth();
                    int height = loadedImage.getHeight();
                    int width1 = textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight();
                    float i = (float) width1 / width;
                    Matrix matrix = new Matrix();
                    matrix.postScale(i, i);
                    Bitmap bmp = Bitmap.createBitmap(loadedImage, 0, 0, width, height, matrix, true);
                    urlDrawable.bitmap = bmp;
                    LogUtils.d(width + "   " + height);
                    int width2 = bmp.getWidth();
                    int height2 = bmp.getHeight();
                    LogUtils.d(width2 + "   " + height2);
                    urlDrawable.setBounds(0, 0, width2, height2);
                    //为了防止图片重叠必须重新设置textView
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.invalidate();
                            textView.setText(textView.getText());

                        }
                    });

                }

            });

            return urlDrawable;

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        ArmsUtils.snackbarText("考试中，请勿退出");
    }
}