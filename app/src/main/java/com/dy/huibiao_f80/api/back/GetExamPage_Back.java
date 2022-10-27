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
 * @data: 10/23/22 9:01 PM
 * Description:
 */
public class GetExamPage_Back {

    private String message;
    private Boolean success;
    private EntityBean entity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public EntityBean getEntity() {
        return entity;
    }

    public void setEntity(EntityBean entity) {
        this.entity = entity;
    }

    public static class EntityBean {
        private ExaminationBean examination;
        private ExaminerBean examiner;

        public ExaminationBean getExamination() {
            return examination;
        }

        public void setExamination(ExaminationBean examination) {
            this.examination = examination;
        }

        public ExaminerBean getExaminer() {
            return examiner;
        }

        public void setExaminer(ExaminerBean examiner) {
            this.examiner = examiner;
        }

        public static class ExaminationBean {
            private String id;
            private String name;
            private String schoolName;
            private String level;
            private String subjectId;
            private String examRoomId;
            private String invigilatorId;
            private String startTime;
            private String endTime;
            private Integer status;
            private String theoryPaperIds;
            private Integer theoryExamTime;
            private Integer theoryTotalScore;
            private String operationPaperId;
            private Integer operationExamTime;
            private Integer operationTotalScore;
            private String analysePaperId;
            private Integer analyseExamTime;
            private Integer analyseTotalScore;
            private String analyseStartTime;
            private String analyseEndTime;
            private String markingSign;
            private String testFormIds;
            private Integer testFormScore;
            private Integer isShowScore;
            private Integer personTestMethod;
            private String virtualConten;
            private Integer virtualFormScore;
            private Integer operationType;
            private String createUser;
            private String createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(String subjectId) {
                this.subjectId = subjectId;
            }

            public String getExamRoomId() {
                return examRoomId;
            }

            public void setExamRoomId(String examRoomId) {
                this.examRoomId = examRoomId;
            }

            public String getInvigilatorId() {
                return invigilatorId;
            }

            public void setInvigilatorId(String invigilatorId) {
                this.invigilatorId = invigilatorId;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getTheoryPaperIds() {
                return theoryPaperIds;
            }

            public void setTheoryPaperIds(String theoryPaperIds) {
                this.theoryPaperIds = theoryPaperIds;
            }

            public Integer getTheoryExamTime() {
                return theoryExamTime;
            }

            public void setTheoryExamTime(Integer theoryExamTime) {
                this.theoryExamTime = theoryExamTime;
            }

            public Integer getTheoryTotalScore() {
                return theoryTotalScore;
            }

            public void setTheoryTotalScore(Integer theoryTotalScore) {
                this.theoryTotalScore = theoryTotalScore;
            }

            public String getOperationPaperId() {
                return operationPaperId;
            }

            public void setOperationPaperId(String operationPaperId) {
                this.operationPaperId = operationPaperId;
            }

            public Integer getOperationExamTime() {
                return operationExamTime;
            }

            public void setOperationExamTime(Integer operationExamTime) {
                this.operationExamTime = operationExamTime;
            }

            public Integer getOperationTotalScore() {
                return operationTotalScore;
            }

            public void setOperationTotalScore(Integer operationTotalScore) {
                this.operationTotalScore = operationTotalScore;
            }

            public String getAnalysePaperId() {
                return analysePaperId;
            }

            public void setAnalysePaperId(String analysePaperId) {
                this.analysePaperId = analysePaperId;
            }

            public Integer getAnalyseExamTime() {
                return analyseExamTime;
            }

            public void setAnalyseExamTime(Integer analyseExamTime) {
                this.analyseExamTime = analyseExamTime;
            }

            public Integer getAnalyseTotalScore() {
                return analyseTotalScore;
            }

            public void setAnalyseTotalScore(Integer analyseTotalScore) {
                this.analyseTotalScore = analyseTotalScore;
            }

            public String getAnalyseStartTime() {
                return analyseStartTime;
            }

            public void setAnalyseStartTime(String analyseStartTime) {
                this.analyseStartTime = analyseStartTime;
            }

            public String getAnalyseEndTime() {
                return analyseEndTime;
            }

            public void setAnalyseEndTime(String analyseEndTime) {
                this.analyseEndTime = analyseEndTime;
            }

            public String getMarkingSign() {
                return markingSign;
            }

            public void setMarkingSign(String markingSign) {
                this.markingSign = markingSign;
            }

            public String getTestFormIds() {
                return testFormIds;
            }

            public void setTestFormIds(String testFormIds) {
                this.testFormIds = testFormIds;
            }

            public Integer getTestFormScore() {
                return testFormScore;
            }

            public void setTestFormScore(Integer testFormScore) {
                this.testFormScore = testFormScore;
            }

            public Integer getIsShowScore() {
                return isShowScore;
            }

            public void setIsShowScore(Integer isShowScore) {
                this.isShowScore = isShowScore;
            }

            public Integer getPersonTestMethod() {
                return personTestMethod;
            }

            public void setPersonTestMethod(Integer personTestMethod) {
                this.personTestMethod = personTestMethod;
            }

            public String getVirtualConten() {
                return virtualConten;
            }

            public void setVirtualConten(String virtualConten) {
                this.virtualConten = virtualConten;
            }

            public Integer getVirtualFormScore() {
                return virtualFormScore;
            }

            public void setVirtualFormScore(Integer virtualFormScore) {
                this.virtualFormScore = virtualFormScore;
            }

            public Integer getOperationType() {
                return operationType;
            }

            public void setOperationType(Integer operationType) {
                this.operationType = operationType;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class ExaminerBean {
            private String id;
            private String examinationId;
            private String name;
            private String phone;
            private String idNumber;
            private Integer examStatus;
            private Double theoryScore;
            private Integer operationStatus;
            private Double operationScore;
            private Integer analyseStatus;
            private Double analyseScore;
            private Double testFormScore;
            private Integer virtualExamStatus;
            private Double virtualExamScore;
            private Double virtualFormScore;
            private String createUser;
            private String createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getExaminationId() {
                return examinationId;
            }

            public void setExaminationId(String examinationId) {
                this.examinationId = examinationId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getIdNumber() {
                return idNumber;
            }

            public void setIdNumber(String idNumber) {
                this.idNumber = idNumber;
            }

            public Integer getExamStatus() {
                return examStatus;
            }

            public void setExamStatus(Integer examStatus) {
                this.examStatus = examStatus;
            }

            public Double getTheoryScore() {
                return theoryScore;
            }

            public void setTheoryScore(Double theoryScore) {
                this.theoryScore = theoryScore;
            }

            public Integer getOperationStatus() {
                return operationStatus;
            }

            public void setOperationStatus(Integer operationStatus) {
                this.operationStatus = operationStatus;
            }

            public Double getOperationScore() {
                return operationScore;
            }

            public void setOperationScore(Double operationScore) {
                this.operationScore = operationScore;
            }

            public Integer getAnalyseStatus() {
                return analyseStatus;
            }

            public void setAnalyseStatus(Integer analyseStatus) {
                this.analyseStatus = analyseStatus;
            }

            public Double getAnalyseScore() {
                return analyseScore;
            }

            public void setAnalyseScore(Double analyseScore) {
                this.analyseScore = analyseScore;
            }

            public Double getTestFormScore() {
                return testFormScore;
            }

            public void setTestFormScore(Double testFormScore) {
                this.testFormScore = testFormScore;
            }

            public Integer getVirtualExamStatus() {
                return virtualExamStatus;
            }

            public void setVirtualExamStatus(Integer virtualExamStatus) {
                this.virtualExamStatus = virtualExamStatus;
            }

            public Double getVirtualExamScore() {
                return virtualExamScore;
            }

            public void setVirtualExamScore(Double virtualExamScore) {
                this.virtualExamScore = virtualExamScore;
            }

            public Double getVirtualFormScore() {
                return virtualFormScore;
            }

            public void setVirtualFormScore(Double virtualFormScore) {
                this.virtualFormScore = virtualFormScore;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
