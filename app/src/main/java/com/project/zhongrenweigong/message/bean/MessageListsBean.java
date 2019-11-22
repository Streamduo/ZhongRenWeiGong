package com.project.zhongrenweigong.message.bean;

/**
 * 作者：Fuduo on 2019/11/21 18:07
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class MessageListsBean {
    //系统基础参数
    public String messageId;
    public String messageMainId;
    public String messageMainLogo;
    public String messageMain;
    public String time;
    public long timestamp;
    public String messageIntro;
    public String content;
    public String isRead;


    //活动信息参数
    /**
     * shopId : 000
     * shopLogo : https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1321051463,3395180679&fm=26&gp=0.jpg
     * shopName : 家乐福
     */
    public String shopId;
    public String shopLogo;
    public String shopName;

    //凭证信息参数
    /**
     * mbId : 444
     * voucherId : 1
     * isPass : 0
     * shopAddr : 北京市
     * phone : 
     */

    public String mbId;
    public String mbName;
    public String voucherId;
    public String mbHeadUrl;
    public String role;
    public String checkStatus;
    public String shopAddr;
    public String phone;
}
