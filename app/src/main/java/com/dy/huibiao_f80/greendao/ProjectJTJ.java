package com.dy.huibiao_f80.greendao;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

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
    private int testMethod;


    private double c1;
    private double t1A;
    private double t1B;
    private double c1_t1A;
    private double c1_t1B;


    private int testTime;
    private String version;


    @Generated(hash = 91319645)
    public ProjectJTJ(Long id, String projectName, String curveName,
            boolean isdefault, String standardName, int testMethod, double c1,
            double t1A, double t1B, double c1_t1A, double c1_t1B, int testTime,
            String version) {
        this.id = id;
        this.projectName = projectName;
        this.curveName = curveName;
        this.isdefault = isdefault;
        this.standardName = standardName;
        this.testMethod = testMethod;
        this.c1 = c1;
        this.t1A = t1A;
        this.t1B = t1B;
        this.c1_t1A = c1_t1A;
        this.c1_t1B = c1_t1B;
        this.testTime = testTime;
        this.version = version;
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

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getT1A() {
        return t1A;
    }

    public void setT1A(double t1A) {
        this.t1A = t1A;
    }

    public double getT1B() {
        return t1B;
    }

    public void setT1B(double t1B) {
        this.t1B = t1B;
    }

    public double getC1_t1A() {
        return c1_t1A;
    }

    public void setC1_t1A(double c1_t1A) {
        this.c1_t1A = c1_t1A;
    }

    public double getC1_t1B() {
        return c1_t1B;
    }

    public void setC1_t1B(double c1_t1B) {
        this.c1_t1B = c1_t1B;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
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
