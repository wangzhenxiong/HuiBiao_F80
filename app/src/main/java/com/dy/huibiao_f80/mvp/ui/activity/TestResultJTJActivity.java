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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.di.component.DaggerTestResultJTJComponent;
import com.dy.huibiao_f80.mvp.contract.TestResultJTJContract;
import com.dy.huibiao_f80.mvp.presenter.TestResultJTJPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class TestResultJTJActivity extends BaseActivity<TestResultJTJPresenter> implements TestResultJTJContract.View, GalleryBean.onJTJResultRecive {

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
    @BindView(R.id.up2)
    ImageButton mUp2;
    @BindView(R.id.surfaceview1)
    SurfaceView mSurfaceview1;
    @BindView(R.id.title2)
    TextView mTitle2;
    @BindView(R.id.up)
    ImageButton mUp;
    @BindView(R.id.surfaceview2)
    SurfaceView mSurfaceview2;
    @BindView(R.id.btn_starttest)
    Button mBtnStarttest;
    private SurfaceHolder mSurfaceHolder1;
    private SurfaceHolder mSurfaceHolder2;
    private int mSurfaceviewState1;
    private int mSurfaceviewState2;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTestResultJTJComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_testresultjtj; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        List<GalleryBean> mJTJGalleryBeanList = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList;
        for (int i = 0; i < mJTJGalleryBeanList.size(); i++) {
            GalleryBean galleryBean = mJTJGalleryBeanList.get(i);
            if (galleryBean.isCheckd()) {
                galleryBean.setJTJResultReciverListener(TestResultJTJActivity.this);
                galleryBean.cardGet_Argmen();
                galleryBean.cardInNotScan();
                galleryBean.getJTJRWHelper().sendMessage(Constants.COLLAURUM_ENT_SCANNING_REQUEST_P, true);
                galleryBean.getJTJRWHelper().stratReadData_P(4000, true);
            }
        }

        initSurfaceView();
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
    public void onReciverSuccess(List<Float> userfuldata, double[] data) {

    }

    @Override
    public void onReciverSuccess(List<List<Float>> userfuldata, List<double[]> data) {

    }

    @Override
    public void onReciverSuccess(Bitmap bitmap, int gallerynum) {
        if (gallerynum==1){
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
        }else if (gallerynum==2){
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


}