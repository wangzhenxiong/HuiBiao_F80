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
 * @data: 10/11/22 1:42 PM
 * Description:
 */
@Entity
public class ProjectFGGD extends BaseProjectMessage {
    @Override
    public int getYuretime() {
        return warmTime;
    }

    @Override
    public int getJiancetime() {
        return testTime;
    }

    @Override
    public int getWavelength() {
        return waveLength;
    }

    @Override
    public String getPjName() {
        return projectName;
    }

    @Override
    public String getCVName() {
        return curveName == null ? "" : curveName;
    }

    @Override
    public int getMethod_sp() {
        return method;
    }

    @Override
    public String getUnit_input() {
        return resultUnit;
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
    /**
     * 1，抑制率法 2，标准曲线法 3，动力学法 4，系数法
     */
    private int method;
    private int waveLength;//波长段/
    private int warmTime;//预热时间/
    private int testTime;//检测时间/
    private String resultUnit;//检测值单位/

    private float controValue;//每个检测项目都有一个对照值
    private String controValueLastTime;//产生对照值的时间

    private double a0;
    private double b0;
    private double c0;
    private double d0;
    private double from0;
    private double to0;

    private double a1;
    private double b1;
    private double c1;
    private double d1;
    private double from1;
    private double to1;

    private double a;
    private double b;
    private double c;
    private double d;
    private boolean user_yin;
    private double yin_a;
    private String yin_a_symbol;
    private double yin_b;
    private String yin_b_symbol;

    private boolean user_yang;
    private double yang_a;
    private String yang_a_symbol;
    private double yang_b;
    private String yang_b_symbol;

    private boolean user_keyi;
    private double keyi_a;
    private String keyi_a_symbol;
    private double keyi_b;
    private String keyi_b_symbol;
    //简要提示
    private String tips;
    private String detectionLimit;
    /**
     * 检测项目信息完成状态，新建时状态都是未完成，在点击保存时候才改完true
     */
    private boolean finishState;





    @Generated(hash = 516655006)
    public ProjectFGGD(Long id, String projectName, String curveName, int curveOrder,
            boolean isdefault, String standardName, int method, int waveLength, int warmTime,
            int testTime, String resultUnit, float controValue, String controValueLastTime,
            double a0, double b0, double c0, double d0, double from0, double to0, double a1,
            double b1, double c1, double d1, double from1, double to1, double a, double b,
            double c, double d, boolean user_yin, double yin_a, String yin_a_symbol,
            double yin_b, String yin_b_symbol, boolean user_yang, double yang_a,
            String yang_a_symbol, double yang_b, String yang_b_symbol, boolean user_keyi,
            double keyi_a, String keyi_a_symbol, double keyi_b, String keyi_b_symbol,
            String tips, String detectionLimit, boolean finishState) {
        this.id = id;
        this.projectName = projectName;
        this.curveName = curveName;
        this.curveOrder = curveOrder;
        this.isdefault = isdefault;
        this.standardName = standardName;
        this.method = method;
        this.waveLength = waveLength;
        this.warmTime = warmTime;
        this.testTime = testTime;
        this.resultUnit = resultUnit;
        this.controValue = controValue;
        this.controValueLastTime = controValueLastTime;
        this.a0 = a0;
        this.b0 = b0;
        this.c0 = c0;
        this.d0 = d0;
        this.from0 = from0;
        this.to0 = to0;
        this.a1 = a1;
        this.b1 = b1;
        this.c1 = c1;
        this.d1 = d1;
        this.from1 = from1;
        this.to1 = to1;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.user_yin = user_yin;
        this.yin_a = yin_a;
        this.yin_a_symbol = yin_a_symbol;
        this.yin_b = yin_b;
        this.yin_b_symbol = yin_b_symbol;
        this.user_yang = user_yang;
        this.yang_a = yang_a;
        this.yang_a_symbol = yang_a_symbol;
        this.yang_b = yang_b;
        this.yang_b_symbol = yang_b_symbol;
        this.user_keyi = user_keyi;
        this.keyi_a = keyi_a;
        this.keyi_a_symbol = keyi_a_symbol;
        this.keyi_b = keyi_b;
        this.keyi_b_symbol = keyi_b_symbol;
        this.tips = tips;
        this.detectionLimit = detectionLimit;
        this.finishState = finishState;
    }

    @Generated(hash = 1476994882)
    public ProjectFGGD() {
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

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getWaveLength() {
        return waveLength;
    }

    public void setWaveLength(int waveLength) {
        this.waveLength = waveLength;
    }

    public int getWarmTime() {
        return warmTime;
    }

    public void setWarmTime(int warmTime) {
        this.warmTime = warmTime;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public String getResultUnit() {
        return resultUnit == null ? "" : resultUnit;
    }

    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit == null ? "" : resultUnit;
    }

    public float getControValue() {
        return controValue;
    }

    public void setControValue(float controValue) {
        this.controValue = controValue;
    }

    public String getControValueLastTime() {
        return controValueLastTime == null ? "" : controValueLastTime;
    }

    public void setControValueLastTime(String controValueLastTime) {
        this.controValueLastTime = controValueLastTime == null ? "" : controValueLastTime;
    }

    public double getA0() {
        return a0;
    }

    public void setA0(double a0) {
        this.a0 = a0;
    }

    public double getB0() {
        return b0;
    }

    public void setB0(double b0) {
        this.b0 = b0;
    }

    public double getC0() {
        return c0;
    }

    public void setC0(double c0) {
        this.c0 = c0;
    }

    public double getD0() {
        return d0;
    }

    public void setD0(double d0) {
        this.d0 = d0;
    }

    public double getFrom0() {
        return from0;
    }

    public void setFrom0(double from0) {
        this.from0 = from0;
    }

    public double getTo0() {
        return to0;
    }

    public void setTo0(double to0) {
        this.to0 = to0;
    }

    public double getA1() {
        return a1;
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public double getB1() {
        return b1;
    }

    public void setB1(double b1) {
        this.b1 = b1;
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getD1() {
        return d1;
    }

    public void setD1(double d1) {
        this.d1 = d1;
    }

    public double getFrom1() {
        return from1;
    }

    public void setFrom1(double from1) {
        this.from1 = from1;
    }

    public double getTo1() {
        return to1;
    }

    public void setTo1(double to1) {
        this.to1 = to1;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public boolean isUser_yin() {
        return user_yin;
    }

    public void setUser_yin(boolean user_yin) {
        this.user_yin = user_yin;
    }

    public double getYin_a() {
        return yin_a;
    }

    public void setYin_a(double yin_a) {
        this.yin_a = yin_a;
    }

    public String getYin_a_symbol() {
        return yin_a_symbol == null ? "" : yin_a_symbol;
    }

    public void setYin_a_symbol(String yin_a_symbol) {
        this.yin_a_symbol = yin_a_symbol == null ? "" : yin_a_symbol;
    }

    public double getYin_b() {
        return yin_b;
    }

    public void setYin_b(double yin_b) {
        this.yin_b = yin_b;
    }

    public String getYin_b_symbol() {
        return yin_b_symbol == null ? "" : yin_b_symbol;
    }

    public void setYin_b_symbol(String yin_b_symbol) {
        this.yin_b_symbol = yin_b_symbol == null ? "" : yin_b_symbol;
    }

    public boolean isUser_yang() {
        return user_yang;
    }

    public void setUser_yang(boolean user_yang) {
        this.user_yang = user_yang;
    }

    public double getYang_a() {
        return yang_a;
    }

    public void setYang_a(double yang_a) {
        this.yang_a = yang_a;
    }

    public String getYang_a_symbol() {
        return yang_a_symbol == null ? "" : yang_a_symbol;
    }

    public void setYang_a_symbol(String yang_a_symbol) {
        this.yang_a_symbol = yang_a_symbol == null ? "" : yang_a_symbol;
    }

    public double getYang_b() {
        return yang_b;
    }

    public void setYang_b(double yang_b) {
        this.yang_b = yang_b;
    }

    public String getYang_b_symbol() {
        return yang_b_symbol == null ? "" : yang_b_symbol;
    }

    public void setYang_b_symbol(String yang_b_symbol) {
        this.yang_b_symbol = yang_b_symbol == null ? "" : yang_b_symbol;
    }

    public boolean isUser_keyi() {
        return user_keyi;
    }

    public void setUser_keyi(boolean user_keyi) {
        this.user_keyi = user_keyi;
    }

    public double getKeyi_a() {
        return keyi_a;
    }

    public void setKeyi_a(double keyi_a) {
        this.keyi_a = keyi_a;
    }

    public String getKeyi_a_symbol() {
        return keyi_a_symbol == null ? "" : keyi_a_symbol;
    }

    public void setKeyi_a_symbol(String keyi_a_symbol) {
        this.keyi_a_symbol = keyi_a_symbol == null ? "" : keyi_a_symbol;
    }

    public double getKeyi_b() {
        return keyi_b;
    }

    public void setKeyi_b(double keyi_b) {
        this.keyi_b = keyi_b;
    }

    public String getKeyi_b_symbol() {
        return keyi_b_symbol == null ? "" : keyi_b_symbol;
    }

    public void setKeyi_b_symbol(String keyi_b_symbol) {
        this.keyi_b_symbol = keyi_b_symbol == null ? "" : keyi_b_symbol;
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

    public boolean getUser_keyi() {
        return this.user_keyi;
    }

    public boolean getUser_yang() {
        return this.user_yang;
    }

    public boolean getUser_yin() {
        return this.user_yin;
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
