package chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * 聊天线程
 */
class ChatThread extends Thread implements Protocol {
    private Map users = new HashMap();

    private String name;

    private Socket s;

    private BufferedReader in;

    private PrintWriter out;

    private JComboBox cb;

    private JFrame f;

    private JTextArea ta;

    private JTextField tf;

    private static long time;// 上一条信息的发出时间

    private static int total;// 在线人数统计

    public ChatThread(String name, Socket s, BufferedReader in, PrintWriter out) {
        this.name = name;
        this.s = s;
        this.in = in;
        this.out = out;
    }

    public void run() {
        /*
         * 设置聊天室窗口界面
         */
        f = new JFrame();
        f.setSize(600, 400);
        f.setTitle(SOFTWARE + " - " + name + "     当前在线人数:" + ++total);
        f.setLocation(300, 200);
        ta = new JTextArea();
        JScrollPane sp = new JScrollPane(ta);
        ta.setEditable(false);
        tf = new JTextField();
        cb = new JComboBox();
        cb.addItem("All");
        JButton jb = new JButton("私聊窗口");
        JPanel pl = new JPanel(new BorderLayout());
        pl.add(cb);
        pl.add(jb, BorderLayout.WEST);
        JPanel p = new JPanel(new BorderLayout());
        p.add(pl, BorderLayout.WEST);
        p.add(tf);
        f.getContentPane().add(p, BorderLayout.SOUTH);
        f.getContentPane().add(sp);

        /*
         * 监听私聊窗口按钮(匿名内部类)
         */
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = (String) cb.getSelectedItem();
                if (!name.equals("All")) {
                    ChatWindow cw = (ChatWindow) users.get(name);
                    if (cw.fs.isVisible())
                        cw.fs.setVisible(false);
                    else
                        cw.fs.setVisible(true);
                }
            }
        });
        /**
         * 监听关闭按钮
         */
        class MyWindowListener implements WindowListener {
            public void windowActivated(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                /*
                 * 向服务器发送退出信息
                 */
                out.println(SYSTEM_MSG + USER_LOGOUT);
                out.flush();
                try {
                    s.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }

            public void windowDeactivated(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowOpened(WindowEvent e) {
            }
        }
        MyWindowListener wl = new MyWindowListener();
        f.addWindowListener(wl);

        /**
         * 接收服务器发送的信息
         */
        class GetMsgThread extends Thread {
            public void run() {
                try {
                    String msg, name;
                    while (!s.isClosed()) {
                        /*
                         * 不断接受服务器信息,外层循环防止读到null跳出循环
                         */
                        while (!s.isClosed() && (msg = in.readLine()) != null) {
                            msg = specialMsg(msg);// 系统信息处理
                            if (msg.startsWith(MSG_FROM)) {
                                msg = msg.replaceFirst(MSG_FROM, "");
                                name = msg.substring(0, msg.indexOf(NAME_END));
                                msg = msg.replaceFirst(name + NAME_END, "");
                                JTextArea tas = ((ChatWindow) users.get(name)).tas;
                                tas.append(name + " 说: " + msg + "\n");
                                tas.setCaretPosition(tas.getText().length());
                                ta.append(name + " 悄悄地对你说: " + msg + "\n");
                            } else if (msg.contains(NAME_END)) {
                                name = msg.substring(0, msg.indexOf(NAME_END));
                                msg = msg.replaceFirst(name + NAME_END, "");
                                ta.append(name + " 说: " + msg + "\n");// 在窗口显示信息
                            } else {
                                ta.append(msg + "\n");
                            }
                            ta.setCaretPosition(ta.getText().length());// 自动滚动到底部
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println(SYSTEM_MSG + USER_LOGOUT);// 当异常产生时向系统发出退出信息
                } finally {
                    try {
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        GetMsgThread gt = new GetMsgThread();
        gt.start();

        /*
         * 监听用户在公聊窗口输入的信息(匿名内部类)
         */
        tf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String msg = e.getActionCommand();
                if (isAllowed(msg, null)) {
                    sendMsg((String) cb.getSelectedItem(), msg, true);
                    tf.setText(null);
                }
            }
        });

        f.setVisible(true);
    }

    /**
     * 处理系统信息
     */
    private String specialMsg(String msg) {
        if (msg.startsWith(SYSTEM_MSG)) {
            msg = msg.replaceFirst(SYSTEM_MSG, "");
            /*
             * 当有人进入聊天室
             */
            if (msg.startsWith(ADD_USER)) {
                msg = msg.replaceFirst(ADD_USER, "");
                cb.addItem(msg);
                users.put(msg, new ChatWindow(msg));
                total++;
                msg += " 进入聊天室";
            }
            /*
             * 当有人离开聊天室
             */
            else if (msg.startsWith(DELETE_USER)) {
                msg = msg.replaceFirst(DELETE_USER, "");
                cb.removeItem(msg);
                ((ChatWindow) users.get(msg)).tas.append(msg + " 退出聊天室\n");
                users.remove(msg);
                total--;
                msg += " 退出聊天室";
            }
            /*
             * 登陆时获得的在线用户列表信息
             */
            else if (msg.startsWith(EXIST_USERS)) {
                msg = msg.replaceFirst(EXIST_USERS, "");
                cb.addItem(msg);
                users.put(msg, new ChatWindow(msg));
                total++;
                msg += " 正在聊天室";
            }
            /*
             * 即时显示在线人数
             */
            f.setTitle(SOFTWARE + " - " + name + "     当前在线人数:" + total);
            return msg;
        }
        return msg;
    }

    /**
     * 检查信息是否允许发送,包括检查敏感词汇/空信息/刷屏
     */
    private boolean isAllowed(String msg, String msgto) {
        /*
         * 过滤空信息
         */
        if (msg.length() == 0)
            return false;
        String errmsg = null;
        /*
         * 过滤敏感词汇
         */
        for (int i = 0; i < FORBID_WORDS.length; i++) {
            if (msg.indexOf(FORBID_WORDS[i]) > -1) {
                errmsg = "包含敏感信息,信息发送失败!\n";
                break;
            }
        }
        long timenow = (new Date()).getTime();// 获得当前时间信息
        /*
         * 防刷屏
         */
        if (timenow - time < TIME_BETWEEN_MSG * 1000) {
            errmsg = "发送信息的最短间隔为" + TIME_BETWEEN_MSG + "秒,请勿刷屏!\n";
        }
        if (errmsg == null) {
            time = timenow;// 记录发送信息时间
            return true;
        } else if (msgto == null)
            ta.append(errmsg);
        else
            ((ChatWindow) users.get(msgto)).tas.append(errmsg);
        return false;
    }

    /*
     * 私聊窗口
     */
    private class ChatWindow {
        JFrame fs;

        JTextArea tas;

        String name;

        public ChatWindow(String username) {
            this.name = username;
            fs = new JFrame();
            fs.setSize(400, 200);
            fs.setTitle(SOFTWARE + " - " + "与" + name + "私聊");
            fs.setLocation(300, 200);
            tas = new JTextArea();
            JScrollPane sps = new JScrollPane(tas);
            tas.setEditable(false);
            final JTextField tfs = new JTextField();
            JPanel ps = new JPanel(new BorderLayout());
            // JComboBox cbs = new JComboBox();
            // cbs.addItem(name);
            // ps.add(cbs, BorderLayout.WEST);
            ps.add(tfs);
            fs.getContentPane().add(ps, BorderLayout.SOUTH);
            fs.getContentPane().add(sps);
            /*
             * 监听用户在私聊窗口输入的信息(匿名内部类)
             */
            tfs.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (users.containsKey(name)) {
                        if (isAllowed(e.getActionCommand(), name)) {
                            sendMsg(name, e.getActionCommand(), true);
                            tfs.setText(null);
                        }
                    } else {
                        tas.append("信息发送失败,用户已经离开聊天室.\n");
                    }
                }
            });
        }
    }

    private void sendMsg(String name1, String msg, boolean isRoomShow) {
        out.println(name1 + NAME_END + msg);
        out.flush();
        System.out.println("名字是：" + name1);
        if (name1.equals("All"))
            ta.append("我 说: " + msg + "\n");
        else {
            ((ChatWindow) users.get(name1)).tas.append("我 说: " + msg + "\n");
            if (isRoomShow)
                ta.append("我悄悄地对 " + name1 + " 说: " + msg + "\n");
        }
    }
}
