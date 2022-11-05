package com.dy.huibiao_f80.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.bean.eventBusBean.FGTestMessageBean;
import com.dy.huibiao_f80.di.component.DaggerTestFGGDComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.mvp.contract.TestFGGDContract;
import com.dy.huibiao_f80.mvp.presenter.TestFGGDPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.FGGDAdapter;
import com.dy.huibiao_f80.mvp.ui.adapter.MySpinnerAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class TestFGGDActivity extends BaseActivity<TestFGGDPresenter> implements TestFGGDContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.controvalue)
    TextView mControvalue;
    @BindView(R.id.recylerview)
    RecyclerView mRecylerview;
    @BindView(R.id.cures)
    Spinner mCures;
    @BindView(R.id.chartview)
    LineChart mChartview;
    @BindView(R.id.btn_clean)
    Button mBtnClean;
    @BindView(R.id.btn_starttest)
    Button mBtnStarttest;
    private String projectname;
    private BaseProjectMessage baseProjectMessage;
    private List<GalleryBean> dataList = new ArrayList<>();
    private FGGDAdapter fggdAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTestFGGDComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_testfggd; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        projectname = getIntent().getStringExtra("projectname");
        mTitle.setText("分光光度检测——" + projectname);
        ArmsUtils.configRecyclerView(mRecylerview, new GridLayoutManager(this, 1));
        fggdAdapter = new FGGDAdapter(R.layout.layout_fggftest_item, dataList);
        fggdAdapter.setEmptyView(R.layout.emptyview, (ViewGroup) mRecylerview.getParent());
        mRecylerview.setAdapter(fggdAdapter);
        getdata();
        initCurve();


    }

    private void initControValue(int method_sp) {
        if (method_sp == 0) {
            mControvalue.setText("当前对照:" + Constants.getControValue0());
            mControvalue.setVisibility(View.GONE);
        } else if (method_sp == 1) {
            mControvalue.setText("当前对照:" + Constants.getControValue1());
            mControvalue.setVisibility(View.GONE);
        } else {
            mControvalue.setVisibility(View.GONE);
        }
    }

    private void initCurve() {
        List<BaseProjectMessage> list = new ArrayList<>();

        list.addAll(DBHelper.getProjectFGGDDao().queryBuilder()
                .where(ProjectFGGDDao.Properties.ProjectName.eq(projectname))
                .where(ProjectFGGDDao.Properties.FinishState.eq(true))
                .build().list());

        LogUtils.d(list);
        MySpinnerAdapter adapter = new MySpinnerAdapter(list, this);
        mCures.setAdapter(adapter);
        mCures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                baseProjectMessage = list.get(position);
                // TODO: 10/17/22 根据曲线信息画曲线
                if (baseProjectMessage.getMethod_sp() == 1) {
                    double a0 = ((ProjectFGGD) baseProjectMessage).getA0();
                    double b0 = ((ProjectFGGD) baseProjectMessage).getB0();
                    double c0 = ((ProjectFGGD) baseProjectMessage).getC0();
                    double d0 = ((ProjectFGGD) baseProjectMessage).getD0();
                    LineData data = getData(mChartview, a0, b0, c0, d0);
                    setupChart(mChartview, data);
                }
                LogUtils.d(baseProjectMessage);
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).setmProjectMessage(baseProjectMessage);
                }
                initControValue(baseProjectMessage.getMethod_sp());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for (int i = 0; i < list.size(); i++) {
            if (((ProjectFGGD) list.get(i)).getIsdefault()) {
                mCures.setSelection(i);
            }
        }


    }

    private void getdata() {
        dataList.clear();
        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.size(); i++) {
            GalleryBean galleryBean = MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i);
            if (galleryBean.isCheckd()) {
                dataList.add(galleryBean);
                LogUtils.d(galleryBean.getGalleryNum());
            } else {
                galleryBean.setState(0);
            }
        }
        fggdAdapter.notifyDataSetChanged();
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

        chart.setBackgroundColor(Color.argb(0, 0, 200, 245));

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

    private LineData getData(LineChart mChart, double a, double b, double c, double d) {
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 1; i < 100; i++) {
            values.add(new Entry(i, (float) (a + b * i + c * i * i + d * i * i * i)));
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
        set1.setDrawCircleHole(false);
        set1.setLineWidth(1.75f);
        set1.setCircleRadius(4f);
        set1.setCircleHoleRadius(1.5f);
        //set1.setColor(Color.WHITE);
        //set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.GREEN);
        set1.setDrawValues(false);

        // create a data object with the data sets
        return new LineData(set1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getdata();
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

    @OnClick({R.id.btn_clean, R.id.btn_starttest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clean:
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).setClearn(true);
                }
                break;
            case R.id.btn_starttest:


                for (int i = 0; i < dataList.size(); i++) {
                    GalleryBean galleryBean = dataList.get(i);
                    galleryBean.setCheckd(false);
                    ((TestRecord) galleryBean).setDecisionoutcome(null);
                    ((TestRecord) galleryBean).setTestresult(null);
                    ((TestRecord) galleryBean).setRetest(0);
                    if (galleryBean.getDowhat() == 1) {
                        galleryBean.setState(1);
                        galleryBean.setRemainingtime(baseProjectMessage.getJiancetime() + baseProjectMessage.getYuretime() + 1);
                    }
                    if (galleryBean.getDowhat() == 2) {
                        galleryBean.setState(1);
                        galleryBean.setRemainingtime(baseProjectMessage.getJiancetime() + baseProjectMessage.getYuretime());
                    }

                }

                ArmsUtils.startActivity(new Intent(this, TestResultFGGDActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FGTestMessageBean tags) {
        switch (tags.tag) {
            case 0:

                fggdAdapter.notifyDataSetChanged();

                break;
        }
    }


}