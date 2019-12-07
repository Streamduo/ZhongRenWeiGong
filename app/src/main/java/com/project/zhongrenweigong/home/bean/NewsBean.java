package com.project.zhongrenweigong.home.bean;

import java.io.Serializable;

/**
 * 作者：Fuduo on 2019/12/7 15:58
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class NewsBean implements Serializable {

    /**
     * title : 8旬母亲患老年痴呆 儿子带着她上工地：陪你变老
     * coverUrl : http://static.statickksmg.com/image/2019/12/05/91c31129ebc9338685ee398ebb20c171_320x240.jpg
     * author : 来源:看看新闻网
     * isCollect : 0
     * isLike : 0
     * nid : 1202773823678713856
     * nurl : http://www.kankanews.com/a/2019-12-02/0039074406.shtml?appid=584255
     */

    private String title;
    private String coverUrl;
    private String author;
    private String authorHeadUrl;
    private String authorAuthGrade;
    private String lookNum;
    private String likeNum;
    private String isCollect;
    private String isLike;
    private String nid;
    private String nurl;

    private String videoUrl;
    private String type;

    private String timestamp;
    private String timeDetail;
    private String topical;

    public String getTopical() {
        return topical;
    }

    public String getAuthorHeadUrl() {
        return authorHeadUrl;
    }

    public void setAuthorHeadUrl(String authorHeadUrl) {
        this.authorHeadUrl = authorHeadUrl;
    }

    public String getAuthorAuthGrade() {
        return authorAuthGrade;
    }

    public void setAuthorAuthGrade(String authorAuthGrade) {
        this.authorAuthGrade = authorAuthGrade;
    }

    public String getLookNum() {
        return lookNum;
    }

    public void setLookNum(String lookNum) {
        this.lookNum = lookNum;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public void setTopical(String topical) {
        this.topical = topical;
    }

    public String getTimeDetail() {
        return timeDetail;
    }

    public void setTimeDetail(String timeDetail) {
        this.timeDetail = timeDetail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNurl() {
        return nurl;
    }

    public void setNurl(String nurl) {
        this.nurl = nurl;
    }
}
