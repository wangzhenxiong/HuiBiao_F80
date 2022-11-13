package com.dy.huibiao_f80.printer;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.android_serialport_api.SerialControl;
import com.dy.huibiao_f80.app.utils.CharUtils;
import com.dy.huibiao_f80.app.utils.NumberUtils;
import com.dy.huibiao_f80.bean.PrintMessage;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.jess.arms.utils.ArmsUtils;

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
 * 单条打印
 */
public class PrintTask_Single implements Itask {
    private String resulttitle = "编号 " + " " + "名称     " + "  " + "结果 " + " " + "判定  " + "\r";
    ;
    private List<TestRecord> mSamplenumber;
    private int mNumber;
    private PrintMessage checkPrintMessage;

    public PrintTask_Single(List<TestRecord> samplenumber, int num) {
        this.mSamplenumber = samplenumber;
        this.mNumber = num;
    }


    @Override
    public void run() {
        checkPrintMessage = Constants.CheckPrintMessage();
        TestRecord fggd_nc = mSamplenumber.get(0);
        LogUtils.d(fggd_nc);
        if (Constants.ISDIRECTION_RECEIPT) {
            //旋转180度

            print1result_180(fggd_nc);
        } else {

            print1result(fggd_nc);

        }


    }


    private void print1result_180(TestRecord q) {
        //LogUtils.d(q);
        LogUtils.d("开始打印单条");

        SerialControl control = MyAppLocation.myAppLocation.mSerialDataService.mPrint_SerialControl;
        // SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String method = q.getTest_method();
        control.send(Constants.CMD_RESET);
        control.send(Constants.CMD_LINEDISTANCE);
        control.send(Constants.CMD_CENTER);
        control.send(Constants.CMD_SETDOUBLESIZE);
        control.send(Constants.CMD_REVERSE);

        if ("".equals(q.getTest_project())) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testreport).trim() + "\r");
        } else {
            String trim1 = q.getTest_project().trim();
            CharUtils.splitLineTitle180(control, trim1);
        }
        control.send(Constants.CMD_RESET);
        control.send(Constants.CMD_LINEDISTANCE);
        control.send(Constants.CMD_REVERSE);
        control.sendPortData(control, "\r\n");
        if (checkPrintMessage.isCheckBox_device()) {
            String string = MyAppLocation.myAppLocation.getString(R.string.print_devicename) + ArmsUtils.getString(MyAppLocation.myAppLocation, R.string.my_app_name);
            CharUtils.splitLine180(control, string);
        }

        if (q.getTest_Moudle().equals("分光光度")) {
            if ("0".equals(method)) {
                control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed1) + "\r");
            } else if ("1".equals(method)) {
                control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed2) + "\r");
            } else if ("2".equals(method)) {
                control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed3) + "\r");
            } else if ("3".equals(method)) {
                control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed4) + "\r");
            } else {
                CharUtils.splitLine180(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed) + method, 31, 32);
            }
        } else if (q.getTest_Moudle().equals("胶体金")) {
            if ("0".equals(method)) {
                control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_xiaoxian) + "\r");
            } else if ("1".equals(method)) {
                control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_bise) + "\r");
            }
        }


        String out = "检测依据：" + q.getStand_num();
        CharUtils.splitLine180(control, out,31, 32);
        if (checkPrintMessage.isCheckBox_testtime()) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testdata) + q.getdfTestingtimeyy_mm_dd_hh_mm_ss() + "\r");
        }


        control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testunit) + q.getCov_unit() + "\r");

        if (checkPrintMessage.isCheckBox_beunit()) {
            String trim = (MyAppLocation.myAppLocation.getString(R.string.print_beunits) + q.getProsecutedunits()).trim();
            CharUtils.splitLine180(control, trim);
        }


        control.sendPortData(control, resulttitle);
        control.sendPortData(control, "--------------------------------");
        String samplename = q.getSamplenamePrint(10);
        LogUtils.d(samplename);
        String decisionoutcome = q.getDecisionoutcomePrint(6);
        String testresult = q.getTestresultPrint(5);
        String s = null;
        //String decisionoutcome_11 = "".equals(decisionoutcome) ? "--" : decisionoutcome.equals("合格") ? "阴性" : "阳性";


        s = (q.getSamplenum().equals("")?q.getGalleryNum()+"":q.getSamplenum() + " " + ("".equals(samplename) ? "--" : samplename)
                + " " + testresult + " " + ("".equals(decisionoutcome) ? "--" : decisionoutcome));

        CharUtils.splitLine180(control, s, 33, 34);
        control.sendPortData(control, "--------------------------------");

        if ("抑制率法".equals(method) || "0".equals(method)) {
            control.sendPortData(control, "对照值：△A=".trim() + NumberUtils.four(Double.parseDouble(q.getControlvalue())) + "\r");
        } else if ("标准曲线法".equals(method) || "1".equals(method)) {
            control.sendPortData(control, "对照值：△A=".trim() + NumberUtils.four(Double.parseDouble(q.getControlvalue())) + "\r");
            control.sendPortData(control, "稀释倍数：△A=".trim() + q.getDilutionratio() + "\r");
        } else if ("系数法".equals(method) || "3".equals(method)) {
            control.sendPortData(control, "反应液滴数：△A=".trim() + q.getEveryresponse() + "\r");
        }
        if (checkPrintMessage.isCheckBox_sampleplace()) {
            String sampleplace = MyAppLocation.myAppLocation.getString(R.string.print_sampleplace).trim()+checkPrintMessage.getEd_sampleplace()+"\r";
            CharUtils.splitLine180(control, sampleplace);
        }
        if (checkPrintMessage.isCheckBox_testpeople()) {
            String testpeople = MyAppLocation.myAppLocation.getString(R.string.print_testpeople).trim() + checkPrintMessage.getEd_testpeople()+"\r";
            CharUtils.splitLine180(control, testpeople);
        }


        if (checkPrintMessage.isCheckBox_jujdger()) {
            String testpeople = MyAppLocation.myAppLocation.getString(R.string.print_checkpeople1).trim() + checkPrintMessage.getEd_jujdger()+"\r";
            CharUtils.splitLine180(control, testpeople);
        }


        PrintPlaceHolder.printPlace(mNumber, control);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void print1result(TestRecord q) {


        //LogUtils.d(q);
        LogUtils.d("开始打印单条");

        SerialControl control = MyAppLocation.myAppLocation.mSerialDataService.mPrint_SerialControl;


        control.send(Constants.CMD_RESET);
        control.send(Constants.CMD_LINEDISTANCE);
        //control.send(Constants.CMD_REVERSE);


        if (checkPrintMessage.isCheckBox_jujdger()) {
            String testpeople = MyAppLocation.myAppLocation.getString(R.string.print_checkpeople1).trim() + checkPrintMessage.getEd_jujdger()+"\r";
            CharUtils.splitLine(control, testpeople);
        }
        if (checkPrintMessage.isCheckBox_testpeople()) {
            String testpeople = MyAppLocation.myAppLocation.getString(R.string.print_testpeople).trim() + checkPrintMessage.getEd_testpeople()+"\r";
            CharUtils.splitLine(control, testpeople);
        }
        if (checkPrintMessage.isCheckBox_sampleplace()) {
            String sampleplace = MyAppLocation.myAppLocation.getString(R.string.print_sampleplace).trim()+checkPrintMessage.getEd_sampleplace()+"\r";
            CharUtils.splitLine(control, sampleplace);
        }


        String method = q.getTest_method();

        if ("抑制率法".equals(method) || "0".equals(method)) {
            control.sendPortData(control, "对照值：△A=".trim() + NumberUtils.four(Double.parseDouble(q.getControlvalue())) + "\r");
        } else if ("标准曲线法".equals(method) || "1".equals(method)) {
            control.sendPortData(control, "对照值：△A=".trim() + NumberUtils.four(Double.parseDouble(q.getControlvalue())) + "\r");
            control.sendPortData(control, "稀释倍数：△A=".trim() + q.getDilutionratio() + "\r");
        } else if ("系数法".equals(method) || "3".equals(method)) {
            control.sendPortData(control, "反应液滴数：△A=".trim() + q.getEveryresponse() + "\r");
        }
        control.sendPortData(control, "--------------------------------");


        String samplename = q.getSamplenamePrint(10);
        LogUtils.d(samplename);
        String decisionoutcome = q.getDecisionoutcomePrint(6);
        String testresult = q.getTestresultPrint(5);

        String s = (q.getSamplenum().equals("")?q.getGalleryNum()+"":q.getSamplenum() + " " + ("".equals(samplename) ? "--" : samplename)
                + " " + testresult + " " + ("".equals(decisionoutcome) ? "--" : decisionoutcome));

        CharUtils.splitLine(control, s, 31, 32);


        control.sendPortData(control, "--------------------------------");

        // control.sendPortData(control,q.getSamplenum().trim()+"  "+q.getSamplename().trim()+"  "+q.getTestresult().trim()+"  "+q.getDecisionoutcome().trim()+"\r");
        //control.sendPortData(control, "编号" + "  " + "名称" + "  " + "结果" + "  " + "判定" + "\r\n");


        control.sendPortData(control, resulttitle);

        if (checkPrintMessage.isCheckBox_beunit()){
            String trim = (MyAppLocation.myAppLocation.getString(R.string.print_beunits) + q.getProsecutedunits()).trim();
            CharUtils.splitLine(control, trim);
        }



        control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testunit) + q.getCov_unit() + "\r");
        if (checkPrintMessage.isCheckBox_testtime()){
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testdata) + q.getdfTestingtimeyy_mm_dd_hh_mm_ss() + "\r");
        }



        String out = "检测依据：" + q.getStand_num();
        CharUtils.splitLine(control, out, 31, 32);

        if ("0".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed1) + "\r");
        } else if ("1".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed2) + "\r");
        } else if ("2".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed3) + "\r");
        } else if ("3".equals(method)) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed4) + "\r");
        } else {
            CharUtils.splitLine180(control, MyAppLocation.myAppLocation.getString(R.string.print_mothed) + method, 31, 32);
        }
        if (checkPrintMessage.isCheckBox_device()){
            String string = MyAppLocation.myAppLocation.getString(R.string.print_devicename) + ArmsUtils.getString(MyAppLocation.myAppLocation, R.string.my_app_name);
            CharUtils.splitLine(control, string);
        }

        control.sendPortData(control, "\r");
        control.send(Constants.CMD_RESET);
        control.send(Constants.CMD_LINEDISTANCE);
        control.send(Constants.CMD_CENTER);
        control.send(Constants.CMD_SETDOUBLESIZE);

        if ("".equals(q.getTest_project())) {
            control.sendPortData(control, MyAppLocation.myAppLocation.getString(R.string.print_testreport).trim() + "\r");
        } else {
            String trim1 = q.getTest_project().trim();
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
