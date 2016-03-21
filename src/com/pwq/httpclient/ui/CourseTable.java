package com.pwq.httpclient.ui;

import com.pwq.httpclient.BaseUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 枫叶 on 2016/3/22.
 */
public class CourseTable extends JFrame {
    static String html = "<html><head><style>body{font-family: 'Arial Normal', 'Arial', 'Microsoft Yahei', '微软雅黑', Helvetica,sans-serif,宋体;font-size:10px;}#subprinttable1 TD.tab_1, #subprinttable1 TD.tab_2, #subprinttable1 TD.tab_3, #subprinttable1 TD.tab_4, #subprinttable1 TD.tab_5, #subprinttable1 TD.tab_6{background-color: #b9cbfa;}#subprinttable1 TD {border: 1px solid #d3dffa;text-align: center;}TD {margin: 0;padding: 0;}#subprinttable1 {border: 1px solid #d3dffa;border-collapse: collapse;height: 100%;}</style></head><body><table id='subprinttable1'><tr class='tab_3'><td>&nbsp;</td><td>星期一</td><td>星期二</td><td>星期三</td><td>星期四</td><td>星期五</td><td>星期六</td><td>星期日</td></tr><tr><td width='180' class='tab_3' >第1节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第2节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td class='tab_1' rowspan=2 width='200'>编译器构造实验<br>实验中心B201<br>2-3节<br>(1-17周)</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第3节</td><td width='200'>&nbsp;</td><td class='tab_1' rowspan=3 width='200'>编译原理<br>东C103<br>3-5节<br>(1-17周)</td><td class='tab_1' rowspan=3 width='200'>软件工程导论<br>东C401<br>3-5节<br>(1-17周)</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第4节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第5节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第6节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第7节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第8节</td><td class='tab_1' rowspan=2 width='200'>软件工程实验<br>实验中心B403<br>8-9节<br>(1-17周)</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第9节</td><td class='tab_1' rowspan=3 width='200'>信息论与编码<br>东C302<br>9-11节<br>(1-12周)</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第10节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第11节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第12节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第13节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第14节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr><tr><td width='180' class='tab_3' >第15节</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td><td width='200'>&nbsp;</td></tr></table></body></html>";
    public void renderTable() {

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

        frame.add(new JLabel(html));
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                new CourseTable().renderTable();
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
