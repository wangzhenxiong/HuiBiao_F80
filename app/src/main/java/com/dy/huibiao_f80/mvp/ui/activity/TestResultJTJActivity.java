package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.NumberUtils;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.di.component.DaggerTestResultJTJComponent;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.contract.TestResultJTJContract;
import com.dy.huibiao_f80.mvp.presenter.TestResultJTJPresenter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.chartview1)
    LineChart mChartview1;
    @BindView(R.id.sampleserial1)
    AutoCompleteTextView mSampleserial1;
    @BindView(R.id.c_value1)
    AutoCompleteTextView mCValue1;
    @BindView(R.id.t_value1)
    AutoCompleteTextView mTValue1;
    @BindView(R.id.tc_value1)
    AutoCompleteTextView mTcValue1;
    @BindView(R.id.result1)
    AutoCompleteTextView mResult1;
    @BindView(R.id.gallery1)
    LinearLayout mGallery1;
    @BindView(R.id.title2)
    TextView mTitle2;
    @BindView(R.id.chartview2)
    LineChart mChartview2;
    @BindView(R.id.sampleserial2)
    AutoCompleteTextView mSampleserial2;
    @BindView(R.id.c_value2)
    AutoCompleteTextView mCValue2;
    @BindView(R.id.t_value2)
    AutoCompleteTextView mTValue2;
    @BindView(R.id.tc_value2)
    AutoCompleteTextView mTcValue2;
    @BindView(R.id.result2)
    AutoCompleteTextView mResult2;
    @BindView(R.id.gallery2)
    LinearLayout mGallery2;
    @BindView(R.id.btn_checkrecord)
    Button mBtnCheckrecord;
    @Inject
    AlertDialog mSportDialog;
    @BindView(R.id.image1)
    ImageView mImage1;
    @BindView(R.id.image2)
    ImageView mImage2;
    @BindView(R.id.btn_retest)
    Button mBtnRetest;
    private Typeface mTf;
    private String pjName;

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
    List<GalleryBean> checklist=new ArrayList<>();
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
        List<GalleryBean> mJTJGalleryBeanList = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList;
        for (int i = 0; i < mJTJGalleryBeanList.size(); i++) {
            GalleryBean galleryBean = mJTJGalleryBeanList.get(i);
            if (galleryBean.isCheckd()) {
                 checklist.add(galleryBean);
                if (galleryBean.getGalleryNum() == 1) {
                    mGallery1.setVisibility(View.VISIBLE);
                } else if (galleryBean.getGalleryNum() == 2) {
                    mGallery2.setVisibility(View.VISIBLE);
                }
                galleryBean.setJTJResultReciverListener(this);

                galleryBean.checkData_P();
                galleryBean.cardOut();
                pjName = galleryBean.getmProjectMessage().getPjName();
                mToolbarTitle.setText("胶体金检测——"+ pjName);

            }
        }

        for (int i = 0; i < mJTJGalleryBeanList.size(); i++) {
            GalleryBean galleryBean = mJTJGalleryBeanList.get(i);
            galleryBean.setCheckd(false);
        }


        //showLoading();

    }

    @Override
    public void showLoading() {
        if (null != mSportDialog) {
            if (!mSportDialog.isShowing()) {
                mSportDialog.show();
            }
        }
    }

    @Override
    public void hideLoading() {
        if (null != mSportDialog) {
            if (mSportDialog.isShowing()) {
                mSportDialog.dismiss();
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

    @Override
    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.gallery2, R.id.btn_checkrecord,R.id.btn_retest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gallery2:
                break;
            case R.id.btn_checkrecord:

                ArmsUtils.startActivity(getActivity(), RecordActivity.class);
                break;
            case R.id.btn_retest:
                for (int i = 0; i < checklist.size(); i++) {
                    checklist.get(i).setCheckd(true);
                    checklist.get(i).cardGet_Argmen();
                    checklist.get(i).cardInNotScan();
                    checklist.get(i).getJTJRWHelper().sendMessage(Constants.COLLAURUM_ENT_SCANNING_REQUEST_P, true);
                    checklist.get(i).getJTJRWHelper().stratReadData_P(2000,true);
                }
                Intent intent = getIntent();
                intent.setClass(getActivity(), TestSettingJTJActivity.class);
                intent.putExtra("project",pjName);
                ArmsUtils.startActivity(intent);
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onReciverSuccess(List<Float> userfuldata, double[] data, int galleryNum) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (galleryNum == 1) {
                    initGallery1(userfuldata, data);
                } else if (galleryNum == 2) {
                    initGallery2(userfuldata, data);
                }
            }
        });

    }


    private void initGallery1(List<Float> userfuldata, double[] datas) {
        hideLoading();
        mCValue1.setText(NumberUtils.three(datas[1]) + "");
        mTValue1.setText(NumberUtils.three(datas[3]) + "");
        mTcValue1.setText(NumberUtils.three(datas[3] / datas[1]) + "");
        GalleryBean galleryBean = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0);
        mResult1.setText(((TestRecord) galleryBean).getDecisionoutcome());
        if (galleryBean.getmProjectMessage().getMethod_sp() == 0) {

            mTcValue1.setVisibility(View.GONE);
        } else {
            mTcValue1.setVisibility(View.VISIBLE);
        }
        LineData data = getData(userfuldata, mChartview1, ((Double) datas[0]).intValue(), ((Double) datas[2]).intValue());
        data.setValueTypeface(mTf);
        setupChart(mChartview1, data);
    }

    private void initGallery2(List<Float> userfuldata, double[] datas) {
        hideLoading();
        mCValue2.setText(NumberUtils.three(datas[1]) + "");
        mTValue2.setText(NumberUtils.three(datas[3]) + "");
        mTcValue2.setText(NumberUtils.three(datas[3] / datas[1]) + "");
        GalleryBean galleryBean = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(1);
        mResult2.setText(((TestRecord) galleryBean).getDecisionoutcome());
        if (galleryBean.getmProjectMessage().getMethod_sp() == 0) {

            mTcValue2.setVisibility(View.GONE);
        } else {
            mTcValue2.setVisibility(View.VISIBLE);
        }
        LineData data2 = getData(userfuldata, mChartview2, ((Double) datas[0]).intValue(), ((Double) datas[2]).intValue());
        data2.setValueTypeface(mTf);
        setupChart(mChartview2, data2);
    }

    private void setupChart(LineChart chart, LineData data) {
        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(Color.rgb(0, 200, 245));

        // no description text
        chart.getDescription().setEnabled(false);

        // chart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false);
//        chart.getRenderer().getGridPaint().setGridColor(Color.WHITE & 0x70FFFFFF);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setBackgroundColor(Color.rgb(0, 200, 245));

        // set custom chart offsets (automatic offset calculation is hereby disabled)
        chart.setViewPortOffsets(10, 0, 10, 0);

        // add data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setSpaceTop(40);
        chart.getAxisLeft().setSpaceBottom(40);
        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setEnabled(false);

        // animate calls invalidate()...
        chart.animateX(2500);
        //chart.setMarker(new MyMaker());
    }

    private LineData getData(List<Float> bytes, LineChart mChart, int v1, int v2) {
        ArrayList<Integer> colors = new ArrayList<Integer>();
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < bytes.size(); i++) {

            values.add(new Entry(i, bytes.get(i)));
        }


        // create a dataset and give it a type
        LineDataSet set1;
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);


        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            mChart.getData().removeDataSet(0);
        }
        // create a dataset and give it a type
        set1 = new LineDataSet(values, "");

        set1.setDrawIcons(false);

        set1.setColor(Color.BLACK);
        /******************************/
        //循环判断y值，并向颜色集合中添加对应的颜色
        for (int i = 0; i < bytes.size(); i++) {
            if ((v1 <= i - 10 && i <= v1 + 30)) {
                colors.add(Color.GREEN);
            } else if ((v2 <= i - 10 && i < v2 + 30)) {
                colors.add(Color.RED);
            } else {
                colors.add(Color.BLUE);
            }

        }


        set1.setCircleColors(colors);


        set1.setLineWidth(1.75f);
        set1.setCircleRadius(4f);
        set1.setCircleHoleRadius(1.5f);
        //set1.setColor(Color.WHITE);
        set1.setColors(colors);
        //set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.GREEN);
        set1.setDrawValues(true);

        // create a data object with the data sets
        return new LineData(set1);
    }


    @Override
    public void onReciverSuccess(List<List<Float>> userfuldata, List<double[]> data) {

    }

    @Override
    public void onReciverSuccess(Bitmap bitmap, int gallery) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (gallery == 1) {
                    if (null!=mImage1){
                        mImage1.setImageBitmap(bitmap);
                    }
                } else if (gallery == 2) {
                    if (null!=mImage1){
                        mImage2.setImageBitmap(bitmap);
                    }
                }
            }
        });
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
    public void onBackPressed() {
        ArmsUtils.startActivity(this,StartTestActivity.class);
    }
}