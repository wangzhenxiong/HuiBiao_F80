package com.dy.huibiao_f80.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.SPUtils;
import com.dy.huibiao_f80.di.component.DaggerBalanceCalibrationComponent;
import com.dy.huibiao_f80.mvp.contract.BalanceCalibrationContract;
import com.dy.huibiao_f80.mvp.presenter.BalanceCalibrationPresenter;
import com.dy.huibiao_f80.mvp.ui.widget.LevelView;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class BalanceCalibrationActivity extends BaseActivity<BalanceCalibrationPresenter> implements BalanceCalibrationContract.View, SensorEventListener {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarly)
    AppBarLayout mToolbarly;
    @BindView(R.id.gv_hv)
    LevelView mGvHv;
    @BindView(R.id.tvv_vertical)
    TextView mTvvVertical;
    @BindView(R.id.tvl_vertical)
    TextView mTvlVertical;
    @BindView(R.id.tvv_horz)
    TextView mTvvHorz;
    @BindView(R.id.tvl_horz)
    TextView mTvlHorz;
    private SensorManager mSensorManager;
    private Gson mGson;
    private float mX_value;
    private float mY_value;
    private float mZ_value;

    private float[] accValues = new float[3];
    private float[] magValues = new float[3];
    private float[] angle = new float[3];
    // 旋转矩阵，用来保存磁场和加速度的数据
    private float[] r = new float[9];
    // 模拟方向传感器的数据（原始数据为弧度）
    private float[] values = new float[3];
    private Sensor acc_sensor;
    private Sensor mag_sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        acc_sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mag_sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // 给传感器注册监听：
        mSensorManager.registerListener(this, acc_sensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mag_sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // 取消方向传感器的监听
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        // 取消方向传感器的监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBalanceCalibrationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_balancecalibration; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mGson = new Gson();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Blance_Value blance_value = mGson.fromJson((String) SPUtils.get(this, Constants.BLANCE_VALUEKEY, mGson.toJson(new Blance_Value(-1, -1, -1))), Blance_Value.class);
        if (blance_value.getX_value() != -1) {
            mX_value = blance_value.getX_value();
            mY_value = blance_value.getY_value();
            mZ_value = blance_value.getZ_value();
        }
        mGvHv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mZ_value = avgz;
                mX_value = avgx;
                mY_value = avgy;
                SPUtils.put(getActivity(), Constants.BLANCE_VALUEKEY, mGson.toJson(new Blance_Value(mX_value, mY_value, mZ_value)));
                return false;
            }
        });

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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    List<Float> xValue = new ArrayList<>();
    List<Float> yValue = new ArrayList<>();
    List<Float> zValue = new ArrayList<>();
    float avgx;
    float avgy;
    float avgz;

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 获取手机触发event的传感器的类型
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                accValues = event.values.clone();
                // 获取　沿着Z轴转过的角度
                float azimuth = accValues[0];
                zValue.add(azimuth);
                // 获取　沿着X轴倾斜时　与Y轴的夹角
                float pitchAngle = accValues[1];
                xValue.add(pitchAngle);
                // 获取　沿着Y轴的滚动时　与X轴的角度
                float rollAngle = -accValues[2];
                yValue.add(rollAngle);
                if (yValue.size() == 20) {
                    avgx = getAVG(xValue);
                    avgz = getAVG(zValue);
                    avgy = getAVG(yValue);
                    onAngleChanged(avgx - mX_value, avgz - mZ_value, avgy - mY_value);
                    yValue.clear();
                    xValue.clear();
                    zValue.clear();
                }
                break;
            /*case Sensor.TYPE_MAGNETIC_FIELD:
                magValues = event.values.clone();
                LogUtils.d(magValues);
                break;
            case Sensor.TYPE_GYROSCOPE:
                 angle=event.values.clone();
                LogUtils.d(angle);
                break;*/
        }

     /*   SensorManager.getRotationMatrix(r, angle, accValues, magValues);
        SensorManager.getOrientation(r, values);*/


    }

    private float getAVG(List<Float> list) {
        float sun = 0;
        float max = 0;
        float min = Float.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            Float aFloat = list.get(i);
            if (max < aFloat) {
                max = aFloat;
            }
            if (min > aFloat) {
                min = aFloat;
            }

            sun = sun + aFloat;
        }
        return (sun-max-min) / (list.size()-2);
    }

    /**
     * 角度变更后显示到界面
     *
     * @param x y
     * @param z x
     * @param y z
     */
    private void onAngleChanged(float x, float z, float y) {

        mGvHv.setAngle(x, z);
        mTvlHorz.setText(String.valueOf((int) Math.toDegrees(x)) + "°");
        mTvlVertical.setText(String.valueOf((int) Math.toDegrees(z)) + "°");
    }


    static class Blance_Value implements Parcelable {
        private float x_value;
        private float y_value;
        private float z_value;

        public Blance_Value(float x_value, float y_value, float z_value) {
            this.x_value = x_value;
            this.y_value = y_value;
            this.z_value = z_value;
        }

        public Blance_Value() {
        }

        public float getX_value() {
            return x_value;
        }

        public void setX_value(float x_value) {
            this.x_value = x_value;
        }

        public float getY_value() {
            return y_value;
        }

        public void setY_value(float y_value) {
            this.y_value = y_value;
        }

        public float getZ_value() {
            return z_value;
        }

        public void setZ_value(float z_value) {
            this.z_value = z_value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(this.x_value);
            dest.writeFloat(this.y_value);
            dest.writeFloat(this.z_value);
        }

        protected Blance_Value(Parcel in) {
            this.x_value = in.readFloat();
            this.y_value = in.readFloat();
            this.z_value = in.readFloat();
        }

        public static final Parcelable.Creator<Blance_Value> CREATOR = new Parcelable.Creator<Blance_Value>() {
            @Override
            public Blance_Value createFromParcel(Parcel source) {
                return new Blance_Value(source);
            }

            @Override
            public Blance_Value[] newArray(int size) {
                return new Blance_Value[size];
            }
        };

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }
}