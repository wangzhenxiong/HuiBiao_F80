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


    private int isupload = 2;//是否上传

    private String unique_testproject;//检测项目唯一编号

    //使用平台时可能会有检测单位
    private String test_unit_name;//检测单位名称
    private String test_unit_reserved;//检测单位预留字段

    private String exam_id;//检测单位预留字段
    private String examinationId;
    private String examinerId;

    /**
     * 0 正常数据
     * 1 为重检数据
     * 2 已经重检
     */
    private int retest = 0;//重检
    private String parentSysCode;//重检的syscode

    private String methodsDetectionLimit;
    private Long samplingID;

    public Long getSamplingID() {
        return samplingID;
    }

    public void setSamplingID(Long samplingID) {
        this.samplingID = samplingID;
    }

    public String getMethodsDetectionLimit() {
        return methodsDetectionLimit == null ? "" : methodsDetectionLimit;
    }

    public void setMethodsDetectionLimit(String methodsDetectionLimit) {
        this.methodsDetectionLimit = methodsDetectionLimit == null ? "" : methodsDetectionLimit;
    }

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
        isupload = in.readInt();
        unique_testproject = in.readString();
        test_unit_name = in.readString();
        test_unit_reserved = in.readString();
        retest = in.readInt();
        parentSysCode = in.readString();
        sn = in.readString();
        exam_id = in.readString();
        examinerId = in.readString();
        examinationId = in.readString();
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
        dest.writeInt(isupload);
        dest.writeString(unique_testproject);
        dest.writeString(test_unit_name);
        dest.writeString(test_unit_reserved);
        dest.writeInt(retest);
        dest.writeString(parentSysCode);
        dest.writeString(sn);
        dest.writeString(exam_id);
        dest.writeString(examinerId);
        dest.writeString(examinationId);
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
        return "TestRecord{" +
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
                ", isupload=" + isupload +
                ", unique_testproject='" + unique_testproject + '\'' +
                ", test_unit_name='" + test_unit_name + '\'' +
                ", test_unit_reserved='" + test_unit_reserved + '\'' +
                ", retest=" + retest +
                ", parentSysCode='" + parentSysCode + '\'' +
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

        return testresult == null ? "" : testresult;
    }


    public String getTestresultPrint(int bytelength) {

        String s;


        s = testresult == null ? "" : testresult;


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

        return decisionoutcome == null ? "" : decisionoutcome;
    }

    public String getDecisionoutcomePrint(int bytelength) {

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
        String test_moudle = getTest_Moudle();
        if (test_moudle.equals("分光光度")) {
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
        } else {
            if (test_method == null) {
                return "";
            } else {
                if ("0".equals(test_method)) {
                    return "消线法";
                } else if ("1".equals(test_method)) {
                    return "比色法";
                } else {
                    return test_method;
                }
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

    @Generated(hash = 1095680771)
    public TestRecord(Long id, String sysCode, int gallery, String samplenum, String samplename,
            String sampletype, String foodCode, String symbol, String cov, String cov_unit,
            String stand_num, String prosecutedunits, String prosecutedunits_adress,
            double dilutionratio, double everyresponse, String controlvalue, String serialNumber,
            String testresult, String decisionoutcome, String inspector, Long testingtime,
            double longitude, double latitude, String testsite, String test_method,
            String test_project, String test_moudle, int isupload, String unique_testproject,
            String test_unit_name, String test_unit_reserved, String exam_id, String examinationId,
            String examinerId, int retest, String parentSysCode, String methodsDetectionLimit,
            Long samplingID) {
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
        this.isupload = isupload;
        this.unique_testproject = unique_testproject;
        this.test_unit_name = test_unit_name;
        this.test_unit_reserved = test_unit_reserved;
        this.exam_id = exam_id;
        this.examinationId = examinationId;
        this.examinerId = examinerId;
        this.retest = retest;
        this.parentSysCode = parentSysCode;
        this.methodsDetectionLimit = methodsDetectionLimit;
        this.samplingID = samplingID;
    }

    public String getUnique_testproject() {
        return unique_testproject == null ? "" : unique_testproject;
    }

    public void setUnique_testproject(String unique_testproject) {
        this.unique_testproject = unique_testproject == null ? "" : unique_testproject;
    }

    public String getTest_unit_name() {
        return test_unit_name == null ? "" : test_unit_name;
    }

    public void setTest_unit_name(String test_unit_name) {
        this.test_unit_name = test_unit_name == null ? "" : test_unit_name;
    }

    public String getTest_unit_reserved() {
        return test_unit_reserved == null ? "" : test_unit_reserved;
    }

    public void setTest_unit_reserved(String test_unit_reserved) {
        this.test_unit_reserved = test_unit_reserved == null ? "" : test_unit_reserved;
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getExam_id() {
        return exam_id == null ? "" : exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id == null ? "" : exam_id;
    }

    public String getExaminationId() {
        return examinationId == null ? "" : examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId == null ? "" : examinationId;
    }

    public String getExaminerId() {
        return examinerId == null ? "" : examinerId;
    }

    public void setExaminerId(String examinerId) {
        this.examinerId = examinerId == null ? "" : examinerId;
    }
}
