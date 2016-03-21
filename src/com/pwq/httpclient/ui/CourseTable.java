package com.pwq.httpclient.ui;

import com.pwq.httpclient.BaseUtil;
import com.pwq.httpclient.JsonUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by 枫叶 on 2016/3/22.
 */
public class CourseTable extends JFrame {
    static String htmlBegin = "<html><head><style>body{font-family: 'Arial Normal', 'Arial', 'Microsoft Yahei', '微软雅黑', Helvetica,sans-serif,宋体;font-size:10px;}#subprinttable1 TD.tab_1, #subprinttable1 TD.tab_2, #subprinttable1 TD.tab_3, #subprinttable1 TD.tab_4, #subprinttable1 TD.tab_5, #subprinttable1 TD.tab_6{background-color: #b9cbfa;}#subprinttable1 TD {border: 1px solid #d3dffa;text-align: center;}TD {margin: 0;padding: 0;}#subprinttable1 {border: 1px solid #d3dffa;border-collapse: collapse;height: 100%;}</style></head><body>";
    static String htmlEnd = "</body></html>";
    public void renderTable(String xn, String xq) {

        Font font1 = new Font("微软雅黑", Font.PLAIN, 11);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            BaseUtil.setFont("微软雅黑");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) { }

        JFrame frame = new JFrame();
        frame.setResizable(true);
        frame.setSize(870, 538);        //黄金分割率
        frame.setMinimumSize(new Dimension(870, 538));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        HttpClient httpclient = BaseUtil.getHttpClient();

        HttpPost httppost = BaseUtil.getHttpPost(Paths.COURSES);
        StringEntity reqEntity = null;
        try {
            reqEntity = new StringEntity(PathsImpl.getCourses(xn, xq));
            BaseUtil.setReqHeader(httppost, "http://uems.sysu.edu.cn/jwxt/sysu/xk/xskbcx/xskbcx.jsp?xnd="+xn+"&xq="+xq);
            httppost.setEntity(reqEntity);
            HttpEntity entity = httpclient.execute(httppost).getEntity();
            HashMap<String, Object> map = JsonUtil.jsonToMap(EntityUtils.toString(entity));
            HashMap<String, java.lang.Object> map0 = (HashMap<String, java.lang.Object>) map.get("body");
            HashMap<String, java.lang.Object> map1 = (HashMap<String, java.lang.Object>) map0.get("parameters");
            String htmlStr = (String) map1.get("rs");
            frame.add(new JLabel(htmlBegin+htmlStr+htmlEnd));
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    /*public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                new CourseTable().renderTable();
            }
        };
        SwingUtilities.invokeLater(r);
    }*/
}
