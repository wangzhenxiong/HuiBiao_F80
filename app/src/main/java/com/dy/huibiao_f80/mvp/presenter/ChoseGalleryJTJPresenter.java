package com.dy.huibiao_f80.mvp.presenter;

import android.app.AlertDialog;
import android.app.Application;
import android.hardware.usb.UsbDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.mvp.contract.ChoseGalleryJTJContract;
import com.dy.huibiao_f80.usbhelps.UsbControl;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class ChoseGalleryJTJPresenter extends BasePresenter<ChoseGalleryJTJContract.Model, ChoseGalleryJTJContract.View> implements UsbControl.onUsbInitFinshed {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    AlertDialog mAlertDialog;
    private List<UsbDevice> mDeviceList;
    @Inject
    public ChoseGalleryJTJPresenter(ChoseGalleryJTJContract.Model model, ChoseGalleryJTJContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void initJTJUSB() {
        mRootView.showSportDialog("模块初始化");
        Constants.mControl = new UsbControl();
        Constants.mControl.setUsbInitStateListener(this);
        Constants.mControl.initUsb();
    }

    @Override
    public void successed(List<UsbDevice> deviceList) {
        mDeviceList = deviceList;
        LogUtils.d(mDeviceList);
        //回调函数不运行在UI线程？？
        mRootView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initChartView();
            }
        });
    }

    private void initChartView() {
        //得到有权限 可以直接通信的usblist
        //新建胶体金bean 并将USBdevice和bean绑定
        if (MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.size() == 0) { //首次进入胶体金检测页面，通道还都为空
            for (int i = 0; i < mDeviceList.size(); i++) {
                UsbDevice device = mDeviceList.get(i);
                GalleryBean bean = new TestRecord();//新建通道benan
                bean.setUsbDevice(device);//通道bean与USBdevice绑定

                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.add(bean);
            }
            //检查胶体金模块编号信息是否正确
            checkGalleryNumFirstTime();

        } else {
            //可能出现一种情况 usb设备掉了 之前有四个 后面再进来就只有两个了 ？？？
            //这里还有一个问题 ，比如 现在有两个设备，连接的时候拔插了下（掉电又上电）这种情况 是不会触发重新初始化usb
            // 从而导致该usb一直打开失败，(bean里面绑定的那个USBdevice已经不是那个了 ，usb重新上电会有不一样的编号 )
            //解决办法，1.重启软件 2.先拔掉一个usb，进入胶体金界面 退出 再插上 再进入 让程序触发重新初始化usb
            if (mDeviceList.size() != MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.size()) {
                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.clear();
                initChartView();
                return;
            }
            mRootView.hideSportDialog();
            mRootView.buildGalleryView();//初始化通道view
        }
    }

    @Override
    public void failure(String s) {
        mRootView.hideSportDialog();
        ArmsUtils.snackbarText(s);
        mRootView.killMyself();
    }
    /**
     * 检查通道编号是否正确  模块读取编号 与gallerybean的编号需要对应上
     */
    private void checkGalleryNumFirstTime() {
        //读取模块编号，设置到bean
        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.size(); i++) {
            GalleryBean bean = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(i);
            bean.getCardNum();
        }
        //可能需要延时一点时间 来等硬件接收返回数据
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int size = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.size();
                for (int i = 0; i < size; i++) {
                    GalleryBean bean = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(i);
                    int mac = bean.getJTJ_MAC();
                    if (mac == -2) { //如果有为-2的说明还没有读取到胶体金模块的编号
                        LogUtils.d("没收到返回的Macnum");
                        checkGalleryNumFirstTime();
                        return;
                    }
                }
                //排序算法，冒泡排序
                BubbleSort1(MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList);
                //LogUtils.d(MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList);
                mRootView.hideSportDialog();
                mRootView.buildGalleryView();


            }
        }, 1000);

        //--判断模块编号是否合理，是不是按照顺序排列 比如1234
        //--考虑，如果刚开始是4个模块，中途掉了一个 顺序变成 234，134，124，或者 掉两个 13，24怎么办？
        //解决，只需要编号是从小到大排列即可，在让用户判断通道时加上让用户手动输入即可
    }

    public static void BubbleSort1(List<GalleryBean> arr) {

        GalleryBean temp;//临时变量
        boolean flag;//是否交换的标志
        for (int i = 0; i < arr.size() - 1; i++) {   //表示趟数，一共 arr.length-1 次

            // 每次遍历标志位都要先置为false，才能判断后面的元素是否发生了交换
            flag = false;

            for (int j = arr.size() - 1; j > i; j--) { //选出该趟排序的最大值往后移动

                if (arr.get(j).getJTJ_MAC() < arr.get(j - 1).getJTJ_MAC()) {
                    temp = arr.get(j);
                    arr.set(j, arr.get(j - 1));
                    //arr.get(j) = arr.get(j-1);
                    arr.set(j - 1, temp);
                    flag = true;    //只要有发生了交换，flag就置为true
                }
            }
            // 判断标志位是否为false，如果为false，说明后面的元素已经有序，就直接return
            if (!flag) {
                break;
            }
        }
    }

    public void changeGallery() {
        //先判断是否有通道在检测
        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.size(); i++) {
            GalleryBean bean = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(i);
            if (bean.getState() == 1) {
                ArmsUtils.snackbarText("有通道正在检测中，请稍后");
                return;
            }
        }
        // 全部通道进卡
        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.size(); i++) {
            GalleryBean bean = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(i);
            bean.cardInNotScan();
        }
        //按目前的排列顺序出卡，让用户手动输入通道编号 然后设置给模块
        makeDialogSetGalleryNum();
    }

    private void makeDialogSetGalleryNum() {
        if (MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.size() <= 0) {
            mRootView.reBuildGalleryView();
            return;
        }
        GalleryBean bean = MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.get(0);
        bean.cardOut();

        AlertDialog builder = new AlertDialog.Builder(mRootView.getActivity()).create();
        View view = LayoutInflater.from(mRootView.getActivity()).inflate(R.layout.dialog_jtjsetgallerynum_layout, null);
        EditText editText = (EditText) view.findViewById(R.id.dialog_jtjnum);
        view.findViewById(R.id.dialog_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if ("".equals(s)) {
                    ArmsUtils.snackbarText("请输入编号");
                    return;
                }
                int i = Integer.parseInt(s);
                if (i > mDeviceList.size()) {
                    ArmsUtils.snackbarText("输入编号大于实际通道数");
                    return;
                }
                bean.cardNumSeting(i);
                //bean.cardInNotScan(500);
                bean.cardInNotScan();

                MyAppLocation.myAppLocation.mSerialDataService.mJTJGalleryBeanList.remove(bean);
                builder.dismiss();
                makeDialogSetGalleryNum();
            }
        });

        builder.setTitle("请输入当前通道的编号");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(false);
        builder.setView(view);
        builder.show();
    }
}