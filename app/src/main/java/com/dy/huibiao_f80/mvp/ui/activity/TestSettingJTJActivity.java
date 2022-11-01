package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.di.component.DaggerTestSettingJTJComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.contract.TestSettingJTJContract;
import com.dy.huibiao_f80.mvp.presenter.TestSettingTJPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.MySpinnerAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class TestSettingJTJActivity extends BaseActivity<TestSettingTJPresenter> implements TestSettingJTJContract.View, GalleryBean.onJTJResultRecive {


    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.title1)
    TextView mTitle1;
    @BindView(R.id.left1)
    ImageView mLeft1;
    @BindView(R.id.up1)
    ImageView mUp1;
    @BindView(R.id.surfaceview1)
    SurfaceView mSurfaceview1;
    @BindView(R.id.down1)
    ImageView mDown1;
    @BindView(R.id.right1)
    ImageView mRight1;
    @BindView(R.id.tips1)
    TextView mTips1;
    @BindView(R.id.title2)
    TextView mTitle2;
    @BindView(R.id.left2)
    ImageView mLeft2;
    @BindView(R.id.up2)
    ImageView mUp2;
    @BindView(R.id.surfaceview2)
    SurfaceView mSurfaceview2;
    @BindView(R.id.down2)
    ImageView mDown2;
    @BindView(R.id.right2)
    ImageView mRight2;
    @BindView(R.id.tips2)
    TextView mTips2;
    @BindView(R.id.btn_starttest)
    Button mBtnStarttest;
    @BindView(R.id.gallery1)
    LinearLayout mGallery1;
    @BindView(R.id.gallery2)
    LinearLayout mGallery2;
    @BindView(R.id.cure1)
    Spinner mCure1;
    @BindView(R.id.btn_cardout1)
    Button mBtnCardout1;
    @BindView(R.id.btn_cardin1)
    Button mBtnCardin1;
    @BindView(R.id.cure2)
    Spinner mCure2;
    @BindView(R.id.btn_cardout2)
    Button mBtnCardout2;
    @BindView(R.id.btn_cardin2)
    Button mBtnCardin2;
    private SurfaceHolder mSurfaceHolder1;
    private SurfaceHolder mSurfaceHolder2;
    private int mSurfaceviewState1;
    private int mSurfaceviewState2;
    private String project;
    private BaseProjectMessage baseProjectMessage1;
    private BaseProjectMessage baseProjectMessage2;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTestSettingJTJComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_testsettingjtj; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        project = intent.getStringExtra("project");
        LogUtils.d(project);
        initSpinner();
        List<GalleryBean> mJTJGalleryBeanList = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList;
        for (int i = 0; i < mJTJGalleryBeanList.size(); i++) {
            GalleryBean galleryBean = mJTJGalleryBeanList.get(i);
            if (galleryBean.isCheckd()) {
                if (galleryBean.getGalleryNum() == 1) {
                    mGallery1.setVisibility(View.VISIBLE);
                } else if (galleryBean.getGalleryNum() == 2) {
                    mGallery2.setVisibility(View.VISIBLE);
                }
                galleryBean.setJTJResultReciverListener(TestSettingJTJActivity.this);
                galleryBean.cardGet_Argmen();
                galleryBean.cardInNotScan();
                galleryBean.getJTJRWHelper().sendMessage(Constants.COLLAURUM_ENT_SCANNING_REQUEST_P, true);
                galleryBean.getJTJRWHelper().stratReadData_P(2000, true);
            }
        }

        initSurfaceView();
    }

    private void initSpinner() {
        List<BaseProjectMessage> list = new ArrayList<>();
         LogUtils.d(project);
        list.addAll(DBHelper.getProjectJTJDao().queryBuilder()
                .where(ProjectJTJDao.Properties.ProjectName.eq(project))
                .where(ProjectJTJDao.Properties.FinishState.eq(true))
                .build().list());

        LogUtils.d(list);
        MySpinnerAdapter adapter1 = new MySpinnerAdapter(list, this);
        mCure1.setAdapter(adapter1);
        mCure1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                baseProjectMessage1 = list.get(position);
                // TODO: 10/17/22 根据曲线信息画曲线
                LogUtils.d(baseProjectMessage1);
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).setmProjectMessage(baseProjectMessage1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for (int i = 0; i < list.size(); i++) {
            if (((ProjectJTJ) list.get(i)).getIsdefault()) {
                mCure1.setSelection(i);
            }
        }

        MySpinnerAdapter adapter2 = new MySpinnerAdapter(list, this);

        mCure2.setAdapter(adapter2);
        mCure2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                baseProjectMessage2 = list.get(position);
                // TODO: 10/17/22 根据曲线信息画曲线
                LogUtils.d(baseProjectMessage2);
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).setmProjectMessage(baseProjectMessage2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for (int i = 0; i < list.size(); i++) {
            if (((ProjectJTJ) list.get(i)).getIsdefault()) {
                mCure2.setSelection(i);
            }
        }
    }

    private void initSurfaceView() {
        mSurfaceHolder1 = mSurfaceview1.getHolder();
        mSurfaceHolder1.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceviewState1 = 1;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mSurfaceviewState1 = 2;
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mSurfaceviewState1 = 3;
            }
        });
        mSurfaceview1.setZOrderOnTop(true);

        mSurfaceHolder2 = mSurfaceview2.getHolder();
        mSurfaceHolder2.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceviewState2 = 1;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mSurfaceviewState2 = 2;
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mSurfaceviewState2 = 2;
            }
        });
        mSurfaceview2.setZOrderOnTop(true);
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
    public void onReciverSuccess(List<Float> userfuldata, double[] data, int galleryNum) {

    }

    @Override
    public void onReciverSuccess(List<List<Float>> userfuldata, List<double[]> data) {

    }

    Bitmap bitmap1;
    Bitmap bitmap2;

    @Override
    public void onReciverSuccess(Bitmap bitmap, int gallerynum) {
        LogUtils.d("onReciverSuccess   "+gallerynum);
        if (gallerynum == 1) {
            bitmap1 = bitmap;
            if (mSurfaceviewState1 == 3) {
                return;
            }
            if (null == mSurfaceHolder1) {
                return;
            }
            Canvas canvas = mSurfaceHolder1.lockCanvas();
            if (null != canvas) {
                canvas.drawColor(Color.WHITE);
                int height1 = canvas.getHeight();
                int width1 = canvas.getWidth();
                int height2 = bitmap.getHeight();
                int width2 = bitmap.getWidth();
                int left = (width1 - width2) / 2;
                int top = (height1 - height2) / 2;
                canvas.drawBitmap(bitmap, left, top, null);
                mSurfaceHolder1.unlockCanvasAndPost(canvas);
            }
            if (mTips1.getText().toString().contains("加载中")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTips1.setText("请检查图像是否正常，检查无误后点击开始检测");
                    }
                });
            }
        } else if (gallerynum == 2) {
            bitmap2 = bitmap;
            if (mSurfaceviewState2 == 3) {
                return;
            }
            if (null == mSurfaceHolder2) {
                return;
            }
            Canvas canvas = mSurfaceHolder2.lockCanvas();
            if (null != canvas) {
                canvas.drawColor(Color.WHITE);
                int height1 = canvas.getHeight();
                int width1 = canvas.getWidth();
                int height2 = bitmap.getHeight();
                int width2 = bitmap.getWidth();
                int left = (width1 - width2) / 2;
                int top = (height1 - height2) / 2;
                canvas.drawBitmap(bitmap, left, top, null);
                mSurfaceHolder2.unlockCanvasAndPost(canvas);
            }
            if (mTips2.getText().toString().contains("加载中")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTips2.setText("请检查图像是否正常，检查无误后点击开始检测");
                    }
                });
            }
        }

    }

    @Override
    public void onReciverfail() {

    }

    @Override
    public void onTimer(int timer) {

    }

    @Override
    public void onRefrish() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.left1, R.id.up1, R.id.down1, R.id.right1, R.id.left2, R.id.up2, R.id.down2, R.id.right2,R.id.btn_cardout1, R.id.btn_cardin1, R.id.btn_cardout2, R.id.btn_cardin2, R.id.btn_starttest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left1:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).getJTJRWHelper().vertical = -1;
                break;
            case R.id.up1:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).getJTJRWHelper().horizontal = 1;
                break;
            case R.id.down1:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).getJTJRWHelper().horizontal = -1;
                break;
            case R.id.right1:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).getJTJRWHelper().vertical = 1;
                break;
            case R.id.left2:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).getJTJRWHelper().vertical = -1;
                break;
            case R.id.up2:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).getJTJRWHelper().horizontal = 1;
                break;
            case R.id.down2:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).getJTJRWHelper().horizontal = -1;
                break;
            case R.id.right2:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).getJTJRWHelper().vertical = 1;
                break;
            case R.id.btn_cardout1:

                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).getJTJRWHelper().stopReadData_P();
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).cardOut();
                break;
            case R.id.btn_cardin1:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).cardGet_Argmen();
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).cardInNotScan();
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).getJTJRWHelper().sendMessage(Constants.COLLAURUM_ENT_SCANNING_REQUEST_P, true);
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0).getJTJRWHelper().stratReadData_P(2000,true);
                break;

            case R.id.btn_cardout2:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).getJTJRWHelper().stopReadData_P();
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).cardOut();
                break;
            case R.id.btn_cardin2:
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).cardGet_Argmen();
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).cardInNotScan();
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).getJTJRWHelper().sendMessage(Constants.COLLAURUM_ENT_SCANNING_REQUEST_P, true);
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1).getJTJRWHelper().stratReadData_P(2000,true);
                break;

            case R.id.btn_starttest:
                List<GalleryBean> mJTJGalleryBeanList = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList;
                for (int i = 0; i < mJTJGalleryBeanList.size(); i++) {
                    GalleryBean galleryBean = mJTJGalleryBeanList.get(i);
                    if (galleryBean.isCheckd()) {
                        if (galleryBean.getGalleryNum() == 1) {
                            if (bitmap1==null){
                              ArmsUtils.snackbarText("通道1图像未加载完成，请稍后");
                              return;
                            }
                            galleryBean.setBitmap(bitmap1);

                        } else if (galleryBean.getGalleryNum() == 2) {
                            if (bitmap2==null){
                                ArmsUtils.snackbarText("通道2图像未加载完成，请稍后");
                                return;
                            }
                            galleryBean.setBitmap(bitmap2);
                        }
                        galleryBean.getJTJRWHelper().stopReadData_P();
                    }
                }
                ArmsUtils.startActivity(new Intent(this, TestResultJTJActivity.class));
                this.finish();
                break;
        }
    }


}