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
public class BeginAnalyseExam_Back {

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
        private AnalysePaperBean analysePaper;
        private List<AnalysePaperListBean> analysePaperList;

        public AnalysePaperBean getAnalysePaper() {
            return analysePaper;
        }

        public void setAnalysePaper(AnalysePaperBean analysePaper) {
            this.analysePaper = analysePaper;
        }

        public List<AnalysePaperListBean> getAnalysePaperList() {
            return analysePaperList;
        }

        public void setAnalysePaperList(List<AnalysePaperListBean> analysePaperList) {
            this.analysePaperList = analysePaperList;
        }

        public static class AnalysePaperBean {
            private String id;
            private String professionId;
            private String profession;
            private Integer level;
            private String title;
            private String totalScore;
            private String delFlag;
            private String createTime;
            private String createUser;
            private Integer analyseExamTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProfessionId() {
                return professionId;
            }

            public void setProfessionId(String professionId) {
                this.professionId = professionId;
            }

            public String getProfession() {
                return profession;
            }

            public void setProfession(String profession) {
                this.profession = profession;
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

            public String getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(String totalScore) {
                this.totalScore = totalScore;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public Integer getAnalyseExamTime() {
                return analyseExamTime;
            }

            public void setAnalyseExamTime(Integer analyseExamTime) {
                this.analyseExamTime = analyseExamTime;
            }
        }

        public static class AnalysePaperListBean {
            private String id;
            private String analyseId;
            private String content;
            private String score;
            private String delFlag;
            private String createTime;
            private String createUser;
            private String studentAnswer;
            public boolean check=false;

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

            public String getAnalyseId() {
                return analyseId;
            }

            public void setAnalyseId(String analyseId) {
                this.analyseId = analyseId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }
        }
    }
}
