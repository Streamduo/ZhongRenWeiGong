package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/20 09:59
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class IndustryListBean extends BaseModel {

    /**
     * shopId : 22234
     * shopName : 小老头
     * mcId : 444
     * shopCategory : 4
     * mcPhone : 1234
     * shopLogo : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572491671053&di=5278ca364d85f89493691a18dffbeded&imgtype=0&src=http%3A%2F%2Fgouwu.haodianxin.cn%2Fg.php%2Ftfscom%2Fi2%2F1844348333%2FTB2FypNnFOWBuNjy0FiXXXFxVXa_%2521%25211844348333.jpg
     * details : 11
     * likeNum : 1
     * isOpen : 1
     * isQualificationAuth : 1
     * shopGrade :
     * isQualified : 1
     * isFreeze : 0
     * beignTime :
     * endTime :
     * detailedAddr : 义乌
     * provinces : 北京市
     * lng :
     * lat :
     */

    private List<IndustryDataBean> data;

    public List<IndustryDataBean> getData() {
        return data;
    }

    public void setData(List<IndustryDataBean> data) {
        this.data = data;
    }
}
