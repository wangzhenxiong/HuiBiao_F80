package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.api.back.BeginOperationExam_Back;
import com.dy.huibiao_f80.app.service.ExamOperationService;
import com.dy.huibiao_f80.di.component.DaggerExamOperationComponent;
import com.dy.huibiao_f80.mvp.contract.ExamOperationContract;
import com.dy.huibiao_f80.mvp.presenter.ExamOperationPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.IFloatWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ExamOperationActivity extends BaseActivity<ExamOperationPresenter> implements ExamOperationContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_time)
    TextView mToolbarTime;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.exam_title)
    LinearLayout mExamTitle;
    @BindView(R.id.examtitle)
    TextView mExamtitle;
    @BindView(R.id.btn_starttest)
    Button mBtnStarttest;
    @BindView(R.id.btn_testrecord)
    Button mBtnTestrecord;
    @BindView(R.id.btn_report)
    Button mBtnReport;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @Inject
    AlertDialog mSportDialog;
    private String examinationId;
    private String examinerId;
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExamOperationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_examoperation; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        LogUtils.d(tags);
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
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        examinationId = intent.getStringExtra("examinationId");
        examinerId = intent.getStringExtra("examinerId");
        mScheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) ArmsUtils.obtainAppComponentFromContext(this).executorService();
        MyAppLocation.myAppLocation.mExamOperationService.cleanMaps();
        mPresenter.beginOperationExam(examinationId, examinerId);
        IFloatWindow iFloatWindow = FloatWindow.get();
        if (null != iFloatWindow) {
            FloatWindow.get().hide();
        }

    }



    @Override
    public boolean useEventBus() {
        return false;
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

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (MyAppLocation.myAppLocation.mExamOperationService.isStartExamOperation()){
            ArmsUtils.snackbarText("考试中，请勿退出");
        }else {
            super.onBackPressed();
        }
    }

    List<BeginOperationExam_Back.EntityBean.OperationPaperListBean> operationPaperList;
    List<TextView> textviewTitleList = new ArrayList<>();

    @Override
    public void showExamTitle(BeginOperationExam_Back back) {


        BeginOperationExam_Back.EntityBean entity = back.getEntity();
        operationPaperList = entity.getOperationPaperList();
        //mExamname.setText("实操考试题，一共" + operationPaperList.size() + "道题");
        for (int i = 0; i < operationPaperList.size(); i++) {
            BeginOperationExam_Back.EntityBean.OperationPaperListBean operationPaperListBean = operationPaperList.get(i);
            View inflate = LayoutInflater.from(this).inflate(R.layout.analyse_title_item1, null);
            TextView viewById = (TextView) inflate.findViewById(R.id.title);
            viewById.setId(i);
            viewById.setOnClickListener(chardClick());
            viewById.setText("第" + (i + 1) + "题" + "(共" + operationPaperListBean.getAllScore() + "分)");
            textviewTitleList.add(viewById);
            mExamTitle.addView(inflate);
        }
        Integer operationExamTime = entity.getExamination().getOperationExamTime() * 60;
        MyAppLocation.myAppLocation.mExamOperationService.setBeginOperationExam_back(back);
        MyAppLocation.myAppLocation.mExamOperationService.setExaminationId(examinationId);
        MyAppLocation.myAppLocation.mExamOperationService.setExaminerId(examinerId);
        if (BuildConfig.DEBUG){
            MyAppLocation.myAppLocation.mExamOperationService.startExamOperation(600);
        }else {
            MyAppLocation.myAppLocation.mExamOperationService.startExamOperation(operationExamTime);
        }
        initExamCont(operationPaperList.get(0), 0);

    }

    @Override
    public void submitSuccess() {
        MyAppLocation.myAppLocation.mExamOperationService.finishOperationExam();
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

    BeginOperationExam_Back.EntityBean.OperationPaperListBean onoeperationPaper;

    private void initExamCont(BeginOperationExam_Back.EntityBean.OperationPaperListBean operationPaperListBean, int index) {
        for (int i = 0; i < textviewTitleList.size(); i++) {
            if (index == i) {
                textviewTitleList.get(i).setTextColor(getResources().getColor(R.color.red));
            } else {
                textviewTitleList.get(i).setTextColor(getResources().getColor(R.color.black));
            }
        }

        MyAppLocation.myAppLocation.mExamOperationService.setNowOperationExam(operationPaperListBean);
        onoeperationPaper = operationPaperListBean;
        String content = operationPaperListBean.getContent();
        //content = content.replaceAll("\\\\", "");
        LogUtils.d(content);
        CharSequence spanned = Html.fromHtml(content, new URLImageGetter(mExamtitle), null);
        mExamtitle.setText(spanned);

    }

    private View.OnClickListener chardClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeginOperationExam_Back.EntityBean.OperationPaperListBean operationPaperListBean = operationPaperList.get(v.getId());
                initExamCont(operationPaperListBean, v.getId());
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_starttest, R.id.btn_testrecord, R.id.btn_report, R.id.btn_submit})
    public void onClick(View view) {
        String id = onoeperationPaper.getId();
        switch (view.getId()) {
            case R.id.btn_submit:
                mPresenter.submitOperation();
                break;
            case R.id.btn_starttest:

                //MyAppLocation.myAppLocation.mSerialDataService.initDialog();
                //View view1 = FloatWindow.get().getView();
                //String content1 = onoeperationPaper.getContent();

                //content1 = content1.replaceAll("\\\\", "");
                //LogUtils.d(content1);
                //CharSequence spanned = Html.fromHtml(content1, new URLImageGetter(mExamtitle), null);
                //((TextView) view1.findViewById(R.id.textview)).setText(spanned);
                //((TextView) view1).setText(onoeperationPaper.getContent());
                //FloatWindow.get().show();

                Intent content = new Intent(this, StartTestActivity.class);
                startActivity(content);
                break;
            case R.id.btn_testrecord:
                Intent c = new Intent(this, RecordActivity.class);
                c.putExtra("examinationId", examinationId + "");
                c.putExtra("examinerId", examinerId + "");
                c.putExtra("examId", id + "");
                startActivity(c);
                break;
            case R.id.btn_report:
                Intent intent = new Intent(ExamOperationActivity.this, PrintReportActivity.class);
                intent.putExtra("examinationId", examinationId);
                intent.putExtra("examinerId", examinerId);
                intent.putExtra("operationPaperId", onoeperationPaper.getId());
                startActivity(intent);
                break;
        }
    }


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
                    urlDrawable.bitmap = loadedImage;

                    urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());


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
}