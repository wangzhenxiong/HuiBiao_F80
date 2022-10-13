package com.dy.huibiao_f80.greendao;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;

import net.sourceforge.pinyin4j.PinyinHelper;

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
    public String getLetters() {
        char c = projectName.charAt(0);
        String[] strings = PinyinHelper.toGwoyeuRomatzyhStringArray(c);
        LogUtils.d(strings);
        return strings[0];
    }


    @Id(autoincrement = true)
    private Long id;
    private String projectName;
    private String curveName;
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

    private String a0;
    private String b0;
    private String c0;
    private String d0;
    private String from0;
    private String to0;

    private String a1;
    private String b1;
    private String c1;
    private String d1;
    private String from1;
    private String to1;

    private double a;
    private double b;
    private double c;
    private double d;

    private double yin_a;
    private double yin_b;
    private double yang_a;
    private double yang_b;
    private double keyi_a;
    private double keyi_b;
    private String version;


    @Generated(hash = 308348171)
    public ProjectFGGD(Long id, String projectName, String curveName, boolean isdefault,
            String standardName, int method, int waveLength, int warmTime, int testTime,
            String resultUnit, float controValue, String controValueLastTime, String a0,
            String b0, String c0, String d0, String from0, String to0, String a1, String b1,
            String c1, String d1, String from1, String to1, double a, double b, double c,
            double d, double yin_a, double yin_b, double yang_a, double yang_b,
            double keyi_a, double keyi_b, String version) {
        this.id = id;
        this.projectName = projectName;
        this.curveName = curveName;
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
        this.yin_a = yin_a;
        this.yin_b = yin_b;
        this.yang_a = yang_a;
        this.yang_b = yang_b;
        this.keyi_a = keyi_a;
        this.keyi_b = keyi_b;
        this.version = version;
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

    public String getA0() {
        return a0 == null ? "" : a0;
    }

    public void setA0(String a0) {
        this.a0 = a0 == null ? "" : a0;
    }

    public String getB0() {
        return b0 == null ? "" : b0;
    }

    public void setB0(String b0) {
        this.b0 = b0 == null ? "" : b0;
    }

    public String getC0() {
        return c0 == null ? "" : c0;
    }

    public void setC0(String c0) {
        this.c0 = c0 == null ? "" : c0;
    }

    public String getD0() {
        return d0 == null ? "" : d0;
    }

    public void setD0(String d0) {
        this.d0 = d0 == null ? "" : d0;
    }

    public String getFrom0() {
        return from0 == null ? "" : from0;
    }

    public void setFrom0(String from0) {
        this.from0 = from0 == null ? "" : from0;
    }

    public String getTo0() {
        return to0 == null ? "" : to0;
    }

    public void setTo0(String to0) {
        this.to0 = to0 == null ? "" : to0;
    }

    public String getA1() {
        return a1 == null ? "" : a1;
    }

    public void setA1(String a1) {
        this.a1 = a1 == null ? "" : a1;
    }

    public String getB1() {
        return b1 == null ? "" : b1;
    }

    public void setB1(String b1) {
        this.b1 = b1 == null ? "" : b1;
    }

    public String getC1() {
        return c1 == null ? "" : c1;
    }

    public void setC1(String c1) {
        this.c1 = c1 == null ? "" : c1;
    }

    public String getD1() {
        return d1 == null ? "" : d1;
    }

    public void setD1(String d1) {
        this.d1 = d1 == null ? "" : d1;
    }

    public String getFrom1() {
        return from1 == null ? "" : from1;
    }

    public void setFrom1(String from1) {
        this.from1 = from1 == null ? "" : from1;
    }

    public String getTo1() {
        return to1 == null ? "" : to1;
    }

    public void setTo1(String to1) {
        this.to1 = to1 == null ? "" : to1;
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

    public double getYin_a() {
        return yin_a;
    }

    public void setYin_a(double yin_a) {
        this.yin_a = yin_a;
    }

    public double getYin_b() {
        return yin_b;
    }

    public void setYin_b(double yin_b) {
        this.yin_b = yin_b;
    }

    public double getYang_a() {
        return yang_a;
    }

    public void setYang_a(double yang_a) {
        this.yang_a = yang_a;
    }

    public double getYang_b() {
        return yang_b;
    }

    public void setYang_b(double yang_b) {
        this.yang_b = yang_b;
    }

    public double getKeyi_a() {
        return keyi_a;
    }

    public void setKeyi_a(double keyi_a) {
        this.keyi_a = keyi_a;
    }

    public double getKeyi_b() {
        return keyi_b;
    }

    public void setKeyi_b(double keyi_b) {
        this.keyi_b = keyi_b;
    }

    public String getVersion() {
        return version == null ? "" : version;
    }

    public void setVersion(String version) {
        this.version = version == null ? "" : version;
    }

    public boolean getIsdefault() {
        return this.isdefault;
    }
}
