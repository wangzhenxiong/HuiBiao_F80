package com.dy.huibiao_f80.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.text.SimpleDateFormat;

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
    private Long creationTime;
    private Long testingTime;
    private String testResult;
    @Transient
    public boolean check;






    @Generated(hash = 1481809501)
    public Sampling(Long id, String SamplingNumber, String SamplingName, String unitDetected,
            String testingUnit, Long creationTime, Long testingTime, String testResult) {
        this.id = id;
        this.SamplingNumber = SamplingNumber;
        this.SamplingName = SamplingName;
        this.unitDetected = unitDetected;
        this.testingUnit = testingUnit;
        this.creationTime = creationTime;
        this.testingTime = testingTime;
        this.testResult = testResult;
    }

    @Generated(hash = 1062560110)
    public Sampling() {
    }






    public String getTestingTimeyymmddhhssmm() {
        String format = null;
        if (testingTime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(testingTime);
        }
        return format;
    }

    public String getCreationTimeyymmddhhssmm() {
        String format = null;
        if (creationTime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(creationTime);
        }
        return format;
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

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Long getTestingTime() {
        return testingTime;
    }

    public void setTestingTime(Long testingTime) {
        this.testingTime = testingTime;
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
