package com.project.zhongrenweigong.mine.bean;

import com.project.zhongrenweigong.base.BaseModel;

/**
 * 作者：Fuduo on 2019/11/30 18:07
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class ProfessionalBean extends BaseModel {

    /**
     * mbId : 358023735078424576
     * professionMain : 在一起的时候
     * professionMainAddress : 北京市朝阳区人民法院
     * professionName : 222
     */

    private ProfessionalDataBean data;

    public ProfessionalDataBean getData() {
        return data;
    }

    public void setData(ProfessionalDataBean data) {
        this.data = data;
    }

    public static class ProfessionalDataBean {
        private String mbId;
        private String professionMain;
        private String professionMainAddress;
        private String professionName;

        public String getMbId() {
            return mbId;
        }

        public void setMbId(String mbId) {
            this.mbId = mbId;
        }

        public String getProfessionMain() {
            return professionMain;
        }

        public void setProfessionMain(String professionMain) {
            this.professionMain = professionMain;
        }

        public String getProfessionMainAddress() {
            return professionMainAddress;
        }

        public void setProfessionMainAddress(String professionMainAddress) {
            this.professionMainAddress = professionMainAddress;
        }

        public String getProfessionName() {
            return professionName;
        }

        public void setProfessionName(String professionName) {
            this.professionName = professionName;
        }
    }
}
