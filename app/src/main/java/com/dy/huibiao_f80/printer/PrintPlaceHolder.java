package com.dy.huibiao_f80.printer;


import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.android_serialport_api.SerialControl;

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
 * @data: 8/4/21 4:43 PM
 * Description:
 */
public class PrintPlaceHolder {
    /**
     * 打印占位行，方便撕小票
     * @param mNumber
     * @param control
     */
    public static void printPlace(int mNumber, SerialControl control) {
        control.send(Constants.CMD_RESET);
        control.send(Constants.CMD_LINEDISTANCE);
        control.send(Constants.CMD_CENTER);
        if (mNumber == 0) {

                control.sendPortData(control, "" + "\r\n");
                control.sendPortData(control, "" + "\r\n");
                control.sendPortData(control, " " + "\r\n");
                control.sendPortData(control, " " + "\r");
                control.sendPortData(control, " " + "\r\n");
                control.sendPortData(control, " " + "\r");
        } else {
            control.sendPortData(control, "" + "\r\n");
        }
    }
}
