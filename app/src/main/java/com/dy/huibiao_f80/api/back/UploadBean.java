package com.dy.huibiao_f80.api.back;

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
 * @data: 11/4/22 11:02 AM
 * Description:
 */
public class UploadBean {
    private String examinationId;
    private String examinerId;
    private String operationPaperId;
    private String sampleCode;
    private String sampleName;
    private String detectionMode;
    private String projectName;
    private String detectionResult;
    private String company;
    private String determine;
    private String detectionTime;
    private String testWay;
    private String uploadStatus;

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

    public String getOperationPaperId() {
        return operationPaperId == null ? "" : operationPaperId;
    }

    public void setOperationPaperId(String operationPaperId) {
        this.operationPaperId = operationPaperId == null ? "" : operationPaperId;
    }

    public String getSampleCode() {
        return sampleCode == null ? "" : sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode == null ? "" : sampleCode;
    }

    public String getSampleName() {
        return sampleName == null ? "" : sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName == null ? "" : sampleName;
    }

    public String getDetectionMode() {
        return detectionMode == null ? "" : detectionMode;
    }

    public void setDetectionMode(String detectionMode) {
        this.detectionMode = detectionMode == null ? "" : detectionMode;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? "" : projectName;
    }

    public String getDetectionResult() {
        return detectionResult == null ? "" : detectionResult;
    }

    public void setDetectionResult(String detectionResult) {
        this.detectionResult = detectionResult == null ? "" : detectionResult;
    }

    public String getCompany() {
        return company == null ? "" : company;
    }

    public void setCompany(String company) {
        this.company = company == null ? "" : company;
    }

    public String getDetermine() {
        return determine == null ? "" : determine;
    }

    public void setDetermine(String determine) {
        this.determine = determine == null ? "" : determine;
    }

    public String getDetectionTime() {
        return detectionTime == null ? "" : detectionTime;
    }

    public void setDetectionTime(String detectionTime) {
        this.detectionTime = detectionTime == null ? "" : detectionTime;
    }

    public String getTestWay() {
        return testWay == null ? "" : testWay;
    }

    public void setTestWay(String testWay) {
        this.testWay = testWay == null ? "" : testWay;
    }

    public String getUploadStatus() {
        return uploadStatus == null ? "" : uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus == null ? "" : uploadStatus;
    }
}
