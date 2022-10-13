package com.dy.huibiao_f80.usbhelps;

import android.os.Parcel;
import android.os.Parcelable;

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
 * @data: 6/17/21 9:23 AM
 * Description:
 */
public class Status implements Parcelable {

    @Override
    public String toString() {
        return "Status{" +
                "evevt=" + evevt +
                ", msg='" + msg + '\'' +
                ", resultLead=" + resultLead +
                ", unitLead='" + unitLead + '\'' +
                ", resultCadmium=" + resultCadmium +
                ", unitCadmium='" + unitCadmium + '\'' +
                '}';
    }

    /**
     * 回调事件
     */
    public enum MyEvent implements Parcelable {
        /**
         *USB设备插入
         */
        ONUSBDEVICEATTACHED(1),
        /**
         *USB设备拔出
         */
        ONUSBDEVICEDETACHED(2),
        /**
         *USB已连接
         */
        USBCONNECTED(3),
        /**
         *usb断开
         */
        USBDISCONNECT(4),

        /**
         * 蓝牙连接
         */
        BLUETOOTHCONNECTED(5),
        /**
         *蓝牙断开
         */
        BLUETOOTHDISCONNECT(6),
        /**
         *倒计时
         */
        COUNTDOWN(7),
        /**
         *数据接收完成
         */
        DATARECEPTIONCOMPLETED(8),
        /**
         *接收检测数据失败
         */
        FAILEDTORECEIVETESTDATA(9),
        /**
         *检测结果
         */
        TESTRESULTS(10),
        /**
         * 检测结果曲线图
         */
        DETECTIONMAP(11),
        /**
         *计算结果失败
         */
        FAILEDTOCALCULATIONRESULTS(12),
        /**
         * 停止检测
         */
        STOPTEST(13),
        /**
         *未检测到重金属模块
         */
        USBNOTDETECTED(14),
        /**
         * 未定义的错误
         */
        UNDEFINEDERROR(15),
        /**
         * USB打开失败
         */
        USBOPENERROR(16),
        /**
         * 蓝牙连接失败
         */
        BLEOPENERROR(17),
        /**
         *未检测到重金属模块
         */
        BLENOTDETECTED(18),
        /**
         * 蓝牙名称设置成功
         */
        RESETBLENAMESUCCESS(19),
        /**
         * 蓝牙名称设置失败
         */
        RESETBLENAMEERROR(20),
        /**
         * 开始搜索蓝牙
         */
        STARTSEACHBLE(21),
        /**
         * 蓝牙连接
         */
        BLUETOOTHCONNECTING(22) ,
        /**
         * 获取usb权限
         */
        GETUSBPERMISSIONFAILURE(23),
        //蓝牙不可用，未打开
        BLUETOOTHDISABLE(24)
        ;


        MyEvent(int i) {

        }



        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MyEvent> CREATOR = new Creator<MyEvent>() {
            @Override
            public MyEvent createFromParcel(Parcel in) {
                return MyEvent.values()[in.readInt()];
            }

            @Override
            public MyEvent[] newArray(int size) {
                return new MyEvent[size];
            }
        };
    }


    private MyEvent evevt;
    private String msg;
    private double resultLead;
    private String unitLead;
    private double resultCadmium;
    private String unitCadmium;


    protected Status(Parcel in) {
        evevt= MyEvent.values()[in.readInt()];
        msg = in.readString();
        resultLead = in.readDouble();
        unitLead = in.readString();
        resultCadmium = in.readDouble();
        unitCadmium = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(evevt.ordinal());
        dest.writeString(msg);
        dest.writeDouble(resultLead);
        dest.writeString(unitLead);
        dest.writeDouble(resultCadmium);
        dest.writeString(unitCadmium);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    public Status(MyEvent evevt) {
        this.evevt = evevt;
    }

    public MyEvent getEvevt() {
        return evevt;
    }

    public void setEvevt(MyEvent evevt) {
        this.evevt = evevt;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? "" : msg;
    }

    public double getResultLead() {
        return resultLead;
    }

    public void setResultLead(double resultLead) {
        this.resultLead = resultLead;
    }

    public String getUnitLead() {
        return unitLead == null ? "" : unitLead;
    }

    public void setUnitLead(String unitLead) {
        this.unitLead = unitLead == null ? "" : unitLead;
    }

    public double getResultCadmium() {
        return resultCadmium;
    }

    public void setResultCadmium(double resultCadmium) {
        this.resultCadmium = resultCadmium;
    }

    public String getUnitCadmium() {
        return unitCadmium == null ? "" : unitCadmium;
    }

    public void setUnitCadmium(String unitCadmium) {
        this.unitCadmium = unitCadmium == null ? "" : unitCadmium;
    }
}
