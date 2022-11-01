package com.dy.huibiao_f80.bean;

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
 * @data: 10/30/22 2:34 PM
 * Description:
 */
public class UpdateMessage {
    /**
     * result : {"appversion":"1.0.7.3","desc":"  本版本主要更新：\n    1.\t修改干式农残值超100%问题。","link":"http://rj.chinafst.cn:8085/Ver_update/LZ7000_V1.0.7.3.apk"}
     * resultCode : success1
     * resultDescripe : 成功
     */

    private ResultBean result;
    private String resultCode;
    private String resultDescripe;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDescripe() {
        return resultDescripe;
    }

    public void setResultDescripe(String resultDescripe) {
        this.resultDescripe = resultDescripe;
    }

    public static class ResultBean {
        /**
         * appversion : 1.0.7.3
         * desc :   本版本主要更新：
         1.	修改干式农残值超100%问题。
         * link : http://rj.chinafst.cn:8085/Ver_update/LZ7000_V1.0.7.3.apk
         */

        private String appversion;
        private String desc;
        private String link;

        public String getAppversion() {
            return appversion;
        }

        public void setAppversion(String appversion) {
            this.appversion = appversion;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
