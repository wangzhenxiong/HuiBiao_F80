package com.dy.huibiao_f80.printer;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.android_serialport_api.SerialControl;
import com.dy.huibiao_f80.app.utils.CharUtils;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;


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
 * Created by wangzx on 2018/5/23.
 * 多条打印
 */
public class PrintTask_Multiple implements Itask {
    private List<TestRecord> mSamplenumber;
    private int mNumber;
    private String resulttitle = "编号 " + " " + "名称     " + "  " + "结果 " + " " + "判定  " + "\r";


    public PrintTask_Multiple(List<TestRecord> samplenumber, int number) {
        this.mSamplenumber = samplenumber;
        this.mNumber = number;
    }


    @Override
    public void run() {

        if (Constants.ISDIRECTION_RECEIPT) {

            printlistResult_180(mSamplenumber);
        } else {

            printlistResult(mSamplenumber);
        }


    }


    /**
     * @param fgNcList 需要排序的对象
     * @return 排序好的队列
     */
    private List<TestRecord> SortBySearinumber(List<TestRecord> fgNcList) {
        TestRecord[] arr = new TestRecord[fgNcList.size()];
        for (int i = 0; i < fgNcList.size(); i++) {
            arr[i] = fgNcList.get(i);
        }

        int i, j, len = arr.length;
        /* 外循环为排序趟数，len个数进行len-1趟 */
        for (i = 0; i < len - 1; i++) {
            /* 内循环为每趟比较的次数，第i趟比较len-i次 */
            for (j = 0; j < len - 1 - i; j++) {
                if (arr[j].getIntSerialNumber() < arr[j + 1].getIntSerialNumber()) {
                    TestRecord temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }


        List<TestRecord> list = new ArrayList<>();
        for (int i1 = 0; i1 < arr.length; i1++) {
            list.add(arr[i1]);
        }
        return list;
    }

    /**
     * @param fgNcList 需要排序的对象
     * @return 排序好的队列
     */
    private List<TestRecord> SortBySearinumber180(List<TestRecord> fgNcList) {
        TestRecord[] arr = new TestRecord[fgNcList.size()];
        for (int i = 0; i < fgNcList.size(); i++) {
            arr[i] = fgNcList.get(i);
        }

        int i, j, len = arr.length;
        /* 外循环为排序趟数，len个数进行len-1趟 */
        for (i = 0; i < len - 1; i++) {
            /* 内循环为每趟比较的次数，第i趟比较len-i次 */
            for (j = 0; j < len - 1 - i; j++) {
                if (arr[j].getIntSerialNumber() < arr[j + 1].getIntSerialNumber()) {
                    TestRecord temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }


        List<TestRecord> list = new ArrayList<>();

        for (int i1 = arr.length - 1; i1 >= 0; i1--) {
            list.add(arr[i1]);
        }
        return list;
    }


    private void printlistResult_180(List<TestRecord> fgNcList) {
        LogUtils.d(fgNcList);
        LogUtils.d("开始打印多条180");

        SerialControl control = MyAppLocation.myAppLocation.mSerialDataService.mPrint_SerialControl;
        TestRecord nc1 = fgNcList.get(0);

        control.send(Constants.CMD_RESET);
        control.send(Constants.CMD_LINEDISTANCE);
        control.send(Constants.CMD_CENTER);
        control.send(Constants.CMD_SETDOUBLESIZE);
        control.send(Constants.CMD_REVERSE);

        if ("".equals(nc1.getTest_project())) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testreport).trim() + "\r");
        } else {
            String trim1 = nc1.getTest_project().trim();
            CharUtils.splitLineTitle180(control, trim1);

        }
        control.send(Constants.CMD_RESET);
        control.send(Constants.CMD_LINEDISTANCE);
        control.send(Constants.CMD_REVERSE);
        control.sendPortData(control, "\r\n");
        String string = MyAppLocation.myAppLocation.getString(R.string.print_devicename) + ArmsUtils.getString(MyAppLocation.myAppLocation, R.string.my_app_name);
        CharUtils.splitLine180(control, string);
        String method = nc1.getTest_method();
        if ("0".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed1) + "\r");
        } else if ("1".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed2) + "\r");
        } else if ("2".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed3) + "\r");
        } else if ("3".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed4) + "\r");
        } else {
            CharUtils.splitLine180(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed) + method, 33, 34);
        }

        String out = "检测依据：" + nc1.getStand_num();
        CharUtils.splitLine180(control, out);


        control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testdata) + nc1.getdfTestingtimeYY_MM_DD() + "\r");


        control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testunit) + nc1.getCov_unit() + "\r");

        String trim = (MyAppLocation.myAppLocation.getString(R.string.print_beunits) + nc1.getProsecutedunits()).trim();
        CharUtils.splitLine180(control, trim);

        control.sendPortData(control, resulttitle);
        control.sendPortData(control, "--------------------------------");
        fgNcList = SortBySearinumber180(fgNcList);
        for (int i = 0; i < fgNcList.size(); i++) {

            TestRecord nc = fgNcList.get(i);
            String samplename = nc.getSamplenamePrint(10);
            LogUtils.d(samplename);
            String decisionoutcome = nc.getDecisionoutcomePrint(6);
            String testresult = nc.getTestresultPrint(5);
            String s = (nc.getSerialNumber() + " " + ("".equals(samplename) ? "--" : samplename)
                    + " " + testresult + " " + ("".equals(decisionoutcome) ? "--" : decisionoutcome));


            CharUtils.splitLine180(control, s, 33, 34);


        }
        control.sendPortData(control, "--------------------------------");

        if ("抑制率法".equals(method) || "0".equals(method) ) {
            control.sendPortData(control, "对照值：△A=".trim() + nc1.getControlvalue() + "\r");
        } else if ("标准曲线法".equals(method) || "1".equals(method)) {
            control.sendPortData(control, "对照值：△A=".trim() + nc1.getControlvalue() + "\r");
            control.sendPortData(control, "稀释倍数：△A=".trim() + nc1.getDilutionratio() + "\r");
        } else if ("系数法".equals(method) || "3".equals(method)) {
            control.sendPortData(control, "反应液滴数：△A=".trim() + nc1.getEveryresponse() + "\r");
        }

        String testpeople = MyAppLocation.myAppLocation.getString(R.string.print_testpeople).trim() + nc1.getInspector();
        CharUtils.splitLine180(control, testpeople);
        if (Constants.CHECKPEOPLE.equals("")) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_checkpeople1).trim() + "\r");
        } else {
            control.sendPortData(control, (MyAppLocation.myAppLocation.getString(R.string.print_checkpeople2) + Constants.CHECKPEOPLE).trim() + "\r");
        }
        PrintPlaceHolder.printPlace(mNumber, control);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void printlistResult(List<TestRecord> fgNcList) {


        // LogUtils.d(fgNcList);
        LogUtils.d("开始打印多条");

        SerialControl control = MyAppLocation.myAppLocation.mSerialDataService.mPrint_SerialControl;
        // SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        control.send(Constants.CMD_RESET);
        control.send(Constants.CMD_LINEDISTANCE);
        if (Constants.CHECKPEOPLE.equals("")) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_checkpeople1).trim() + "\r");
        } else {
            control.sendPortData(control, (MyAppLocation.myAppLocation.getString(R.string.print_checkpeople1) + Constants.CHECKPEOPLE).trim() + "\r");
        }

        TestRecord nc1 = fgNcList.get(0);
        String testpeople;

        testpeople = MyAppLocation.myAppLocation.getString(R.string.print_testpeople).trim() + nc1.getInspector();
        CharUtils.splitLine(control, testpeople);


        String method = nc1.getTest_method();


        if ("抑制率法".equals(method) || "0".equals(method)) {
            control.sendPortData(control, "对照值：△A=".trim() + nc1.getControlvalue() + "\r");
        } else if ("标准曲线法".equals(method) || "1".equals(method)) {
            control.sendPortData(control, "对照值：△A=".trim() + nc1.getControlvalue() + "\r");
            control.sendPortData(control, "稀释倍数：△A=".trim() + nc1.getDilutionratio() + "\r");
        } else if ("系数法".equals(method) || "3".equals(method)) {
            control.sendPortData(control, "反应液滴数：△A=".trim() + nc1.getEveryresponse() + "\r");
        }
        control.sendPortData(control, "--------------------------------");
        fgNcList = SortBySearinumber(fgNcList);
        for (int i = 0; i < fgNcList.size(); i++) {

            TestRecord nc = fgNcList.get(i);
            String samplename = nc.getSamplenamePrint(10);
            LogUtils.d(samplename);

            String decisionoutcome = nc.getDecisionoutcomePrint(6);
            String testresult = nc.getTestresultPrint(5);
            String s = (nc.getSerialNumber() + " " + ("".equals(samplename) ? "--" : samplename)
                    + " " + testresult + " " + ("".equals(decisionoutcome) ? "--" : decisionoutcome));

            CharUtils.splitLine(control, s, 33, 34);

            //LogUtils.d(s.length());

        }
        control.sendPortData(control, "--------------------------------");

        // control.sendPortData(control,q.getSamplenum().trim()+"  "+q.getSamplename().trim()+"  "+q.getTestresult().trim()+"  "+q.getDecisionoutcome().trim()+"\r");


        control.sendPortData(control, resulttitle);
        String trim = (MyAppLocation.myAppLocation.getString(R.string.print_beunits) + nc1.getProsecutedunits()).trim();
        CharUtils.splitLine(control, trim);


        control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testunit) + nc1.getCov_unit() + "\r");

        control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testdata) + nc1.getdfTestingtimeYY_MM_DD() + "\r");


        String out = "检测依据：" + nc1.getStand_num();
        CharUtils.splitLine(control, out, 33, 34);


        //control.sendPortData(control, out);
        if ("0".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed1) + "\r");
        } else if ("1".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed2) + "\r");
        } else if ("2".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed3) + "\r");
        } else if ("3".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed4) + "\r");
        } else {
            CharUtils.splitLine180(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed) + method, 33, 34);
        }

        String string = MyAppLocation.myAppLocation.getString(R.string.print_devicename) + ArmsUtils.getString(MyAppLocation.myAppLocation, R.string.my_app_name);
        CharUtils.splitLine(control, string, 33, 34);

        control.sendPortData(control, "\r");
        control.send(Constants.CMD_RESET);
        control.send(Constants.CMD_LINEDISTANCE);
        control.send(Constants.CMD_CENTER);

        control.send(Constants.CMD_SETDOUBLESIZE);


        if ("".equals(nc1.getTest_project())) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testreport).trim() + "\r");
        } else {
            String trim1 = nc1.getTest_project().trim();
            CharUtils.splitLineTitle(control, trim1);

        }
        PrintPlaceHolder.printPlace(mNumber, control);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
