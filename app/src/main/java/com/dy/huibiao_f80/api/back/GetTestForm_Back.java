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
 * @data: 11/7/22 10:45 AM
 * Description:
 */
public class GetTestForm_Back {

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
        private List<TestFormListBean> testFormList;

        public List<TestFormListBean> getTestFormList() {
            return testFormList;
        }

        public void setTestFormList(List<TestFormListBean> testFormList) {
            this.testFormList = testFormList;
        }

        public static class TestFormListBean {
            private String id;
            private String professionId;
            private Integer level;
            private String name;
            private String totalScore;
            private String isVirtual;
            private String content;
            private String delFlag;
            private String createTime;
            private String createUser;
            private List<TestFormDetailListBean> testFormDetailList;

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

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(String totalScore) {
                this.totalScore = totalScore;
            }

            public String getIsVirtual() {
                return isVirtual;
            }

            public void setIsVirtual(String isVirtual) {
                this.isVirtual = isVirtual;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public List<TestFormDetailListBean> getTestFormDetailList() {
                return testFormDetailList;
            }

            public void setTestFormDetailList(List<TestFormDetailListBean> testFormDetailList) {
                this.testFormDetailList = testFormDetailList;
            }

            public static class TestFormDetailListBean {
                private String id;
                private String testFormId;
                private String fieldName;
                private String rule;
                private String answer;
                private String score;
                private String delFlag;
                private String createTime;
                private String createUser;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTestFormId() {
                    return testFormId;
                }

                public void setTestFormId(String testFormId) {
                    this.testFormId = testFormId;
                }

                public String getFieldName() {
                    return fieldName;
                }

                public void setFieldName(String fieldName) {
                    this.fieldName = fieldName;
                }

                public String getRule() {
                    return rule;
                }

                public void setRule(String rule) {
                    this.rule = rule;
                }

                public String getAnswer() {
                    return answer;
                }

                public void setAnswer(String answer) {
                    this.answer = answer;
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
}
