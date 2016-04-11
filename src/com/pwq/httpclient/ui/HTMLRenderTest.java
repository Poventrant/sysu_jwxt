package com.pwq.httpclient.ui;

import com.pwq.httpclient.BaseUtil;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 枫叶 on 2016/4/11.
 */
public class HTMLRenderTest extends JFrame{

    void render() {
        JFrame frame = new JFrame();
        frame.setFocusable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font font1 = new Font("微软雅黑", Font.PLAIN, 11);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            BaseUtil.setFont("微软雅黑");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
        }

        String html = "<html><head><style>body{font-family: 'Arial Normal', 'Arial', 'Microsoft Yahei', '微软雅黑', Helvetica,sans-serif,宋体;font-size:10px;}" +
                "#subprinttable1 TD.tab_1, #subprinttable1 TD.tab_2, #subprinttable1 TD.tab_3, #subprinttable1 TD.tab_4, #subprinttable1 TD.tab_5, " +
                "#subprinttable1 TD.tab_6{background-color: #b9cbfa;}#subprinttable1 TD {border: 1px solid #d3dffa;text-align: center;}TD {margin: 0px;padding: 0px;}#subprinttable1" +
                " {border: 0px solid #d3dffa;border-collapse: collapse;height: 100%;}</style></head><body><table id='subprinttable1'><tr class='tab_3'>" +
                "<td>&nbsp;</td><td>星期一</td><td>星期二</td><td>星期三</td><td>星期四</td><td>星期五</td><td>星期六</td><td>星期日</td></tr><tr>" +
                "<td width='180' class='tab_3' >第1节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td>" +
                "<td width='200'>&nbsp;</td><td class='tab_1' rowspan=5 width='200'>专业技术综合实践Ⅰ <br>东B204<br>1-5节<br>(2-3周)</td>" +
                "<td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第2节</td><td width='200'>&nbsp;</td><td class='tab_1' rowspan=4 width='200'>物联网导论与综合实践<br>" +
                "东B102<br>2-5节<br>(3-5周)</td><td class='tab_1' rowspan=4 width='200'>专业技术综合实践Ⅰ <br>实验中心B401<br>2-5节<br>(2-3周)</td><td width='200'>" +
                "&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第3节</td><td width='200'>&nbsp;</td><td class='tab_1' rowspan=3 width='200'>" +
                "数字家庭技术综合实践<br>东B102<br>3-5节<br>(1-5周)</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第4节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第5节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第6节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td>" +
                "<td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第7节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;" +
                "</td><td width='200'>&nbsp;</td><td class='tab_1' rowspan=5 width='200'>物联网导论与综合实践<br>东B102<br>7-11节<br>(3-5周)</td>" +
                "<td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第8节</td><td width='200'>&nbsp;</td><td class='tab_1' rowspan=3 width='200'>物联网导论与综合实践<br>东B102<br>8-10节" +
                "<br>(3-5周)</td><td width='200'>&nbsp;</td><td class='tab_1' rowspan=3 width='200'>数字家庭技术综合实践<br>东B102<br>8-10节<br>(1-5周)</td>" +
                "<td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第9节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第10节</td><td class='tab_1' rowspan=2 width='200'>数字家庭技术综合实践<br>东B102<br>10-11节<br>(1-5周)</td><td width='200'>" +
                "&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第11节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td>" +
                "<td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第12节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td>" +
                "<td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第13节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;" +
                "</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第14节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>" +
                "&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr>" +
                "<td width='180' class='tab_3' >第15节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;" +
                "</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr></table></body></html>\n";

        System.out.println(html);
        Pattern p = Pattern.compile("(第[\\d]*节)");
        Matcher m = p.matcher(html);
        String times[] = new String[] {"08:00-08:45", "08:55-09:40", "09:50-10:35", "10:45-11:30", "11:40-12:25",
                "12:35-13:20", "13:30-14:15", "14:25-15:10", "15:20-16:05", "16:15-17:00", "17:10-17:55", "18:05-18:50",
                "19:00-19:45", "19:55-20:40", "20:50-21:35"};
        int count = 0;
        while(m.find()) {
            html = html.replaceAll(m.group(1), m.group(1) + "<br/>" + times[count ++]);
        }

        JLabel course = new JLabel(html);

        frame.add(course);
        frame.pack();

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HTMLRenderTest().render();
            }
        });
    }
}
