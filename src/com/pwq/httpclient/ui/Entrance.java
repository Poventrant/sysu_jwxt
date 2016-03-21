package com.pwq.httpclient.ui;

import com.pwq.httpclient.JavascriptUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 枫叶 on 2016/1/19.
 */
public class Entrance extends JFrame {

    final  String savePasswordPath = "./password";

    private void loginFrame() {

        Font font1 = new Font("微软雅黑", Font.PLAIN, 13);

        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
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

        String [] savePwd = getPassword();
        boolean flag = savePwd == null ? false : true;

        JFrame frame = new JFrame("中山大学第N方教务系统");
        try {
            InputStream iconStream = this.getClass().getResourceAsStream("/logo.jpg");
            frame.setIconImage(ImageIO.read(iconStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setSize(420, 280);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centered(frame);

        frame.setLayout(null);//设置布局管理器为空

        JLabel t1 = new JLabel("用户名");
        t1.setBounds(30, 60, 50, 30);
        frame.add(t1);
        JTextField field = new JTextField(flag?savePwd[0]:"");
        field.setFont(font1);
        field.setBounds(new Rectangle(100, 60, 180, 25));
        frame.add(field);

        JLabel t2 = new JLabel("密码");
        t2.setBounds(30, 90, 50, 30);
        frame.add(t2);
        JPasswordField tfPwd = new JPasswordField(flag?savePwd[1]:"");
        tfPwd.setFont(font1);
        tfPwd.setBounds(new Rectangle(100, 90, 180, 25));
        frame.add(tfPwd);

        JLabel t3 = new JLabel("验证码");
        t3.setBounds(30, 120, 50, 30);
        frame.add(t3);
        JTextField captcha = new JTextField();
        captcha.setFont(font1);
        captcha.setBounds(new Rectangle(100, 120, 180, 25));
        frame.add(captcha);

        LoginUtil util = new LoginUtil();

        HttpGet httpget = util.getHttpGet(Paths.ENTRANCE);
        try {
            HttpResponse response = util.httpclient.execute(httpget);
            String html = EntityUtils.toString(response.getEntity());
            String pat = "id=\"rno\"[\\s]*name=\"rno\"[\\s]*value=([^>]*)";
            Pattern reg = Pattern.compile(pat);
            Matcher m = reg.matcher(html);
            if(m.find()) {
                LoginUtil.rno = m.group(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpget.releaseConnection();
        }

        //下载验证码图片
        JLabel t4 = new JLabel();
        t4.setIcon( new ImageIcon(util.getImage(Paths.CAPTCHA, util.httpclient) ) );
        t4.setBounds(300, 120, 100, 25);
        t4.setToolTipText("更换验证码");
        frame.add(t4);

        JCheckBox cbxkclb3 = new JCheckBox("  记住密码  ");   //30
        if(flag) cbxkclb3.setSelected(true);
        cbxkclb3.setBounds(new Rectangle(100, 150, 100, 30));//参数分别是坐标x，y，宽，高
        frame.add(cbxkclb3);

        JButton btn = new JButton("登     录");
        btn.setBounds(new Rectangle(100, 190, 180, 30));//参数分别是坐标x，y，宽，高
        frame.add(btn);

        //错误提示
        JLabel errorHit = new JLabel();
        errorHit.setForeground(Color.RED);
        errorHit.setBounds(100, 15, 200, 30);

        frame.add(errorHit);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = field.getText(), password = String.valueOf(tfPwd.getPassword());
                if(!flag) password = JavascriptUtil.encodePassword(password);
                boolean success = util.login(username, password, captcha.getText());
                if(success) {
                    frame.setVisible(false);
                    if(cbxkclb3.isSelected()) {
                        savePassword(username, password);
                    } else {
                        deleteSavePassword();
                    }
                    new Query().queryFrame(username);
                } else {
                    errorHit.setText("用户名或密码或验证码非法");
                    t4.setIcon( new ImageIcon(util.getImage(Paths.CAPTCHA, util.httpclient) ) );
                }
            }
        });
        frame.getRootPane().setDefaultButton(btn);

        t4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                t4.setIcon( new ImageIcon(util.getImage(Paths.CAPTCHA, util.httpclient) ) );
            }
        });
        //退出时候注销
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    HttpGet httpget = new HttpGet(Paths.LOGOUT);
                    util.httpclient.execute(httpget);
                    util.httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void deleteSavePassword() {
        File file = new File(savePasswordPath);
        if(file.exists()) {
            file.delete();
        }
    }

    private String[] getPassword()  {
        File file = new File(savePasswordPath);
        if(!file.exists()) {
            return null;
        }

        String line = "";
        String encoding = "UTF-8";
        String[] temp = new String[2];
        if (file.exists() && file.isFile()) {
            InputStreamReader reader = null;
            try {
                reader = new InputStreamReader(new FileInputStream(file), encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(reader);
                int i = 0;
                while ((line = bufferedReader.readLine()) != null)  {
                    if(i >= 2) break;
                    temp[i++] = line;
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }


    private void savePassword(String username, String password) {
        File file = new File(savePasswordPath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(username+"\r\n"+password);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void centered(Container container) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int w = container.getWidth();
        int h = container.getHeight();
        container.setBounds((screenSize.width - w) / 2,
                (screenSize.height - h) / 2, w, h);
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Entrance().loginFrame();
            }
        });
    }

    private void setFont(String fontType) {
        UIManager.put("Button.font", fontType);
        UIManager.put("ComboBox.font", fontType);
        UIManager.put("Label.font", fontType);
        UIManager.put("Panel.font", fontType);
    }

}
