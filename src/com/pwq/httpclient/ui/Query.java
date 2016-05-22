package com.pwq.httpclient.ui;

import com.pwq.httpclient.JsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 枫叶 on 2016/1/20.
 */
public class Query extends JFrame {

    public void queryFrame(final LoginUtil util, final String username, final HttpContext localContext) {

        Font font1 = new Font("微软雅黑", Font.PLAIN, 13);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setFont("微软雅黑");
        }
        catch (UnsupportedLookAndFeelException e) {
        }
        catch (ClassNotFoundException e) {
        }
        catch (InstantiationException e) {
        }
        catch (IllegalAccessException e) {
        }


        JFrame frame = new JFrame("中山大学第N方教务系统");
        try {
            InputStream iconStream = this.getClass().getResourceAsStream("/logo.jpg");
            frame.setIconImage(ImageIO.read(iconStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setResizable(true);
        frame.setSize(880, 520);
        frame.setMinimumSize(new Dimension(880, 520));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //查询学年，用于选择
        HttpGet httpget = util.getHttpGet(Paths.ALLXN);
        HttpResponse response = null;
        Vector model = new Vector();
        try {
            response = util.getHttpClient().execute(httpget, localContext);
            String jsonStr = EntityUtils.toString(response.getEntity());

            String pattern = "CODENAME:\\s*\"([0-9]{4}-[0-9]{4})\"";
            Pattern reg = Pattern.compile(pattern);
            Matcher m = reg.matcher(jsonStr);
            while (m.find()) {
                model.addElement( new SelectItem(m.group(1), m.group(1)) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpget.releaseConnection();
        }
        JPanel params = new JPanel(); //use FlowLayout
        final JComboBox cbxn = new JComboBox(model);
        cbxn.setFont(font1);
        cbxn.setEditable(false);

        final JComboBox cbxq = new JComboBox(new SelectItem[]{new SelectItem("第一学期", "1"),new SelectItem("第二学期", "2"), new SelectItem("第三学期", "3")});
        cbxq.setFont(font1);
        cbxq.setEditable(false);

        final JComboBox cbpylb = new JComboBox(new SelectItem[]{new SelectItem("主修", "01"),new SelectItem("双专业", "03"), new SelectItem("双学位", "04"), new SelectItem("辅修", "02")});
        cbpylb.setFont(font1);
        cbpylb.setEditable(false);

        final JCheckBox cbxkclb0 = new JCheckBox("专必");   //11
        cbxkclb0.setSelected(true);
        final JCheckBox cbxkclb1 = new JCheckBox("专选");   //21
        cbxkclb1.setSelected(true);
        final JCheckBox cbxkclb2 = new JCheckBox("公必");   //10
        cbxkclb2.setSelected(true);
        final JCheckBox cbxkclb3 = new JCheckBox("公选");   //30
        cbxkclb3.setSelected(true);

        JButton queryBtn = new JButton("查  询");

        JLabel t0 = new JLabel("学年度 ");
        JLabel t1 = new JLabel("    学期 ");
        JLabel t2 = new JLabel("    培养类别 ");
        JLabel t3 = new JLabel("    课程类别 ");

        params.add(t0);
        params.add(cbxn);
        params.add(t1);
        params.add(cbxq);
        params.add(t2);
        params.add(cbpylb);
        params.add(t3);
        params.add(cbxkclb0);
        params.add(cbxkclb1);
        params.add(cbxkclb2);
        params.add(cbxkclb3);
        params.add(queryBtn);
        frame.add(params, BorderLayout.NORTH);

        final MyTableModel tableModel = new MyTableModel();
        tableModel.setColumnNames(new String[]{ "课程名称", "课程类别", "教师", "学分", "成绩", "绩点", "教学班排名"});
        tableModel.setLen(7);
        JTable table = new JTable(tableModel);
        TableColumn tcol = table.getColumnModel().getColumn(0);
        tcol.setPreferredWidth(200);
        table.setFont(font1);
        table.setEnabled(false);
        table.setShowGrid(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 20, 20, 20));
        frame.add(scrollPane, BorderLayout.CENTER);


        //----------------------------------------------------------------------------------------------------------------
        final JLabel credit_title0 = new JLabel("学年度第一学期完成情况");
        JLabel credit_title1 = new JLabel("全部学年度学期完成情况");
        JLabel credit_title2 = new JLabel("主修专业毕业学分要求");

        JPanel downPanel = new JPanel();
        downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));


        //--------------------------left------------------------------------------------------------------------
        final MyTableModel lTableModel = new MyTableModel();
        lTableModel.setColumnNames(new String[]{ "", "学分", "平均绩点"});
        lTableModel.setLen(3);
        JTable leftTable = new JTable(lTableModel);
        leftTable.setFont(font1);
        leftTable.setEnabled(false);
        leftTable.setShowGrid(false);
        JPanel lPanel = new JPanel();
        lPanel.setLayout(new BoxLayout(lPanel, BoxLayout.Y_AXIS));
        lPanel.add(credit_title0, BorderLayout.NORTH);
        JScrollPane lcreditSP = new JScrollPane(leftTable);
        lcreditSP.setBorder(new EmptyBorder(10, 20, 10, 5));
        lcreditSP.setPreferredSize(new Dimension(100, 150));
        lPanel.add(lcreditSP, BorderLayout.SOUTH);

        //--------------------------middle------------------------------------------------------------------------
        final MyTableModel mTableModel = new MyTableModel();
        mTableModel.setColumnNames(new String[]{ "", "学分", "平均绩点"});
        mTableModel.setLen(3);
        JTable midTable = new JTable(mTableModel);
        midTable.setFont(font1);
        midTable.setEnabled(false);
        midTable.setShowGrid(false);
        JPanel mPanel = new JPanel();
        mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
        mPanel.add(credit_title1, BorderLayout.NORTH);
        JScrollPane mcreditSP = new JScrollPane(midTable);
        mcreditSP.setBorder(new EmptyBorder(10, 5, 10, 5));
        mcreditSP.setPreferredSize(new Dimension(100, 150));
        mPanel.add(mcreditSP, BorderLayout.SOUTH);

        //--------------------------right------------------------------------------------------------------------
        final MyTableModel rTableModel = new MyTableModel();
        rTableModel.setColumnNames(new String[]{ "", "学分"});
        rTableModel.setLen(2);
        JTable rightTable = new JTable(rTableModel);
        rightTable.setFont(font1);
        rightTable.setEnabled(false);
        rightTable.setShowGrid(false);
        JPanel rPanel = new JPanel();
        rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
        rPanel.add(credit_title2, BorderLayout.NORTH);
        JScrollPane rcreditSP = new JScrollPane(rightTable);
        rcreditSP.setBorder(new EmptyBorder(10, 5, 10, 20));
        rcreditSP.setPreferredSize(new Dimension(100, 150));
        rPanel.add(rcreditSP, BorderLayout.SOUTH);
        //--------------------------add------------------------------------------------------------------------
        downPanel.add(lPanel, BorderLayout.WEST);
        downPanel.add(mPanel, BorderLayout.CENTER);
        downPanel.add(rPanel, BorderLayout.EAST);

        frame.add(downPanel, BorderLayout.SOUTH);

        //获取学生基本信息
        try {
            HttpPost httppost = util.getHttpPost(Paths.JUDGE);
            StringEntity reqEntity = new StringEntity(PathsImpl.getJudge());
            reqEntity.setContentType("application/json");
            setReqHeader(httppost, "http://uems.sysu.edu.cn/jwxt/forward.action?path=/sysu/xscj/xscjcx/xsgrcj");
            httppost.setEntity(reqEntity);
            HttpEntity entity = util.getHttpClient().execute(httppost, localContext).getEntity();
            String pat = "result:\"([^\"]*)";
            Pattern reg = Pattern.compile(pat);
            Matcher m = reg.matcher(EntityUtils.toString(entity));
            if(m.find()) {
                String temp[] = m.group(1).split(",");
                setGrade(temp[1]);      //年级级数
                setProNo(temp[2]);      //专业编号
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        queryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checked = "";
                if(cbxkclb0.isSelected()) checked+="'11'";
                if(cbxkclb1.isSelected()) {
                    if(checked.equals(""))  checked+="'21'";
                    else checked+=",'21'";
                }
                if(cbxkclb2.isSelected()) {
                    if(checked.equals("")) checked+="'10'";
                    else checked+=",'10'";
                }
                if(cbxkclb3.isSelected()) {
                    if(checked.equals("")) checked+="'30'";
                    else checked+=",'30'";
                }

                String mainXn = ((SelectItem) cbxn.getSelectedItem()).getValue(),
                        mainXq = ((SelectItem) cbxq.getSelectedItem()).getValue(),
                        mainPylb = ((SelectItem) cbpylb.getSelectedItem()).getValue();


                HttpPost httppost = util.getHttpPost(Paths.KCCJLIST);
                try {
                    httppost = util.getHttpPost(Paths.KCCJLIST);
                    StringEntity reqEntity = new StringEntity(PathsImpl.getKcList(mainXn, mainXq, mainPylb, checked));
                    reqEntity.setContentType("application/json");
                    setReqHeader(httppost, "http://uems.sysu.edu.cn/jwxt/forward.action?path=/sysu/xscj/xscjcx/xsgrcj_list");
                    httppost.setEntity(reqEntity);

                    HttpEntity entity = util.getHttpClient().execute(httppost, localContext).getEntity();
                    String postResult = EntityUtils.toString(entity);

                    HashMap<String, java.lang.Object> map = JsonUtil.jsonToMap(postResult);
                    HashMap<String, java.lang.Object> map0 = (HashMap<String, java.lang.Object>) map.get("body");
                    HashMap<String, java.lang.Object> map1 = (HashMap<String, java.lang.Object>) map0.get("dataStores");
                    HashMap<String, java.lang.Object> map2 = (HashMap<String, java.lang.Object>) map1.get("kccjStore");
                    HashMap<String, java.lang.Object> map3 = (HashMap<String, java.lang.Object>) map2.get("rowSet");
                    ArrayList<HashMap<String, String>> map4 = (ArrayList<HashMap<String, String>>) map3.get("primary");

                    tableModel.clearTable();
                    for(HashMap<String, String> m : map4) {
                        Set<Map.Entry<String, String>> es =  m.entrySet();
                        String[] temp = new String[7];
                        for(Map.Entry<String, String> te : es) {
                            if (te.getKey().equals("kcmc"))temp[0] = te.getValue();
                            else if (te.getKey().equals("kclb")) {
                                if(te.getValue().equals("11")) {
                                    temp[1] = "专必";
                                } else if(te.getValue().equals("21")) {
                                    temp[1] = "专选";
                                } else if(te.getValue().equals("10")) {
                                    temp[1] = "公必";
                                } else if(te.getValue().equals("30")) {
                                    temp[1] = "公选";
                                } else {
                                    temp[1] = "其他";
                                }
                            }
                            else if (te.getKey().equals("jsxm"))temp[2] = te.getValue();
                            else if (te.getKey().equals("xf")) temp[3] = te.getValue();
                            else if (te.getKey().equals("zzcj"))temp[4] = te.getValue();
                            else if (te.getKey().equals("jd"))temp[5] = te.getValue();
                            else if (te.getKey().equals("jxbpm"))temp[6] = te.getValue();
                        }
                        tableModel.addRow(temp);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    httppost.releaseConnection();
                }


                //下边栏-----------------------------------left-----------------------------------------------------------------------------------------------------------
                String[][] leftRows = new String[5][3];
                try {
                    StringEntity entityStr0 = new StringEntity(PathsImpl.getLeftJd(username, mainXn, mainXq)),//左边绩点
                            entityStr1 = new StringEntity( PathsImpl.getLeftXf(username, mainXn, mainXq, mainPylb));//左边学分


                    ArrayList<HashMap<String, String>> lmapjd = httpGetAllXFJD(entityStr0, util, Paths.ALLJD, "jdStore", localContext);
                    int len = 1;
                    for(HashMap<String, String> m : lmapjd) {
                        Set<Map.Entry<String, String>> es = m.entrySet();
                        String[] temp = new String[3];
                        for (Map.Entry<String, String> te : es) {
                            if (te.getKey().equals("oneColumn")) {
                                temp[0] = te.getValue().substring(0,2);
                            }
                            else if(te.getKey().equals("twoColumn")) {
                                temp[2] = te.getValue();   //第3列是绩点
                            }
                        }
                        leftRows[len++] = temp;
                    }

                    ArrayList<HashMap<String, String>> lmapxf = httpGetAllXFJD(entityStr0, util, Paths.ALLXF, "jdStore", localContext);
                    len = 1;
                    for(HashMap<String, String> m : lmapxf) {
                        Set<Map.Entry<String, String>> es = m.entrySet();
                        String[] temp = new String[3];
                        for (Map.Entry<String, String> te : es) {
                            if(te.getKey().equals("twoColumn"))  {
                                leftRows[len++][1] = te.getValue();   //第2列是学分
                            }
                        }
                    }

                } catch (UnsupportedEncodingException et) {
                    et.printStackTrace();
                }
                //-------------------------------------middle----------------------------------------------------------------------------------------------------------------
                String[][] midRows = new String[5][3];
                try {
                    StringEntity entityStr0 = new StringEntity(PathsImpl.getMidXf(username, mainPylb)),//学分
                    entityStr1 = new StringEntity(PathsImpl.getMidJd(username)); //绩点

                    ArrayList<HashMap<String, String>> lmapallxf = httpGetAllXFJD(entityStr0, util, Paths.ALLXF, "allXfStore", localContext);
                    int len = 1;
                    for(HashMap<String, String> m : lmapallxf) {
                        Set<Map.Entry<String, String>> es = m.entrySet();
                        String[] temp = new String[3];
                        for (Map.Entry<String, String> te : es) {
                            if (te.getKey().equals("oneColumn")) {
                                temp[0] = te.getValue().substring(0,2);
                            }
                            else if(te.getKey().equals("twoColumn")) {
                                temp[1] = te.getValue();   //第2列是学分
                            }
                        }
                        midRows[len++] = temp;
                    }

                    ArrayList<HashMap<String, String>> lmapalljd = httpGetAllXFJD(entityStr1, util, Paths.ALLJD, "allJdStore", localContext);
                    len = 1;
                    for(HashMap<String, String> m : lmapalljd) {
                        Set<Map.Entry<String, String>> es = m.entrySet();
                        String[] temp = new String[3];
                        for (Map.Entry<String, String> te : es) {
                            if(te.getKey().equals("twoColumn"))  {
                                midRows[len++][2] = te.getValue();   //第3列是绩点
                            }
                        }
                    }
                } catch (UnsupportedEncodingException et) {
                    et.printStackTrace();
                }
                //--------------------------------------------------------------right ------------------------------------------------------------------------------
                String[][] rightRows = new String[5][2];
                try {
                    StringEntity entityStr = new StringEntity(PathsImpl.getRight(mainPylb, getGrade(), getProNo()));
                    ArrayList<HashMap<String, String>> lmapallxyxf = httpGetAllXFJD(entityStr, util, Paths.ZYXF, "zxzyxfStore", localContext);

                    int len = 1;
                    for(HashMap<String, String> m : lmapallxyxf) {
                        Set<Map.Entry<String, String>> es = m.entrySet();
                        String[] temp = new String[2];
                        for (Map.Entry<String, String> te : es) {
                            if(te.getKey().equals("twoColumn"))  {
                                temp[1] = te.getValue();
                            } else if(te.getKey().equals("oneColumn")) {
                                temp[0] = te.getValue();
                            }
                        }
                        rightRows[len++] = temp;
                    }
                } catch (UnsupportedEncodingException e11) {
                    e11.printStackTrace();
                }
                //----------------------------------------计算总和-------------------------------------------------------------------------------------------------
                try {
                    String params[][] = new String[3][4];
                    for(int i = 0; i < 4; ++ i) {
                        params[0][i] = rightRows[i+1][1];
                    }
                    for(int i = 0; i < 4; ++ i) {
                        params[1][i] = midRows[i+1][1];
                    }
                    for(int i = 0; i < 4; ++ i) {
                        params[2][i] = leftRows[i+1][1];
                    }
                    StringEntity reqEntity = new StringEntity( PathsImpl.getCountAll(params, username,mainXn, mainXq, mainPylb ) );
                    httppost = util.getHttpPost(Paths.COUNTALL);
                    reqEntity.setContentType("application/json");
                    setReqHeader(httppost, "http://uems.sysu.edu.cn/jwxt/forward.action?path=/sysu/xscj/xscjcx/xsgrcj_list");
                    httppost.setEntity(reqEntity);
                    HttpEntity entity = util.getHttpClient().execute(httppost, localContext).getEntity();
                    String pat = "str:\"([^\"]*)";
                    Pattern reg = Pattern.compile(pat);
                    Matcher m = reg.matcher(EntityUtils.toString(entity));
                    if(m.find()) {
                        String temp[] = m.group(1).split(",");
                        leftRows[0][0] = "总计";
                        leftRows[0][1] = temp[0];
                        leftRows[0][2] = temp[1];

                        midRows[0][0] = "总计";
                        midRows[0][1] = temp[2];
                        midRows[0][2] = temp[3];

                        rightRows[0] = new String[]{"总学分要求", temp[4]};
                    }

                } catch(UnsupportedEncodingException ee) {

                } catch (ClientProtocolException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    httppost.releaseConnection();
                }

                //-----------------------更新表格界面---------------------------------------------------------------------------------------
                credit_title0.setText(mainXn + "学年度第一学期完成情况");

                lTableModel.clearTable();
                for(int i = 0; i < leftRows.length; ++ i) {
                    lTableModel.addRow(leftRows[i]);
                }

                mTableModel.clearTable();
                for(int i = 0; i < midRows.length; ++ i) {
                    mTableModel.addRow(midRows[i]);
                }

                rTableModel.clearTable();
                for(int i = 0; i < rightRows.length; ++ i) {
                    rTableModel.addRow(rightRows[i]);
                }
            }
        });

    }

    private void setReqHeader(HttpEntityEnclosingRequestBase req, String refer) {
        req.setHeader("Content-Type", "multipart/form-data");
        req.setHeader(HttpHeaders.REFERER, refer);
        req.setHeader("_clientType", "unieap");
        req.setHeader("ajaxRequest", "true");
        req.setHeader("render", "unieap");
        req.setHeader("resourceid", null);
        req.setHeader("workitemid", null);
    }

    private ArrayList<HashMap<String, String>> httpGetAllXFJD(StringEntity reqEntity,LoginUtil util, String url, String storeKey, HttpContext localContext) {
        HttpPost httppost = util.getHttpPost(url);
        reqEntity.setContentType("application/json");
        setReqHeader(httppost,  "http://uems.sysu.edu.cn/jwxt/forward.action?path=/sysu/xscj/xscjcx/xsgrcj_list");
        httppost.setEntity(reqEntity);
        try {
            HttpResponse response = util.getHttpClient().execute(httppost, localContext);
            String entityStr = EntityUtils.toString(response.getEntity());
            System.out.println(entityStr);
            HashMap<String, java.lang.Object> map = JsonUtil.jsonToMap(entityStr);
            HashMap<String, java.lang.Object> map0 = (HashMap<String, java.lang.Object>) map.get("body");
            HashMap<String, java.lang.Object> map1 = (HashMap<String, java.lang.Object>) map0.get("dataStores");
            HashMap<String, java.lang.Object> map2 = (HashMap<String, java.lang.Object>) map1.get(storeKey);
            HashMap<String, java.lang.Object> map3 = (HashMap<String, java.lang.Object>) map2.get("rowSet");
            ArrayList<HashMap<String, String>> map4 = (ArrayList<HashMap<String, String>>) map3.get("primary");
            return map4;
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            httppost.releaseConnection();
        }
        return null;
    }


    class SelectItem {
        private String key;
        private String value;

        public SelectItem(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String toString(){return key;}
    }
    private String grade;

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    private String proNo;


    private void setFont(String fontType) {
        UIManager.put("Button.font", fontType);
//        UIManager.put("CheckBox.font", fontType);
        UIManager.put("ComboBox.font", fontType);
        UIManager.put("Label.font", fontType);
//        UIManager.put("CheckBoxMenuItem.font", fontType);
        UIManager.put("Panel.font", fontType);
        UIManager.put("ScrollPane.font", fontType);
        UIManager.put("TabbedPane.font", fontType);
        UIManager.put("Table.font", fontType);
        UIManager.put("TableHeader.font", fontType);
        UIManager.put("PasswordField.font", fontType);
    }
}
