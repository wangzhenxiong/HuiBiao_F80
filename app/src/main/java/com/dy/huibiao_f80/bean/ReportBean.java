package com.dy.huibiao_f80.bean;

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
 * @data: 11/3/22 4:08 PM
 * Description:
 */
public class ReportBean {
    private String examinationId;
    private String examinerId;
    private String operationPaperId;
    private String createTime;
    private List<TestFormDetail> testFormDetailList;

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

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? "" : createTime;
    }

    public List<TestFormDetail> getTestFormDetailList() {
        return testFormDetailList;
    }

    public void setTestFormDetailList(List<TestFormDetail> testFormDetailList) {
        this.testFormDetailList = testFormDetailList;
    }

    public static class TestFormDetail {
        private String testFormId;
        private String testFormDetailId;
        private String fieldName;
        private String answer;

        public String getTestFormId() {
            return testFormId == null ? "" : testFormId;
        }

        public void setTestFormId(String testFormId) {
            this.testFormId = testFormId == null ? "" : testFormId;
        }

        public String getTestFormDetailId() {
            return testFormDetailId == null ? "" : testFormDetailId;
        }

        public void setTestFormDetailId(String testFormDetailId) {
            this.testFormDetailId = testFormDetailId == null ? "" : testFormDetailId;
        }

        public String getFieldName() {
            return fieldName == null ? "" : fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName == null ? "" : fieldName;
        }

        public String getAnswer() {
            return answer == null ? "" : answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer == null ? "" : answer;
        }
    }
}
