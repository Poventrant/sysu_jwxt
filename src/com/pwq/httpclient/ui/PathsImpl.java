package com.pwq.httpclient.ui;

import java.nio.charset.Charset;

/**
 * Created by 枫叶 on 2016/1/21.
 */
public class PathsImpl implements Paths{

    public static String getKcList(String xn, String xq, String pylb, String types) {
        return "{header:{\"code\": -100, \"message\": {\"title\": \"\", \"detail\": \"\"}},body:{dataStores:{kccjStore:{rowSet:{\"primary\":[],\"filter\":[],\"delete\":[]},name:\"kccjStore\"," +
                "pageNumber:1,pageSize:30,recordCount:30,rowSetName:\"pojo_com.neusoft.education.sysu.xscj.xscjcx.model.KccjModel\",order:\"t.xn, t.xq, t.kch, t.bzw\"}},parameters:{\"kccjStore-params\": [{\"name\": \"Filter_t.pylbm_0.7772497498853678\", \"type\": \"String\", \"value\": \"'"+
                pylb +"'\", \"condition\": \" = \", \"property\": \"t.pylbm\"}, {\"name\": \"Filter_t.xn_0.9182861938425414\", \"type\": \"String\", \"value\": \"'"+
                xn +"'\", \"condition\": \" = \", \"property\": \"t.xn\"}, {\"name\": \"Filter_t.xq_0.28583447990866473\", \"type\": \"String\", \"value\": \"'"+
                xq +"'\", \"condition\": \" = \", \"property\": \"t.xq\"}, {\"name\": \"kclb\", \"type\": \"String\", \"value\": \"("+
                types+")\", \"condition\": \" in \", \"property\": \"t.kclb\"}], \"args\": [\"student\"]}}}";
    }

    public static String getJudge() {
        return "{header:{\"code\": -100, \"message\": {\"title\": \"\", \"detail\": \"\"}},body:{dataStores:{tempStore:{rowSet:" +
                "{\"primary\":[],\"filter\":[],\"delete\":[]},name:\"tempStore\",pageNumber:1,pageSize:2147483647,recordCount:0,rowSetName:" +
                "\"pojo_com.neusoft.education.sysu.xy.xyjy.model.OnecolumModel\"}},parameters:{\"args\": [], \"responseParam\": \"result\"}}}";
    }

    public static String getCountAll(String[][] param, String username, String xn, String xq, String pylx) {

        String temp1 = "class com.neusoft.education.sysu.djks.ksgl.model.TwoColumnModel",
                temp2 = "pojo_com.neusoft.education.sysu.djks.ksgl.model.TwoColumnModel";

        StringBuffer alternativ = new StringBuffer("");

        if(param[2][0] != null && !param[2][0].equals("")) {
            alternativ.append("{\"oneColumn\":\"公必学分\",\"twoColumn\":\""+param[2][0]+"\",\"class\":\""+temp1+"\"}");
        } if(param[2][1] != null && !param[2][1].equals("")) {
            if(!alternativ.equals("")) alternativ.append(",");
            alternativ.append("{\"oneColumn\":\"专必学分\",\"twoColumn\":\""+param[2][1]+"\",\"class\":\""+temp1+"\"}");
        } if(param[2][2] != null && !param[2][2].equals("")) {
            if(!alternativ.equals("")) alternativ.append(",");
            alternativ.append("{\"oneColumn\":\"专选学分\",\"twoColumn\":\""+param[2][2]+"\",\"class\":\""+temp1+"\"}");
        }if(param[2][3] != null && !param[2][3].equals("")) {
            if(!alternativ.equals("")) alternativ.append(",");
            alternativ.append("{\"oneColumn\":\"公选学分\",\"twoColumn\":\""+param[2][3]+"\",\"class\":\""+temp1+"\"}");
        }
        String res =
                "{header:{\"code\": -100, \"message\": {\"title\": \"\", \"detail\": \"\"}},body:{dataStores:{zxzyxfStore:{rowSet:{\"primary\":[" +
                "{\"oneColumn\":\"公必学分\",\"twoColumn\":\""+param[0][0]+"\",\"class\":\""+temp1+"\"}," +
                "{\"oneColumn\":\"专必学分\",\"twoColumn\":\""+param[0][1]+"\",\"class\":\""+temp1+"\"}," +
                "{\"oneColumn\":\"专选学分\",\"twoColumn\":\""+param[0][2]+"\",\"class\":\""+temp1+"\"}," +
                "{\"oneColumn\":\"公选学分\",\"twoColumn\":\""+param[0][3]+"\",\"class\":\""+temp1+"\"}]," +
                "\"filter\":[],\"delete\":[]},name:\"zxzyxfStore\",pageNumber:1,pageSize:0,recordCount:4,rowSetName:\""+temp2+"\",metaData:{\"twoColumn\": " +
                "{\"dataType\": 12}, \"class\": {\"dataType\": 12}, \"serialVersionUID\": {\"dataType\": 12}, \"oneColumn\":" +
                " {\"dataType\": 12}},condition:\"[object Object],[object Object],[object Object]\"},allXfStore:{rowSet:{\"primary\":[" +
                "{\"oneColumn\":\"公必学分\",\"twoColumn\":\""+param[1][0]+"\",\"class\":\""+temp1+"\"}," +
                "{\"oneColumn\":\"专必学分\",\"twoColumn\":\""+param[1][1]+"\",\"class\":\""+temp1+"\"}," +
                "{\"oneColumn\":\"专选学分\",\"twoColumn\":\""+param[1][2]+"\",\"class\":\""+temp1+"\"}," +
                "{\"oneColumn\":\"公选学分\",\"twoColumn\":\""+param[1][3]+"\",\"class\":\""+temp1+"\"}]," +
                "\"filter\":[],\"delete\":[]},name:\"allXfStore\",pageNumber:1,pageSize:0,recordCount:4,rowSetName:\""+temp2+"\",metaData:" +
                "{\"twoColumn\": {\"dataType\": 12}, \"class\": {\"dataType\": 12}, \"serialVersionUID\": {\"dataType\": 12}, \"oneColumn\": {\"dataType\": 12}}},xfStore:{rowSet:{\"primary\":[" +
                        alternativ +
                "], \"filter\":[],\"delete\":[]},name:\"xfStore\",pageNumber:1,pageSize:0,recordCount:4,rowSetName:\""+temp2+"\",metaData:{\"twoColumn\":" +
                " {\"dataType\": 12}, \"class\": {\"dataType\": 12}, \"serialVersionUID\": {\"dataType\": 12}, \"oneColumn\": {\"dataType\": 12}}}},parameters:" +
                "{\"args\": [\"ds_zxzyxfStore_all\", \"ds_allXfStore_all\", \"ds_xfStore_all\", \""+username+"\", \""+xn+"\", \""+xq+"\", \""+pylx+"\"], \"responseParam\": \"str\"}}}";
        return res;
    }

    public static String getLeftJd(String username, String xn, String xq) {
        return "{header:{\"code\": -100, \"message\": {\"title\": \"\", \"detail\": \"\"}},body:{dataStores:{jdStore:" +
                "{rowSet:{\"primary\":[],\"filter\":[],\"delete\":[]},name:\"jdStore\",pageNumber:1,pageSize:0,recordCount:4," +
                "rowSetName:\"pojo_com.neusoft.education.sysu.djks.ksgl.model.TwoColumnModel\"}},parameters:{\"args\":" +
                " [\""+username+"\", \""+ xn +"\", \""+ xq +"\", \"\"]}}}";
    }

    public static String getLeftXf(String username, String xn, String xq, String pylb) {
        return "{header:{\"code\": -100, \"message\": {\"title\": \"\", \"detail\": \"\"}},body:{dataStores:{xfStore:{rowSet:" +
                "{\"primary\":[],\"filter\":[],\"delete\":[]},name:\"xfStore\",pageNumber:1,pageSize:0,recordCount:4,rowSetName:" +
                "\"pojo_com.neusoft.education.sysu.djks.ksgl.model.TwoColumnModel\"}},parameters:{\"args\": [\"" +
                username+"\", \""+ xn +"\", \""+ xq +"\", \""+ pylb +"\"]}}}";
    }
    public static String getMidXf(String username, String pylb) {
        return "{header:{\"code\": -100, \"message\": {\"title\": \"\", \"detail\": \"\"}},body:{dataStores:{allXfStore:{rowSet" +
                ":{\"primary\":[],\"filter\":[],\"delete\":[]},name:\"allXfStore\",pageNumber:1,pageSize:0,recordCount:4,rowSetName:" +
                "\"pojo_com.neusoft.education.sysu.djks.ksgl.model.TwoColumnModel\"}},parameters:{\"args\": [\""+username+"\", \"\", \"\", \""+
                pylb+"\"]}}}";
    }

    public static String getMidJd(String username) {
        return "{header:{\"code\": -100, \"message\": {\"title\": \"\", \"detail\": \"\"}},body:{dataStores:{allJdStore:{rowSet:" +
                "{\"primary\":[],\"filter\":[],\"delete\":[]},name:\"allJdStore\",pageNumber:1,pageSize:0,recordCount:4,rowSetName:" +
                "\"pojo_com.neusoft.education.sysu.djks.ksgl.model.TwoColumnModel\"}},parameters:{\"args\": [\""+username+"\", \"\", \"\", \"\"]}}}";
    }

    public static String getRight(String pylb, String grage, String pro) {
        return "{header:{\"code\": -100, \"message\": {\"title\": \"\", \"detail\": \"\"}},body:{dataStores:{zxzyxfStore:{rowSet:" +
                "{\"primary\":[],\"filter\":[],\"delete\":[]},name:\"zxzyxfStore\",pageNumber:1,pageSize:0,recordCount:4,rowSetName:" +
                "\"pojo_com.neusoft.education.sysu.djks.ksgl.model.TwoColumnModel\"}},parameters:{\"zxzyxfStore-params\": " +
                "[{\"name\": \"pylbm\", \"type\": \"String\", \"value\": \"'"+pylb+"'\", \"condition\": \" = \", \"property\": \"x.pylbm\"}," +
                " {\"name\": \"nj\", \"type\": \"String\", \"value\": \"'"+grage+"'\", \"condition\": \" = \", \"property\": \"x.nj\"}, " +
                "{\"name\": \"zyh\", \"type\": \"String\", \"value\": \"'"+pro+"'\", \"condition\": \" = \", \"property\": \"x.zyh\"}], \"args\": []}}}";
    }
}
