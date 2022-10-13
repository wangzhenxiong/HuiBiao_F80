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
import com.dy.huibiao_f80.android_serialport_api.SerialControl;
import com.dy.huibiao_f80.android_serialport_api.SerialHelper;
import com.dy.huibiao_f80.app.utils.ByteUtils;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.usbhelps.UsbControl;
import com.dy.huibiao_f80.usbhelps.UsbReadWriteHelper;
import com.jess.arms.base.BaseService;
import com.jess.arms.utils.ArmsUtils;

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
    public List<GalleryBean> mZJSGalleryBeanList = new ArrayList<>();
    public List<GalleryBean> mMYYGGalleryBeanList = new ArrayList<>();

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
                if (RUNFLAG_FGGD || RUNFLAG_FGGD_HASSTART) {
                    for (int i = 0; i < mFGGDGalleryBeanList.size(); i++) {
                        TestRecord bean = (TestRecord) mFGGDGalleryBeanList.get(i);

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
                                    if (Constants.PLATFORM_TAG == 21) {
                                        ((TestRecord) e).setReservedfield6("种植业");
                                        ((TestRecord) e).setReservedfield10("例行抽检");
                                    }
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
                            //检测项目修改后需要对没有开始检测但是已经选了检测项目的通道进行修改
                            // TODO: 10/9/22
                          /*  if (bean.isProjectChange()) {
                                if (bean.getState() != 1) {
                                    BaseProjectMessage message = bean.getProjectMessage();
                                    if (null != message) {
                                        if (message instanceof FGGDTestItem) {
                                            FGGDTestItem load = DBHelper.getFGGDTestItemDao(MyAppLocation.myAppLocation).load(message.getId_base());
                                            bean.removeAll();
                                            bean.setProjectMessage(load);
                                        } else if (message instanceof DeviceItem_KJFW) {
                                            DeviceItem_KJFW load = DBHelper.getDeviceItem_KJFWDao(MyAppLocation.myAppLocation).load(message.getId_base());
                                            bean.removeAll();
                                            bean.setProjectMessage(load);
                                        }

                                    }
                                    bean.setProjectChange(false);
                                }

                            }*/
                            // LogUtils.d(w1+"-"+w2+"-"+w3+"-"+w4);


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
