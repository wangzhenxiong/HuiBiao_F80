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
 * @data: 10/23/22 9:01 PM
 * Description:
 */
public class BeginTheoryExam_Back {

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
        private List<TheoryQuestionMultipleListBean> theoryQuestionMultipleList;
        private List<TheoryQuestionJudgeListBean> theoryQuestionJudgeList;
        private TheoryPaperBean theoryPaper;
        private List<TheoryQuestionRadioListBean> theoryQuestionRadioList;

        public List<TheoryQuestionMultipleListBean> getTheoryQuestionMultipleList() {
            return theoryQuestionMultipleList;
        }

        public void setTheoryQuestionMultipleList(List<TheoryQuestionMultipleListBean> theoryQuestionMultipleList) {
            this.theoryQuestionMultipleList = theoryQuestionMultipleList;
        }

        public List<TheoryQuestionJudgeListBean> getTheoryQuestionJudgeList() {
            return theoryQuestionJudgeList;
        }

        public void setTheoryQuestionJudgeList(List<TheoryQuestionJudgeListBean> theoryQuestionJudgeList) {
            this.theoryQuestionJudgeList = theoryQuestionJudgeList;
        }

        public TheoryPaperBean getTheoryPaper() {
            return theoryPaper;
        }

        public void setTheoryPaper(TheoryPaperBean theoryPaper) {
            this.theoryPaper = theoryPaper;
        }

        public List<TheoryQuestionRadioListBean> getTheoryQuestionRadioList() {
            return theoryQuestionRadioList;
        }

        public void setTheoryQuestionRadioList(List<TheoryQuestionRadioListBean> theoryQuestionRadioList) {
            this.theoryQuestionRadioList = theoryQuestionRadioList;
        }

        public static class TheoryPaperBean {
            private String id;
            private String name;
            private String subjectId;
            private Integer level;
            private Integer status;
            private Integer allScore;
            private Integer questionNum;
            private String createUser;
            private String createTime;
            private Integer theoryExamTime;

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

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public Integer getAllScore() {
                return allScore;
            }

            public void setAllScore(Integer allScore) {
                this.allScore = allScore;
            }

            public Integer getQuestionNum() {
                return questionNum;
            }

            public void setQuestionNum(Integer questionNum) {
                this.questionNum = questionNum;
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

            public Integer getTheoryExamTime() {
                return theoryExamTime;
            }

            public void setTheoryExamTime(Integer theoryExamTime) {
                this.theoryExamTime = theoryExamTime;
            }
        }

        public static class TheoryQuestionMultipleListBean {
            private String id;
            private Integer quesType;
            private String content;
            private String options;
            private String studentAnswer;
            private String answer;
            private Double score;
            private Integer status;
            private String theoryPaperId;
            private String createUser;
            private String createTime;
            private boolean check;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getStudentAnswer() {
                return studentAnswer == null ? "" : studentAnswer;
            }

            public void setStudentAnswer(String studentAnswer) {
                this.studentAnswer = studentAnswer == null ? "" : studentAnswer;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Integer getQuesType() {
                return quesType;
            }

            public void setQuesType(Integer quesType) {
                this.quesType = quesType;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getOptions() {
                return options;
            }

            public void setOptions(String options) {
                this.options = options;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public Double getScore() {
                return score;
            }

            public void setScore(Double score) {
                this.score = score;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getTheoryPaperId() {
                return theoryPaperId;
            }

            public void setTheoryPaperId(String theoryPaperId) {
                this.theoryPaperId = theoryPaperId;
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

        public static class TheoryQuestionJudgeListBean {
            private String id;
            private Integer quesType;
            private String content;
            private String options;

            public String getStudentAnswer() {
                return studentAnswer == null ? "" : studentAnswer;
            }

            public void setStudentAnswer(String studentAnswer) {
                this.studentAnswer = studentAnswer == null ? "" : studentAnswer;
            }

            private String studentAnswer;
            private String answer;
            private Double score;
            private Integer status;
            private String theoryPaperId;
            private String createUser;
            private String createTime;
            private boolean check;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Integer getQuesType() {
                return quesType;
            }

            public void setQuesType(Integer quesType) {
                this.quesType = quesType;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getOptions() {
                return options;
            }

            public void setOptions(String options) {
                this.options = options;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public Double getScore() {
                return score;
            }

            public void setScore(Double score) {
                this.score = score;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getTheoryPaperId() {
                return theoryPaperId;
            }

            public void setTheoryPaperId(String theoryPaperId) {
                this.theoryPaperId = theoryPaperId;
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

        public static class TheoryQuestionRadioListBean {
            private String id;
            private Integer quesType;
            private String content;
            private String options;
            private String answer;
            private String studentAnswer;
            private Double score;
            private Integer status;
            private String theoryPaperId;
            private String createUser;
            private String createTime;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            private boolean check;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Integer getQuesType() {
                return quesType;
            }

            public void setQuesType(Integer quesType) {
                this.quesType = quesType;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getOptions() {
                return options;
            }

            public void setOptions(String options) {
                this.options = options;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public Double getScore() {
                return score;
            }

            public void setScore(Double score) {
                this.score = score;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getTheoryPaperId() {
                return theoryPaperId;
            }

            public void setTheoryPaperId(String theoryPaperId) {
                this.theoryPaperId = theoryPaperId;
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

            public String getStudentAnswer() {
                return studentAnswer == null ? "" : studentAnswer;
            }

            public void setStudentAnswer(String studentAnswer) {
                this.studentAnswer = studentAnswer == null ? "" : studentAnswer;
            }
        }
    }
}
