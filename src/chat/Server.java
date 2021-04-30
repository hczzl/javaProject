package chat;

/**
 * QQ聊天室服务器端
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Protocol {
    /*
     * 用户名存入key,线程存入对应value
     */
    private static Map users = new HashMap();

    /**
     * 用户线程类,每个用户一个线程
     */
    static class UserThread extends Thread {
        private Socket s;

        private String username = "";

        private PrintWriter out;

        private static int online;// 统计在线人数

        private static String lock = "";

        public UserThread(Socket s) {
            this.s = s;
        }

        public void run() {
            try {
                /*
                 * 创建流
                 */
                InputStream is = s.getInputStream();
                InputStreamReader ir = new InputStreamReader(is, "GBK");
                // java.nio.charset.Charset.defaultCharset());
                BufferedReader in = new BufferedReader(ir);
                OutputStream os = s.getOutputStream();
                OutputStreamWriter or = new OutputStreamWriter(os, "GBK");
                out = new PrintWriter(or);
                out.println(VERSION);
                out.flush();
                /*
                 * 判断版本是否过期
                 */
                if (!in.readLine().equals(VERSION)) {
                    throw new Exception("版本过期");
                }
                this.username = in.readLine();
                synchronized (lock) {
                    /*
                     * 读取用户名,并判断是否已经存在
                     */
                    if (isExist(this.username)) {
                        throw new ExistException();
                    }
                    /*
                     * 登陆成功
                     */
                    out.println(SYSTEM_MSG);
                    out.flush();
                    /*
                     * 通知所有人有新用户登陆
                     */
                    sendAll(SYSTEM_MSG + ADD_USER + this.username);
                    /*
                     * 刷新在线人数
                     */
                    System.out.print("\rOnline:" + ++online);
                    /*
                     * 给本线程用户发送在线用户列表
                     */
                    listAll();
                    /*
                     * 将本用户加入集合
                     */
                    users.put(this.username, this);
                }
                String msg = "";
                String touser = "All";
                while (!s.isClosed()) {
                    while (!s.isClosed() && (msg = in.readLine()) != null
                            && msg.length() > 0) {
                        /*
                         * 收到用户退出的系统信息,删除集合中对应项,通知所有用户
                         */
                        if (msg.startsWith(SYSTEM_MSG + USER_LOGOUT)) {
                            synchronized (lock) {
                                users.remove(this.username);
                            }
                            sendAll(SYSTEM_MSG + DELETE_USER + this.username);
                            s.close();
                            System.out.print("\rOnline:" + --online + " ");
                        }
                        /*
                         * 收到聊天信息,解析出发送对象和信息内容,并发送
                         */
                        else {
                            touser = msg.substring(0, msg.indexOf(NAME_END));
                            msg = msg.replaceFirst(touser + NAME_END, "");
                            System.out.println("消息是：" + msg);
                            send(msg, touser);
                        }
                    }
                }
            }
            /*
             * 登陆时出现用户名已存在情况,通知用户
             */ catch (ExistException e) {
                out.println(SYSTEM_MSG + USER_EXIST);
                out.flush();
            } catch (Exception e) {
            } finally {
                try {
                    s.close();
                } catch (Exception e) {
                }
            }
        }

        /**
         * 发送信息给所有用户
         */
        private void sendAll(String msg) {
            Set s = users.keySet();
            Iterator it = s.iterator();
            while (it.hasNext()) {
                UserThread t = (UserThread) users.get(it.next());
                if (t != this) {
                    t.sendUser(msg);
                }
            }
        }

        /**
         * 给本线程发送在线用户列表
         */
        private void listAll() {
            Set s = users.keySet();
            Iterator it = s.iterator();
            while (it.hasNext()) {
                this.sendUser(SYSTEM_MSG + EXIST_USERS + it.next());
            }
        }

        /**
         * 判断用户名是否已经有人使用
         */
        private boolean isExist(String name) {
            Set s = users.keySet();
            Iterator it = s.iterator();
            while (it.hasNext()) {
                if (name.equals((String) it.next())) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 给本线程对应的用户发信息
         */
        private void sendUser(String msg) {
            out.println(msg);
            out.flush();
            // System.out.println("to " + this.username + ": " + msg);// 调试用代码
        }

        /**
         * 给指定对象发送信息
         */
        private void send(String msg, String touser) {
            /*
             * 调用相应函数,给所有人发信息时
             */
            if (touser.equals("All")) {
                sendAll(this.username + NAME_END + msg);
                return;
            }
            /*
             * 根据发送目标的名字获得相应线程,调用目标线程的函数给目标发送信息
             */
            if (users.containsKey(touser))// 加判断,防止用户已经离线
            {
                ((UserThread) users.get(touser)).sendUser(MSG_FROM
                        + this.username + NAME_END + msg);
            }
        }
    }

    /**
     * 主方法:启动服务器
     */
    public static void main(String[] args) {
        /*
         * 根据参数的情况,获得端口号,无效时使用默认值,并返回相应信息
         */
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            int newport;
            try {
                newport = Integer.parseInt(args[0]);
                /*
                 * 无效端口
                 */
                if (newport > 65535 || newport < 0) {
                    System.out.println("The port " + newport + " is invalid.");
                }
                /*
                 * 操作系统预留端口
                 */
                else if (newport <= 1024) {
                    System.out.println("The port 0~1024 is not allowed.");
                } else {
                    port = newport;
                }
            }
            /*
             * 不能转换成整数的参数
             */ catch (NumberFormatException e) {
                System.out.println("Invalid port number!");
            }
        }
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.print("Server is running.\nPort:" + port + "\nOnline:0");
            while (true) {
                Socket s = ss.accept();
                Thread t = new UserThread(s);
                t.start();
            }
        }
        /*
         * 端口绑定失败
         */ catch (IOException e) {
            System.out.println("Failed to bind " + port + "port.");
        }
    }
}
