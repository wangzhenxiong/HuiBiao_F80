/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dy.huibiao_f80.app.service;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.apkfuns.logutils.LogUtils;
import com.apkfuns.logutils.utils.ObjectUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.android_serialport_api.SerialControl;
import com.dy.huibiao_f80.android_serialport_api.SerialHelper;
import com.dy.huibiao_f80.app.utils.ByteUtils;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.bean.eventBusBean.FGTestMessageBean;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.usbhelps.UsbControl;
import com.dy.huibiao_f80.usbhelps.UsbReadWriteHelper;
import com.jess.arms.base.BaseService;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * ================================================
 * 展示 {@link BaseService} 的用法
 * <p>
 * Created by JessYan on 09/07/2016 16:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class SerialDataService extends BaseService implements UsbReadWriteHelper.onUsbReciver, UsbControl.onUsbInitFinshed {

    private DecimalFormat mFormatResult_2 = new DecimalFormat("##0.00");
    private DecimalFormat mFormatContro_3 = new DecimalFormat("##0.000");
    private IBinder bind = new MyBinder();

    /**
     * 分光，干式农残串口数据
     */
    public SerialControl mData_SerialControl;
    /**
     * 打印机串口数据
     */
    public SerialControl mPrint_SerialControl;

    /**
     * 开始检测标志，当有通道处于后台检测时为true
     */
    public Boolean RUNFLAG_FGGD_HASSTART = false;
    public Boolean RUNFLAG_GSNC_HASSTART = false;
    /**
     * 运行标志，是否在检测界面
     */
    public Boolean RUNFLAG_GSNC = false;
    public Boolean RUNFLAG_FGGD = false;


    /**
     * 串口是否打开
     */
    private boolean mOpenComPort;

    public List<GalleryBean> mFGGDGalleryBeanList = new ArrayList<>();
    public List<GalleryBean> mGSNCGalleryBeanList = new ArrayList<>();
    public List<GalleryBean> mJTJGalleryBeanList = new ArrayList<>();

    public int mTempGallery1;
    public int mTempGallery2;

    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;


    private UsbReadWriteHelper mUSBRWHelper;


    /*
     * 真菌读数相关
     * */

    boolean zjds_gettem_flag = false;
    boolean zjds_settem_flag = false;
    boolean zjds_balance_flag = false;
    boolean zjds_startseach_flag = false;
    boolean zjds_CountDown_flag = false;
    private Timer mZJDSTemp;


    public UsbDevice mUsbDevice_multicard;
    private Handler mHandler;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return bind;
    }


    public class MyBinder extends Binder {
        public SerialDataService getService() {
            return SerialDataService.this;
        }
    }

    /**
     *
     */
    @Override
    public void init() {
        mScheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) ArmsUtils.obtainAppComponentFromContext(this).executorService();

        //百度地图 定位初始化
        initLocate();

        //初始化串口
        initSerialcontrol();

        //接收数据
        reciveDatas();

        //初始化倒计时，不能用与下位机通信机制来作为倒计时，会有误差，必须新开倒计时
        initTimes();

        //删除分光异常保存的文件，不删会导致内存消耗
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dayuan/fglog/";

        FileUtils.deleteDir(path);
        mHandler = new Handler(Looper.getMainLooper());
    }


    AlertDialog dialog = null;

    public void showCleandialog(String title, String msg) {
        if (null != dialog) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(MyAppLocation.myAppLocation);
                mAlertDialog.setTitle(title);
                mAlertDialog.setCancelable(true);
                mAlertDialog.setMessage(msg);
                dialog = mAlertDialog.create();
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        mScheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                if (null == dialog) {
                    return;
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 15, TimeUnit.SECONDS);
    }

    public void hideCleandialog() {
        if (null != dialog) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }


    @Override
    public void successed(List<UsbDevice> mDeviceList) {
        UsbDevice device = mDeviceList.get(0);
        initZJDSUSBReciverListener(device);
    }

    @Override
    public void failure(String s) {
        ArmsUtils.snackbarText(s);
    }

    public void initZJDSUSBReciverListener(UsbDevice device) {
        mUSBRWHelper = new UsbReadWriteHelper(device);
        mUSBRWHelper.setReciverListener(this);

    }


    @Override
    public void reciver(byte[] bytes, int width, int height) {
        LogUtils.d(bytes);
        // zjds_flag = false;
        //获得图片


    }


    @Override
    public void reciver(List<Byte> bytes) {
        LogUtils.d(bytes);
        //zjds_flag = false;


    }


    //白平衡
    public void blanceAdjust() {

        if (null == mUSBRWHelper) {
            ArmsUtils.snackbarText("创建读取工具失败");
            return;
        }
        if (zjds_startseach_flag) {
            ArmsUtils.snackbarText("正在扫描");
            return;
        }
        if (zjds_balance_flag) {
            ArmsUtils.snackbarText("正在进行白平衡校准");
            return;
        }
        if (zjds_settem_flag) {
            ArmsUtils.snackbarText("正在设置温度");
            return;
        }
        if (zjds_gettem_flag) {
            ArmsUtils.snackbarText("正在读取温度");
            return;
        }
        if (zjds_CountDown_flag) {
            ArmsUtils.snackbarText("正在倒计时");
            return;
        }
        mUSBRWHelper.sendMessage(Constants.CALIBRATION_JTJSCANNER, true);
        zjds_balance_flag = true;
    }


    //设置温度
    public boolean setTemperature(int temperature) {

        if (mUSBRWHelper == null) {
            ArmsUtils.snackbarText("创建读取工具失败");
            return false;
        }
        if (zjds_startseach_flag) {
            ArmsUtils.snackbarText("正在扫描");
            return false;
        }
        if (zjds_balance_flag) {
            ArmsUtils.snackbarText("正在进行白平衡校准");
            return false;
        }
        if (zjds_settem_flag) {
            ArmsUtils.snackbarText("正在设置温度");
            return false;
        }
        if (zjds_gettem_flag) {
            ArmsUtils.snackbarText("正在读取温度");
            return false;
        }
        mUSBRWHelper.sendMessage(Constants.getTemByte(temperature), true);
        zjds_settem_flag = true;
        return true;
    }

    //获取温度
    private void getTemperature() {
        //温度请求后台默认操作，不用提示失败信息
        LogUtils.d("温度请求");
        if (mUSBRWHelper != null) {
            if (mUSBRWHelper == null) {
                //ArmsUtils.snackbarText("创建读取工具失败");
                return;
            }
            if (zjds_startseach_flag) {
                //ArmsUtils.snackbarText("正在扫描");
                return;
            }
            if (zjds_balance_flag) {
                //ArmsUtils.snackbarText("正在进行白平衡校准");
                return;
            }
            if (zjds_settem_flag) {
                //ArmsUtils.snackbarText("正在设置温度");
                return;
            }
            if (zjds_gettem_flag) {
                //ArmsUtils.snackbarText("正在读取温度");
                return;
            }

            mUSBRWHelper.sendMessage(Constants.zjds_asktemp_s, true);
            zjds_gettem_flag = true;
        }

    }


    /**
     * 接收异步返回的定位结果
     **/
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            String str = location.getAddrStr();
            if (null != str) {
                Constants.ADDR_WF = str;
                Constants.LATITUDE = location.getLatitude();
                Constants.LONTITUDE = location.getLongitude();
            }
            LogUtils.d(Constants.ADDR_WF + "--" + Constants.LATITUDE + "--" + Constants.LONTITUDE);
        }
    }

    private LocationClient locationClient;

    /**
     * 配置定位SDK参数
     **/
    private void initLocate() {

        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
//        int span=1000;
//        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.setEnableSimulateGps(false);
        locationClient.setLocOption(option);
        locationClient.start();
    }


    /**
     * 每10分钟轮询一次检测记录，发现没有上传的检测记录就上传该条记录
     */

    private int fggd_send_flag, gsnc_send_flag, send_lz7000_flag, send_dy3500p_flag;

    private void initTimes() {
        //请求数据，分光，农残 频率500毫秒一次
        mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        mScheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (mOpenComPort) {
                    switch (BuildConfig.APPLICATION_ID) {


                        default:
                            switch (fggd_send_flag) {
                                case 0:
                                    if (RUNFLAG_FGGD || RUNFLAG_FGGD_HASSTART) {

                                        mData_SerialControl.send(Constants.SPECTRAL_DATA_REQUEST_DY1000);//分光数据请求
                                    }
                                    fggd_send_flag++;
                                    break;
                                case 1:
                                    fggd_send_flag--;
                                    break;
                            }
                            break;


                    }

                }
            }
        }, 0, 500, TimeUnit.MILLISECONDS);

        //地理位置更新 频率2分钟一次
        mScheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                boolean connected = ArmsUtils.isNetworkConnected(getApplicationContext());
                if (connected) {
                    locationClient.requestLocation();
                }
            }
        }, 0, 120, TimeUnit.SECONDS);

        //分光光度倒计时 频率1秒一次
        mScheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //分光模块的数据更新
                //LogUtils.d("genxing1");
                if (RUNFLAG_FGGD || RUNFLAG_FGGD_HASSTART) {
                    for (int i = 0; i < mFGGDGalleryBeanList.size(); i++) {

                        TestRecord bean = (TestRecord) mFGGDGalleryBeanList.get(i);
                        BaseProjectMessage message = bean.getmProjectMessage();
                        if (null == message) {
                            continue;
                        }
                        //LogUtils.d(message);
                        int jiancetime = message.getJiancetime();
                        int method = message.getMethod_sp();
                        int remainingtime = bean.getRemainingtime();
                        if (remainingtime > 0) {
                            if (remainingtime == jiancetime) { //去掉预热时间 预热时间结束后才赋值开始的ad值
                                bean.startTest();
                            }
                            bean.setRemainingtime(remainingtime - 1);
                            RUNFLAG_FGGD_HASSTART = true;
                        }

                        int state = bean.getState();

                        if (state == 1 && remainingtime == 0) {
                            bean.testFinished();
                            switch (method) {
                                case 0: //抑制率法  检测项目自带限量值判断
                                    switch (bean.getDowhat()) {
                                        case 1:

                                            getFGmethod1result(i);
                                            break;
                                        case 2:
                                            getFGmethod1contro(i);
                                            break;
                                    }
                                    break;
                                case 1: //标准曲线法  需要根据样品的限量值来判断结果 （或者从匹配的检测项目中获取）
                                    switch (bean.getDowhat()) {
                                        case 1:
                                            getFGmethod2result(i);
                                            break;
                                        case 2:
                                            getFGmethod2contro(i);
                                            break;
                                    }
                                    break;
                                case 3: //动力学法  检测项目自带限量值判断
                                    getFGmethod3result(i);
                                    break;
                                case 4: //系数法  需要根据样品的限量值来判断结果 （或者从匹配的检测项目中获取）
                                    getFGmethod4result(i);
                                    break;
                            }
                            //checkFGState();
                        }
                    }
                    if (mFGGDGalleryBeanList.size() > 0) {
                        //发送更新
                        // LogUtils.d("发送更新");
                        EventBus.getDefault().post(new FGTestMessageBean(0));
                    }

                }

                //干式农残模块的数据更新
                if (RUNFLAG_GSNC || RUNFLAG_GSNC_HASSTART) {
                    for (int i = 0; i < mGSNCGalleryBeanList.size(); i++) {
                        GalleryBean bean = mGSNCGalleryBeanList.get(i);
                        int remainingtime = bean.getRemainingtime();
                        int dowhat = bean.getDowhat(); //样品1  对照2
                        int state = bean.getState(); //通道状态  3空闲 0等待测试 1正在测试 2测试结束

                        if (remainingtime > 0) {
                            if (remainingtime == 480) {  //第0秒
                                bean.setR_0s(bean.getR());
                                bean.setG_0s(bean.getG());
                                bean.setB_0s(bean.getB());
                            }

                            if (remainingtime == 390) { //第40 秒
                                bean.setR_40s(bean.getR());
                                bean.setG_40s(bean.getG());
                                bean.setB_40s(bean.getB());
                            }

                            bean.setRemainingtime(remainingtime - 1);
                            RUNFLAG_GSNC_HASSTART = true;
                        }
                        //第 480 秒
                        if (state == 1 && remainingtime == 0) {
                            bean.setR_480s(bean.getR());
                            bean.setG_480s(bean.getG());
                            bean.setB_480s(bean.getB());
                            if (dowhat == 1) {
                            } else if (dowhat == 2) {
                            }
                            checkGSNCState();
                        }

                    }


                }


            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }


    /**
     * @param i 抑制率法结果
     */
    private void getFGmethod1result(int i) {
        TestRecord detection_record_fggd_nc = (TestRecord) mFGGDGalleryBeanList.get(i);
        LogUtils.d(detection_record_fggd_nc);
        //销库存
        //DownLoadBaseDataService.updateJxcByxmAjcs_ZHENJIANG(this, detection_record_fggd_nc.getUnique_testproject());
        ProjectFGGD message2 = (ProjectFGGD) detection_record_fggd_nc.getmProjectMessage();
        //FGGDTestItem fggdTestItem = (FGGDTestItem) message2;
        LogUtils.d(message2);
        //每个检测项目都有自己的一个对照值 所以需要每次都从数据库拿最新的检测项目
        //FGGDTestItem fggdTestItem = DBHelper.getFGGDTestItemDao(this).load(((FGGDTestItem) detection_record_fggd_nc.getProjectMessage()).getId_base());
        //判断是否合格所需参数
        //计算结果所需的参数
        double start_Absorbance = 0;//开始吸光度
        double stop_Absorbance = 0; //结束吸光度
        int wavelength = message2.getWavelength();
        String unit_input = message2.getUnit_input();
        //需要根据不同波长取不同的吸光度
        switch (wavelength) {
            case 0:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance1_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance1_after(); //结束吸光度
                break;
            case 1:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance2_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance2_after(); //结束吸光度
                break;
            case 2:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance3_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance3_after(); //结束吸光度
                break;
            case 3:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance4_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance4_after(); //结束吸光度
                break;
        }
        LogUtils.d(start_Absorbance);
        LogUtils.d(stop_Absorbance);
        if ("Infinity".equals(start_Absorbance + "") || "Infinity".equals(stop_Absorbance + "")) {
            ArmsUtils.snackbarText(getString(R.string.contro_erromessage1));
            detection_record_fggd_nc.setState(3);
            return;
        }
        double df_abs = 0; //吸光度差
        //吸光度差 = 结束时吸光度 - 开始时吸光度
        df_abs = 0;
        if (start_Absorbance < stop_Absorbance) {
            df_abs = stop_Absorbance - start_Absorbance;
        }
        LogUtils.d(df_abs);
        // 抑制率= (对照值-df_abs) / 对照值
        double df_contro_abs = 0;
        //对照值
        //double controlValue = Constants.FGGD_YIZHILV_CONTROL_VALUE;
        float controValue0 = Constants.controvalue0;
        LogUtils.d(controValue0);
        double controlValue = controValue0;
        if (controlValue == 0) {
            ArmsUtils.snackbarText(getString(R.string.lastcontrovalue));
            detection_record_fggd_nc.setState(3);
            return;
        }
        if (controlValue > df_abs) {
            df_contro_abs = controlValue - df_abs;
        }
        //检测结果
        double testResult = (df_contro_abs / controlValue) * 100;
        LogUtils.d(testResult);
        //判断结果
        String conclusion = judge(testResult, message2);


        //设置一些检测完成时需要添加的信息
        String unit = detection_record_fggd_nc.getCov_unit();
        if ("".equals(unit)) {
            detection_record_fggd_nc.setCov_unit(unit_input);
        }
        //检测结果 结论
        detection_record_fggd_nc.setTestresult(mFormatResult_2.format(testResult));
        detection_record_fggd_nc.setDecisionoutcome(conclusion);
        //检测完成时间
        detection_record_fggd_nc.setTestingtime(System.currentTimeMillis());
        //检测地点
        detection_record_fggd_nc.setTestsite(Constants.ADDR_WF);
        detection_record_fggd_nc.setLatitude(Constants.LATITUDE);
        detection_record_fggd_nc.setLongitude(Constants.LONTITUDE);
        //当前平台
        //detection_record_fggd_nc.setPlatform_tag(Constants.PLATFORM_TAG + "");

        //对照值
        //detection_record_fggd_nc.setControlvalue(Constants.FGGD_YIZHILV_CONTROL_VALUE + "");
        detection_record_fggd_nc.setControlvalue(controValue0 + "");
        //检测人员   这里填的是本地登录的账号名称
        //detection_record_fggd_nc.setInspector(Constants.NOWUSER.getUsername());
        //设置检测模块
        detection_record_fggd_nc.setTest_Moudle(ArmsUtils.getString(MyAppLocation.myAppLocation, R.string.FGGD_TestMoudle));
        // 保存至数据库
        detection_record_fggd_nc.setId(null);//自增ID
        //设置状态为检测完成 2
        detection_record_fggd_nc.setState(2);
        //detection_record_fggd_nc.setDowhat(0);


        long insert = DBHelper.getTestRecordDao().insert(detection_record_fggd_nc);

        //检测完成后的操作，自动打印，自动上传等
        // printAndUpload(detection_record_fggd_nc, insert);
    }

    private String judge(double testResult, ProjectFGGD message2) {
        if (message2.getUser_yin()) {


            double yin_a = message2.getYin_a();
            String yin_a_symbol = message2.getYin_a_symbol();
            boolean yin_a_a = true;
            switch (yin_a_symbol) {
                case "<":
                    yin_a_a = yin_a < testResult;
                    break;
                case ">":
                    yin_a_a = yin_a > testResult;
                    break;
                case "≥":
                    yin_a_a = yin_a >= testResult;
                    break;
                case "≤":
                    yin_a_a = yin_a <= testResult;
                    break;
            }
            double yin_b = message2.getYin_b();
            String yin_b_symbol = message2.getYin_b_symbol();
            boolean yin_b_b = true;
            switch (yin_b_symbol) {
                case "<":
                    yin_b_b = testResult < yin_b;
                    break;
                case ">":
                    yin_b_b = testResult > yin_b;
                    break;
                case "≥":
                    yin_b_b = testResult >= yin_b;
                    break;
                case "≤":
                    yin_b_b = testResult <= yin_b;
                    break;
            }
            if (yin_a_a && yin_b_b) {
                return getString(R.string.ok);
            }
        }


        if (message2.getUser_yang()) {
            double yang_a = message2.getYang_a();
            String yang_a_symbol = message2.getYang_a_symbol();
            boolean yang_a_a = true;
            switch (yang_a_symbol) {
                case "<":
                    yang_a_a = yang_a < testResult;
                    break;
                case ">":
                    yang_a_a = yang_a > testResult;
                    break;
                case "≥":
                    yang_a_a = yang_a >= testResult;
                    break;
                case "≤":
                    yang_a_a = yang_a <= testResult;
                    break;
            }

            double yang_b = message2.getYang_b();
            boolean yang_b_b = true;
            String yang_b_symbol = message2.getYang_b_symbol();
            switch (yang_b_symbol) {
                case "<":
                    yang_b_b = testResult < yang_b;
                    break;
                case ">":
                    yang_b_b = testResult > yang_b;
                    break;
                case "≥":
                    yang_b_b = testResult >= yang_b;
                    break;
                case "≤":
                    yang_b_b = testResult <= yang_b;
                    break;
            }
            if (yang_a_a && yang_b_b) {
                return getString(R.string.ng);
            }
        }
        if (message2.getUser_keyi()) {
            double keyi_a = message2.getKeyi_a();
            String keyi_a_symbol = message2.getKeyi_a_symbol();
            boolean keyi_a_a = true;
            switch (keyi_a_symbol) {
                case "<":
                    keyi_a_a = keyi_a < testResult;
                    break;
                case ">":
                    keyi_a_a = keyi_a > testResult;
                    break;
                case "≥":
                    keyi_a_a = keyi_a >= testResult;
                    break;
                case "≤":
                    keyi_a_a = keyi_a <= testResult;
                    break;
            }
            double keyi_b = message2.getKeyi_b();
            String keyi_b_symbol = message2.getKeyi_b_symbol();
            boolean keyi_b_b = true;
            switch (keyi_b_symbol) {
                case "<":
                    keyi_b_b = testResult < keyi_b;
                    break;
                case ">":
                    keyi_b_b = testResult > keyi_b;
                    break;
                case "≥":
                    keyi_b_b = testResult >= keyi_b;
                    break;
                case "≤":
                    keyi_b_b = testResult <= keyi_b;
                    break;
            }
            if (keyi_a_a && keyi_b_b) {
                return getString(R.string.keyi);
            }
        }
        return "--";
    }

    /**
     * @param i 分光数组下标
     *          抑制率法对照值
     */
    private void getFGmethod1contro(int i) {
        TestRecord detection_record_fggd_nc = (TestRecord) mFGGDGalleryBeanList.get(i);
        LogUtils.d(detection_record_fggd_nc);
        ProjectFGGD message = (ProjectFGGD) detection_record_fggd_nc.getmProjectMessage();
        int wavelength = message.getWavelength();


        //计算结果所需的参数
        double start_Absorbance = 0;//开始吸光度
        double stop_Absorbance = 0; //结束吸光度

        //需要根据不同波长取不同的吸光度
        switch (wavelength) {
            case 0:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance1_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance1_after(); //结束吸光度
                break;
            case 1:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance2_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance2_after(); //结束吸光度
                break;
            case 2:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance3_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance3_after(); //结束吸光度
                break;
            case 3:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance4_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance4_after(); //结束吸光度
                break;
        }
        if ("Infinity".equals(start_Absorbance + "") || "Infinity".equals(stop_Absorbance + "")) {
            ArmsUtils.snackbarText(getString(R.string.sampleerro_message1));
            detection_record_fggd_nc.setState(3);
            return;
        }
        if (stop_Absorbance <= start_Absorbance) {
            ArmsUtils.snackbarText(getString(R.string.sampleerro_message2));
            detection_record_fggd_nc.setState(3);
            return;
        }
        double df_abs = stop_Absorbance - start_Absorbance;
        if (df_abs < Constants.FGCONTROVALUE_YIZHILVFA()) {
            ArmsUtils.snackbarText(getString(R.string.sampleerro_message3));
            detection_record_fggd_nc.setState(3);
            return;
        }
        String format = mFormatContro_3.format(df_abs);
        Float value = Float.valueOf(format);
        LogUtils.d(format + "");
        LogUtils.d(value + "");

        Constants.setControValue0(value);


        /*Constants.FGGD_YIZHILV_CONTROL_VALUE = value; //double 转换float 不能强制转换
        long l = System.currentTimeMillis();//时间戳
        SPUtils.put(MyAppLocation.myAppLocation, Constants.KEY_FGGD_YIZHILV_CONTROLTIME, l);
        SPUtils.put(MyAppLocation.myAppLocation, Constants.KEY_FGGD_YIZHILV_CONTROL_VALUE, Constants.FGGD_YIZHILV_CONTROL_VALUE);
        Constants.FGGD_YIZHILV_CONTROLTIME = l;*/

        detection_record_fggd_nc.setState(2);
    }

    /**
     * @param i 标准曲线法结果
     */
    private void getFGmethod2result(int i) {
        TestRecord detection_record_fggd_nc = (TestRecord) mFGGDGalleryBeanList.get(i);
        LogUtils.d(detection_record_fggd_nc);
        //销库存
        //DownLoadBaseDataService.updateJxcByxmAjcs_ZHENJIANG(this, detection_record_fggd_nc.getUnique_testproject());
        //每个检测项目都有自己的一个对照值 所以需要每次都从数据库拿最新的检测项目 检测流程是先做对照 后做样品
        ProjectFGGD projectMessage = (ProjectFGGD) detection_record_fggd_nc.getmProjectMessage();
        LogUtils.d(projectMessage);
        //FGGDTestItem projectMessage = DBHelper.getFGGDTestItemDao(this).load(((FGGDTestItem) detection_record_fggd_nc.getProjectMessage()).getId_base());
        String unit_input = projectMessage.getUnit_input();
        Double from0 = Double.valueOf(projectMessage.getFrom0());
        Double from1 = Double.valueOf(projectMessage.getFrom1());
        Double to0 = Double.valueOf(projectMessage.getTo0());
        Double to1 = Double.valueOf(projectMessage.getTo1());
        Double a0 = Double.valueOf(projectMessage.getA0());
        Double b0 = Double.valueOf(projectMessage.getB0());
        Double c0 = Double.valueOf(projectMessage.getC0());
        Double d0 = Double.valueOf(projectMessage.getD0());
        Double a1 = Double.valueOf(projectMessage.getA1());
        Double b1 = Double.valueOf(projectMessage.getB1());
        Double c1 = Double.valueOf(projectMessage.getC1());
        Double d1 = Double.valueOf(projectMessage.getD1());
        Double jzha = Double.valueOf(projectMessage.getA());
        Double jzhb = Double.valueOf(projectMessage.getB());
        //检测结束时吸光度
        double stop_Absorbance = 0;
        switch (projectMessage.getWavelength()) {
            case 0:
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance1_after(); //结束吸光度
                break;
            case 1:
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance2_after(); //结束吸光度
                break;
            case 2:
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance3_after(); //结束吸光度
                break;
            case 3:
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance4_after(); //结束吸光度
                break;
        }
        if ("Infinity".equals(stop_Absorbance + "")) {
            ArmsUtils.snackbarText("结束时吸光度无穷大，此次对照无效！");
            detection_record_fggd_nc.setState(3);
            return;
        }
        //稀释倍数
        double dilutionratio = detection_record_fggd_nc.getDilutionratio();
        //对照值
        //double controlValue = Constants.FGGD_BIAOZHUN_CONTROL_VALUE;
        double controlValue = Constants.controvalue1;
        LogUtils.d("对照值：" + controlValue);
        if (controlValue == 0) {
            ArmsUtils.snackbarText("对照值为0，检测无效！");
            detection_record_fggd_nc.setState(3);
            return;
        }
        //吸光度差
        double df_abs_cont = 0;
        if (controlValue < stop_Absorbance) {
            df_abs_cont = stop_Absorbance - controlValue;
        }
        //浓度值
        double testresult = 0;

        if (df_abs_cont >= from0 && df_abs_cont < to0) {
            testresult = (a0 + b0 * df_abs_cont + c0 * df_abs_cont * df_abs_cont + d0 * df_abs_cont * df_abs_cont * df_abs_cont) * dilutionratio;
        } else if (df_abs_cont >= from1 && df_abs_cont < to1) {
            testresult = (a1 + b1 * df_abs_cont + c1 * df_abs_cont * df_abs_cont + d1 * df_abs_cont * df_abs_cont * df_abs_cont) * dilutionratio;
        } else {
            ArmsUtils.snackbarText("吸光度超出判断范围，请检查检测方法参数是否正确！");
            detection_record_fggd_nc.setState(3);

            return;
        }
        testresult = testresult * jzha + jzhb;
        if (testresult < 0) {
            testresult = 0;
        }
        LogUtils.d(testresult);
        //判断结果
        String conclusion = judge(testresult, projectMessage);


        LogUtils.d(testresult);
        //设置一些检测完成时需要添加的信息
        String unit = detection_record_fggd_nc.getCov_unit();
        if ("".equals(unit)) {
            detection_record_fggd_nc.setCov_unit(unit_input);
        }
        //检测结果 结论
        detection_record_fggd_nc.setTestresult(mFormatResult_2.format(testresult));
        detection_record_fggd_nc.setDecisionoutcome(conclusion);
        //检测完成时间
        detection_record_fggd_nc.setTestingtime(System.currentTimeMillis());
        //检测地点
        detection_record_fggd_nc.setTestsite(Constants.ADDR_WF);
        detection_record_fggd_nc.setLatitude(Constants.LATITUDE);
        detection_record_fggd_nc.setLongitude(Constants.LONTITUDE);
        //对照值
        //当前平台
        detection_record_fggd_nc.setPlatform_tag(Constants.PLATFORM_TAG + "");
        //detection_record_fggd_nc.setControlvalue(Constants.FGGD_BIAOZHUN_CONTROL_VALUE + "");
        detection_record_fggd_nc.setControlvalue(projectMessage.getControValue() + "");
        //检测人员   这里填的是本地登录的账号名称
        //detection_record_fggd_nc.setInspector(Constants.NOWUSER.getUsername());
        //设置检测模块
        detection_record_fggd_nc.setTest_Moudle(ArmsUtils.getString(MyAppLocation.myAppLocation, R.string.FGGD_TestMoudle));
        // 保存至数据库
        detection_record_fggd_nc.setId(null);//自增ID
        //设置状态为检测完成 2
        detection_record_fggd_nc.setState(2);
        //detection_record_fggd_nc.setDowhat(0);

        long insert = DBHelper.getTestRecordDao().insert(detection_record_fggd_nc);

        //检测完成后的操作，自动打印，自动上传等
        //printAndUpload(detection_record_fggd_nc, insert);
    }

    /**
     * @param i 标准曲线法对照值
     */
    private void getFGmethod2contro(int i) {
        TestRecord detection_record_fggd_nc = (TestRecord) mFGGDGalleryBeanList.get(i);

        ProjectFGGD message = (ProjectFGGD) detection_record_fggd_nc.getmProjectMessage();
        int wavelength = 0;
        wavelength = message.getWavelength();
        LogUtils.d(wavelength);
        /*if (message instanceof FGGDTestItem) {
            FGGDTestItem projectMessage = (FGGDTestItem) message;
            wavelength = projectMessage.getWavelength();
        } else if (message instanceof DeviceItem_KJFW) {
            DeviceItem_KJFW projectMessage = (DeviceItem_KJFW) message;
            wavelength = projectMessage.getWavelength();
        }*/

        //计算结果所需的参数
        float stop_Absorbance = 0; //结束吸光度

        //需要根据不同波长取不同的吸光度
        switch (wavelength) {
            case 0:
                stop_Absorbance = Float.valueOf(detection_record_fggd_nc.getAbsorbance1_after() + ""); //结束吸光度
                break;
            case 1:
                stop_Absorbance = Float.valueOf(detection_record_fggd_nc.getAbsorbance2_after() + ""); //结束吸光度
                break;
            case 2:
                stop_Absorbance = Float.valueOf(detection_record_fggd_nc.getAbsorbance3_after() + ""); //结束吸光度
                break;
            case 3:
                stop_Absorbance = Float.valueOf(detection_record_fggd_nc.getAbsorbance4_after() + ""); //结束吸光度
                break;
        }
        if ("Infinity".equals("" + stop_Absorbance)) {
            ArmsUtils.snackbarText("结束时吸光度为无穷大，此次对照无效！");
            detection_record_fggd_nc.setState(3);
            return;
        }
        if (stop_Absorbance <= 0) {
            ArmsUtils.snackbarText("结束时吸光度为0，此次对照无效！");
            detection_record_fggd_nc.setState(3);
            return;
        }

        String format = mFormatContro_3.format(stop_Absorbance);
        Float aFloat = Float.valueOf(format);

        Constants.setControValue1(aFloat);

        /*Constants.FGGD_BIAOZHUN_CONTROL_VALUE = aFloat;
        long l = System.currentTimeMillis();//时间戳
        SPUtils.put(MyAppLocation.myAppLocation, Constants.KEY_FGGD_BIAOHUN_CONTROL_VALUE, aFloat);
        SPUtils.put(MyAppLocation.myAppLocation, Constants.KEY_FGGD_BIAOZHUN_CONTROLTIME, l);
        Constants.FGGD_BIAOZHUN_CONTROLTIME = l;*/
        detection_record_fggd_nc.setState(2);
    }

    /**
     * @param i 动力学法结果
     */
    private void getFGmethod3result(int i) {
        TestRecord detection_record_fggd_nc = (TestRecord) mFGGDGalleryBeanList.get(i);
        //销库存
        //DownLoadBaseDataService.updateJxcByxmAjcs_ZHENJIANG(this, detection_record_fggd_nc.getUnique_testproject());
        ProjectFGGD projectMessage = (ProjectFGGD) detection_record_fggd_nc.getmProjectMessage();
        String unit_input = projectMessage.getUnit_input();

        double a3 = projectMessage.getA();
        double b3 = projectMessage.getB();
        //计算结果所需的参数
        double start_Absorbance = 0;//开始吸光度
        double stop_Absorbance = 0; //结束吸光度
        int wavelength = projectMessage.getWavelength();
        //需要根据不同波长取不同的吸光度
        switch (wavelength) {
            case 410:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance1_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance1_after(); //结束吸光度
                break;
            case 536:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance2_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance2_after(); //结束吸光度
                break;
            case 595:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance3_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance3_after(); //结束吸光度
                break;
            case 620:
                start_Absorbance = detection_record_fggd_nc.getAbsorbance4_start();//开始吸光度
                stop_Absorbance = detection_record_fggd_nc.getAbsorbance4_after(); //结束吸光度
                break;
        }
        if ("Infinity".equals(start_Absorbance + "") || "Infinity".equals(stop_Absorbance + "")) {
            ArmsUtils.snackbarText("开始或结束时吸光度无穷大，此次对照无效！");
            detection_record_fggd_nc.setState(3);
            return;
        }
        double df_abs = 0; //吸光度差
        double testresult = 0; //检测结果

        if (start_Absorbance < stop_Absorbance) {
            df_abs = stop_Absorbance - start_Absorbance;
        }
        testresult = a3 * df_abs + b3;
        if (testresult < 0) {
            testresult = 0;
        }

       /* if ("%".equals(detection_record_fggd_nc.getCov_unit())) {
            testresult = testresult * 100;
        }*/
        LogUtils.d(testresult);
        //判断结果
        String conclusion = judge(testresult, projectMessage);
        //设置一些检测完成时需要添加的信息
        String unit = detection_record_fggd_nc.getCov_unit();
        if ("".equals(unit)) {
            detection_record_fggd_nc.setCov_unit(unit_input);
        }
        //检测结果 结论
        detection_record_fggd_nc.setTestresult(mFormatResult_2.format(testresult));
        detection_record_fggd_nc.setDecisionoutcome(conclusion);
        //检测完成时间
        detection_record_fggd_nc.setTestingtime(System.currentTimeMillis());
        //检测地点

        detection_record_fggd_nc.setTestsite(Constants.ADDR_WF);
        detection_record_fggd_nc.setLatitude(Constants.LATITUDE);
        detection_record_fggd_nc.setLongitude(Constants.LONTITUDE);
        //当前平台
        detection_record_fggd_nc.setPlatform_tag(Constants.PLATFORM_TAG + "");
        //检测人员   这里填的是本地登录的账号名称
        //detection_record_fggd_nc.setInspector(Constants.NOWUSER.getUsername());
        //设置检测模块
        detection_record_fggd_nc.setTest_Moudle(ArmsUtils.getString(MyAppLocation.myAppLocation, R.string.FGGD_TestMoudle));
        // 保存至数据库
        detection_record_fggd_nc.setId(null);//自增ID
        //设置状态为检测完成 2
        detection_record_fggd_nc.setState(2);
        //detection_record_fggd_nc.setDowhat(0);
        long insert = DBHelper.getTestRecordDao().insert(detection_record_fggd_nc);

        //检测完成后的操作，自动打印，自动上传等
        //printAndUpload(detection_record_fggd_nc, insert);
    }

    /**
     * @param i 系数法结果
     */
    private void getFGmethod4result(int i) {
        TestRecord detection_record_fggd_nc = (TestRecord) mFGGDGalleryBeanList.get(i);
        //销库存
        //DownLoadBaseDataService.updateJxcByxmAjcs_ZHENJIANG(this, detection_record_fggd_nc.getUnique_testproject());
        ProjectFGGD projectMessage = (ProjectFGGD) detection_record_fggd_nc.getmProjectMessage();
        String unit_input = projectMessage.getUnit_input();
        double a2 = projectMessage.getA();
        double b2 = projectMessage.getB();
        double c2 = projectMessage.getC();
        double d2 = projectMessage.getD();
        //系数法   直接得结果
        double everyresponse = detection_record_fggd_nc.getEveryresponse();//反应液滴数
        //检测结果
        double testresult = 0;

        testresult = a2 + b2 * everyresponse + c2 * everyresponse * everyresponse + d2 * everyresponse * everyresponse * everyresponse;
        LogUtils.d(testresult);
        //判断结果
        String conclusion = judge(testresult, projectMessage);
        //设置一些检测完成时需要添加的信息
        String unit = detection_record_fggd_nc.getCov_unit();
        if ("".equals(unit)) {
            detection_record_fggd_nc.setCov_unit(unit_input);
        }
        //检测结果 结论
        detection_record_fggd_nc.setTestresult(mFormatResult_2.format(testresult));
        detection_record_fggd_nc.setDecisionoutcome(conclusion);
        //检测完成时间
        detection_record_fggd_nc.setTestingtime(System.currentTimeMillis());
        //检测地点
        detection_record_fggd_nc.setTestsite(Constants.ADDR_WF);
        detection_record_fggd_nc.setLatitude(Constants.LATITUDE);
        detection_record_fggd_nc.setLongitude(Constants.LONTITUDE);
        //当前平台
        detection_record_fggd_nc.setPlatform_tag(Constants.PLATFORM_TAG + "");
        //稀释倍数
        detection_record_fggd_nc.setControlvalue(everyresponse + "");
        //检测人员   这里填的是本地登录的账号名称
        //detection_record_fggd_nc.setInspector(Constants.NOWUSER.getUsername());
        //设置检测模块
        detection_record_fggd_nc.setTest_Moudle(ArmsUtils.getString(MyAppLocation.myAppLocation, R.string.FGGD_TestMoudle));
        // 保存至数据库
        detection_record_fggd_nc.setId(null);//自增ID
        //设置状态为检测完成 2
        //detection_record_fggd_nc.setDowhat(0);
        detection_record_fggd_nc.setState(2);
        long insert = DBHelper.getTestRecordDao().insert(detection_record_fggd_nc);

        //检测完成后的操作，自动打印，自动上传等
        //printAndUpload(detection_record_fggd_nc, insert);
    }

    private double getRandomNumber() {
        double random = Math.random();
        if (random == 0) {
            getRandomNumber();
        }
        return random;
    }

    /**
     * 检查干式农残模块的检测状态 看是否有在检测的通道，没有的话就停止与底层的数据传输
     */
    public void checkGSNCState() {
        for (int i = 0; i < mGSNCGalleryBeanList.size(); i++) {
            int state = mGSNCGalleryBeanList.get(i).getState();
            if (state == 1) {
                return;
            }
        }
        RUNFLAG_GSNC_HASSTART = false;
    }

    /**
     * 检查分光光度模块的检测状态 看是否有在检测的通道，没有的话就停止与底层的数据传输
     */
    public void checkFGState() {
        for (int i = 0; i < mFGGDGalleryBeanList.size(); i++) {
            int state = mFGGDGalleryBeanList.get(i).getState();
            if (state == 1) {
                return;
            }
        }
        RUNFLAG_FGGD_HASSTART = false;

    }


    private void reciveDatas() {
        RxErrorHandler mRxErrorHandler = ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler();
        //分光光度信息接收处理
        Observable.create(new ObservableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(ObservableEmitter<byte[]> emitter) throws Exception {
                String s8 = "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
                String s6 = "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
                if (null != mData_SerialControl) {

                    mData_SerialControl.setOnFGGDDataReceiveListener(new SerialHelper.OnFGGDDataReceiveListener() {
                        @Override
                        public void onDataReceive(byte[] buffer, int size) {
                            // LogUtils.d(buffer);
                            //需要区分新旧固件 旧的固件固定返回24个通道的数据
                            // 新的固件有多少通道返回多少通道
                            // 所以现在的方法就会存在16通道的仪器会出现24个检测通道
                            //每个通道有8个字节 截取后6个或8个通道的值，看是否为0 为0则舍去
                            int mSize = size;
                            if (BuildConfig.FGGD_TYPE == 6) {
                                byte[] bytes = ByteUtils.subBytes(buffer, ((size - 6) / 8 - 6) * 8 + 4, 6 * 8);
                                //LogUtils.d(bytes);
                                String s1 = Arrays.toString(bytes);
                                //LogUtils.d(s1);
                                if (s6.equals(s1)) {
                                    //都为0 ，需要去掉
                                    mSize = mSize - 6 * 8;
                                }
                            } else if (BuildConfig.FGGD_TYPE == 8) {
                                byte[] bytes = ByteUtils.subBytes(buffer, ((size - 6) / 8 - 8) * 8 + 4, 8 * 8);
                                //LogUtils.d(bytes);
                                String s1 = Arrays.toString(bytes);
                                //LogUtils.d(s1);
                                if (s8.equals(s1)) {
                                    //都为0 ，需要去掉
                                    mSize = mSize - 8 * 8;
                                }
                            }


                            if (mFGGDGalleryBeanList.size() != (mSize - 6) / 8) {

                                if (mFGGDGalleryBeanList.size() != 0) {
                                    MyAppLocation.myAppLocation.speak("通道数据丢失");
                                    ArmsUtils.snackbarText("通道数据丢失");
                                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dayuan/fglog/";
                                    FileUtils.writeTxtToFile(ObjectUtil.objectToString(buffer), path, System.currentTimeMillis() + ".txt");
                                    return;
                                }
                                //mFGGDGalleryBeanList = new ArrayList<>((size - 6) / 8);
                                //初始化数据arry

                                for (int i = 0; i < (mSize - 6) / 8; i++) {
                                    GalleryBean e = new TestRecord();
                                    e.setGalleryNum(i + 1);
                                    e.setTestMoudle(1 + "");

                                    mFGGDGalleryBeanList.add(e);
                                }

                            }
                            // MyAppLocation.myAppLocation.speak("数据正常");
                            // LogUtils.d(buffer);
                            emitter.onNext(buffer);
                        }
                    });
                }
            }
        }).map(new Function<byte[], List<Integer>>() {
            @Override
            public List<Integer> apply(byte[] buffer) throws Exception {
                List<Integer> mlist = new ArrayList<>();
                int b = 4;
                for (int i = 0; i < mFGGDGalleryBeanList.size(); i++) {  //遍历解析数据

                    //波长 1
                    String byte1 = ByteUtils.byte2HexStr(ByteUtils.subBytes(buffer, b + 0, 2)).toString();
                    //波长2
                    String byte2 = ByteUtils.byte2HexStr(ByteUtils.subBytes(buffer, b + 2, 2)).toString();
                    //波长3
                    String byte3 = ByteUtils.byte2HexStr(ByteUtils.subBytes(buffer, b + 4, 2)).toString();
                    //波长4
                    String byte4 = ByteUtils.byte2HexStr(ByteUtils.subBytes(buffer, b + 6, 2)).toString();
                    int w1 = 0;
                    int w2 = 0;
                    int w3 = 0;
                    int w4 = 0;
                    if (BuildConfig.FGGD_TYPE == 6) {
                        w1 = ((ByteUtils.HexToInt(byte1.substring(2, 4)) * 256 + ByteUtils.HexToInt(byte1.substring(0, 2))));  //波长1 ad值
                        w2 = ((ByteUtils.HexToInt(byte2.substring(2, 4)) * 256 + ByteUtils.HexToInt(byte2.substring(0, 2))));
                        w3 = ((ByteUtils.HexToInt(byte3.substring(2, 4)) * 256 + ByteUtils.HexToInt(byte3.substring(0, 2))));
                        w4 = ((ByteUtils.HexToInt(byte4.substring(2, 4)) * 256 + ByteUtils.HexToInt(byte4.substring(0, 2))));
                    } else if (BuildConfig.FGGD_TYPE == 8) {
                        w1 = ((ByteUtils.HexToInt(byte1.substring(2, 4)) * 256 + ByteUtils.HexToInt(byte1.substring(0, 2))) * 1000) / 65535;  //波长1 ad值
                        w2 = ((ByteUtils.HexToInt(byte2.substring(2, 4)) * 256 + ByteUtils.HexToInt(byte2.substring(0, 2))) * 1000) / 65535;
                        w3 = ((ByteUtils.HexToInt(byte3.substring(2, 4)) * 256 + ByteUtils.HexToInt(byte3.substring(0, 2))) * 1000) / 65535;
                        w4 = ((ByteUtils.HexToInt(byte4.substring(2, 4)) * 256 + ByteUtils.HexToInt(byte4.substring(0, 2))) * 1000) / 65535;

                    }

                    mlist.add(w1);
                    mlist.add(w2);
                    mlist.add(w3);
                    mlist.add(w4);
                    //LogUtils.d(w1+"-"+w2+"-"+w3+"-"+w4+"-");

                    b = b + 8;
                }
                // LogUtils.d(mlist);
                return mlist;
            }
        })
                .onErrorReturn(new Function<Throwable, List<Integer>>() {
                    @Override
                    public List<Integer> apply(Throwable throwable) throws Exception {
                        ArmsUtils.snackbarText("分光数据异常");

                        return new ArrayList<>(mFGGDGalleryBeanList.size());
                    }
                }).subscribeOn(Schedulers.computation())
                .subscribe(new ErrorHandleSubscriber<List<Integer>>(mRxErrorHandler) {
                    @Override
                    public void onNext(List<Integer> beans) {
                        for (int i = 0, j = 0; i < beans.size(); ) {
                            GalleryBean bean = mFGGDGalleryBeanList.get(j);
                            int w1, w2, w3, w4;
                            w1 = beans.get(i++);
                            w2 = beans.get(i++);
                            w3 = beans.get(i++);
                            w4 = beans.get(i++);

                            bean.setWave1(w1);
                            bean.setWave2(w2);
                            bean.setWave3(w3);
                            bean.setWave4(w4);
                            j++;

                            //设置每个通道实时的四个波长段的ad值

                            if (bean.isClearn()) { //清零标志
                                //设置作为比较比色皿是否放入的ad值
                                bean.setDzh_wave1_start(w1);
                                bean.setDzh_wave2_start(w2);
                                bean.setDzh_wave3_start(w3);
                                bean.setDzh_wave4_start(w4);
                                bean.setClearn(false);
                                /// Detection_Record_FGGD_NC fg_nc= (Detection_Record_FGGD_NC) bean;
                                //fg_nc.getCheckPID();
                            }


                            int wave1 = bean.getWave1();
                            int start1 = bean.getDzh_wave1_start();
                            float i1 = 0;
                            double absorbance1 = 0;
                            if (start1 != 0) {
                                i1 = (float) wave1 / start1;
                                absorbance1 = Math.log10(1 / i1);
                            }


                            int wave2 = bean.getWave2();
                            int start2 = bean.getDzh_wave2_start();
                            float i2 = 0;
                            double absorbance2 = 0;
                            if (start2 != 0) {
                                i2 = (float) wave2 / start2;
                                absorbance2 = Math.log10(1 / i2);
                            }


                            int wave3 = bean.getWave3();
                            int start3 = bean.getDzh_wave3_start();
                            float i3 = 0;
                            double absorbance3 = 0;
                            if (start3 != 0) {
                                i3 = (float) wave3 / start3;
                                absorbance3 = Math.log10(1 / i3);
                            }
                            int wave4 = bean.getWave4();
                            int start4 = bean.getDzh_wave4_start();
                            float i4 = 0;
                            double absorbance4 = 0;
                            if (start4 != 0) {
                                i4 = (float) wave4 / start4;
                                absorbance4 = Math.log10(1 / i4);
                            }

                            //透光率
                            bean.setLuminousness1(i1);
                            bean.setLuminousness2(i2);
                            bean.setLuminousness3(i3);
                            bean.setLuminousness4(i4);
                            // LogUtils.d(i1 + "-" + i2 + "-" + i3 + "-" + i4 + "-");
                            //吸光度
                            bean.setAbsorbance1(absorbance1);
                            bean.setAbsorbance2(absorbance2);
                            bean.setAbsorbance3(absorbance3);
                            bean.setAbsorbance4(absorbance4);
                        }
                    }
                });


        //干式农残数据接收处理
        Observable.create(new ObservableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(ObservableEmitter<byte[]> emitter) throws Exception {
                if (null != mData_SerialControl) {
                    mData_SerialControl.setOnGSNCDataReceiveListener(new SerialHelper.OnGSNCDataReceiveListener() {
                        @Override
                        public void onDataReceive(byte[] buffer, int size) {
                            LogUtils.d(buffer);
                            if (mGSNCGalleryBeanList.size() == 0) {
                                //mGSNCGalleryBeanList = new ArrayList<>((size - 6) / 3);
                                //初始化数据arry
                                for (int i = 0; i < (size - 6) / 3; i++) {
                                    GalleryBean e = new TestRecord();
                                    e.setGalleryNum(i + 1);
                                    e.setTestMoudle(2 + "");


                                    mGSNCGalleryBeanList.add(e);
                                }


                            }
                            emitter.onNext(buffer);
                        }
                    });
                }

            }
        }).map(new Function<byte[], List<Integer>>() {
            @Override
            public List<Integer> apply(byte[] buffer) throws Exception {
                List<Integer> mlist = new ArrayList<>();
                for (int i = 0; i < mGSNCGalleryBeanList.size(); i++) {
                    int R1 = ByteUtils.HexToInt(ByteUtils.byte2HexStr(ByteUtils.subBytes(buffer, i * 3 + 4, 1)).toString().trim());
                    int G1 = ByteUtils.HexToInt(ByteUtils.byte2HexStr(ByteUtils.subBytes(buffer, i * 3 + 4 + 1, 1)).toString().trim());
                    int B1 = ByteUtils.HexToInt(ByteUtils.byte2HexStr(ByteUtils.subBytes(buffer, i * 3 + 4 + 2, 1)).toString().trim());
                    mlist.add(R1);
                    mlist.add(G1);
                    mlist.add(B1);
                }
                return mlist;
            }
        }).subscribeOn(Schedulers.computation())
                .subscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<List<Integer>>(mRxErrorHandler) {
                    @Override
                    public void onNext(List<Integer> beans) {
                        for (int i = 0, j = 0; i < beans.size(); ) {
                            GalleryBean bean = mGSNCGalleryBeanList.get(j);
                            int r, g, b;
                            bean.setR(r = beans.get(i++));
                            bean.setG(g = beans.get(i++));
                            bean.setB(b = beans.get(i++));
                            j++;
                        }
                    }
                });


        //干式农残温度信息
        Observable.create(new ObservableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(ObservableEmitter<byte[]> emitter) throws Exception {
                if (null != mData_SerialControl) {
                    mData_SerialControl.setOnGSNCTEMPDataReceiveListener(new SerialHelper.OnGSNCTEMPDataReceiveListener() {
                        @Override
                        public void onDataReceive(byte[] buffer, int size) {
                            //LogUtils.d(buffer);
                            emitter.onNext(buffer);
                        }
                    });
                }

            }
        }).map(new Function<byte[], List<Integer>>() {
            @Override
            public List<Integer> apply(byte[] buffer) throws Exception {
                List<Integer> list = new ArrayList<>();
                byte[] bytes1 = ByteUtils.subBytes(buffer, 4, 1);
                byte[] bytes2 = ByteUtils.subBytes(buffer, 5, 1);
                list.add(ByteUtils.HexToInt(ByteUtils.byte2HexStr(bytes1).toString().trim()) - 4);
                list.add(ByteUtils.HexToInt(ByteUtils.byte2HexStr(bytes2).toString().trim()) - 4);
                return list;
            }
        }).subscribeOn(Schedulers.computation())
                .subscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<List<Integer>>(mRxErrorHandler) {
                    @Override
                    public void onNext(List<Integer> integers) {
                        //LogUtils.d(integers);
                        mTempGallery1 = integers.get(0);
                        mTempGallery2 = integers.get(1);
                        //Intent intent1 = new Intent(Constants.TEMP_ACTION);
                        //sendBroadcast(intent1);
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RUNFLAG_FGGD = false;
        RUNFLAG_GSNC = false;
        mOpenComPort = false;
        mData_SerialControl.CloseComPort(mData_SerialControl);
        mPrint_SerialControl.CloseComPort(mPrint_SerialControl);
        //unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        RUNFLAG_FGGD = false;
        RUNFLAG_GSNC = false;
        mOpenComPort = false;
        mData_SerialControl.CloseComPort(mData_SerialControl);
        mPrint_SerialControl.CloseComPort(mPrint_SerialControl);
        return super.onUnbind(intent);
    }

    private void initSerialcontrol() {
        mData_SerialControl = new SerialControl();
        //区分Android版不同串口名称不同 ttys4 ttys0
        mData_SerialControl.setPort(Constants.DATA_SERIAPort);
        mData_SerialControl.setBaudRate(Constants.DATA_SERIALBaudRate);
        mOpenComPort = mData_SerialControl.OpenComPort(mData_SerialControl);
        LogUtils.d(mOpenComPort);
        if (!mOpenComPort) {
            ArmsUtils.snackbarText(Constants.DATA_SERIAPort + "打开失败！");
        }
        //打印机串口的开启
        mPrint_SerialControl = new SerialControl();
        mPrint_SerialControl.setPort(Constants.PRINT_SERIAPort);
        mPrint_SerialControl.setBaudRate(Constants.PRINT_SERIALBaudRate);
        boolean b = mPrint_SerialControl.OpenComPort(mPrint_SerialControl);
        if (!b) {
            ArmsUtils.snackbarText(Constants.PRINT_SERIAPort + "打开失败！");
        }
    }


    public void stopsFGGDSendthread() {
        RUNFLAG_FGGD = false;
    }

    public void stopsGSNCSendthread() {
        RUNFLAG_GSNC = false;
    }

    public void startFGGDSendthread() {
        RUNFLAG_FGGD = true;
    }

    public void startGSNCSendthread() {
        RUNFLAG_GSNC = true;
    }


}
