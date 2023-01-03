package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerSamplingComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.Sampling;
import com.dy.huibiao_f80.mvp.contract.SamplingContract;
import com.dy.huibiao_f80.mvp.presenter.SamplingPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.SamplingAdapter;
import com.dy.huibiao_f80.mvp.ui.widget.MyDatePickerDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class SamplingActivity extends BaseActivity<SamplingPresenter> implements SamplingContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.chose_time)
    Button mChoseTime;
    @BindView(R.id.chose_result)
    Button mChoseResult;
    @BindView(R.id.seach)
    Button mSeach;
    @BindView(R.id.choseall)
    CheckBox mChoseall;
    @BindView(R.id.recylerview)
    RecyclerView mRecylerview;
    @BindView(R.id.newsampling)
    Button mNewsampling;
    @BindView(R.id.editor)
    Button mEditor;
    @BindView(R.id.delete)
    Button mDelete;
    @Inject
    List<Sampling> samplingList;
    @Inject
    SamplingAdapter samplingAdapter;
    @Inject
    AlertDialog sportDialog;
    @BindView(R.id.chose_sampletime)
    Button mChoseSampletime;
    private String startsampletime = "无";
    private String stopsampletime = "";
    private String starttime = "无";
    private String stoptime = "";
    private boolean isSeaching;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSamplingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sampling; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.configRecyclerView(mRecylerview, new GridLayoutManager(this, 1));
        mRecylerview.setAdapter(samplingAdapter);
        samplingAdapter.setEmptyView(R.layout.emptyview, (ViewGroup) mRecylerview.getParent());
        samplingAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.checkbox) {
                    Sampling galleryBean = samplingList.get(position);
                    if (galleryBean.isCheck()) {
                        galleryBean.setCheck(false);
                        if (mChoseall.isChecked()){
                            mChoseall.setChecked(false);
                        }
                    } else {
                        galleryBean.setCheck(true);
                    }
                }
            }
        });

        mPresenter.load(true);
        mChoseall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = mChoseall.isChecked();
                for (int i = 0; i < samplingList.size(); i++) {
                    samplingList.get(i).setCheck(isChecked);
                }
                samplingAdapter.notifyDataSetChanged();
            }
        });
       
        int requestcode = getIntent().getIntExtra("requestcode", 0);
        if (requestcode == RecordDetailActivity.REQUESTCODE) {
            setTitle("选择采样单");
            mChoseall.setVisibility(View.GONE);
            samplingAdapter.showCheckBox=false;
            samplingAdapter.notifyDataSetChanged();
            samplingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Long id = samplingList.get(position).getId();
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    setResult(Activity.RESULT_OK, intent);
                    // RESULT_OK就是一个默认值，=-1，它说OK就OK吧
                    finish();
                }
            });
        }
    }


    @Override
    public void showLoading() {
        if (null != sportDialog) {
            if (!sportDialog.isShowing()) {
                sportDialog.show();
            }
        }
    }

    @Override
    public void hideLoading() {
        if (null != sportDialog) {
            if (sportDialog.isShowing()) {
                sportDialog.dismiss();
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

    @OnClick({R.id.chose_sampletime,R.id.chose_time, R.id.chose_result, R.id.seach, R.id.newsampling, R.id.editor, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chose_sampletime:
                choseSampleingTime();
                break;
            case R.id.chose_time:
                choseTime();
                break;
            case R.id.chose_result:
                choseResult();
                break;
            case R.id.seach:
                if (isSeaching) {
                    mSeach.setText("查询");
                    isSeaching = false;

                    mChoseSampletime.setText("选择创建时间");
                    mChoseTime.setText("选择检测时间");
                    mChoseResult.setText("选择判定结果");

                    mPresenter.load(true);

                } else {
                    if (mChoseSampletime.getText().toString().equals("选择创建时间")&&mChoseTime.getText().toString().equals("选择检测时间") && mChoseResult.getText().toString().equals("选择判定结果")) {
                        ArmsUtils.snackbarText("请选择搜索条件");
                        return;
                    }
                    mSeach.setText("取消");
                    isSeaching = true;
                    mPresenter.seach(startsampletime,stopsampletime,starttime, stoptime, mChoseResult.getText().toString(), true);
                }
                break;
            case R.id.newsampling:
                makeDialog();
                break;
            case R.id.editor:
                int checkNum = 0;
                Sampling checkSample = null;
                for (int i = 0; i < samplingList.size(); i++) {
                    Sampling sampling = samplingList.get(i);
                    boolean check = sampling.isCheck();
                    if (check) {
                        checkNum++;
                        checkSample = sampling;
                    }
                }
                if (checkNum == 0) {
                    ArmsUtils.snackbarText("请选择需要编辑的采样单");
                } else if (checkNum == 1) {
                    makeEdtorDialog(checkSample);
                } else {
                    ArmsUtils.snackbarText("只支持单条编辑");
                }
                break;
            case R.id.delete:
                int checkNum_delete = 0;
                List<Sampling> checkSamples = new ArrayList<>();
                for (int i = 0; i < samplingList.size(); i++) {
                    Sampling sampling = samplingList.get(i);
                    boolean check = sampling.isCheck();
                    if (check) {
                        checkNum_delete++;
                        checkSamples.add(sampling);
                    }
                }
                if (checkNum_delete == 0) {
                    ArmsUtils.snackbarText("请选择需要删除的采样单");
                } else {
                    makeDeleteDialog(checkSamples);

                }
                break;
        }
    }



    private void makeDeleteDialog(List<Sampling> checkSamples) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("提示");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setMessage("确定要删除所选择的采样单信息吗？");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper.getSamplingDao().deleteInTx(checkSamples);
                ArmsUtils.snackbarText("删除成功");
                if (isSeaching) {
                    mPresenter.seach(startsampletime, stopsampletime,starttime, stoptime, mChoseResult.getText().toString(), true);
                } else {
                    mPresenter.load(true);
                }
                dialog.dismiss();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void makeEdtorDialog(Sampling checkSample) {
        if (null == checkSample) {
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        View inflate = LayoutInflater.from(this).inflate(R.layout.newsampleing_layout, null);
        dialog.setView(inflate);
        dialog.show();
        AutoCompleteTextView samplename = (AutoCompleteTextView) inflate.findViewById(R.id.samplename);
        AutoCompleteTextView samplenumber = (AutoCompleteTextView) inflate.findViewById(R.id.samplenumber);
        AutoCompleteTextView unitsunderinspection = (AutoCompleteTextView) inflate.findViewById(R.id.unitsunderinspection);
        AutoCompleteTextView testunit = (AutoCompleteTextView) inflate.findViewById(R.id.testunit);
        samplename.setText(checkSample.getSamplingName());
        samplenumber.setText(checkSample.getSamplingNumber());
        unitsunderinspection.setText(checkSample.getUnitDetected());
        testunit.setText(checkSample.getTestingUnit());
        inflate.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = samplename.getText().toString();
                String number = samplenumber.getText().toString();
                String beunit = unitsunderinspection.getText().toString();
                String unit = testunit.getText().toString();
                /*if (name.isEmpty() || number.isEmpty() || beunit.isEmpty() || unit.isEmpty()) {
                    ArmsUtils.snackbarText("请完善抽样单信息");
                    return;
                }*/
                if (name.isEmpty()) {
                    ArmsUtils.snackbarText("请完输入样品名称");
                    return;
                }

                checkSample.setSamplingName(name);
                checkSample.setSamplingNumber(number);
                checkSample.setUnitDetected(beunit);
                checkSample.setTestingUnit(unit);
                DBHelper.getSamplingDao().update(checkSample);
                ArmsUtils.snackbarText("修改成功");
                mPresenter.load(true);
                dialog.dismiss();
            }
        });
    }

    private void choseResult() {
        String[] strings = new String[]{"合格", "不合格", "可疑", "--"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("请选择判断结果");
        builder.setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String string = strings[which];
                mChoseResult.setText(string);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        // dialog弹出后，点击界面其他部分dialog消失
        dialog.setCanceledOnTouchOutside(true);
    }

    private void choseSampleingTime() {
        MyDatePickerDialog myDatePickerDialog = new MyDatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        myDatePickerDialog.setOnImgDialogListener(new MyDatePickerDialog.OnImgDialogListener() {
            @Override
            public void onItemImg(int year1_start, int month1_start, int day1_start, int year1_stop, int month1_stop, int day1_stop, String type) {
                LogUtils.d(year1_start + "年" + month1_start + "月" + day1_start + "日" + year1_stop + "年" + month1_stop + "月" + day1_stop + "日" + type);
                Calendar calendarstart = Calendar.getInstance();
                calendarstart.set(year1_start, month1_start, day1_start);
                Calendar calendarstop = Calendar.getInstance();
                calendarstop.set(year1_stop, month1_stop, day1_stop);


                Date time1 = calendarstart.getTime();
                Date time2 = calendarstop.getTime();

                if (time1.getTime() > time2.getTime()) {
                    startsampletime = getTime(time2);
                    stopsampletime = getTime(time1);
                    mChoseSampletime.setText(startsampletime + "-" + stopsampletime);
                } else if (time1.getTime() < time2.getTime()) {
                    startsampletime = getTime(time1);
                    stopsampletime = getTime(time2);
                    mChoseSampletime.setText(startsampletime + "-" + stopsampletime);

                } else {
                    startsampletime = getTime(time1);
                    stopsampletime = getTime(time2);
                    mChoseSampletime.setText(startsampletime);
                }



            }

            @Override
            public void cancle() {
                isSeaching = false;
                startsampletime = "无";
                stopsampletime = "";
                mChoseTime.setText("选择检测时间");


            }
        });
        myDatePickerDialog.myShow();
    }

    private void choseTime() {
        MyDatePickerDialog myDatePickerDialog = new MyDatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        myDatePickerDialog.setOnImgDialogListener(new MyDatePickerDialog.OnImgDialogListener() {
            @Override
            public void onItemImg(int year1_start, int month1_start, int day1_start, int year1_stop, int month1_stop, int day1_stop, String type) {
                LogUtils.d(year1_start + "年" + month1_start + "月" + day1_start + "日" + year1_stop + "年" + month1_stop + "月" + day1_stop + "日" + type);
                Calendar calendarstart = Calendar.getInstance();
                calendarstart.set(year1_start, month1_start, day1_start);
                Calendar calendarstop = Calendar.getInstance();
                calendarstop.set(year1_stop, month1_stop, day1_stop);


                Date time1 = calendarstart.getTime();
                Date time2 = calendarstop.getTime();

                if (time1.getTime() > time2.getTime()) {
                    starttime = getTime(time2);
                    stoptime = getTime(time1);
                    mChoseTime.setText(starttime + "-" + stoptime);
                } else if (time1.getTime() < time2.getTime()) {
                    starttime = getTime(time1);
                    stoptime = getTime(time2);
                    mChoseTime.setText(starttime + "-" + stoptime);

                } else {
                    starttime = getTime(time1);
                    stoptime = getTime(time2);
                    mChoseTime.setText(starttime);
                }


                //mPresenter.seach(starttime, stoptime, mChoseResult.getText().toString(), true);
            }

            @Override
            public void cancle() {
                isSeaching = false;
                starttime = "无";
                stoptime = "";
                mChoseTime.setText("选择检测时间");

                //mPresenter.seach(starttime, stoptime, mChoseResult.getText().toString(), true);
            }
        });
        myDatePickerDialog.myShow();
    }

    private String getTime(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.format(date);
    }

    private void makeDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        View inflate = LayoutInflater.from(this).inflate(R.layout.newsampleing_layout, null);
        dialog.setView(inflate);
        dialog.show();
        AutoCompleteTextView samplename = (AutoCompleteTextView) inflate.findViewById(R.id.samplename);
        AutoCompleteTextView samplenumber = (AutoCompleteTextView) inflate.findViewById(R.id.samplenumber);
        AutoCompleteTextView unitsunderinspection = (AutoCompleteTextView) inflate.findViewById(R.id.unitsunderinspection);
        AutoCompleteTextView testunit = (AutoCompleteTextView) inflate.findViewById(R.id.testunit);
        inflate.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = samplename.getText().toString();
                String number = samplenumber.getText().toString();
                String beunit = unitsunderinspection.getText().toString();
                String unit = testunit.getText().toString();

                if (name.isEmpty()) {
                    ArmsUtils.snackbarText("请输入样品名称");
                    return;
                }
                /*if (number.isEmpty()) {
                    ArmsUtils.snackbarText("请输入采样单编号");
                    return;
                }
                if (beunit.isEmpty()) {
                    ArmsUtils.snackbarText("请输入被检单位");
                    return;
                }
                if (unit.isEmpty()) {
                    ArmsUtils.snackbarText("请输入检测单位");
                    return;
                }*/

                Sampling entity = new Sampling();
                entity.setId(null);
                entity.setCreationTime(System.currentTimeMillis());
                entity.setSamplingName(name);
                entity.setSamplingNumber(number);
                entity.setUnitDetected(beunit);
                entity.setTestingUnit(unit);
                DBHelper.getSamplingDao().insert(entity);
                ArmsUtils.snackbarText("新建成功");
                mPresenter.load(true);
                dialog.dismiss();
            }
        });


    }

    @Override
    public Activity getActivity() {
        return this;
    }
}