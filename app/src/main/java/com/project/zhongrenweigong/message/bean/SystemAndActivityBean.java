package com.project.zhongrenweigong.message.bean;

import com.project.zhongrenweigong.base.BaseModel;

/**
 * 作者：Fuduo on 2019/11/26 11:41
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class SystemAndActivityBean extends BaseModel {

    /**
     * messageId : 358508034785415168
     * messageMainLogo : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574313975407&di=866e48ff5355f34f83be940d615b998c&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a4fc574d77066ac72525ae739d58.png%40900w_1l_2o_100sh.jpg
     * messageMain : 维公科技
     * timestamp : 1574586586475
     * time : 1天前
     * title : 11111
     * content : 1111111111
     */

    private SystemAndActivityDataBean data;

    public SystemAndActivityDataBean getData() {
        return data;
    }

    public void setData(SystemAndActivityDataBean data) {
        this.data = data;
    }
}
