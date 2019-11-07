package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/5 15:46
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class WorkerListBean extends BaseModel {


    /**
     * data : [{"employeesId":"string","employeesName":"string","employeesTitleUrl":"string"}]
     * pageSize : 0
     */

    private int pageSize;
    /**
     * employeesId : string
     * employeesName : string
     * employeesTitleUrl : string
     */

    private List<WorkerDataBean> data;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<WorkerDataBean> getData() {
        return data;
    }

    public void setData(List<WorkerDataBean> data) {
        this.data = data;
    }
}
