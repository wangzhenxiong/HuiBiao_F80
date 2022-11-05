package com.dy.huibiao_f80.api.back;

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
 * @data: 10/23/22 9:02 PM
 * Description:
 */
public class BeginOperationExam_Back {


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
        private List<OperationPaperListBean> operationPaperList;
        private ExaminationBean examination;

        public List<OperationPaperListBean> getOperationPaperList() {
            return operationPaperList;
        }

        public void setOperationPaperList(List<OperationPaperListBean> operationPaperList) {
            this.operationPaperList = operationPaperList;
        }

        public ExaminationBean getExamination() {
            return examination;
        }

        public void setExamination(ExaminationBean examination) {
            this.examination = examination;
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

        public static class OperationPaperListBean {
            private String id;
            private String subjectId;
            private Integer level;
            private String title;
            private String content;
            private Integer allScore;
            private Integer status;
            private String testFormIds;
            private String createUser;
            private String createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(String subjectId) {
                this.subjectId = subjectId;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Integer getAllScore() {
                return allScore;
            }

            public void setAllScore(Integer allScore) {
                this.allScore = allScore;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getTestFormIds() {
                return testFormIds;
            }

            public void setTestFormIds(String testFormIds) {
                this.testFormIds = testFormIds;
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
