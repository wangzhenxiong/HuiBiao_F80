package com.dy.huibiao_f80.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

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
 * @data: 4/21/22 9:46 AM
 * Description:
 */
public class DetectionDetail implements Parcelable, IExpandable, MultiItemEntity {
    public int sn;
    public String sampleName;
    public String projectName;
    public String testResult;
    public String decisionoutcome;
    public String testTime;
    public String units;
    public String testPeople;


    protected DetectionDetail(Parcel in) {
        sn = in.readInt();
        projectName = in.readString();
        testResult = in.readString();
        decisionoutcome = in.readString();
    }

    public DetectionDetail() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sn);
        dest.writeString(projectName);
        dest.writeString(testResult);
        dest.writeString(decisionoutcome);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DetectionDetail> CREATOR = new Creator<DetectionDetail>() {
        @Override
        public DetectionDetail createFromParcel(Parcel in) {
            return new DetectionDetail(in);
        }

        @Override
        public DetectionDetail[] newArray(int size) {
            return new DetectionDetail[size];
        }
    };

    @Override
    public boolean isExpanded() {
        return false;
    }

    @Override
    public void setExpanded(boolean expanded) {

    }

    @Override
    public List getSubItems() {
        return new ArrayList();
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return 1;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? "" : projectName;
    }

    public String getTestResult() {
        return testResult == null ? "" : testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult == null ? "" : testResult;
    }

    public static Creator<DetectionDetail> getCREATOR() {
        return CREATOR;
    }

    public String getDecisionoutcome() {
        return decisionoutcome == null ? "" : decisionoutcome;
    }

    public void setDecisionoutcome(String decisionoutcome) {
        this.decisionoutcome = decisionoutcome == null ? "" : decisionoutcome;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getSampleName() {
        return sampleName == null ? "" : sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName == null ? "" : sampleName;
    }

    public String getTestTime() {
        return testTime == null ? "" : testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime == null ? "" : testTime;
    }

    public String getUnits() {
        return units == null ? "" : units;
    }

    public void setUnits(String units) {
        this.units = units == null ? "" : units;
    }

    public String getTestPeople() {
        return testPeople == null ? "" : testPeople;
    }

    public void setTestPeople(String testPeople) {
        this.testPeople = testPeople == null ? "" : testPeople;
    }
}
