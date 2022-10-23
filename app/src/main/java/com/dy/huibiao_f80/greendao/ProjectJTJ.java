package com.dy.huibiao_f80.greendao;

import com.dy.huibiao_f80.bean.base.BaseProjectMessage;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

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
 * @data: 10/11/22 1:44 PM
 * Description:
 */
@Entity
public class ProjectJTJ extends BaseProjectMessage {
    @Override
    public int getYuretime() {
        return 0;
    }

    @Override
    public int getJiancetime() {
        return testTime;
    }

    @Override
    public int getWavelength() {
        return -1;
    }

    @Override
    public String getPjName() {
        return projectName;
    }

    @Override
    public String getCVName() {
        return curveName;
    }

    @Override
    public int getMethod_sp() {
        return testMethod;
    }

    @Override
    public String getUnit_input() {
        return "";
    }

    @Override
    public String getStandNum() {
        return standardName;
    }

    @Override
    public String getmethodLimit() {
        return detectionLimit;
    }


    @Id(autoincrement = true)
    private Long id;
    private String projectName;
    private String curveName;
    private int curveOrder;
    //一个项目可能存在多条曲线，需要有一个是默认曲线
    private boolean isdefault;
    private String standardName;
    private int testMethod;


    private double c;
    private double tA;
    private double tB;
    private double c_tA;
    private double c_tB;


    private int testTime;

    //简要提示
    private String tips;
    private String detectionLimit;
    /**
     * 检测项目信息完成状态，新建时状态都是未完成，在点击保存时候才改完true
     */
    private boolean finishState;





    @Generated(hash = 1369740684)
    public ProjectJTJ(Long id, String projectName, String curveName,
            int curveOrder, boolean isdefault, String standardName, int testMethod,
            double c, double tA, double tB, double c_tA, double c_tB, int testTime,
            String tips, String detectionLimit, boolean finishState) {
        this.id = id;
        this.projectName = projectName;
        this.curveName = curveName;
        this.curveOrder = curveOrder;
        this.isdefault = isdefault;
        this.standardName = standardName;
        this.testMethod = testMethod;
        this.c = c;
        this.tA = tA;
        this.tB = tB;
        this.c_tA = c_tA;
        this.c_tB = c_tB;
        this.testTime = testTime;
        this.tips = tips;
        this.detectionLimit = detectionLimit;
        this.finishState = finishState;
    }

    @Generated(hash = 984178824)
    public ProjectJTJ() {
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? "" : projectName;
    }

    public String getCurveName() {
        return curveName == null ? "" : curveName;
    }

    public void setCurveName(String curveName) {
        this.curveName = curveName == null ? "" : curveName;
    }

    public boolean isdefault() {
        return isdefault;
    }

    public void setIsdefault(boolean isdefault) {
        this.isdefault = isdefault;
    }

    public String getStandardName() {
        return standardName == null ? "" : standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName == null ? "" : standardName;
    }

    public int getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(int testMethod) {
        this.testMethod = testMethod;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double gettA() {
        return tA;
    }

    public void settA(double tA) {
        this.tA = tA;
    }

    public double gettB() {
        return tB;
    }

    public void settB(double tB) {
        this.tB = tB;
    }

    public double getC_tA() {
        return c_tA;
    }

    public void setC_tA(double c_tA) {
        this.c_tA = c_tA;
    }

    public double getC_tB() {
        return c_tB;
    }

    public void setC_tB(double c_tB) {
        this.c_tB = c_tB;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public String getTips() {
        return tips == null ? "" : tips;
    }

    public void setTips(String tips) {
        this.tips = tips == null ? "" : tips;
    }

    public String getDetectionLimit() {
        return detectionLimit == null ? "" : detectionLimit;
    }

    public void setDetectionLimit(String detectionLimit) {
        this.detectionLimit = detectionLimit == null ? "" : detectionLimit;
    }

    public boolean isFinishState() {
        return finishState;
    }

    public void setFinishState(boolean finishState) {
        this.finishState = finishState;
    }

    public boolean getFinishState() {
        return this.finishState;
    }

    public double getTB() {
        return this.tB;
    }

    public void setTB(double tB) {
        this.tB = tB;
    }

    public double getTA() {
        return this.tA;
    }

    public void setTA(double tA) {
        this.tA = tA;
    }

    public boolean getIsdefault() {
        return this.isdefault;
    }

    public int getCurveOrder() {
        return curveOrder;
    }

    public void setCurveOrder(int curveOrder) {
        this.curveOrder = curveOrder;
    }
}
