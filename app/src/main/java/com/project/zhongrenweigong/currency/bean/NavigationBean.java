package com.project.zhongrenweigong.currency.bean;

/**
 * 作者：Fuduo on 2019/11/16 09:32
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class NavigationBean {


    /**
     * status : 0
     * result : {"location":{"lng":116.3084202915042,"lat":40.05703033345938},"precise":1,"confidence":80,"comprehension":100,"level":"门址"}
     */

    private int status;
    /**
     * location : {"lng":116.3084202915042,"lat":40.05703033345938}
     * precise : 1
     * confidence : 80
     * comprehension : 100
     * level : 门址
     */

    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * lng : 116.3084202915042
         * lat : 40.05703033345938
         */

        private LocationBean location;
        private int precise;
        private int confidence;
        private int comprehension;
        private String level;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public int getPrecise() {
            return precise;
        }

        public void setPrecise(int precise) {
            this.precise = precise;
        }

        public int getConfidence() {
            return confidence;
        }

        public void setConfidence(int confidence) {
            this.confidence = confidence;
        }

        public int getComprehension() {
            return comprehension;
        }

        public void setComprehension(int comprehension) {
            this.comprehension = comprehension;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public static class LocationBean {
            private double lng;
            private double lat;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }
    }
}
