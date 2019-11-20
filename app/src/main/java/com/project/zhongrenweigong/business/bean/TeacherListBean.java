package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/19 15:25
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class TeacherListBean extends BaseModel {

    /**
     * mbId : 999
     * mbPhone : 1111122345
     * mbName : 111
     * sex : 1
     * headUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573812466752&di=58e35567a4998e3840e953f8ac8e8069&imgtype=0&src=http%3A%2F%2Fwww.5068.com%2Fz%2Ftsdxgzbj%2Fimages%2Fzz2.jpg
     * birth : 2019-10-23T16:00:00.000+0000
     * authGarde :
     * isAuthName : 0
     * isAuthFace : 0
     * isAuthBankcard : 0
     * isAuthMerchant : 0
     * role : 1
     * isFreeze : 0
     * isAuthProfession : 1
     */

    private List<TeacherDataBean> data;

    public List<TeacherDataBean> getData() {
        return data;
    }

    public void setData(List<TeacherDataBean> data) {
        this.data = data;
    }
}
