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
    private String startsampletime = "???";
    private String stopsampletime = "";
    private String starttime = "???";
    private String stoptime = "";
    private boolean isSeaching;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSamplingComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sampling; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
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
            setTitle("???????????????");
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
                    // RESULT_OK????????????????????????=-1?????????OK???OK???
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
                    mSeach.setText("??????");
                    isSeaching = false;

                    mChoseSampletime.setText("??????????????????");
                    mChoseTime.setText("??????????????????");
                    mChoseResult.setText("??????????????????");

                    mPresenter.load(true);

                } else {
                    if (mChoseSampletime.getText().toString().equals("??????????????????")&&mChoseTime.getText().toString().equals("??????????????????") && mChoseResult.getText().toString().equals("??????????????????")) {
                        ArmsUtils.snackbarText("?????????????????????");
                        return;
                    }
                    mSeach.setText("??????");
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
                    ArmsUtils.snackbarText("?????????????????????????????????");
                } else if (checkNum == 1) {
                    makeEdtorDialog(checkSample);
                } else {
                    ArmsUtils.snackbarText("?????????????????????");
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
                    ArmsUtils.snackbarText("?????????????????????????????????");
                } else {
                    makeDeleteDialog(checkSamples);

                }
                break;
        }
    }



    private void makeDeleteDialog(List<Sampling> checkSamples) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("??????");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setMessage("????????????????????????????????????????????????");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper.getSamplingDao().deleteInTx(checkSamples);
                ArmsUtils.snackbarText("????????????");
                if (isSeaching) {
                    mPresenter.seach(startsampletime, stopsampletime,starttime, stoptime, mChoseResult.getText().toString(), true);
                } else {
                    mPresenter.load(true);
                }
                dialog.dismiss();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "??????", new DialogInterface.OnClickListener() {
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
                    ArmsUtils.snackbarText("????????????????????????");
                    return;
                }*/
                if (name.isEmpty()) {
                    ArmsUtils.snackbarText("????????????????????????");
                    return;
                }

                checkSample.setSamplingName(name);
                checkSample.setSamplingNumber(number);
                checkSample.setUnitDetected(beunit);
                checkSample.setTestingUnit(unit);
                DBHelper.getSamplingDao().update(checkSample);
                ArmsUtils.snackbarText("????????????");
                mPresenter.load(true);
                dialog.dismiss();
            }
        });
    }

    private void choseResult() {
        String[] strings = new String[]{"??????", "?????????", "??????", "--"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("?????????????????????");
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String string = strings[which];
                mChoseResult.setText(string);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        // dialog????????????????????????????????????dialog??????
        dialog.setCanceledOnTouchOutside(true);
    }

    private void choseSampleingTime() {
        MyDatePickerDialog myDatePickerDialog = new MyDatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        myDatePickerDialog.setOnImgDialogListener(new MyDatePickerDialog.OnImgDialogListener() {
            @Override
            public void onItemImg(int year1_start, int month1_start, int day1_start, int year1_stop, int month1_stop, int day1_stop, String type) {
                LogUtils.d(year1_start + "???" + month1_start + "???" + day1_start + "???" + year1_stop + "???" + month1_stop + "???" + day1_stop + "???" + type);
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
                startsampletime = "???";
                stopsampletime = "";
                mChoseTime.setText("??????????????????");


            }
        });
        myDatePickerDialog.myShow();
    }

    private void choseTime() {
        MyDatePickerDialog myDatePickerDialog = new MyDatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        myDatePickerDialog.setOnImgDialogListener(new MyDatePickerDialog.OnImgDialogListener() {
            @Override
            public void onItemImg(int year1_start, int month1_start, int day1_start, int year1_stop, int month1_stop, int day1_stop, String type) {
                LogUtils.d(year1_start + "???" + month1_start + "???" + day1_start + "???" + year1_stop + "???" + month1_stop + "???" + day1_stop + "???" + type);
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
                starttime = "???";
                stoptime = "";
                mChoseTime.setText("??????????????????");

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
                    ArmsUtils.snackbarText("?????????????????????");
                    return;
                }
                /*if (number.isEmpty()) {
                    ArmsUtils.snackbarText("????????????????????????");
                    return;
                }
                if (beunit.isEmpty()) {
                    ArmsUtils.snackbarText("?????????????????????");
                    return;
                }
                if (unit.isEmpty()) {
                    ArmsUtils.snackbarText("?????????????????????");
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
                ArmsUtils.snackbarText("????????????");
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