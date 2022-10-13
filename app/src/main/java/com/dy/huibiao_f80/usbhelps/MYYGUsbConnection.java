package com.dy.huibiao_f80.usbhelps;

import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.app.utils.ByteUtils;
import com.dy.huibiao_f80.usbhelps.listeners.OnDataReciverListener;
import com.dy.huibiao_f80.usbhelps.listeners.OnStatuChangeListener;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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
 * @data: 6/18/21 1:47 PM
 * Description:
 */
public class MYYGUsbConnection implements Connector {
    /**
     * 免疫荧光
     */
    public byte[] mReciverData;
    private OnDataReciverListener mDataReciverListener;
    private OnStatuChangeListener mStatuChangeListener;
    private boolean mConnectionStatu;
    private UsbSerialDriver mUsbSerialDriver;
    private UsbSerialPort mPorts;
    private UsbManager mUsbManager;
    private SerialInputOutputManager mSerialIoManager;
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    public MYYGUsbConnection(UsbSerialDriver usbSerialDriver, UsbManager usbManager, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        mUsbManager = usbManager;
        mUsbSerialDriver = usbSerialDriver;
        mScheduledThreadPoolExecutor = scheduledThreadPoolExecutor;
        connection();
    }

    @Override
    public void setOnDataReciverListener(OnDataReciverListener onDataReciverListener) {
        mDataReciverListener = onDataReciverListener;
    }

    @Override
    public void setOnStatuChangeListener(OnStatuChangeListener onStatuChangeListener) {
        mStatuChangeListener = onStatuChangeListener;
    }

    @Override
    public void connection() {
        openUSB();
    }

    @Override
    public void disConnection() {
        mConnectionStatu = false;
        stopIoManager();
        if (null != mStatuChangeListener) {
            Status status = new Status(Status.MyEvent.USBDISCONNECT);
            status.setMsg("USB已断开连接");
            mStatuChangeListener.onStatuChang(status);
        }
    }

    @Override
    public boolean getStatu() {
        return mConnectionStatu;
    }

    /**
     * 返回写入数据长度不是准确的
     * 请慎重使用
     *
     * @param bytes
     * @return
     */
    @Override
    public int writeData(byte[] bytes) {
        mReciverData = null;
        try {
            mPorts.write(bytes, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes.length;
    }

    private Runnable runnableOpenUSB;

    public void openUSB() {
        runnableOpenUSB = new Runnable() {
            @Override
            public void run() {
                // LogUtils.d("通过Binder得到Service的引用来调用Service内部的方法");

                UsbDevice mdevice = null;
                List<UsbSerialPort> ports = mUsbSerialDriver.getPorts();
                LogUtils.d(ports);
                mPorts = ports.get(0);
                mdevice = mPorts.getDriver().getDevice();
                if (mdevice != null && mPorts != null) {

                    if (!mUsbManager.hasPermission(mdevice)) {
                        PendingIntent mPermissionIntent1 = PendingIntent.getBroadcast(MyAppLocation.myAppLocation, 0, new Intent("com.android.example.USB_PERMISSION"), 0);
                        mUsbManager.requestPermission(mdevice, mPermissionIntent1);
                        LogUtils.d("请求权限");
                        openUSB();
                    } else {
                        UsbDevice device = mPorts.getDriver().getDevice();
                        LogUtils.d(device);
                        UsbDeviceConnection connection = mUsbManager.openDevice(device);
                        //LogUtils.d(connection);
                        if (connection == null) {
                            return;
                        }
                        try {
                            mPorts.open(connection);
                            mPorts.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
                            mConnectionStatu = true;
                            if (null != mStatuChangeListener) {
                                Status status = new Status(Status.MyEvent.USBCONNECTED);
                                status.setMsg("USB已连接");
                                mStatuChangeListener.onStatuChang(status);
                            }
                        } catch (IOException e) {
                            try {
                                mPorts.close();
                            } catch (IOException e2) {
                            }
                            mPorts = null;
                            mConnectionStatu = false;
                            if (null != mStatuChangeListener) {
                                Status status = new Status(Status.MyEvent.USBOPENERROR);
                                status.setMsg("USB设备连接失败\r\n" + e.getMessage());
                                mStatuChangeListener.onStatuChang(status);
                            }
                            return;
                        }
                    }


                    statrtlListenerDevice();


                } else {
                    //LogUtils.d("meiyou");
                    if (null != mStatuChangeListener) {
                        Status status = new Status(Status.MyEvent.USBNOTDETECTED);
                        status.setMsg("未检测到重金属模块,请检查USB线是否正确连接");
                        mStatuChangeListener.onStatuChang(status);
                    }
                }
            }
        };
        mScheduledThreadPoolExecutor.execute(runnableOpenUSB);


    }


    /**
     * usb设备连接状态发生改变
     */
    private void statrtlListenerDevice() {
        stopIoManager();
        startIoManager();
    }

    private void stopIoManager() {
        if (mSerialIoManager != null) {
            mSerialIoManager.stop();
            mSerialIoManager = null;
        }
    }

    private void startIoManager() {
        if (mPorts != null) {
            mSerialIoManager = new SerialInputOutputManager(mPorts, serialInputOutputManagerListener);
            mScheduledThreadPoolExecutor.submit(mSerialIoManager);
        }
    }

    private SerialInputOutputManager.Listener serialInputOutputManagerListener = new SerialInputOutputManager.Listener() {
        int length_usb;

        /**
         * @param data 串口数据接收监听
         */
        @Override
        public void onNewData(byte[] data) {
            //LogUtils.d(data.length);
            if (data.length == 0) {
                return;
            }
            if (mReciverData == null) {
                length_usb = -1;
                mReciverData = new byte[0];
            }
            mReciverData = ByteUtils.byteMerger(mReciverData, data);//数组拼接
            // LogUtils.d(mReciverData);
            if (mReciverData.length < 6) { //数组字节数小于6直接返回 没有达到返回的最小字节数
                return;
            }
            if (mReciverData[0] != 126 || mReciverData[mReciverData.length - 1] != -86) { //判断头尾是否对应,不对应直接返回，还不是 最终 的返回数组
                return;
            }
            //计算返回字节长度
            length_usb = ByteUtils.HexToInt(ByteUtils.byteToHex(mReciverData[3]) + ByteUtils.byteToHex(mReciverData[2]));
            //LogUtils.d(length_usb);
            if (mReciverData[1] == 32) {
                if (length_usb < 367) {
                    length_usb = length_usb * 2;
                }
            }
            //if (bledata.length == lenth_ble + 6) { //返回的字节 头x1 +标志位x1 +长度位置x2 +CRC8校验位x1 +尾x1
            //考虑有可能丢包  实际长度会比预计长度小
            if (mReciverData.length == length_usb + 6) { //返回的字节 头x1 +标志位x1 +长度位置x2 +CRC8校验位x1 +尾x1
                //需要判断看 是不是要补全数据（在头部或尾部）？？
                if (null != mDataReciverListener) {
                    mDataReciverListener.onReciver(mReciverData);
                }
                mReciverData = null;
            }
        }

        /**
         * @param e 串口数据接收监听,错误返回
         */
        @Override
        public void onRunError(Exception e) {
            if (null != mStatuChangeListener) {
                Status status = new Status(Status.MyEvent.FAILEDTORECEIVETESTDATA);
                status.setMsg(e.getMessage());
                mStatuChangeListener.onStatuChang(status);
            }
        }
    };


}
