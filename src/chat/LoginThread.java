package chat;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * 登陆线程
 */
public class LoginThread extends Thread implements Protocol {
    private JFrame loginf;

    private JTextField t;

    public void run() {
        /*
         * 设置登陆界面
         */
        loginf = new JFrame();
        loginf.setLocation(300, 200);
        loginf.setSize(400, 150);
        loginf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginf.setTitle(SOFTWARE + " - 登录");

        t = new JTextField("Version " + VERSION + "        By BlueSky");
        t.setHorizontalAlignment(JTextField.CENTER);
        t.setEditable(false);
        loginf.getContentPane().add(t, BorderLayout.SOUTH);

        JPanel loginp = new JPanel(new GridLayout(4, 2));
        loginf.getContentPane().add(loginp);

        JTextField t1 = new JTextField("登录名:");
        t1.setHorizontalAlignment(JTextField.CENTER);
        t1.setEditable(false);
        loginp.add(t1);

        final JTextField loginname = new JTextField("");
        loginname.setHorizontalAlignment(JTextField.CENTER);
        loginp.add(loginname);

        JTextField t2 = new JTextField("服务器IP:");
        t2.setHorizontalAlignment(JTextField.CENTER);
        t2.setEditable(false);
        loginp.add(t2);

        final JTextField loginIP = new JTextField(DEFAULT_IP);
        loginIP.setHorizontalAlignment(JTextField.CENTER);
        loginp.add(loginIP);

        JTextField t3 = new JTextField("端口号:");
        t3.setHorizontalAlignment(JTextField.CENTER);
        t3.setEditable(false);
        loginp.add(t3);

        final JTextField loginport = new JTextField(DEFAULT_PORT + "");
        loginport.setHorizontalAlignment(JTextField.CENTER);
        loginp.add(loginport);

        /*
         * 监听退出按钮(匿名内部类)
         */
        JButton b1 = new JButton("退  出");
        loginp.add(b1);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        final JButton b2 = new JButton("登  录");
        loginp.add(b2);

        loginf.setVisible(true);

        /**
         * 监听器,监听"登陆"Button的点击和TextField的回车
         */
        class ButtonListener implements ActionListener {
            private Socket s;

            public void actionPerformed(ActionEvent e) {
                if (checkName(loginname.getText())) {
                    try {
                        s = new Socket(loginIP.getText(), Integer
                                .parseInt(loginport.getText()));
                        /*
                         * 连接服务器
                         */
                        try {
                            InputStream is = s.getInputStream();
                            InputStreamReader ir = new InputStreamReader(is,
                                    "GBK");
                            // java.nio.charset.Charset.defaultCharset());
                            BufferedReader in = new BufferedReader(ir);
                            OutputStream os = s.getOutputStream();
                            OutputStreamWriter or = new OutputStreamWriter(os,
                                    "GBK");
                            PrintWriter out = new PrintWriter(or);
                            out.println(VERSION);
                            out.flush();
                            out.println(loginname.getText());
                            out.flush();
                            String ver;
                            if (!(ver = in.readLine()).equals(VERSION)) {
                                throw new Protocol.VersionException(ver);
                            }
                            if (in.readLine().equals(USER_EXIST)) {
                                throw new Protocol.ExistException();
                            }
                            /*
                             * 启动聊天线程
                             */
                            Thread chat = new ChatThread(loginname.getText(),
                                    s, in, out);
                            loginf.setVisible(false);
                            loginf.setEnabled(false);
                            chat.start();
                        } catch (IOException e1) {// 流操作异常
                            t.setText("通讯失败,请重试!");
                            try {
                                s.close();
                            } catch (IOException e2) {
                            }
                        } catch (Protocol.VersionException e3) {// 用户存在异常(接口中定义)
                            t.setText(e3.getMessage());
                            try {
                                s.close();
                            } catch (IOException e4) {
                            }
                        } catch (Protocol.ExistException e5) {// 用户存在异常(接口中定义)
                            t.setText(e5.getMessage());
                            try {
                                s.close();
                            } catch (IOException e6) {
                            }
                        }
                    } catch (IOException e7) {// Socket连接服务器异常
                        t.setText("连接服务器失败,请重试!");
                    }
                }
            }
        }
        ButtonListener bl = new ButtonListener();
        b2.addActionListener(bl);
        loginname.addActionListener(bl);
        loginIP.addActionListener(bl);
        loginport.addActionListener(bl);
    }

    /**
     * 判断登陆名是否有效
     */
    private boolean checkName(String name) {
        if (name.length() < NAME_MIN) {
            t.setText("错误:登陆名不能小于" + NAME_MIN + "字符");
            return false;
        }
        if (name.length() > NAME_MAX) {
            t.setText("错误:登陆名不能大于" + NAME_MAX + "字符");
            return false;
        }
        if (name.indexOf(" ") > -1) {
            t.setText("错误:登陆名不能包含空格");
            return false;
        }
        for (int i = 0; i < FORBID_WORDS.length; i++) {
            if (name.indexOf(FORBID_WORDS[i]) > -1) {
                t.setText("错误:登陆名不能包含敏感信息");
                return false;
            }
        }
        return true;
    }
}
