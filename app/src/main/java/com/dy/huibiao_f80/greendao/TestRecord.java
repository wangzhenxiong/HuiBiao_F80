package com.dy.huibiao_f80.greendao;

import android.os.Parcel;
import android.os.Parcelable;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.dy.huibiao_f80.BuildConfig;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.DetectionDetail;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.bean.OutMoudle;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 王振雄 on 2017/5/25.
 */
@Entity
public class TestRecord extends GalleryBean implements Parcelable, IExpandable, MultiItemEntity {
    @Id(autoincrement = true)
    private Long id; //数据库id
    private String sysCode;//检测唯一编号
    private int gallery;//通道id
    private String samplenum;//样品编号
    private String samplename;//样品名称
    private String sampletype;//样品种类
    private String foodCode;//样品种类别编号
    private String symbol;//判定符号
    private String cov;//判定值sy
    private String cov_unit;//判定单位
    private String stand_num;//判定标准号

    private String prosecutedunits;//被检单位
    private String prosecutedunits_adress;//被检单位地址
    private double dilutionratio = 1; //稀释倍数
    private double everyresponse = 1;//反应液滴数
    private String controlvalue;//对照值
    private String serialNumber;//流水号

    private String testresult;//检测结果值
    private String decisionoutcome;//判定结果
    private String inspector; //检测人员
    private Long testingtime;//检测时间
    private double longitude;//经度
    private double latitude;//纬度
    private String testsite;//检测地点

    private String test_method;//检测方法
    private String test_project;//检测项目
    private String test_moudle;//检测模块

    private String planName;//任务名称

    private int isupload = 2;//是否上传

    private String unique_sample;//样品名称唯一编号
    private String unique_method;//检测方法唯一编号
    private String unique_testproject;//检测项目唯一编号
    private String unique_beunit;//被检单位唯一编号
    private String unique_task;//检测任务唯一编号
    private String platform_tag;//产生记录时使用的平台

    //使用平台时可能会有检测单位
    private String test_unit_id;//检测单位唯一id
    private String test_unit_name;//检测单位名称
    private String test_unit_reserved;//检测单位预留字段


    //增加数据库字段
    private String sampleplace;//样品产地

    /**
     * 0 正常数据
     * 1 为重检数据
     * 2 已经重检
     */
    private int retest = 0;//重检
    private String parentSysCode;//重检的syscode

    private String reservedfield1;//预留字段1
    private String reservedfield2;//预留字段2
    private String reservedfield3;//预留字段3
    private String reservedfield4;//预留字段4
    private String reservedfield5;//预留字段5
    private String reservedfield6;//预留字段6
    private String reservedfield7;//预留字段7
    private String reservedfield8;//预留字段8
    private String reservedfield9;//预留字段9
    private String reservedfield10;//预留字段10
    //多联卡检测记录增加保存信息
    private String test_project1;
    private String test_project2;
    private String test_project3;
    private String test_method1;
    private String test_method2;
    private String test_method3;

    private String unique_testproject1;
    private String unique_testproject2;
    private String unique_testproject3;

    private String testresult1;
    private String testresult2;
    private String testresult3;
    private String decisionoutcome1;
    private String decisionoutcome2;
    private String decisionoutcome3;


    protected TestRecord(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        sysCode = in.readString();
        gallery = in.readInt();
        samplenum = in.readString();
        samplename = in.readString();
        sampletype = in.readString();
        foodCode = in.readString();
        symbol = in.readString();
        cov = in.readString();
        cov_unit = in.readString();
        stand_num = in.readString();
        prosecutedunits = in.readString();
        prosecutedunits_adress = in.readString();
        dilutionratio = in.readDouble();
        everyresponse = in.readDouble();
        controlvalue = in.readString();
        serialNumber = in.readString();
        testresult = in.readString();
        decisionoutcome = in.readString();
        inspector = in.readString();
        if (in.readByte() == 0) {
            testingtime = null;
        } else {
            testingtime = in.readLong();
        }
        longitude = in.readDouble();
        latitude = in.readDouble();
        testsite = in.readString();
        test_method = in.readString();
        test_project = in.readString();
        test_moudle = in.readString();
        planName = in.readString();
        isupload = in.readInt();
        unique_sample = in.readString();
        unique_method = in.readString();
        unique_testproject = in.readString();
        unique_beunit = in.readString();
        unique_task = in.readString();
        platform_tag = in.readString();
        test_unit_id = in.readString();
        test_unit_name = in.readString();
        test_unit_reserved = in.readString();
        sampleplace = in.readString();
        retest = in.readInt();
        parentSysCode = in.readString();
        reservedfield1 = in.readString();
        reservedfield2 = in.readString();
        reservedfield3 = in.readString();
        reservedfield4 = in.readString();
        reservedfield5 = in.readString();
        reservedfield6 = in.readString();
        reservedfield7 = in.readString();
        reservedfield8 = in.readString();
        reservedfield9 = in.readString();
        reservedfield10 = in.readString();
        test_project1 = in.readString();
        test_project2 = in.readString();
        test_project3 = in.readString();
        test_method1 = in.readString();
        test_method2 = in.readString();
        test_method3 = in.readString();
        unique_testproject1 = in.readString();
        unique_testproject2 = in.readString();
        unique_testproject3 = in.readString();
        testresult1 = in.readString();
        testresult2 = in.readString();
        testresult3 = in.readString();
        decisionoutcome1 = in.readString();
        decisionoutcome2 = in.readString();
        decisionoutcome3 = in.readString();
        sn = in.readString();
        mExpandable = in.readByte() != 0;
        mSubItems = in.createTypedArrayList(DetectionDetail.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(sysCode);
        dest.writeInt(gallery);
        dest.writeString(samplenum);
        dest.writeString(samplename);
        dest.writeString(sampletype);
        dest.writeString(foodCode);
        dest.writeString(symbol);
        dest.writeString(cov);
        dest.writeString(cov_unit);
        dest.writeString(stand_num);
        dest.writeString(prosecutedunits);
        dest.writeString(prosecutedunits_adress);
        dest.writeDouble(dilutionratio);
        dest.writeDouble(everyresponse);
        dest.writeString(controlvalue);
        dest.writeString(serialNumber);
        dest.writeString(testresult);
        dest.writeString(decisionoutcome);
        dest.writeString(inspector);
        if (testingtime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(testingtime);
        }
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(testsite);
        dest.writeString(test_method);
        dest.writeString(test_project);
        dest.writeString(test_moudle);
        dest.writeString(planName);
        dest.writeInt(isupload);
        dest.writeString(unique_sample);
        dest.writeString(unique_method);
        dest.writeString(unique_testproject);
        dest.writeString(unique_beunit);
        dest.writeString(unique_task);
        dest.writeString(platform_tag);
        dest.writeString(test_unit_id);
        dest.writeString(test_unit_name);
        dest.writeString(test_unit_reserved);
        dest.writeString(sampleplace);
        dest.writeInt(retest);
        dest.writeString(parentSysCode);
        dest.writeString(reservedfield1);
        dest.writeString(reservedfield2);
        dest.writeString(reservedfield3);
        dest.writeString(reservedfield4);
        dest.writeString(reservedfield5);
        dest.writeString(reservedfield6);
        dest.writeString(reservedfield7);
        dest.writeString(reservedfield8);
        dest.writeString(reservedfield9);
        dest.writeString(reservedfield10);
        dest.writeString(test_project1);
        dest.writeString(test_project2);
        dest.writeString(test_project3);
        dest.writeString(test_method1);
        dest.writeString(test_method2);
        dest.writeString(test_method3);
        dest.writeString(unique_testproject1);
        dest.writeString(unique_testproject2);
        dest.writeString(unique_testproject3);
        dest.writeString(testresult1);
        dest.writeString(testresult2);
        dest.writeString(testresult3);
        dest.writeString(decisionoutcome1);
        dest.writeString(decisionoutcome2);
        dest.writeString(decisionoutcome3);
        dest.writeString(sn);
        dest.writeByte((byte) (mExpandable ? 1 : 0));
        dest.writeTypedList(mSubItems);
    }

    public static final Creator<TestRecord> CREATOR = new Creator<TestRecord>() {
        @Override
        public TestRecord createFromParcel(Parcel in) {
            return new TestRecord(in);
        }

        @Override
        public TestRecord[] newArray(int size) {
            return new TestRecord[size];
        }
    };

    public String getTestresult1() {
        return testresult1 == null ? "" : testresult1;
    }

    public void setTestresult1(String testresult1) {
        this.testresult1 = testresult1 == null ? "" : testresult1;
    }

    public String getTestresult2() {
        return testresult2 == null ? "" : testresult2;
    }

    public void setTestresult2(String testresult2) {
        this.testresult2 = testresult2 == null ? "" : testresult2;
    }

    public String getTestresult3() {
        return testresult3 == null ? "" : testresult3;
    }

    public void setTestresult3(String testresult3) {
        this.testresult3 = testresult3 == null ? "" : testresult3;
    }

    public String getDecisionoutcome1() {
        return decisionoutcome1 == null ? "" : decisionoutcome1;
    }

    public void setDecisionoutcome1(String decisionoutcome1) {
        this.decisionoutcome1 = decisionoutcome1 == null ? "" : decisionoutcome1;
    }

    public String getDecisionoutcome2() {
        return decisionoutcome2 == null ? "" : decisionoutcome2;
    }

    public void setDecisionoutcome2(String decisionoutcome2) {
        this.decisionoutcome2 = decisionoutcome2 == null ? "" : decisionoutcome2;
    }

    public String getDecisionoutcome3() {
        return decisionoutcome3 == null ? "" : decisionoutcome3;
    }

    public void setDecisionoutcome3(String decisionoutcome3) {
        this.decisionoutcome3 = decisionoutcome3 == null ? "" : decisionoutcome3;
    }


    public String getTest_method1() {
        return test_method1 == null ? "" : test_method1;
    }

    public void setTest_method1(String test_method1) {
        this.test_method1 = test_method1 == null ? "" : test_method1;
    }

    public String getTest_method2() {
        return test_method2 == null ? "" : test_method2;
    }

    public void setTest_method2(String test_method2) {
        this.test_method2 = test_method2 == null ? "" : test_method2;
    }

    public String getTest_method3() {
        return test_method3 == null ? "" : test_method3;
    }

    public void setTest_method3(String test_method3) {
        this.test_method3 = test_method3 == null ? "" : test_method3;
    }



    public String getTest_project1() {
        return test_project1 == null ? "" : test_project1;
    }

    public void setTest_project1(String test_project1) {
        this.test_project1 = test_project1 == null ? "" : test_project1;
    }

    public String getTest_project2() {
        return test_project2 == null ? "" : test_project2;
    }

    public void setTest_project2(String test_project2) {
        this.test_project2 = test_project2 == null ? "" : test_project2;
    }

    public String getTest_project3() {
        return test_project3 == null ? "" : test_project3;
    }

    public void setTest_project3(String test_project3) {
        this.test_project3 = test_project3 == null ? "" : test_project3;
    }

    public String getUnique_testproject1() {
        return unique_testproject1 == null ? "" : unique_testproject1;
    }

    public void setUnique_testproject1(String unique_testproject1) {
        this.unique_testproject1 = unique_testproject1 == null ? "" : unique_testproject1;
    }

    public String getUnique_testproject2() {
        return unique_testproject2 == null ? "" : unique_testproject2;
    }

    public void setUnique_testproject2(String unique_testproject2) {
        this.unique_testproject2 = unique_testproject2 == null ? "" : unique_testproject2;
    }

    public String getUnique_testproject3() {
        return unique_testproject3 == null ? "" : unique_testproject3;
    }

    public void setUnique_testproject3(String unique_testproject3) {
        this.unique_testproject3 = unique_testproject3 == null ? "" : unique_testproject3;
    }

    public boolean ismExpandable() {
        return mExpandable;
    }

    public void setmExpandable(boolean mExpandable) {
        this.mExpandable = mExpandable;
    }

    public List<DetectionDetail> getmSubItems() {
        return mSubItems;
    }

    public void setmSubItems(List<DetectionDetail> mSubItems) {
        this.mSubItems = mSubItems;
    }



    public String getSn() {
        return sn == null ? "" : sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? "" : sn;
    }

    @Transient
    private String sn;







    @Override
    public String toString() {
        return "Detection_Record_FGGD_NC{" +
                "id=" + id +
                ", sysCode='" + sysCode + '\'' +
                ", gallery=" + gallery +
                ", samplenum='" + samplenum + '\'' +
                ", samplename='" + samplename + '\'' +
                ", sampletype='" + sampletype + '\'' +
                ", foodCode='" + foodCode + '\'' +
                ", symbol='" + symbol + '\'' +
                ", cov='" + cov + '\'' +
                ", cov_unit='" + cov_unit + '\'' +
                ", stand_num='" + stand_num + '\'' +
                ", prosecutedunits='" + prosecutedunits + '\'' +
                ", prosecutedunits_adress='" + prosecutedunits_adress + '\'' +
                ", dilutionratio=" + dilutionratio +
                ", everyresponse=" + everyresponse +
                ", controlvalue='" + controlvalue + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", testresult='" + testresult + '\'' +
                ", decisionoutcome='" + decisionoutcome + '\'' +
                ", inspector='" + inspector + '\'' +
                ", testingtime=" + testingtime +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", testsite='" + testsite + '\'' +
                ", test_method='" + test_method + '\'' +
                ", test_project='" + test_project + '\'' +
                ", test_moudle='" + test_moudle + '\'' +
                ", planName='" + planName + '\'' +
                ", isupload=" + isupload +
                ", unique_sample='" + unique_sample + '\'' +
                ", unique_method='" + unique_method + '\'' +
                ", unique_testproject='" + unique_testproject + '\'' +
                ", unique_beunit='" + unique_beunit + '\'' +
                ", unique_task='" + unique_task + '\'' +
                ", platform_tag='" + platform_tag + '\'' +
                ", test_unit_id='" + test_unit_id + '\'' +
                ", test_unit_name='" + test_unit_name + '\'' +
                ", test_unit_reserved='" + test_unit_reserved + '\'' +
                ", sampleplace='" + sampleplace + '\'' +
                ", retest=" + retest +
                ", parentSysCode='" + parentSysCode + '\'' +
                ", reservedfield1='" + reservedfield1 + '\'' +
                ", reservedfield2='" + reservedfield2 + '\'' +
                ", reservedfield3='" + reservedfield3 + '\'' +
                ", reservedfield4='" + reservedfield4 + '\'' +
                ", reservedfield5='" + reservedfield5 + '\'' +
                ", reservedfield6='" + reservedfield6 + '\'' +
                ", reservedfield7='" + reservedfield7 + '\'' +
                ", reservedfield8='" + reservedfield8 + '\'' +
                ", reservedfield9='" + reservedfield9 + '\'' +
                ", reservedfield10='" + reservedfield10 + '\'' +
                ", test_project1='" + test_project1 + '\'' +
                ", test_project2='" + test_project2 + '\'' +
                ", test_project3='" + test_project3 + '\'' +
                ", test_method1='" + test_method1 + '\'' +
                ", test_method2='" + test_method2 + '\'' +
                ", test_method3='" + test_method3 + '\'' +
                ", unique_testproject1='" + unique_testproject1 + '\'' +
                ", unique_testproject2='" + unique_testproject2 + '\'' +
                ", unique_testproject3='" + unique_testproject3 + '\'' +
                ", testresult1='" + testresult1 + '\'' +
                ", testresult2='" + testresult2 + '\'' +
                ", testresult3='" + testresult3 + '\'' +
                ", decisionoutcome1='" + decisionoutcome1 + '\'' +
                ", decisionoutcome2='" + decisionoutcome2 + '\'' +
                ", decisionoutcome3='" + decisionoutcome3 + '\'' +
                ", sn='" + sn + '\'' +
                ", mExpandable=" + mExpandable +
                ", mSubItems=" + mSubItems +
                '}';
    }


    /**
     * 0 正常数据
     * 1 为重检数据
     * 2 已经重检
     */
    public int getRetest() {
        return retest;
    }

    public void setRetest(int retest) {
        this.retest = retest;
    }

    public String getParentSysCode() {
        return parentSysCode == null ? "" : parentSysCode;
    }

    public void setParentSysCode(String parentSysCode) {
        this.parentSysCode = parentSysCode == null ? "" : parentSysCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGallery() {
        return gallery;
    }

    public void setGallery(int gallery) {
        this.gallery = gallery;
    }

    public String getSamplenum() {
        return samplenum == null ? "" : samplenum;
    }

    public void setSamplenum(String samplenum) {
        this.samplenum = samplenum == null ? "" : samplenum;
    }

    public String getSamplename() {
        return samplename == null ? "" : samplename;
    }

    /**
     * 输出一个固定最小长度的样品名称<p>
     * 小于这个长度的用空格补齐 <p>
     * 大于这个长度的按照实际长度输出 <p>
     *
     * @param bytelength 需要输出的字节长度，是字节长度，一个中文两字节，英文一个字节
     * @return
     */
    public String getSamplenamePrint(int bytelength) {

        String s = samplename == null ? "" : samplename;

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return s;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename == null ? "" : samplename;
    }

    public void setSamplenameByself(String samplename) {

        this.samplename = samplename == null ? "" : samplename;
    }

    public String getCov() {
        if ("com.wangzx.dy.sample_DY6310".equals(BuildConfig.APPLICATION_ID)) {
            if (cov == null || "".equals(cov)) {
                return "-&-";
            }
        }
        return cov == null ? "" : cov;
    }

    public void setCov(String cov) {
        LogUtils.d(cov);
        this.cov = cov == null ? "" : cov;
    }

    public String getSymbol() {
        if ("com.wangzx.dy.sample_DY6310".equals(BuildConfig.APPLICATION_ID)) {
            if (symbol == null || "".equals(symbol)) {
                return "-&-";
            }
        }
        return symbol == null ? "" : symbol;
    }

    public String getSymbolText() {

        String s = symbol == null ? "" : symbol;
        if (s.equals("<")) {
            return "小于";
        } else if (s.equals(">")) {
            return "大于";
        }
        return s;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? "" : symbol;
    }

    public String getCov_unit() {
        if ("com.wangzx.dy.sample_DY6310".equals(BuildConfig.APPLICATION_ID)) {
            if (cov_unit == null || "".equals(cov_unit)) {
                return "-&-";
            }
        }
        return cov_unit == null ? "" : cov_unit;
    }

    public void setCov_unit(String cov_unit) {
        this.cov_unit = cov_unit == null ? "" : cov_unit;
    }

    public String getStand_num() {
        return stand_num == null ? "" : stand_num;
    }

    public void setStand_num(String stand_num) {
        this.stand_num = stand_num == null ? "" : stand_num;
    }

    public String getProsecutedunits() {
        return prosecutedunits == null ? "" : prosecutedunits;
    }

    public void setProsecutedunits(String prosecutedunits) {
        this.prosecutedunits = prosecutedunits == null ? "" : prosecutedunits;
    }

    public String getProsecutedunits_adress() {
        return prosecutedunits_adress == null ? "" : prosecutedunits_adress;
    }

    public void setProsecutedunits_adress(String prosecutedunits_adress) {
        this.prosecutedunits_adress = prosecutedunits_adress == null ? "" : prosecutedunits_adress;
    }

    public double getDilutionratio() {
        return dilutionratio;
    }

    public void setDilutionratio(double dilutionratio) {
        this.dilutionratio = dilutionratio;
    }

    public double getEveryresponse() {
        return everyresponse;
    }

    public void setEveryresponse(double everyresponse) {
        this.everyresponse = everyresponse;
    }

    public String getControlvalue() {
        return controlvalue == null ? "" : controlvalue;
    }

    public void setControlvalue(String controlvalue) {
        this.controlvalue = controlvalue == null ? "" : controlvalue;
    }

    public String getSerialNumber() {
        return serialNumber == null ? "" : serialNumber;
    }

    public int getIntSerialNumber() {
        try {

            return serialNumber == null ? 0 : Integer.valueOf(serialNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? "" : serialNumber;
    }

    public String getTestresult() {
        if ("com.wangzx.dy.sample_DY6310".equals(BuildConfig.APPLICATION_ID)) {
            if (testresult == null || "".equals(testresult)) {
                return "-&-";
            }
        }
        return testresult == null ? "" : testresult;
    }


    public String getTestresultPrint(int bytelength) {
        if ("com.wangzx.dy.sample_DY6310".equals(BuildConfig.APPLICATION_ID)) {
            if (testresult == null || "".equals(testresult)) {
                return "-&-";
            }
        }
        String s;

        if ("com.wangzx.dy.sample_DY7500".equals(BuildConfig.APPLICATION_ID)) {
            s=reservedfield9==null?testresult:reservedfield9;
        } else {
            if (Constants.PLATFORM_TAG == 11) {
                if (getTest_Moudle().contains("胶体金")) {
                    s = testresult == null ? "" : testresult.equals("阴性") ? "未检出" : "检出";
                }else {
                    s = testresult == null ? "" : testresult;
                }
            } else {
                s = testresult == null ? "" : testresult;

            }
        }

       

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    public void setTestresult(String testresult) {
        this.testresult = testresult == null ? "" : testresult;
    }

    public String getDecisionoutcome() {
        if ("com.wangzx.dy.sample_DY6310".equals(BuildConfig.APPLICATION_ID)) {
            if (decisionoutcome == null || "".equals(decisionoutcome)) {
                return "-&-";
            }
        }
        return decisionoutcome == null ? "" : decisionoutcome;
    }

    public String getDecisionoutcomePrint(int bytelength) {
        if ("com.wangzx.dy.sample_DY6310".equals(BuildConfig.APPLICATION_ID)) {
            if (decisionoutcome == null || "".equals(decisionoutcome)) {
                return "-&-";
            }
        }
        String s;
        if (Constants.PLATFORM_TAG == 11) {
            s = decisionoutcome == null ? "" : decisionoutcome.equals("合格") ? "阴性" : "阳性";

        } else {
            s = decisionoutcome == null ? "" : decisionoutcome;

        }
        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    public void setDecisionoutcome(String decisionoutcome) {
        this.decisionoutcome = decisionoutcome == null ? "" : decisionoutcome;
    }

    public String getInspector() {
        return inspector == null ? "" : inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector == null ? "" : inspector;
    }

    public Long getTestingtime() {
        return testingtime;
    }

    public String getdfTestingtimeYY_MM_DD() {
        String format = null;
        if (testingtime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd").format(testingtime);
        }
        return format;
    }

    public String getdfTestingtimeYYMMDD() {
        String format = null;
        if (testingtime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("yyyyMMdd").format(testingtime);
        }
        return format;
    }

    /**
     * @param s 需要转换的样式 yymmdd ，yyyy-MM-dd
     * @return
     */
    public String getdfTestingtime(String s) {
        String format = null;
        if (testingtime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat(s).format(testingtime);
        }
        return format;
    }


    public String getdfTestingtimeyy_mm_dd_hh_mm_ss() {
        String format = null;
        if (testingtime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(testingtime);
        }
        return format;
    }

    public String getdfTestingtimeyyImmIdd_hh_mm_ss() {
        String format = null;
        if (testingtime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(testingtime);
        }
        return format;
    }

    public String getdfTestingtimeyy_mm_ddThh_mm_ss() {
        String format = null;
        if (testingtime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(testingtime);
        }
        return format;
    }

    public String getdfTestingtimeyyymmddhhmmss() {
        String format = null;
        if (testingtime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("yyyyMMddHHmmss").format(testingtime);
        }
        return format;
    }

    public String getdfTestingtimeyy_mm_dd_hh_mm() {
        String format = null;
        if (testingtime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(testingtime);
        }
        return format;
    }

    public String getdfTestingtimehh_mm_ss() {
        String format = null;
        if (testingtime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("HH:mm:ss").format(testingtime);
        }

        return format;
    }

    public String getdfTestingtimeyymmddhhmmss() {
        String format = null;
        if (testingtime == null) {
            format = "";
        } else {
            format = new SimpleDateFormat("yyyyMMddHHmmss").format(testingtime);
        }

        return format;
    }

    public void setTestingtime(Long testingtime) {

        this.testingtime = testingtime;

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTestsite() {
        return testsite == null ? "" : testsite;
    }

    public void setTestsite(String testsite) {
        this.testsite = testsite == null ? "" : testsite;
    }


    public String getTest_method() {
        if (test_method == null) {
            return "";
        } else {
            if ("0".equals(test_method)) {
                return MyAppLocation.myAppLocation.getString(R.string.mothod1);
            } else if ("1".equals(test_method)) {
                return MyAppLocation.myAppLocation.getString(R.string.mothod2);
            } else if ("2".equals(test_method)) {
                return MyAppLocation.myAppLocation.getString(R.string.mothod3);
            } else if ("3".equals(test_method)) {
                return MyAppLocation.myAppLocation.getString(R.string.mothod4);
            } else {
                return test_method;
            }
        }
    }

    public void setTest_method(String test_method) {
        this.test_method = test_method == null ? "" : test_method;
    }

    public String getTest_project() {
        return test_project == null ? "" : test_project;
    }

    public void setTest_project(String test_project) {
        this.test_project = test_project == null ? "" : test_project;
    }

    public String getTest_Moudle() {
        return test_moudle == null ? "" : test_moudle;
    }

    public void setTest_Moudle(String test_moudle) {
        this.test_moudle = test_moudle == null ? "" : test_moudle;
    }

    public String getSysCode() {
        return sysCode == null ? "" : sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? "" : sysCode.replace("-", "");
    }

    public String getSampletype() {
        return sampletype == null ? "" : sampletype;
    }

    public void setSampletype(String sampletype) {
        this.sampletype = sampletype == null ? "" : sampletype;
    }


    public String getFoodCode() {
        return foodCode == null ? "" : foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode == null ? "" : foodCode;
    }


    public int getIsupload() {
        return isupload;
    }

    public void setIsupload(int isupload) {
        this.isupload = isupload;
    }


    public TestRecord() {
    }

    @Generated(hash = 1714602023)
    public TestRecord(Long id, String sysCode, int gallery, String samplenum, String samplename,
            String sampletype, String foodCode, String symbol, String cov, String cov_unit,
            String stand_num, String prosecutedunits, String prosecutedunits_adress,
            double dilutionratio, double everyresponse, String controlvalue, String serialNumber,
            String testresult, String decisionoutcome, String inspector, Long testingtime,
            double longitude, double latitude, String testsite, String test_method,
            String test_project, String test_moudle, String planName, int isupload,
            String unique_sample, String unique_method, String unique_testproject,
            String unique_beunit, String unique_task, String platform_tag, String test_unit_id,
            String test_unit_name, String test_unit_reserved, String sampleplace, int retest,
            String parentSysCode, String reservedfield1, String reservedfield2, String reservedfield3,
            String reservedfield4, String reservedfield5, String reservedfield6, String reservedfield7,
            String reservedfield8, String reservedfield9, String reservedfield10, String test_project1,
            String test_project2, String test_project3, String test_method1, String test_method2,
            String test_method3, String unique_testproject1, String unique_testproject2,
            String unique_testproject3, String testresult1, String testresult2, String testresult3,
            String decisionoutcome1, String decisionoutcome2, String decisionoutcome3) {
        this.id = id;
        this.sysCode = sysCode;
        this.gallery = gallery;
        this.samplenum = samplenum;
        this.samplename = samplename;
        this.sampletype = sampletype;
        this.foodCode = foodCode;
        this.symbol = symbol;
        this.cov = cov;
        this.cov_unit = cov_unit;
        this.stand_num = stand_num;
        this.prosecutedunits = prosecutedunits;
        this.prosecutedunits_adress = prosecutedunits_adress;
        this.dilutionratio = dilutionratio;
        this.everyresponse = everyresponse;
        this.controlvalue = controlvalue;
        this.serialNumber = serialNumber;
        this.testresult = testresult;
        this.decisionoutcome = decisionoutcome;
        this.inspector = inspector;
        this.testingtime = testingtime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.testsite = testsite;
        this.test_method = test_method;
        this.test_project = test_project;
        this.test_moudle = test_moudle;
        this.planName = planName;
        this.isupload = isupload;
        this.unique_sample = unique_sample;
        this.unique_method = unique_method;
        this.unique_testproject = unique_testproject;
        this.unique_beunit = unique_beunit;
        this.unique_task = unique_task;
        this.platform_tag = platform_tag;
        this.test_unit_id = test_unit_id;
        this.test_unit_name = test_unit_name;
        this.test_unit_reserved = test_unit_reserved;
        this.sampleplace = sampleplace;
        this.retest = retest;
        this.parentSysCode = parentSysCode;
        this.reservedfield1 = reservedfield1;
        this.reservedfield2 = reservedfield2;
        this.reservedfield3 = reservedfield3;
        this.reservedfield4 = reservedfield4;
        this.reservedfield5 = reservedfield5;
        this.reservedfield6 = reservedfield6;
        this.reservedfield7 = reservedfield7;
        this.reservedfield8 = reservedfield8;
        this.reservedfield9 = reservedfield9;
        this.reservedfield10 = reservedfield10;
        this.test_project1 = test_project1;
        this.test_project2 = test_project2;
        this.test_project3 = test_project3;
        this.test_method1 = test_method1;
        this.test_method2 = test_method2;
        this.test_method3 = test_method3;
        this.unique_testproject1 = unique_testproject1;
        this.unique_testproject2 = unique_testproject2;
        this.unique_testproject3 = unique_testproject3;
        this.testresult1 = testresult1;
        this.testresult2 = testresult2;
        this.testresult3 = testresult3;
        this.decisionoutcome1 = decisionoutcome1;
        this.decisionoutcome2 = decisionoutcome2;
        this.decisionoutcome3 = decisionoutcome3;
    }


    public String getUnique_sample() {
        return unique_sample == null ? "" : unique_sample;
    }

    public void setUnique_sample(String unique_sample) {
        this.unique_sample = unique_sample == null ? "" : unique_sample;
    }

    public String getUnique_method() {
        return unique_method == null ? "" : unique_method;
    }

    public void setUnique_method(String unique_method) {
        this.unique_method = unique_method == null ? "" : unique_method;
    }

    public String getUnique_testproject() {
        return unique_testproject == null ? "" : unique_testproject;
    }

    public void setUnique_testproject(String unique_testproject) {
        this.unique_testproject = unique_testproject == null ? "" : unique_testproject;
    }

    public String getUnique_beunit() {
        return unique_beunit == null ? "" : unique_beunit;
    }

    public void setUnique_beunit(String unique_beunit) {
        this.unique_beunit = unique_beunit == null ? "" : unique_beunit;
    }

    public String getUnique_task() {
        return unique_task == null ? "" : unique_task;
    }

    public void setUnique_task(String unique_task) {
        this.unique_task = unique_task == null ? "" : unique_task;
    }


    public String getPlatform_tag() {
        return platform_tag == null ? "" : platform_tag;
    }

    public void setPlatform_tag(String platform_tag) {
        this.platform_tag = platform_tag == null ? "" : platform_tag;
    }


    public String getPlanName() {
        return planName == null ? "" : planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? "" : planName;
    }

    public String getTest_unit_name() {
        return test_unit_name == null ? "" : test_unit_name;
    }

    public void setTest_unit_name(String test_unit_name) {
        this.test_unit_name = test_unit_name == null ? "" : test_unit_name;
    }

    public String getTest_unit_id() {
        return test_unit_id == null ? "" : test_unit_id;
    }

    public void setTest_unit_id(String test_unit_id) {
        this.test_unit_id = test_unit_id == null ? "" : test_unit_id;
    }

    public String getTest_unit_reserved() {
        return test_unit_reserved == null ? "" : test_unit_reserved;
    }

    public void setTest_unit_reserved(String test_unit_reserved) {
        this.test_unit_reserved = test_unit_reserved == null ? "" : test_unit_reserved;
    }

   

    public String getSampleplace() {
        return this.sampleplace == null ? "" : sampleplace;
    }

    public void setSampleplace(String sampleplace) {
        if (this.getRetest() == 1) {
            this.setRetest(0);
            this.setParentSysCode(null);
        }
        this.sampleplace = sampleplace;
    }


    public String getReservedfield1() {
        return reservedfield1 == null ? "" : reservedfield1;
    }

    public void setReservedfield1(String reservedfield1) {
        this.reservedfield1 = reservedfield1 == null ? "" : reservedfield1;
    }

    public String getReservedfield2() {
        return reservedfield2 == null ? "" : reservedfield2;
    }

    public void setReservedfield2(String reservedfield2) {
        this.reservedfield2 = reservedfield2 == null ? "" : reservedfield2;
    }

    public String getReservedfield3() {
        return reservedfield3 == null ? "" : reservedfield3;
    }

    public void setReservedfield3(String reservedfield3) {
        this.reservedfield3 = reservedfield3 == null ? "" : reservedfield3;
    }

    public String getReservedfield4() {
        return reservedfield4 == null ? "" : reservedfield4;
    }

    public void setReservedfield4(String reservedfield4) {
        this.reservedfield4 = reservedfield4 == null ? "" : reservedfield4;
    }

    public String getReservedfield5() {
        return reservedfield5 == null ? "" : reservedfield5;
    }

    public void setReservedfield5(String reservedfield5) {
        this.reservedfield5 = reservedfield5 == null ? "" : reservedfield5;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public String getReservedfield6() {
        return reservedfield6 == null ? "" : reservedfield6;
    }

    public void setReservedfield6(String reservedfield6) {
        this.reservedfield6 = reservedfield6 == null ? "" : reservedfield6;
    }

    public String getReservedfield7() {
        return reservedfield7 == null ? "" : reservedfield7;
    }

    public void setReservedfield7(String reservedfield7) {
        this.reservedfield7 = reservedfield7 == null ? "" : reservedfield7;
    }

    public String getReservedfield8() {
        return reservedfield8 == null ? "" : reservedfield8;
    }

    public void setReservedfield8(String reservedfield8) {
        this.reservedfield8 = reservedfield8 == null ? "" : reservedfield8;
    }

    public String getReservedfield9() {
        return reservedfield9 == null ? "" : reservedfield9;
    }

    public void setReservedfield9(String reservedfield9) {
        this.reservedfield9 = reservedfield9 == null ? "" : reservedfield9;
    }

    public String getReservedfield10() {
        return reservedfield10 == null ? "" : reservedfield10;
    }

    public void setReservedfield10(String reservedfield10) {
        this.reservedfield10 = reservedfield10 == null ? "" : reservedfield10;
    }

    @Override
    public boolean isExpanded() {
        return mExpandable;
    }

    @Override
    public void setExpanded(boolean expanded) {
        mExpandable = expanded;
    }

    @Transient
    protected boolean mExpandable = false;
    @Transient
    protected List<DetectionDetail> mSubItems;

    public void setSubItems(List<DetectionDetail> subItems) {
        mSubItems = subItems;
    }

    @Override
    public List<DetectionDetail> getSubItems() {
        return mSubItems;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public String getTest_moudle() {
        return this.test_moudle;
    }

    public void setTest_moudle(String test_moudle) {
        this.test_moudle = test_moudle;
    }


    public String getPrintTest_project1(int bytelength) {
        String s = test_project1 == null ? "" : test_project1;

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return s;
    }

    public String getPrintTestresult1(int bytelength) {
        String s = testresult1 == null ? "" : testresult1;

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return s;
    }

    public String getPrintDecisionoutcome1(int bytelength) {
        String s = decisionoutcome1 == null ? "" : decisionoutcome1;

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return s;
    }

    public String getPrintTest_project2(int bytelength) {
        String s = test_project2 == null ? "" : test_project2;

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return s;
    }

    public String getPrintTestresult2(int bytelength) {
        String s = testresult2 == null ? "" : testresult2;

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return s;
    }

    public String getPrintDecisionoutcome2(int bytelength) {
        String s = decisionoutcome2 == null ? "" : decisionoutcome2;

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return s;
    }

    public String getPrintTest_project3(int bytelength) {
        String s = test_project3 == null ? "" : test_project3;

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return s;
    }

    public String getPrintTestresult3(int bytelength) {
        String s = testresult3 == null ? "" : testresult3;

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return s;
    }

    public String getPrintDecisionoutcome3(int bytelength) {
        String s = decisionoutcome3 == null ? "" : decisionoutcome3;

        for (; true; ) {
            try {
                if (s.getBytes("GBK").length < bytelength) {
                    s = s + " ";
                } else {
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return s;
    }
}
