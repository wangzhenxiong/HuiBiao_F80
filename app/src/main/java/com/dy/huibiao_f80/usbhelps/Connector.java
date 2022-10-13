package com.dy.huibiao_f80.usbhelps;


import com.dy.huibiao_f80.usbhelps.listeners.OnDataReciverListener;
import com.dy.huibiao_f80.usbhelps.listeners.OnStatuChangeListener;

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
 * @data: 6/18/21 1:43 PM
 * Description:
 */
public interface Connector {

    /**
     * 设置硬件数据监听
     * @param onDataReciverListener
     */
    void setOnDataReciverListener(OnDataReciverListener onDataReciverListener);

    /**
     * 设置状态改变，事件发生 监听
     * @param onStatuChangeListener
     */
    void setOnStatuChangeListener(OnStatuChangeListener onStatuChangeListener);
    /**
     * 连接
     */
   void connection();

    /**
     * 断开连接
     */
   void disConnection();

    /**
     * 获取状态
     * @return
     */
   boolean getStatu();

    /**
     * 数据写入
     * @param bytes
     * @return
     */
   int writeData(byte[] bytes);
}
