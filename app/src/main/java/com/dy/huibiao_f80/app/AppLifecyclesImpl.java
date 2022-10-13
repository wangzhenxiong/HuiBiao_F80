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
package com.dy.huibiao_f80.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;
import com.baidu.mapapi.SDKInitializer;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.Constants;
import com.inuker.bluetooth.library.BluetoothContext;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import butterknife.ButterKnife;

/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AppLifecyclesImpl implements AppLifecycles {

    @Override
    public void attachBaseContext(@NonNull Context base) {
        //super.attachBaseContext(base);
        // MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化

    }

    @Override
    public void onCreate(@NonNull Application application) {

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        SDKInitializer.initialize(application.getApplicationContext());
        //
        if ("com.wangzx.dy.sample_DY6310".equals(BuildConfig.APPLICATION_ID)) {
            BluetoothContext.set(application);
        }

        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        if (BuildConfig.LOG_DEBUG) {//Timber初始化
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
//            Timber.plant(new Timber.DebugTree());
            // 如果你想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展
//                    Logger.addLogAdapter(new AndroidLogAdapter());
//                    Timber.plant(new Timber.DebugTree() {
//                        @Override
//                        protected void log(int priority, String tag, String message, Throwable t) {
//                            Logger.log(priority, tag, message, t);
//                        }
//                    });
            LogUtils.getLogConfig()
                    .configAllowLog(true)
                    .configTagPrefix("RE_ZXYTJ")
                    .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
                    .configShowBorders(true)
//                .configMethodOffset(1)
//                .addParserClass(OkHttpResponseParse.class)
                    .configLevel(LogLevel.TYPE_VERBOSE);
            ButterKnife.setDebug(true);


        } else {
            LogUtils.getLogConfig()
                    .configAllowLog(false)
                    .configTagPrefix("RE_ZXYTJ")
                    .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
                    .configShowBorders(false)
                    .configLevel(LogLevel.TYPE_VERBOSE);
            ButterKnife.setDebug(false);
        }

        //LeakCanary 内存泄露检查
        //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
        //否则存储在 LRU 算法的存储空间中, 前提是 extras 使用的是 IntelligentCache (框架默认使用)
        ArmsUtils.obtainAppComponentFromContext(application).extras()
                .put(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName())
                        , BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);


         /*
         * TextView imageView = new TextView(application);
        imageView.setText("测试");*/

        /*FloatWindow
                .with(application)
                .setView(imageView)
                .setWidth(100)                               //设置控件宽高
                .setHeight(Screen.width,0.2f)
                .setX(100)                                   //设置控件初始位置
                .setY(Screen.height,0.3f)
                .setDesktopShow(false)                        //桌面显示
                .setViewStateListener(mViewStateListener)    //监听悬浮控件状态改变
                .setPermissionListener(mPermissionListener)  //监听权限申请结果
                .setMoveType(MoveType.slide)
                .setFilter(true, HomeActivity.class, JTJ_TestActivity.class)
                .setMoveStyle(500, new AccelerateInterpolator())  //贴边动画时长为500ms，加速插值器
                .build();*/

        Constants.init(application);


        //opencv库初始化
        BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(application) {
            @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS: {

                    }
                    break;
                    default: {
                        super.onManagerConnected(status);
                    }
                    break;
                }
            }
        };
        if (!OpenCVLoader.initDebug()) {
            LogUtils.d( "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, application, mLoaderCallback);
        } else {
            LogUtils.d( "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

    }




    /**
     * @param application 只会在虚拟环境下才会调用该方法，也就是说真机永远不会调用，所以这个方法可以说是无用
     */
    @Override
    public void onTerminate(@NonNull Application application) {
        // application.unbindService(mConnection);
    }
}
