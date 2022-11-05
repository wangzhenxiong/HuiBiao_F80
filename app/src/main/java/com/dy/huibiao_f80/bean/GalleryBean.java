package com.dy.huibiao_f80.bean;

import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.os.Parcelable;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.service.UploadService;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.app.utils.PictureToolUtils;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.JTJPoint;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.ui.widget.JTJDataModel_P;
import com.dy.huibiao_f80.usbhelps.Status;
import com.dy.huibiao_f80.usbhelps.UsbReadWriteHelper;
import com.dy.huibiao_f80.usbhelps.listeners.OnStatuChangeListener;
import com.jess.arms.utils.ArmsUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * ━━━━━━神兽出没━━━━━━
 * 　　 ┏┓　 ┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　┃
 * 　　┃ -　━   ┃
 * 　　┃┳┛　┗┳ ┃
 * 　　┃　　　　┃
 * 　　┃　　┻　 ┃
 * 　　┃　　　　┃
 * 　　┗━┓　　┏━┛Code is far away from bug with the animal protecting
 * 　　　┃　　┃    神兽保佑,代码无bug
 * 　　　┃　　┃
 * 　　　┃　　┗━━━┓
 * 　　　┃　　　　 ┣┓
 * 　　　┃　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　 ┃┫┫ ┃┫┫
 * 　　　 ┗┻┛ ┗┻┛
 * <p>
 * Created by wangzhenxiong on 2019/3/6.
 */
public abstract class GalleryBean implements Parcelable, UsbReadWriteHelper.onUsbReciver {
    private int galleryNum;
    private int JTJ_MAC = -2;//胶体金模块编号
    private int MYYG_MAC = -2;//免疫荧光模块编号
    private int state;//通道状态  0等待测试 1正在测试 2测试结束 3测试失败
    private int dowhat = 1; //样品1  对照2
    private boolean clearn = true;//清零标志位 需要清零设为true，在解析数据时会重新设置作为对照的ad值 ，默认为true，会设置一次
    private boolean projectChange = false;//清零标志位 需要清零设为true，在解析数据时会重新设置作为对照的ad值 ，默认为true，会设置一次

    private int howmanysecond;//检测时长
    private int remainingtime;//剩余时间
    private String testmoudle;//检测模块 //1分光光度，2干式农残，3胶体金，4胶体金扫描 5 重金属 6真菌读数  7外接胶体金（只测试廋肉精三项）

    //分光光度属性
    private int wave1; //实时的波长
    private int wave2;
    private int wave3;
    private int wave4;

    private int dzh_wave1_start;//对照ad值，用于检测是否放入比色皿
    private int dzh_wave2_start;
    private int dzh_wave3_start;
    private int dzh_wave4_start;

    private int wave1_start;//开始检测时的波长
    private int wave2_start;
    private int wave3_start;
    private int wave4_start;

    private float luminousness1;//透光率1
    private float luminousness2;//透光率2
    private float luminousness3;//透光率3
    private float luminousness4;//透光率4


    private double absorbance1; //实时吸光度
    private double absorbance2;
    private double absorbance3;
    private double absorbance4;

    private double absorbance1_start;  //开始反应时吸光度 ,在点击开始检测时将当前吸光度设置给它即可
    private double absorbance2_start;
    private double absorbance3_start;
    private double absorbance4_start;

    private double absorbance1_after;  //反应后吸光度 ，计算结果时取当前的吸光度即可
    private double absorbance2_after;
    private double absorbance3_after;
    private double absorbance4_after;

    public BaseProjectMessage getmProjectMessage() {
        return mProjectMessage;
    }

    public void setmProjectMessage(BaseProjectMessage mProjectMessage) {
        this.mProjectMessage = mProjectMessage;
        ((TestRecord) this).setTest_project(mProjectMessage.getPjName());
        ((TestRecord) this).setTest_method(mProjectMessage.getMethod_sp() + "");
        ((TestRecord) this).setStand_num(mProjectMessage.getStandNum() + "");
        ((TestRecord) this).setCov_unit(mProjectMessage.getUnit_input() + "");
        ((TestRecord) this).setMethodsDetectionLimit(mProjectMessage.getmethodLimit() + "");

    }

    private BaseProjectMessage mProjectMessage;


    public UsbDevice getUsbDevice() {
        return mUsbDevice;
    }

    private UsbDevice mUsbDevice;
    private int mJTJModel;// 1=3500p扫描  2=3500p摄像头  3=v9000（6270，导轨+摄像头，外接胶体金模块
    private int mJTJCardModel = 0;// 默认为单卡 3 = 三连卡
    private UsbReadWriteHelper mJTJRWHelper;
    private Byte horizontal_d;
    private Byte vertical_d;
    private double[] JTJResultData;
    private double[] MYYGResultData;
    private List<double[]> JTJResultDatas;
    private double[] ZJSResultData;
    private int backgroundResous;
    private List<Float> mUserfullData;
    private List<Double> mUserfullData_myyg;
    private List<List<Float>> mUserfullDatas;
    private ScheduledThreadPoolExecutor mTimer;
    public boolean issend = false;
    public RunnableScheduledFuture<?> mRunnableScheduledFuture;
    public boolean checkd;
    /**
     * 自定义曲线需要设置浓度
     */
    private double mic;

    public double getMic() {
        return mic;
    }

    public void setMic(double mic) {
        this.mic = mic;
    }

    public boolean isCheckd() {
        return checkd;
    }

    public void setCheckd(boolean checkd) {
        this.checkd = checkd;
    }


    /**
     * 胶体金卡样式 3为三连卡 0为单卡
     *
     * @return
     */
    public int getJTJCardModel() {
        return mJTJCardModel;
    }

    /**
     * 胶体金卡样式 3为三连卡 0为单卡
     *
     * @param JTJCardModel
     */
    public void setJTJCardModel(int JTJCardModel) {
        mJTJCardModel = JTJCardModel;
    }

    public RunnableScheduledFuture<?> getRunnableScheduledFuture() {
        return mRunnableScheduledFuture;
    }

    public void setRunnableScheduledFuture(RunnableScheduledFuture<?> runnableScheduledFuture) {
        mRunnableScheduledFuture = runnableScheduledFuture;
    }


    public double[] getZJSResultData() {
        return ZJSResultData;
    }

    public void setZJSResultData(double[] ZJSResultData) {
        this.ZJSResultData = ZJSResultData;
    }

    public UsbReadWriteHelper getJTJRWHelper() {
        return mJTJRWHelper;
    }

    public Byte getHorizontal_d() {
        return horizontal_d;
    }

    public Byte getVertical_d() {
        return vertical_d;
    }

    public double[] getJTJResultData() {
        return JTJResultData;
    }

    public void setJTJResultData(double[] JTJResultData) {
        this.JTJResultData = JTJResultData;
    }

    public double[] getMYYGResultData() {
        return MYYGResultData;
    }

    public void setMYYGResultData(double[] MYYGesultData) {
        this.MYYGResultData = MYYGesultData;
    }

    public void setJTJResultDatas(List<double[]> JTJResultData) {
        this.JTJResultDatas = JTJResultData;
    }

    public List<double[]> getJTJResultDatas() {
        return JTJResultDatas;
    }

    public int getBackgroundResous() {
        return backgroundResous;
    }

    public void setBackgroundResous(int backgroundResous) {
        this.backgroundResous = backgroundResous;
    }

    public List<Float> getUserfullData() {
        return mUserfullData;
    }

    public void setUserfullData(List<Float> userfullData) {
        mUserfullData = userfullData;
    }

    public void setUserfullDatas(List<Float> userfullData) {
        if (null == mUserfullDatas) {
            mUserfullDatas = new ArrayList<>();
        }
        mUserfullDatas.add(userfullData);
    }

    public List<Double> getUserfullData_myyg() {
        return mUserfullData_myyg;
    }

    public void setUserfullData_myyg(List<Double> userfullData_myyg) {
        mUserfullData_myyg = userfullData_myyg;
    }

    public int getJTJ_MAC() {
        return JTJ_MAC;
    }

    public void setJTJ_MAC(int JTJ_MAC) {
        this.JTJ_MAC = JTJ_MAC;
        setGalleryNum(JTJ_MAC);
    }

    public int getMYYG_MAC() {
        return MYYG_MAC;
    }

    public void setMYYG_MAC(int MYYG_MAC) {
        this.MYYG_MAC = MYYG_MAC;
        setGalleryNum(MYYG_MAC);
    }

    /**
     * 1=3500p扫描  2=3500p摄像头  3=v9000（6270，导轨+摄像头，外接胶体金模块
     *
     * @return
     */
    public int getJTJModel() {
        return mJTJModel;
    }

    /**
     * 1=3500p扫描  2=3500p摄像头  3=v9000（6270，导轨+摄像头，外接胶体金模块
     *
     * @param JTJModel
     */
    public void setJTJModel(int JTJModel) {
        mJTJModel = JTJModel;
    }


    public void setUsbDevice(UsbDevice usbDevice) {
        mUsbDevice = usbDevice;
        int productId = usbDevice.getProductId();
        int vendorId = usbDevice.getVendorId();
        if (productId == Constants.MYPID_P && vendorId == Constants.MYVID_P) {
            setJTJModel(2);//设置模式 1为扫描2为摄像头
            setTestMoudle(4 + "");
        } else if (productId == Constants.MYPID && vendorId == Constants.MYVID) {
            setJTJModel(3);
            setTestMoudle(3 + "");
        }
        mJTJRWHelper = new UsbReadWriteHelper(mUsbDevice);
        mJTJRWHelper.setReciverListener(this);
    }


    /**
     * USB状态改变监听
     */
    private OnStatuChangeListener mUsbOnStatuChangeListener = new OnStatuChangeListener() {

        @Override
        public void onStatuChang(Status status) {
            LogUtils.d(status);

        }
    };


    public void removedata() {
        ((TestRecord) this).setSamplename(null);
        ((TestRecord) this).setSamplenum(null);
        ((TestRecord) this).setDilutionratio(1);
        ((TestRecord) this).setDowhat(1);
    }









    /*public void setProjectMessages(BaseProjectMessage baseProjectMessage1, BaseProjectMessage baseProjectMessage2, BaseProjectMessage baseProjectMessage3) {
       mProjectMessage1=baseProjectMessage1;
       mProjectMessage2=baseProjectMessage2;
       mProjectMessage3=baseProjectMessage3;
    }*/


    public interface onJTJResultRecive {
        /**
         * @param userfuldata 接收成功 针对扫描模块
         * @param data
         * @param galleryNum
         */
        void onReciverSuccess(List<Float> userfuldata, double[] data, int galleryNum);


        void onReciverSuccess(List<List<Float>> userfuldata, List<double[]> data);

        /**
         * @param bitmap 接收成功，针对摄像头模块
         */
        void onReciverSuccess(Bitmap bitmap, int gallery);

        /**
         * 接收失败
         */
        void onReciverfail();

        /**
         * @param timer 倒计时
         */
        void onTimer(int timer);

        /**
         * 通知刷新UI
         */
        void onRefrish();


    }


    protected WeakReference<onJTJResultRecive> mReciveWeakReference;


    public void setJTJResultReciverListener(onJTJResultRecive reciverListener) {
        //mJTJResultRecive = reciverListener;
        mReciveWeakReference = new WeakReference<>(reciverListener);
    }

    public void removeJTJResultReciverListener() {
        //mJTJResultRecive = null;
        mReciveWeakReference.clear();
    }








    /*
     * 干式农残
     * */

    //干式农残属性
    private int r; //实时rgb
    private int g;
    private int b;
    private int r_0s; //开始时rgb
    private int g_0s;
    private int b_0s;
    private int r_40s; //第40srgb
    private int g_40s;
    private int b_40s;
    private int r_480s; //第480s时rgb
    private int g_480s;
    private int b_480s;
    private int r_start; //开始检测时rgb
    private int g_start;
    private int b_start;

    public boolean checkState;


    public double getAbsorbance1_start() {
        return absorbance1_start;
    }

    public void setAbsorbance1_start(float absorbance1_start) {
        this.absorbance1_start = absorbance1_start;
    }

    public double getAbsorbance2_start() {
        return absorbance2_start;
    }

    public void setAbsorbance2_start(double absorbance2_start) {
        this.absorbance2_start = absorbance2_start;
    }

    public double getAbsorbance3_start() {
        return absorbance3_start;
    }

    public void setAbsorbance3_start(double absorbance3_start) {
        this.absorbance3_start = absorbance3_start;
    }

    public double getAbsorbance4_start() {
        return absorbance4_start;
    }

    public void setAbsorbance4_start(double absorbance4_start) {
        this.absorbance4_start = absorbance4_start;
    }

    public boolean isCheckState() {
        return checkState;
    }

    public void setCheckState(boolean checkState) {
        this.checkState = checkState;
    }

    /**
     * 模块编号
     *
     * @return
     */
    public int getGalleryNum() {
        return galleryNum;
    }

    /**
     * 模块编号
     *
     * @param galleryNum
     */
    public void setGalleryNum(int galleryNum) {
        this.galleryNum = galleryNum;
        ((TestRecord) this).setGallery(galleryNum);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDowhat() {
        return dowhat;
    }

    public void setDowhat(int dowhat) {
        this.dowhat = dowhat;
    }

    public boolean isClearn() {
        return clearn;
    }

    public void setClearn(boolean clearn) {
        this.clearn = clearn;
    }

    public int getHowmanysecond() {
        return howmanysecond;
    }

    public void setHowmanysecond(int howmanysecond) {
        this.howmanysecond = howmanysecond;
    }

    public int getRemainingtime() {
        //LogUtils.d(galleryNum+"---"+remainingtime);
        return remainingtime;
    }

    public void setRemainingtime(int remainingtime) {
        this.remainingtime = remainingtime;
    }

    public void startTest() {
        //if (this.mProjectMessage instanceof FGGDTestItem) {
        //FGGDTestItem message = (FGGDTestItem) mProjectMessage;
        int wavelength = mProjectMessage.getWavelength();
        switch (wavelength) {
            case 0:
                absorbance1_start = absorbance1;
                break;
            case 1:
                absorbance2_start = absorbance2;
                break;
            case 2:
                absorbance3_start = absorbance3;
                break;
            case 3:
                absorbance4_start = absorbance4;
                break;
        }
        // }
    }

    public void testFinished() {
        // if (this.mProjectMessage instanceof FGGDTestItem) {
        //FGGDTestItem message = (FGGDTestItem) mProjectMessage;
        int wavelength = mProjectMessage.getWavelength();
        switch (wavelength) {
            case 0:
                absorbance1_after = absorbance1;
                break;
            case 1:
                absorbance2_after = absorbance2;
                break;
            case 2:
                absorbance3_after = absorbance3;
                break;
            case 3:
                absorbance4_after = absorbance4;
                break;
        }

        // }
    }

    /**
     * 检测模块
     *
     * @param test_moudle
     */
    public void setTestMoudle(String test_moudle) {
        this.testmoudle = test_moudle;
        ((TestRecord) this).setTest_Moudle(test_moudle);
    }

    public int getWave1() {
        return wave1;
    }

    public void setWave1(int wave1) {
        this.wave1 = wave1;
    }

    public int getWave2() {
        return wave2;
    }

    public void setWave2(int wave2) {
        this.wave2 = wave2;
    }

    public int getWave3() {
        return wave3;
    }

    public void setWave3(int wave3) {
        this.wave3 = wave3;
    }

    public int getWave4() {
        return wave4;
    }

    public void setWave4(int wave4) {
        this.wave4 = wave4;
    }

    public int getDzh_wave1_start() {
        return dzh_wave1_start;
    }

    public void setDzh_wave1_start(int dzh_wave1_start) {
        this.dzh_wave1_start = dzh_wave1_start;
    }

    public int getDzh_wave2_start() {
        return dzh_wave2_start;
    }

    public void setDzh_wave2_start(int dzh_wave2_start) {
        this.dzh_wave2_start = dzh_wave2_start;
    }

    public int getDzh_wave3_start() {
        return dzh_wave3_start;
    }

    public void setDzh_wave3_start(int dzh_wave3_start) {
        this.dzh_wave3_start = dzh_wave3_start;
    }

    public int getDzh_wave4_start() {
        return dzh_wave4_start;
    }

    public void setDzh_wave4_start(int dzh_wave4_start) {
        this.dzh_wave4_start = dzh_wave4_start;
    }

    public int getWave1_start() {
        return wave1_start;
    }

    public void setWave1_start(int wave1_start) {
        this.wave1_start = wave1_start;
    }

    public int getWave2_start() {
        return wave2_start;
    }

    public void setWave2_start(int wave2_start) {
        this.wave2_start = wave2_start;
    }

    public int getWave3_start() {
        return wave3_start;
    }

    public void setWave3_start(int wave3_start) {
        this.wave3_start = wave3_start;
    }

    public int getWave4_start() {
        return wave4_start;
    }

    public void setWave4_start(int wave4_start) {
        this.wave4_start = wave4_start;
    }

    public float getLuminousness1() {
        return luminousness1;
    }

    public void setLuminousness1(float luminousness1) {
        this.luminousness1 = luminousness1;
    }

    public float getLuminousness2() {
        return luminousness2;
    }

    public void setLuminousness2(float luminousness2) {
        this.luminousness2 = luminousness2;
    }

    public float getLuminousness3() {
        return luminousness3;
    }

    public void setLuminousness3(float luminousness3) {
        this.luminousness3 = luminousness3;
    }

    public float getLuminousness4() {
        return luminousness4;
    }

    public void setLuminousness4(float luminousness4) {
        this.luminousness4 = luminousness4;
    }

    public double getAbsorbance1() {
        return absorbance1;
    }

    public void setAbsorbance1(double absorbance1) {
        this.absorbance1 = absorbance1;
    }

    public double getAbsorbance2() {
        return absorbance2;
    }

    public void setAbsorbance2(double absorbance2) {
        this.absorbance2 = absorbance2;
    }

    public double getAbsorbance3() {
        return absorbance3;
    }

    public void setAbsorbance3(double absorbance3) {
        this.absorbance3 = absorbance3;
    }

    public double getAbsorbance4() {
        return absorbance4;
    }

    public void setAbsorbance4(double absorbance4) {
        this.absorbance4 = absorbance4;
    }

    public double getAbsorbance1_after() {
        return absorbance1_after;
    }

    public void setAbsorbance1_after(double absorbance1_after) {
        this.absorbance1_after = absorbance1_after;
    }

    public double getAbsorbance2_after() {
        return absorbance2_after;
    }

    public void setAbsorbance2_after(double absorbance2_after) {
        this.absorbance2_after = absorbance2_after;
    }

    public double getAbsorbance3_after() {
        return absorbance3_after;
    }

    public void setAbsorbance3_after(double absorbance3_after) {
        this.absorbance3_after = absorbance3_after;
    }

    public double getAbsorbance4_after() {
        return absorbance4_after;
    }

    public void setAbsorbance4_after(double absorbance4_after) {
        this.absorbance4_after = absorbance4_after;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getR_0s() {
        return r_0s;
    }

    public void setR_0s(int r_0s) {
        this.r_0s = r_0s;
    }

    public int getG_0s() {
        return g_0s;
    }

    public void setG_0s(int g_0s) {
        this.g_0s = g_0s;
    }

    public int getB_0s() {
        return b_0s;
    }

    public void setB_0s(int b_0s) {
        this.b_0s = b_0s;
    }

    public int getR_40s() {
        return r_40s;
    }

    public void setR_40s(int r_40s) {
        this.r_40s = r_40s;
    }

    public int getG_40s() {
        return g_40s;
    }

    public void setG_40s(int g_40s) {
        this.g_40s = g_40s;
    }

    public int getB_40s() {
        return b_40s;
    }

    public void setB_40s(int b_40s) {
        this.b_40s = b_40s;
    }

    public int getR_480s() {
        return r_480s;
    }

    public void setR_480s(int r_480s) {
        this.r_480s = r_480s;
    }

    public int getG_480s() {
        return g_480s;
    }

    public void setG_480s(int g_480s) {
        this.g_480s = g_480s;
    }

    public int getB_480s() {
        return b_480s;
    }

    public void setB_480s(int b_480s) {
        this.b_480s = b_480s;
    }

    public int getR_start() {
        return r_start;
    }

    public void setR_start(int r_start) {
        this.r_start = r_start;
    }

    public int getG_start() {
        return g_start;
    }

    public void setG_start(int g_start) {
        this.g_start = g_start;
    }

    public int getB_start() {
        return b_start;
    }

    public void setB_start(int b_start) {
        this.b_start = b_start;
    }

    public boolean isProjectChange() {
        return projectChange;
    }

    public void setProjectChange(boolean projectChange) {
        this.projectChange = projectChange;
    }

    public void setAbsorbance1_start(double absorbance1_start) {
        this.absorbance1_start = absorbance1_start;
    }


    /**
     * 出卡
     */
    public void cardOut() {
        //stopJTJTest();
        if (mJTJModel == 1) {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_OUT_REQUEST_S, true);
        } else {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_OUT_REQUEST_P, true);
        }

    }

    /**
     * @param dealy 延迟多长时间出卡 1000 为1秒
     */
    public void cardOut(int dealy) {
        if (mJTJModel == 1) {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_OUT_REQUEST_S, true, dealy);
        } else {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_OUT_REQUEST_P, true, dealy);
        }

    }

    /**
     * 进卡不扫描
     */
    public void cardInNotScan() {
        if (mJTJModel == 1) {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_ENT_REQUEST_S, true);
        } else {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_ENT_REQUEST_P, true);
        }

    }

    /**
     * 获取摄像头参数
     */
    public void cardGet_Argmen() {
        if (mJTJModel == 1) {
            //mJTJRWHelper.sendMessage(Constants.COLLAURUM_ENT_REQUEST_S, true);
        } else {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_GET_ARGMENT, true);
        }

    }


    /**
     * @param dealy 延迟多长时间进卡 1000 为1秒
     */
    public void cardInNotScan(int dealy) {
        if (mJTJModel == 1) {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_ENT_REQUEST_S, true, dealy);
        } else {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_ENT_REQUEST_P, true, dealy);
        }

    }

    /**
     * 卡状态请求
     */
    public void getCardStatus() {
        if (mJTJModel == 1) {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_STATE_REQUEST_S, true);
        } else {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_STATE_REQUEST_P, true);
        }

    }


    /**
     * 读取模块编号
     */
    public void getCardNum() {
        if (mJTJModel == 1) {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_NUMBER_ASK_S, true);
        } else {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_NUMBER_ASK_P, true);
        }

    }


    /**
     * 设置模块编号
     */
    public void cardNumSeting(int num) {
        if (mJTJModel == 1) {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_NUMBER_SET_S(num), true);
        } else {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_NUMBER_SET_P(num), true);
        }

    }


    /**
     * 校准请求
     */
    public void cardAdjust() {
        if (mJTJModel == 1) {
            mJTJRWHelper.sendMessage(Constants.COLLAURUM_CALIBRATION_REQUEST, true);
        } else {
            // mJTJRWHelper.sendMessage(Constants.COLLAURUM_NUMBER_ASK_P,true,dealy);
        }

    }


    private List<Bitmap> mBitmapList;

    @Override
    public void reciver(byte[] bytes, int width, int height) {
        if (4 == mJTJModel) {
            mBitmapList = PictureToolUtils.byteToBitMapExternal(bytes, width, height);
        } else {
            mBitmapList = PictureToolUtils.byteToBitMap(bytes, width, height);
        }
        //更新UI
        if (mReciveWeakReference != null) {
            onJTJResultRecive recive = mReciveWeakReference.get();
            if (BuildConfig.DEBUG) {
                PictureToolUtils.isHaveCard(mBitmapList.get(0));
                if (recive != null) {
                    recive.onReciverSuccess(mBitmapList.get(0), galleryNum);
                }
                //mJTJResultRecive.onReciverSuccess(PictureToolUtils.drawRectFourcorners(mBitmapList.get(0)));
            } else {
                if (recive != null) {
                    recive.onReciverSuccess(mBitmapList.get(0), galleryNum);
                }
            }

        }
        //
    }

    @Override
    public void reciver(List<Byte> bytes) {
        //LogUtils.d(bytes);
        if (bytes.size() < 2) {
            LogUtils.d(bytes);
            return;
        }
        // LogUtils.d(bytes);
        byte aByte = bytes.get(1);//fla位，可根据它来判断是什么响应
        switch (aByte) {
            case 0x14: //卡状态响应
                LogUtils.d("卡状态响应" + bytes);
                if (bytes.get(4) == 0x01) { //有卡状态
                    // cardScanning();

                } else if (bytes.get(4) == 0x02) { //无卡状态
                    if (state == 1) { //倒计时状态
                        setState(0);
                    }
                    ArmsUtils.snackbarText("没有检测到胶体金卡条，请放入卡条后重试");
                }

                break;
            case 0x1c://获取模块编号响应
                LogUtils.d(bytes);
                this.setJTJ_MAC(bytes.get(4));
                break;
            case 0x1A:
                LogUtils.d(bytes);
                break;
            case 0x16://检测数据响应
                // TODO: 2020/9/5 这里需要注意下：一定要区分胶体金的当前检测模块，在摄像头模块进行校准后，
                //  再返回检测界面会触发扫描模块的计算结果代码，
                //  原因就是在校准完成，关闭校准界面时候会发送进卡命令，
                //  发送命令的代码会有一个读取返回的操作，读取到的结果会有上一次的摄像头模块请求数据的返回，那个返回也是0x7e，0x16开头，
                //  然后返回到监听，也就是这个位置，就触发了这个计算结果的代码


                LogUtils.d("扫描检测数据响应");
                if (1 == mJTJModel) {
                    // checkData_S(bytes);
                }
                break;
            case 0x21: //获取摄像头参数 左右 上下
                horizontal_d = bytes.get(4);
                vertical_d = bytes.get(5);
                break;
            case 0x1E:
                //cardGet_Argmen();
                break;
            case 0x18:
                LogUtils.d("0x18 " + bytes);
                if (1 == mJTJModel) {
                    cardOut(15000);
                }

                break;
        }

    }


    @Override
    public String toString() {
        return "GalleryBean{" +
                "galleryNum=" + galleryNum +
                ", JTJ_MAC=" + JTJ_MAC +
                ", MYYG_MAC=" + MYYG_MAC +
                ", state=" + state +
                ", dowhat=" + dowhat +
                ", clearn=" + clearn +
                ", projectChange=" + projectChange +
                ", howmanysecond=" + howmanysecond +
                ", remainingtime=" + remainingtime +
                ", testmoudle='" + testmoudle + '\'' +
                ", wave1=" + wave1 +
                ", wave2=" + wave2 +
                ", wave3=" + wave3 +
                ", wave4=" + wave4 +
                ", dzh_wave1_start=" + dzh_wave1_start +
                ", dzh_wave2_start=" + dzh_wave2_start +
                ", dzh_wave3_start=" + dzh_wave3_start +
                ", dzh_wave4_start=" + dzh_wave4_start +
                ", wave1_start=" + wave1_start +
                ", wave2_start=" + wave2_start +
                ", wave3_start=" + wave3_start +
                ", wave4_start=" + wave4_start +
                ", luminousness1=" + luminousness1 +
                ", luminousness2=" + luminousness2 +
                ", luminousness3=" + luminousness3 +
                ", luminousness4=" + luminousness4 +
                ", absorbance1=" + absorbance1 +
                ", absorbance2=" + absorbance2 +
                ", absorbance3=" + absorbance3 +
                ", absorbance4=" + absorbance4 +
                ", absorbance1_start=" + absorbance1_start +
                ", absorbance2_start=" + absorbance2_start +
                ", absorbance3_start=" + absorbance3_start +
                ", absorbance4_start=" + absorbance4_start +
                ", absorbance1_after=" + absorbance1_after +
                ", absorbance2_after=" + absorbance2_after +
                ", absorbance3_after=" + absorbance3_after +
                ", absorbance4_after=" + absorbance4_after +
                ", mProjectMessage=" + mProjectMessage +
                ", mUsbDevice=" + mUsbDevice +
                ", mJTJModel=" + mJTJModel +
                ", mJTJCardModel=" + mJTJCardModel +
                ", mJTJRWHelper=" + mJTJRWHelper +
                ", horizontal_d=" + horizontal_d +
                ", vertical_d=" + vertical_d +
                ", JTJResultData=" + Arrays.toString(JTJResultData) +
                ", MYYGResultData=" + Arrays.toString(MYYGResultData) +
                ", JTJResultDatas=" + JTJResultDatas +
                ", ZJSResultData=" + Arrays.toString(ZJSResultData) +
                ", backgroundResous=" + backgroundResous +
                ", mUserfullData=" + mUserfullData +
                ", mUserfullData_myyg=" + mUserfullData_myyg +
                ", mUserfullDatas=" + mUserfullDatas +
                ", mTimer=" + mTimer +
                ", issend=" + issend +
                ", mRunnableScheduledFuture=" + mRunnableScheduledFuture +
                ", checkd=" + checkd +
                ", mUsbOnStatuChangeListener=" + mUsbOnStatuChangeListener +
                ", mReciveWeakReference=" + mReciveWeakReference +
                ", r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", r_0s=" + r_0s +
                ", g_0s=" + g_0s +
                ", b_0s=" + b_0s +
                ", r_40s=" + r_40s +
                ", g_40s=" + g_40s +
                ", b_40s=" + b_40s +
                ", r_480s=" + r_480s +
                ", g_480s=" + g_480s +
                ", b_480s=" + b_480s +
                ", r_start=" + r_start +
                ", g_start=" + g_start +
                ", b_start=" + b_start +
                ", checkState=" + checkState +
                ", mBitmapList=" + mBitmapList +
                '}';
    }

    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void checkData_P() {
        LogUtils.d("checkData_P");
        Bitmap bitmap1 = mBitmapList.get(1);

        List<List<Float>> lists = PictureToolUtils.bitmap2RGB_list(bitmap1);


        List<Float> userfuldata = lists.get(1);
        //设置可用数据到bean
        setUserfullData(userfuldata);
        //设置检测状态
        setState(2);
        //计算检测结果 耗时操作，需要在子线程运行

        double[] data = getData_P(userfuldata);
        LogUtils.d(data);
        setJTJResultData(data);
        //判断和保存检测结果
        judgeAndSaveZJDSData(data, bitmap);
        //将数据回调给 view做更新
        if (mReciveWeakReference != null) {
            onJTJResultRecive recive = mReciveWeakReference.get();
            if (recive != null) {
                recive.onReciverSuccess(userfuldata, data,galleryNum);
                recive.onReciverSuccess(bitmap1,galleryNum);
            }
        }
    }

    private double[] getData_P(List<Float> bytes) {

        return new JTJDataModel_P(bytes).getData_S();

    }

    protected void judgeAndSaveZJDSData(double[] data, Bitmap bitmap) {
        // LogUtils.d(data);
        TestRecord detection_record_fggd_nc = (TestRecord) this;
        //JTJTestItem message = (JTJTestItem) mProjectMessage;
        //根据胶体金检测项目的相关参数 判定是否合格
        int method = mProjectMessage.getMethod_sp();
        //根据通道号来取不同的参数（其实两个参数都是一样的，防止通道偏差造成结果不准）
        int i = JTJ_MAC % 2;
        double mCValue = 0;
        double mTValueA = 0;
        double mTValueB = 0;
        double mCTValueA = 0;
        double mCTValueB = 0;
        ProjectJTJ projectJTJ = (ProjectJTJ) this.mProjectMessage;
        mCValue=projectJTJ.getC();
        mTValueA=projectJTJ.gettA();
        mTValueB=projectJTJ.getTB();
        mCTValueA=projectJTJ.getC_tA();
        mCTValueB=projectJTJ.getC_tB();
        LogUtils.d(method);
        //检测结果 结论
        if (method == 0) {//消线法

            if (data[1] <= mCValue) { //无c线
                //无效卡
                if (data[3] <= mTValueA) {  //无c无t线
                    detection_record_fggd_nc.setTestresult("无效");
                    detection_record_fggd_nc.setDecisionoutcome("无效");
                    backgroundResous = 1;
                } else { //无c 有t线
                    detection_record_fggd_nc.setTestresult("无效");
                    detection_record_fggd_nc.setDecisionoutcome("无效");
                    backgroundResous = 2;
                }
            } else {//有c线/  判断t线

                if (data[3] <= mTValueA) {//有c无t线 /阳性
                    detection_record_fggd_nc.setTestresult("阳性");
                    detection_record_fggd_nc.setDecisionoutcome("不合格");
                    backgroundResous = 3;
                } else if (data[3] >= mTValueB) {//有c 有t线 阴性y
                    detection_record_fggd_nc.setTestresult("阴性");
                    detection_record_fggd_nc.setDecisionoutcome("合格");
                    backgroundResous = 4;
                } else {
                    detection_record_fggd_nc.setTestresult("可疑");
                    detection_record_fggd_nc.setDecisionoutcome("可疑");
                    backgroundResous = 7;
                }
            }

        } else if (method == 1) {//比色法

            if (data[1] <= mCValue) { //无c线
                //无效卡
                if (data[3] <= mTValueA) {  //无c无t线
                    detection_record_fggd_nc.setTestresult("无效");
                    detection_record_fggd_nc.setDecisionoutcome("无效");
                    backgroundResous = 1;
                } else { //无c 有t线
                    detection_record_fggd_nc.setTestresult("无效");
                    detection_record_fggd_nc.setDecisionoutcome("无效");
                    backgroundResous = 2;
                }

            } else {
                if (data[3] / data[1] <= mCTValueA) {  //阳性
                    detection_record_fggd_nc.setTestresult("阳性");
                    detection_record_fggd_nc.setDecisionoutcome("不合格");
                    backgroundResous = 5;
                } else if (data[3] / data[1] >= mCTValueB) {  //阴性
                    detection_record_fggd_nc.setTestresult("阴性");
                    detection_record_fggd_nc.setDecisionoutcome("合格");
                    backgroundResous = 6;
                } else {
                    detection_record_fggd_nc.setTestresult("可疑");
                    detection_record_fggd_nc.setDecisionoutcome("可疑");
                    backgroundResous = 8;
                }
            }


        }

        //检测完成时间
        detection_record_fggd_nc.setTestingtime(System.currentTimeMillis());
        //检测地点
        detection_record_fggd_nc.setTestsite(Constants.ADDR_WF);
        detection_record_fggd_nc.setLatitude(Constants.LATITUDE);
        detection_record_fggd_nc.setLongitude(Constants.LONTITUDE);
        //当前平台
        //对照值
        //检测人员   这里填的是本地登录的账号名称
        //detection_record_fggd_nc.setInspector(Constants.NOWUSER.getUsername());
        //设置检测模块
        detection_record_fggd_nc.setTest_Moudle(ArmsUtils.getString(MyAppLocation.myAppLocation, R.string.JTJ_TestMoudle_P));
        // 保存至数据库
        detection_record_fggd_nc.setId(null);//自增ID
        //设置状态为检测完成 2
        detection_record_fggd_nc.setState(2);
        //detection_record_fggd_nc.setDowhat(0);
        if (MyAppLocation.myAppLocation.mExamOperationService.isStartExamOperation()){
            detection_record_fggd_nc.setExam_id(MyAppLocation.myAppLocation.mExamOperationService.getNowOperationExam().getId());
            detection_record_fggd_nc.setExaminerId(MyAppLocation.myAppLocation.mExamOperationService.getExaminerId());
            detection_record_fggd_nc.setExaminationId(MyAppLocation.myAppLocation.mExamOperationService.getExaminationId());
            DBHelper.getTestRecordDao().insert(detection_record_fggd_nc);
            UploadService.startUpLoad(MyAppLocation.myAppLocation,detection_record_fggd_nc.getSysCode());
        }else {

            DBHelper.getTestRecordDao().insert(detection_record_fggd_nc);
        }
        StringBuilder builder = new StringBuilder(); //使用线程安全的StringBuffer
        for (int i1 = 0; i1 < mUserfullData.size(); i1++) {
            builder.append(mUserfullData.get(i1) + ",");
        }
        JTJPoint entity = new JTJPoint();
        entity.setId(null);
        entity.setPointData(builder.toString());
        entity.setUuid(detection_record_fggd_nc.getSysCode());
        long insert = DBHelper.getJTJPointDao().insert(entity);
        // FileUtils.saveBitmaplevel1(mBitmapList.get(0), detection_record_fggd_nc.getSysCode());
        FileUtils.saveBitmaplevel2(bitmap, detection_record_fggd_nc.getSysCode());
        //printAndUpload(detection_record_fggd_nc, insert);

    }
}
