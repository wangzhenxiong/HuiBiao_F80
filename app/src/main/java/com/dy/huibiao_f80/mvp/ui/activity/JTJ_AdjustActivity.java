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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.app.utils.SPUtils;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.di.component.DaggerJTJ_AdjustComponent;
import com.dy.huibiao_f80.mvp.contract.JTJ_AdjustContract;
import com.dy.huibiao_f80.mvp.presenter.JTJ_AdjustPresenter;
import com.dy.huibiao_f80.usbhelps.UsbReadWriteHelper;
import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/09/2019 15:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class JTJ_AdjustActivity extends BaseActivity<JTJ_AdjustPresenter> implements JTJ_AdjustContract.View, SurfaceHolder.Callback, GalleryBean.onJTJResultRecive {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.btn_incard)
    Button mBtnIncard;
    @BindView(R.id.btn_outcard)
    Button mBtnOutcard;
    @BindView(R.id.btn_left)
    Button mBtnLeft;
    @BindView(R.id.btn_right)
    Button mBtnRight;
    @BindView(R.id.btn_up)
    Button mBtnUp;
    @BindView(R.id.btn_down)
    Button mBtnDown;
    @BindView(R.id.surfaceview)
    SurfaceView mSurfaceview;

    int mSurfaceviewState = -1;
    @BindView(R.id.btn_width_sub)
    Button mBtnWidthSub;
    @BindView(R.id.width_value)
    AutoCompleteTextView mWidthValue;
    @BindView(R.id.btn_height_add)
    Button mBtnHeightAdd;
    @BindView(R.id.btn_height_sub)
    Button mBtnHeightSub;
    @BindView(R.id.height_value)
    AutoCompleteTextView mHeightValue;
    @BindView(R.id.btn_width_add)
    Button mBtnWidthAdd;
    @BindView(R.id.btn_cardspacing_sub)
    Button mBtnCardspacingSub;
    @BindView(R.id.cardspacing_value)
    AutoCompleteTextView mCardspacingValue;
    @BindView(R.id.btn_cardspacing_add)
    Button mBtnCardspacingAdd;
    @BindView(R.id.ll_model4)
    LinearLayout mLlModel4;
    @BindView(R.id.btn_getbitmapnocard)
    Button mBtnGetBitmapNoCard;
    @BindView(R.id.iv_controbitmapnocard)
    PhotoView mIvControBitmapNoCard;
    private SurfaceHolder mSurfaceHolder;
    private int mIntExtra;
    private GalleryBean mGalleryBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerJTJ_AdjustComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_jtj_adjust; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mIntExtra = intent.getIntExtra("data", -1);
        LogUtils.d(mIntExtra);
        if (mIntExtra == -1) {
            ArmsUtils.snackbarText("参数错误，请重试");
            killMyself();
        }
        mGalleryBean = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(mIntExtra-1);

        mGalleryBean.setJTJResultReciverListener(this);
        //先获取摄像头参数 左右上下
        mGalleryBean.cardGet_Argmen();
        initSurfaceView();
        //LogUtils.d(mGalleryBean);
        if (3 == mGalleryBean.getJTJModel()) {
            mLlModel4.setVisibility(View.VISIBLE);
            mHeightValue.setText(Constants.drowrectheight+"");
            mWidthValue.setText(Constants.drowrectwidth+"");
            mCardspacingValue.setText(Constants.cardSpacing+"");
            mGalleryBean.getJTJRWHelper().stratReadData_P(1000, true);
            mBtnIncard.setVisibility(View.GONE);
            mBtnOutcard.setVisibility(View.GONE);
            mBtnLeft.setVisibility(View.VISIBLE);
            mBtnRight.setVisibility(View.VISIBLE);
            mBtnUp.setVisibility(View.VISIBLE);
            mBtnDown.setVisibility(View.VISIBLE);
            mBtnGetBitmapNoCard.setVisibility(View.VISIBLE);
            mIvControBitmapNoCard.setImageBitmap(FileUtils.getControlBitmap());
        }
        
    }

    private void initSurfaceView() {
        mSurfaceHolder = mSurfaceview.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceview.setZOrderOnTop(true);
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
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceviewState = 1;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceviewState = 2;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceviewState = 3;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mGalleryBean.getJTJRWHelper().stopReadData_P();
        mGalleryBean.cardOut();
    }

    int drowrectheight;
    int drowrectwidth;
    int cardSpacing;

    @OnClick({R.id.btn_incard, R.id.btn_outcard, R.id.btn_left, R.id.btn_right, R.id.btn_up, R.id.btn_down
            , R.id.btn_width_sub, R.id.btn_height_add, R.id.btn_height_sub, R.id.btn_width_add, R.id.btn_cardspacing_sub, R.id.btn_cardspacing_add,R.id.btn_getbitmapnocard})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_incard:
                mGalleryBean.cardInNotScan();
                mGalleryBean.getJTJRWHelper().sendMessage(Constants.COLLAURUM_ENT_SCANNING_REQUEST_P, true);
                mGalleryBean.getJTJRWHelper().stratReadData_P(4000, true);
                mBtnLeft.setVisibility(View.VISIBLE);
                mBtnRight.setVisibility(View.VISIBLE);
                mBtnUp.setVisibility(View.VISIBLE);
                mBtnDown.setVisibility(View.VISIBLE);

                break;
            case R.id.btn_outcard:
                mBtnLeft.setVisibility(View.GONE);
                mBtnRight.setVisibility(View.GONE);
                mBtnUp.setVisibility(View.GONE);
                mBtnDown.setVisibility(View.GONE);
                mGalleryBean.getJTJRWHelper().stopReadData_P();
                mGalleryBean.cardOut();
                break;
            case R.id.btn_up:
                UsbReadWriteHelper.horizontal = 1;
                break;
            case R.id.btn_down:
                UsbReadWriteHelper.horizontal = -1;
                break;
            case R.id.btn_left:
                UsbReadWriteHelper.vertical = -1;
                break;
            case R.id.btn_right:
                UsbReadWriteHelper.vertical = 1;
                break;
            case R.id.btn_width_sub:
                drowrectwidth = Integer.parseInt(mWidthValue.getText().toString());
                if (drowrectwidth--<=100){
                    ArmsUtils.snackbarText("超出限定值");
                    break;
                }
                mWidthValue.setText(drowrectwidth+"");
                Constants.drowrectwidth=drowrectwidth;
                SPUtils.put(this, Constants.KEY_DROWRECTWIDTH, drowrectwidth);

                break;
            case R.id.btn_width_add:
                drowrectwidth = Integer.parseInt(mWidthValue.getText().toString());
                if (drowrectwidth++>=120){
                    ArmsUtils.snackbarText("超出限定值");
                    break;
                }
                mWidthValue.setText(drowrectwidth+"");
                Constants.drowrectwidth=drowrectwidth;
                SPUtils.put(this, Constants.KEY_DROWRECTWIDTH, drowrectwidth);

                break;
            case R.id.btn_height_sub:
                drowrectheight = Integer.parseInt(mHeightValue.getText().toString());
                if (drowrectheight--<=20){
                    ArmsUtils.snackbarText("超出限定值");
                    break;
                }
                mHeightValue.setText(drowrectheight+"");
                Constants.drowrectheight=drowrectheight;
                SPUtils.put(this, Constants.KEY_DROWRECTHEIGHT, drowrectheight);

                break;
            case R.id.btn_height_add:
                drowrectheight = Integer.parseInt(mHeightValue.getText().toString());
                if (drowrectheight++>=30){
                    ArmsUtils.snackbarText("超出限定值");
                    break;
                }
                mHeightValue.setText(drowrectheight+"");
                Constants.drowrectheight=drowrectheight;
                SPUtils.put(this, Constants.KEY_DROWRECTHEIGHT, drowrectheight);
                break;

            case R.id.btn_cardspacing_sub:
                cardSpacing = Integer.parseInt(mCardspacingValue.getText().toString());
                if (cardSpacing--<=40){
                    ArmsUtils.snackbarText("超出限定值");
                    break;
                }
                mCardspacingValue.setText(cardSpacing+"");
                Constants.cardSpacing=cardSpacing;
                SPUtils.put(this, Constants.KEY_CARDSPACING, cardSpacing);
                break;
            case R.id.btn_cardspacing_add:
                cardSpacing = Integer.parseInt(mCardspacingValue.getText().toString());
                if (cardSpacing++>=80){
                    ArmsUtils.snackbarText("超出限定值");
                    break;
                }
                mCardspacingValue.setText(cardSpacing+"");
                Constants.cardSpacing=cardSpacing;
                SPUtils.put(this, Constants.KEY_CARDSPACING, cardSpacing);
                break;
            case R.id.btn_getbitmapnocard:
                if (null!=currentBitmap){
                    mIvControBitmapNoCard.setImageBitmap(currentBitmap);
                    FileUtils.saveControlBitmap(currentBitmap);
                }

                break;

        }
    }


    @Override
    public void onReciverSuccess(List<Float> userfuldata, double[] data) {

    }

    @Override
    public void onReciverSuccess(List<List<Float>> userfuldata, List<double[]> data) {

    }
    private Bitmap currentBitmap;
    @Override
    public void onReciverSuccess(Bitmap bitmap,int gallerynum) {
        currentBitmap=bitmap;
        setPicture(bitmap);
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

    private void setPicture(Bitmap bitmap) {
        if (mSurfaceviewState == 3) {
            return;
        }
        if (null == mSurfaceHolder) {
            return;
        }
        Canvas canvas = mSurfaceHolder.lockCanvas();
        if (null != canvas) {
            canvas.drawColor(Color.WHITE);
            int height1 = canvas.getHeight();
            int width1 = canvas.getWidth();
            int height2 = bitmap.getHeight();
            int width2 = bitmap.getWidth();
            int left = (width1 - width2) / 2;
            int top = (height1 - height2) / 2;
            canvas.drawBitmap(bitmap, left, top, null);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放监听，以免造成内存泄露
        mGalleryBean.removeJTJResultReciverListener();
    }


}
