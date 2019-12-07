package com.project.zhongrenweigong.home.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/12/7 15:57
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class RecommedBean extends BaseModel {

    /**
     * title : 自备零钱供路人免费自取 店主一年"倒贴"五六千
     * coverUrl : http://static.statickksmg.com/image/2019/12/05/91c31129ebc9338685ee398ebb20c171_320x240.jpg
     * author : 来源:看看新闻网
     * authorHeadUrl : null
     * authorAuthGrade : null
     * type : null
     * lookNum : 123
     * likeNum : 456
     * isLike : 1
     * isCollect : 0
     * nid : 1202774946535837696
     * nurl : http://www.kankanews.com/a/2019-12-01/0039073403.shtml?appid=583859
     */

    private List<NewsBean> data;

    public List<NewsBean> getData() {
        return data;
    }

    public void setData(List<NewsBean> data) {
        this.data = data;
    }
}
