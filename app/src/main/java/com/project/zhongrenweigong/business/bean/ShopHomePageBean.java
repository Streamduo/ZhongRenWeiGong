package com.project.zhongrenweigong.business.bean;

import com.project.zhongrenweigong.base.BaseModel;

import java.util.List;

/**
 * 作者：Fuduo on 2019/11/15 11:26
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ShopHomePageBean extends BaseModel {

    /**
     * shopId : 1111
     * mcId : 444
     * headUrl : http://b-ssl.duitang.com/uploads/item/201804/30/20180430223158_stfyy.thumb.700_0.jpeg
     * mcName : 3
     * isMerchantAuth : 1
     * shopName : 哇减肥积分哈哈
     * shopLogo : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=482597086,4280928371&fm=26&gp=0.jpg
     * details : 在中国传统文化教育中的阴阳五行哲学思想、儒家伦理道德观念、中医营养养生学说，还有文化艺术成就、饮食审美风尚、民族性格特征等诸多因素的影响下，劳动人民创造出彪炳史册的中国烹饪技艺，形成博大精深的中国饮食文化。
     * detailedAddr : 北京朝阳区
     * mcPhone : 1123322
     * fansNum : 0
     * beignTime : null
     * endTime : null
     * goodsLists : [{"goodsId":"350166172349632512","mcId":"","name":"借记卡阿狸","type":"0","price":12,"goodsTitleUrl":"http://f.hiphotos.baidu.com/image/h%3D300/sign=d985fb87d81b0ef473e89e5eedc551a1/b151f8198618367aa7f3cc7424738bd4b31ce525.jpg","status":1}]
     * employees : [{"employeesTitleUrl":"http://hbimg.b0.upaiyun.com/ef9978f541e23dca04f5658150ac06e79877fc709b33-NlsaW7_fw658","employeesId":"444","employeesName":"11"},{"employeesTitleUrl":"http://hbimg.b0.upaiyun.com/ef9978f541e23dca04f5658150ac06e79877fc709b33-NlsaW7_fw658","employeesId":"555","employeesName":"11"}]
     * shareholder : [{"employeesTitleUrl":"http://hbimg.b0.upaiyun.com/ef9978f541e23dca04f5658150ac06e79877fc709b33-NlsaW7_fw658","employeesId":"444","employeesName":"11"},{"employeesTitleUrl":"http://hbimg.b0.upaiyun.com/ef9978f541e23dca04f5658150ac06e79877fc709b33-NlsaW7_fw658","employeesId":"555","employeesName":"11"}]
     */

    private ShopHomePageDataBean data;

    public ShopHomePageDataBean getData() {
        return data;
    }

    public void setData(ShopHomePageDataBean data) {
        this.data = data;
    }

    public static class ShopHomePageDataBean {
        public String shopId;
        public String mcId;
        public String headUrl;
        public String mcName;
        public String isMerchantAuth;
        public String shopName;
        public String shopLogo;
        public String details;
        public String detailedAddr;
        public String mcPhone;
        public String fansNum;
        public String beignTime;
        public String endTime;
        public int isLike;
        /**
         * goodsId : 350166172349632512
         * mcId :
         * name : 借记卡阿狸
         * type : 0
         * price : 12
         * goodsTitleUrl : http://f.hiphotos.baidu.com/image/h%3D300/sign=d985fb87d81b0ef473e89e5eedc551a1/b151f8198618367aa7f3cc7424738bd4b31ce525.jpg
         * status : 1
         */

        public List<GoodsListsBean> goodsLists;
        /**
         * employeesTitleUrl : http://hbimg.b0.upaiyun.com/ef9978f541e23dca04f5658150ac06e79877fc709b33-NlsaW7_fw658
         * employeesId : 444
         * employeesName : 11
         */

        public List<EmployeesBean> employees;
        /**
         * employeesTitleUrl : http://hbimg.b0.upaiyun.com/ef9978f541e23dca04f5658150ac06e79877fc709b33-NlsaW7_fw658
         * employeesId : 444
         * employeesName : 11
         */

        public List<EmployeesBean> shareholder;

        /**
         * lng : 116.359713
         * lat : 40.048838
         */

        private String lng;
        private String lat;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
