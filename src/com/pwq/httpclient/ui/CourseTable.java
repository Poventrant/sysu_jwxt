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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 枫叶 on 2016/3/22.
 */
public class CourseTable extends JFrame {
    static String htmlBegin = "<html><head><style>body{font-family: 'Arial Normal', 'Arial', 'Microsoft Yahei', '微软雅黑', Helvetica,sans-serif,宋体;font-size:10px;}" +
            "#subprinttable1 TD.tab_1, #subprinttable1 TD.tab_2, #subprinttable1 TD.tab_3, #subprinttable1 TD.tab_4, #subprinttable1 TD.tab_5, " +
            "#subprinttable1 TD.tab_6{background-color: #b9cbfa;}#subprinttable1 TD {border: 1px solid #d3dffa;text-align: center;}" +
            "TD {margin: 0;padding: 0;}#subprinttable1 {border: 1px solid #d3dffa;border-collapse: collapse;height: 100%;}</style></head><body>";
    static String htmlEnd = "</body></html>";
    JFrame frame = new JFrame();
    JLabel course = null;

    public void renderTable(String xn, String xq) {
        if (course != null) {
            reshow(xn, xq);
            return;
        }
        Font font1 = new Font("微软雅黑", Font.PLAIN, 11);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            BaseUtil.setFont("微软雅黑");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
        }

        frame.setResizable(true);
        frame.setSize(870, 538);        //黄金分割率
        frame.setMinimumSize(new Dimension(870, 538));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);

        course = new JLabel(htmlBegin + appendTime(getHtml(xn, xq)) + htmlEnd);
        frame.add(course);

        frame.pack();
        frame.setVisible(true);
    }

    String getHtml(String xn, String xq) {
        HttpClient httpclient = BaseUtil.getHttpClient();

        HttpPost httppost = BaseUtil.getHttpPost(Paths.COURSES);
        StringEntity reqEntity = null;
        try {
            reqEntity = new StringEntity(PathsImpl.getCourses(xn, xq));
            BaseUtil.setReqHeader(httppost, "http://uems.sysu.edu.cn/jwxt/sysu/xk/xskbcx/xskbcx.jsp?xnd=" + xn + "&xq=" + xq);
            httppost.setEntity(reqEntity);
            HttpEntity entity = httpclient.execute(httppost).getEntity();
            HashMap<String, Object> map = JsonUtil.jsonToMap(EntityUtils.toString(entity));
            HashMap<String, java.lang.Object> map0 = (HashMap<String, java.lang.Object>) map.get("body");
            HashMap<String, java.lang.Object> map1 = (HashMap<String, java.lang.Object>) map0.get("parameters");
            String htmlStr = (String) map1.get("rs");
            return htmlStr;
        } catch (IOException e) {
        }
        return "";
    }

    private String appendTime(String html) {
        Pattern p = Pattern.compile("(第[\\d]*节)");
        Matcher m = p.matcher(html);
        String times[] = new String[] {"08:00-08:45", "08:55-09:40", "09:50-10:35", "10:45-11:30", "11:40-12:25",
                "12:35-13:20", "13:30-14:15", "14:25-15:10", "15:20-16:05", "16:15-17:00", "17:10-17:55", "18:05-18:50",
                "19:00-19:45", "19:55-20:40", "20:50-21:35"};
        int count = 0;
        while(m.find()) {
            html = html.replaceAll(m.group(1), m.group(1) + "<br/>" + times[count ++]);
        }
        return html;
    }

    private void reshow(String xn, String xq) {
        course.setText(htmlBegin + appendTime(getHtml(xn, xq)) + htmlEnd);
        frame.pack();
        Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        int height = frame.getHeight();
        frameSize.setSize(frame.getWidth() > screen.getWidth() ? screen.getWidth() : frame.getWidth() ,height);
        frame.setSize(frameSize);
        frame.setVisible(true);
    }

}
