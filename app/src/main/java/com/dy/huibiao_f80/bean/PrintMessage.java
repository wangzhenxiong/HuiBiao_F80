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
 * @data: 10/30/22 3:58 PM
 * Description:
 */
public class PrintMessage {
    private boolean checkBox_project;
    private boolean checkBox_gallery;
    private boolean checkBox_samplenum;
    private boolean checkBox_samplename;
    private boolean checkBox_standname;
    private boolean checkBox_resultunit;
    private boolean checkBox_testtime;
    private boolean checkBox_beunit;
    private boolean checkBox_sampleplace;
    private boolean checkBox_testpeople;
    private boolean checkBox_jujdger;
    private boolean checkBox_all;
    private boolean checkBox_device;
    private String ed_sampleplace;
    private String ed_testpeople;
    private String ed_jujdger;

    public PrintMessage() {
    }

    public boolean isCheckBox_project() {
        return checkBox_project;
    }

    public void setCheckBox_project(boolean checkBox_project) {
        this.checkBox_project = checkBox_project;
    }

    public boolean isCheckBox_gallery() {
        return checkBox_gallery;
    }

    public void setCheckBox_gallery(boolean checkBox_gallery) {
        this.checkBox_gallery = checkBox_gallery;
    }

    public boolean isCheckBox_samplenum() {
        return checkBox_samplenum;
    }

    public void setCheckBox_samplenum(boolean checkBox_samplenum) {
        this.checkBox_samplenum = checkBox_samplenum;
    }

    public boolean isCheckBox_samplename() {
        return checkBox_samplename;
    }

    public void setCheckBox_samplename(boolean checkBox_samplename) {
        this.checkBox_samplename = checkBox_samplename;
    }

    public boolean isCheckBox_standname() {
        return checkBox_standname;
    }

    public void setCheckBox_standname(boolean checkBox_standname) {
        this.checkBox_standname = checkBox_standname;
    }

    public boolean isCheckBox_resultunit() {
        return checkBox_resultunit;
    }

    public void setCheckBox_resultunit(boolean checkBox_resultunit) {
        this.checkBox_resultunit = checkBox_resultunit;
    }

    public boolean isCheckBox_testtime() {
        return checkBox_testtime;
    }

    public void setCheckBox_testtime(boolean checkBox_testtime) {
        this.checkBox_testtime = checkBox_testtime;
    }

    public boolean isCheckBox_beunit() {
        return checkBox_beunit;
    }

    public void setCheckBox_beunit(boolean checkBox_beunit) {
        this.checkBox_beunit = checkBox_beunit;
    }

    public boolean isCheckBox_sampleplace() {
        return checkBox_sampleplace;
    }

    public void setCheckBox_sampleplace(boolean checkBox_sampleplace) {
        this.checkBox_sampleplace = checkBox_sampleplace;
    }

    public boolean isCheckBox_testpeople() {
        return checkBox_testpeople;
    }

    public void setCheckBox_testpeople(boolean checkBox_testpeople) {
        this.checkBox_testpeople = checkBox_testpeople;
    }

    public boolean isCheckBox_jujdger() {
        return checkBox_jujdger;
    }

    public void setCheckBox_jujdger(boolean checkBox_jujdger) {
        this.checkBox_jujdger = checkBox_jujdger;
    }

    public boolean isCheckBox_all() {
        return checkBox_all;
    }

    public void setCheckBox_all(boolean checkBox_all) {
        this.checkBox_all = checkBox_all;
    }

    public String getEd_sampleplace() {
        return ed_sampleplace == null ? "" : ed_sampleplace;
    }

    public void setEd_sampleplace(String ed_sampleplace) {
        this.ed_sampleplace = ed_sampleplace == null ? "" : ed_sampleplace;
    }

    public String getEd_testpeople() {
        return ed_testpeople == null ? "" : ed_testpeople;
    }

    public void setEd_testpeople(String ed_testpeople) {
        this.ed_testpeople = ed_testpeople == null ? "" : ed_testpeople;
    }

    public String getEd_jujdger() {
        return ed_jujdger == null ? "" : ed_jujdger;
    }

    public void setEd_jujdger(String ed_jujdger) {
        this.ed_jujdger = ed_jujdger == null ? "" : ed_jujdger;
    }

    public boolean isCheckBox_device() {
        return checkBox_device;
    }

    public void setCheckBox_device(boolean checkBox_device) {
        this.checkBox_device = checkBox_device;
    }
}
