package com.dy.huibiao_f80.printer;

import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.TestRecord;
import com.dy.huibiao_f80.greendao.daos.TestRecordDao;
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
 */
public class PrintHelper {
    private List<String> mSamplenumber;

    public PrintHelper(List<String> samplenumber) {
        this.mSamplenumber = samplenumber;
    }

    public PrintHelper() {
    }

    public void run() {
        List<TestRecord> mList = new ArrayList<>();

        TestRecordDao dao = DBHelper.getTestRecordDao();
        for (int i = 0; i < mSamplenumber.size(); i++) {
            // List<TestRecord> ncs = dao.loadAll();
            List<TestRecord> list = dao.queryBuilder().where(TestRecordDao.Properties.SysCode.like(mSamplenumber.get(i))).list();
            if (list.size() != 0 && list != null) {
                TestRecord nc = list.get(0);
                mList.add(nc);
            }
        }
        mListList = null;
        deailList(mList);
    }

    public List<List<TestRecord>> mListList;

    public void deailList(List<TestRecord> list) {
        if (list.size() <= 0) {
            ArmsUtils.snackbarText(MyAppLocation.myAppLocation.getString(R.string.withoutprintdata));
        } else {

            if (mListList == null) {
                mListList = new ArrayList<>();
            }
            List<TestRecord> objects = new ArrayList<>();
            TestRecord nc = list.get(0);
            String project = nc.getTest_project();

            String inspector;

            inspector = nc.getInspector();
            String moudle = nc.getTest_Moudle();
            String method = nc.getTest_method();
            String num = nc.getStand_num();
            String testingtime = nc.getdfTestingtimeYY_MM_DD();
            String prosecutedunits = nc.getProsecutedunits();
            double dilutionratio = nc.getDilutionratio();//beishu
            double everyresponse = nc.getEveryresponse();//dishu
            objects.add(nc);
            list.remove(nc);
            for (int i = 0; i < list.size(); i++) {
                TestRecord nc1 = list.get(i);
                String project1 = nc1.getTest_project();
                String moudle1 = nc1.getTest_Moudle();
                String method1 = nc1.getTest_method();
                String inspector1;
                inspector1 = nc.getInspector();
                String num1 = nc1.getStand_num();
                String testingtime1 = nc1.getdfTestingtimeYY_MM_DD();
                String prosecutedunits1 = nc1.getProsecutedunits();
                if ("抑制率法".equals(method)) {
                    if (project1.equals(project)
                            && moudle1.equals(moudle)
                            && method1.equals(method)
                            && num1.equals(num)
                            && testingtime1.equals(testingtime)
                            && inspector1.equals(inspector)
                            && prosecutedunits1.equals(prosecutedunits)) {
                        objects.add(nc1);
                    }
                } else if ("标准曲线法".equals(method)) {
                    double dilutionratio1 = nc1.getDilutionratio();
                    if (project1.equals(project)
                            && moudle1.equals(moudle)
                            && method1.equals(method)
                            && num1.equals(num)
                            && dilutionratio1 == dilutionratio
                            && testingtime1.equals(testingtime)
                            && inspector1.equals(inspector)
                            && prosecutedunits1.equals(prosecutedunits)) {
                        objects.add(nc1);
                    }
                } else if ("动力学法".equals(method)) {
                    if (project1.equals(project)
                            && moudle1.equals(moudle)
                            && method1.equals(method)
                            && num1.equals(num)
                            && testingtime1.equals(testingtime)
                            && inspector1.equals(inspector)
                            && prosecutedunits1.equals(prosecutedunits)) {
                        objects.add(nc1);
                    }
                } else if ("系数法".equals(method)) {
                    double everyresponse1 = nc1.getEveryresponse();
                    if (project1.equals(project)
                            && moudle1.equals(moudle)
                            && method1.equals(method)
                            && num1.equals(num)
                            && everyresponse1 == everyresponse
                            && testingtime1.equals(testingtime)
                            && inspector1.equals(inspector)
                            && prosecutedunits1.equals(prosecutedunits)) {
                        objects.add(nc1);
                    }
                } else {
                    if (project1.equals(project)
                            && moudle1.equals(moudle)
                            && method1.equals(method)
                            && num1.equals(num)
                            && testingtime1.equals(testingtime)
                            && inspector1.equals(inspector)
                            && prosecutedunits1.equals(prosecutedunits)) {
                        objects.add(nc1);
                    }
                }
            }

            if (objects.size() == 1) {
                // print1result(objects.get(0));
                mListList.add(objects);
            } else {
                mListList.add(objects);
                // printlistResult(objects);
                for (int i = 0; i < objects.size(); i++) {
                    list.remove(objects.get(i));
                }
            }
            if (list.size() > 0) {
                deailList(list);
            } else {


                for (int i = 0; i < mListList.size(); i++) {
                    List<TestRecord> ncs = mListList.get(i);
                    if (ncs.size() == 1) {  //单条打印
                        //不需要打印二维码
                        MyPrinterIntentService.startActionPrintSingle(MyAppLocation.myAppLocation, ncs);
                        //MyAppLocation.myAppLocation.mSerialDataService.mPrintTaskQueue.add(new PrintTask_Single(ncs));

                    } else {  //多条打印
                        MyPrinterIntentService.startActionPrintMultiple(MyAppLocation.myAppLocation, ncs);
                        //MyAppLocation.myAppLocation.mSerialDataService.mPrintTaskQueue.add(new PrintTask_Multiple(ncs));
                    }
                }
            }
        }


    }


}
