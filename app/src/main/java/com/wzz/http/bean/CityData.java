package com.wzz.http.bean;

import java.util.List;

public class CityData {
    /**
     * code : 1
     * msg : 数据返回成功
     * data : [{"code":"440000","name":"广东省","pchilds":[{"code":"440300","name":"深圳市","cchilds":[{"code":"440301","name":"市辖区"},{"code":"440303","name":"罗湖区"},{"code":"440304","name":"福田区"},{"code":"440305","name":"南山区"},{"code":"440306","name":"宝安区"},{"code":"440307","name":"龙岗区"},{"code":"440308","name":"盐田区"},{"code":"440309","name":"龙华区"},{"code":"440310","name":"坪山区"}]}]}]
     */

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * code : 440000
         * name : 广东省
         * pchilds : [{"code":"440300","name":"深圳市","cchilds":[{"code":"440301","name":"市辖区"},{"code":"440303","name":"罗湖区"},{"code":"440304","name":"福田区"},{"code":"440305","name":"南山区"},{"code":"440306","name":"宝安区"},{"code":"440307","name":"龙岗区"},{"code":"440308","name":"盐田区"},{"code":"440309","name":"龙华区"},{"code":"440310","name":"坪山区"}]}]
         */

        public String code;
        public String name;
        public List<PchildsBean> pchilds;

        public static class PchildsBean {
            /**
             * code : 440300
             * name : 深圳市
             * cchilds : [{"code":"440301","name":"市辖区"},{"code":"440303","name":"罗湖区"},{"code":"440304","name":"福田区"},{"code":"440305","name":"南山区"},{"code":"440306","name":"宝安区"},{"code":"440307","name":"龙岗区"},{"code":"440308","name":"盐田区"},{"code":"440309","name":"龙华区"},{"code":"440310","name":"坪山区"}]
             */

            public String code;
            public String name;
            public List<CchildsBean> cchilds;

            public static class CchildsBean {
                /**
                 * code : 440301
                 * name : 市辖区
                 */

                public String code;
                public String name;

            }
        }
    }
}
