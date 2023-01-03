package com.dy.huibiao_f80.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.MyAppLocation;

/**
 * 　 ┏┓　  ┏┓+ +
 * 　┏┛┻━━ ━┛┻┓ + +
 * 　┃　　　　 ┃
 * 　┃　　　　 ┃  ++ + + +
 * 　┃████━████+
 * 　┃　　　　 ┃ +
 * 　┃　　┻　  ┃
 * 　┃　　　　 ┃ + +
 * 　┗━┓　  ┏━┛
 * 　  ┃　　┃
 * 　  ┃　　┃　　 + + +
 * 　  ┃　　┃
 * 　  ┃　　┃ + 神兽保佑,代码无bug
 * 　  ┃　　┃
 * 　  ┃　　┃　　+
 * 　  ┃　 　┗━━━┓ + +
 * 　　┃ 　　　　 ┣┓
 * 　　┃ 　　　 ┏┛
 * 　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　 ┃┫┫ ┃┫┫
 * 　　 ┗┻┛ ┗┻┛+ + + +
 *
 * @author: wangzhenxiong
 * @data: 12/29/22 4:22 PM
 * Description:
 */
public class NetworkUtils {
    public static boolean getNetworkType(){
        ConnectivityManager mConnectivity = (ConnectivityManager) MyAppLocation.myAppLocation.getSystemService(Context.CONNECTIVITY_SERVICE);
        LogUtils.d(mConnectivity);
        //检查网络链接
       // NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        //LogUtils.d(info);
        NetworkInfo activeNetworkInfo = mConnectivity.getActiveNetworkInfo();
        if (null==activeNetworkInfo){
            LogUtils.d("当前没有网络连接 isConnected = 无");
            return false;
        }
        int netType = activeNetworkInfo.getType();
        LogUtils.d(netType);
        if (netType == ConnectivityManager.TYPE_WIFI) {  //WIFI
            LogUtils.d("当前是WIFI连接 isConnected = WIFI");
            return true;
        } else if (netType == ConnectivityManager.TYPE_MOBILE) {   //MOBILE
            LogUtils.d("当前是手机网络连接 isConnected =MOBILE ");
            return true;
        } else {
            LogUtils.d("当前没有网络连接 isConnected = 无");
            return false;
        }
    }
}
