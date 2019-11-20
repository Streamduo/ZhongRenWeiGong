package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/19 15:17
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class TeachListBean extends BaseModel {

    /**
     * shopId : 3232
     * shopName : 金华火腿肠
     * mcId : 333
     * shopCategory : 3
     * mcPhone : 1234
     * shopLogo : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572491658996&di=75f1a59a2b42940299cdac69c4312949&imgtype=0&src=http%3A%2F%2Fimgqn.koudaitong.com%2Fupload_files%2F2015%2F01%2F22%2FFkzgVFmI_3lqSvyqKXOexSFOENgL.jpg
     * details : 22
     * likeNum : 1
     * isOpen : 1
     * isQualificationAuth : 1
     * shopGrade :
     * isQualified : 1
     * isFreeze : 0
     * beignTime :
     * endTime :
     * detailedAddr : 金华
     * provinces : 北京市
     * lng :
     * lat :
     */

    private List<TeachDataBean> data;

    public List<TeachDataBean> getData() {
        return data;
    }

    public void setData(List<TeachDataBean> data) {
        this.data = data;
    }
}
