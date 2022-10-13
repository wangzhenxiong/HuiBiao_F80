package com.dy.huibiao_f80;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.multidex.MultiDex;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.app.service.SerialDataService;
import com.dy.huibiao_f80.crash.CaocConfig;
import com.dy.huibiao_f80.crash.CustomActivityOnCrash;
import com.dy.huibiao_f80.mvp.ui.activity.HomeActivity;
import com.jess.arms.base.BaseApplication;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.service.PosprinterService;

import java.util.Iterator;
import java.util.Locale;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * <p>
 * Created by wangzhenxiong on 2019/3/4.
 */
public class MyAppLocation extends BaseApplication implements TextToSpeech.OnInitListener {
    public static MyAppLocation myAppLocation = null;
    public SerialDataService mSerialDataService;
    public IMyBinder mPrintCertificateOfConformityBinder;
    private Intent mIntent;
    private Intent mIntent1;
    private Intent mIntent2;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mSerialDataService = ((SerialDataService.MyBinder) service).getService();
            if (BuildConfig.DEBUG){
                mSerialDataService.startFGGDSendthread();
                mSerialDataService.startGSNCSendthread();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d("SerialDataService启动失败");
        }
    };


    private ServiceConnection mConnection2 = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //绑定成功
            mPrintCertificateOfConformityBinder = (IMyBinder) service;
            LogUtils.d("PosprinterService启动成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d("PosprinterService启动失败");
        }
    };
    private TextToSpeech mTextToSpeech;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化全局异常崩溃
        if (BuildConfig.INITCRASH) {
            initCrash();
        }
        myAppLocation = this;
        mIntent = new Intent(this, SerialDataService.class);
        mIntent2 = new Intent(this, PosprinterService.class);
        LogUtils.d(isAppProcess());
        if (isAppProcess()) {
            bindService(mIntent, mConnection, BIND_AUTO_CREATE);
            bindService(mIntent2, mConnection2, BIND_AUTO_CREATE);
            initTextToSpeech();
        }




    }

    /**
     * 1普通话 2粤语
     */
    public int languagetype = 1;

    /**
     * 文字转语音
     */
    public void initTextToSpeech() {
        if (null != mTextToSpeech) {
            mTextToSpeech.stop();
            mTextToSpeech = null;
        }
        // 参数Context,TextToSpeech.OnInitListener
        mTextToSpeech = new TextToSpeech(this, this, languagetype == 1 ? "com.google.android.tts" : "com.iflytek.speechcloud");
        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        mTextToSpeech.setPitch(1.0f);
        // 设置语速
        mTextToSpeech.setSpeechRate(1.0f);

    }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.drawable.error_icn) //错误图标
                .restartActivity(HomeActivity.class) //重新启动后的activity
                //.errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
                //.eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
        CustomActivityOnCrash.install(this);
    }

    /**
     * 判断该进程是否是app进程
     *
     * @return
     */
    public boolean isAppProcess() {
        String processName = getThisProcessName();
        if (processName == null || !processName.equalsIgnoreCase(this.getPackageName())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取运行该方法的进程的进程名
     *
     * @return 进程名称
     */
    public String getThisProcessName() {
        int processId = android.os.Process.myPid();
        String processName = null;
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        Iterator iterator = manager.getRunningAppProcesses().iterator();
        while (iterator.hasNext()) {
            ActivityManager.RunningAppProcessInfo processInfo = (ActivityManager.RunningAppProcessInfo) (iterator.next());
            try {
                if (processInfo.pid == processId) {
                    processName = processInfo.processName;
                    return processName;
                }
            } catch (Exception e) {
//                LogD(e.getMessage())
            }
        }
        return processName;
    }

    public void speak(String paramString) {

        LogUtils.d("speak");
        if (null == mTextToSpeech) {
            return;
        }
        if (mTextToSpeech.isSpeaking()) {
            mTextToSpeech.stop();
        }
        if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
            /*
                TextToSpeech的speak方法有两个重载。
                // 执行朗读的方法
                speak(CharSequence text,int queueMode,Bundle params,String utteranceId);
                // 将朗读的的声音记录成音频文件
                synthesizeToFile(CharSequence text,Bundle params,File file,String utteranceId);
                第二个参数queueMode用于指定发音队列模式，两种模式选择
                （1）TextToSpeech.QUEUE_FLUSH：该模式下在有新任务时候会清除当前语音任务，执行新的语音任务
                （2）TextToSpeech.QUEUE_ADD：该模式下会把新的语音任务放到语音任务之后，
                等前面的语音任务执行完了才会执行新的语音任务
             */
            mTextToSpeech.speak(paramString, TextToSpeech.QUEUE_FLUSH, null);
            LogUtils.d("speak");
        }
    }


    public void onStop() {
        // 不管是否正在朗读TTS都被打断
        mTextToSpeech.stop();
        // 关闭，释放资源
        mTextToSpeech.shutdown();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            /*
                使用的是小米手机进行测试，打开设置，在系统和设备列表项中找到更多设置，
            点击进入更多设置，在点击进入语言和输入法，见语言项列表，点击文字转语音（TTS）输出，
            首选引擎项有三项为Pico TTs，科大讯飞语音引擎3.0，度秘语音引擎3.0。其中Pico TTS不支持
            中文语言状态。其他两项支持中文。选择科大讯飞语音引擎3.0。进行测试。

                如果自己的测试机里面没有可以读取中文的引擎，
            那么不要紧，我在该Module包中放了一个科大讯飞语音引擎3.0.apk，将该引擎进行安装后，进入到
            系统设置中，找到文字转语音（TTS）输出，将引擎修改为科大讯飞语音引擎3.0即可。重新启动测试
            Demo即可体验到文字转中文语言。
             */
            // setLanguage设置语言
            int result;
            result = mTextToSpeech.setLanguage(Locale.CHINA);
            /*if (languagetype == 1) {
            } else {
                Locale locale = new Locale("yue", "HKG");
                result = mTextToSpeech.setLanguage(locale);
            }*/


            // TextToSpeech.LANG_MISSING_DATA：表示语言的数据丢失 -1
            // TextToSpeech.LANG_NOT_SUPPORTED：不支持
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                LogUtils.d("表示语言的数据丢失或者不支持" + result);
            }
        }
    }
}
