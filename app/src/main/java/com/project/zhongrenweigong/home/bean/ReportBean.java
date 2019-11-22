package com.project.zhongrenweigong.home.bean;

/**
 * 作者：Fuduo on 2019/11/22 11:17
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ReportBean {
    private int id;
    private String reportIntro;
    private int isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportIntro() {
        return reportIntro;
    }

    public void setReportIntro(String reportIntro) {
        this.reportIntro = reportIntro;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
