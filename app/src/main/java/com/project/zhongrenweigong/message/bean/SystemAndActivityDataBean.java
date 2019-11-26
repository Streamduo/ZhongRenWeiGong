package com.project.zhongrenweigong.message.bean;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/26 11:42
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class SystemAndActivityDataBean {

    //系统消息
    public String messageId;
    public String messageMainLogo;
    public String messageMain;
    public long timestamp;
    public String time;
    public String title;
    public String content;

    //活动消息
    /**
     * shopId : 358025420337188864
     * shopLogo : http://img1.imgtn.bdimg.com/it/u=3608155120,1630233151&fm=26&gp=0.jpg
     * shopName : 店铺测试
     * images : ["http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&hd=undefined&latest=undefined&copyright=undefined&cs=2562391915,843461705&os=176105554,177254020&simid=3516473590,287236674&pn=6&rn=1&di=10340&ln=1345&fr=&fmq=1574412029766_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fpic16.nipic.com%2F20110824%2F6954443_222914449000_2.jpg&rpstart=0&rpnum=0&adpicid=0&force=undefined","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1188387633,958216909&fm=26&gp=0.jpg","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1188387633,958216909&fm=26&gp=0.jpg","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1188387633,958216909&fm=26&gp=0.jpg","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1188387633,958216909&fm=26&gp=0.jpg"]
     */
    public String shopId;
    public String shopLogo;
    public String shopName;
    public List<String> images;


}
