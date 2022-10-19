package com.dy.huibiao_f80.greendao;

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
 * @data: 10/18/22 12:57 AM
 * Description:
 */
@Entity
public class Sampling {
    @Id(autoincrement = true)
    private Long id;
    private String SamplingNumber;
    private String SamplingName;
    private String unitDetected;
    private String testingUnit;
    private String creationTime;
    private String testingTime;
    private String testResult;
    public boolean check;

    @Generated(hash = 854045647)
    public Sampling(Long id, String SamplingNumber, String SamplingName,
            String unitDetected, String testingUnit, String creationTime,
            String testingTime, String testResult, boolean check) {
        this.id = id;
        this.SamplingNumber = SamplingNumber;
        this.SamplingName = SamplingName;
        this.unitDetected = unitDetected;
        this.testingUnit = testingUnit;
        this.creationTime = creationTime;
        this.testingTime = testingTime;
        this.testResult = testResult;
        this.check = check;
    }

    @Generated(hash = 1062560110)
    public Sampling() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSamplingNumber() {
        return SamplingNumber == null ? "" : SamplingNumber;
    }

    public void setSamplingNumber(String samplingNumber) {
        SamplingNumber = samplingNumber == null ? "" : samplingNumber;
    }

    public String getSamplingName() {
        return SamplingName == null ? "" : SamplingName;
    }

    public void setSamplingName(String samplingName) {
        SamplingName = samplingName == null ? "" : samplingName;
    }

    public String getUnitDetected() {
        return unitDetected == null ? "" : unitDetected;
    }

    public void setUnitDetected(String unitDetected) {
        this.unitDetected = unitDetected == null ? "" : unitDetected;
    }

    public String getTestingUnit() {
        return testingUnit == null ? "" : testingUnit;
    }

    public void setTestingUnit(String testingUnit) {
        this.testingUnit = testingUnit == null ? "" : testingUnit;
    }

    public String getCreationTime() {
        return creationTime == null ? "" : creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime == null ? "" : creationTime;
    }

    public String getTestingTime() {
        return testingTime == null ? "" : testingTime;
    }

    public void setTestingTime(String testingTime) {
        this.testingTime = testingTime == null ? "" : testingTime;
    }

    public String getTestResult() {
        return testResult == null ? "" : testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult == null ? "" : testResult;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean getCheck() {
        return this.check;
    }
}
