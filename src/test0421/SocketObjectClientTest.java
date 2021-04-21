package test0421;

import java.io.*;
import java.net.Socket;

/**
 * @author zhongzhilong
 * @date 2021/4/21
 * @description socket通信传输对象-客户端
 */
public class SocketObjectClientTest {
    public static void main(String[] args) {
        try {
            System.out.println("--------客户端开始连接服务端------");
            Socket socket = new Socket("localhost", 9200);
            OutputStream os = socket.getOutputStream();
            // 使用ObjectOutputStream对象序列化输出流，传递对象
            ObjectOutputStream oos = new ObjectOutputStream(os);
            // 封装对象
            Student stu = new Student(1, "小明");
            // 序列化
            oos.writeObject(stu);
            socket.shutdownOutput();

            // 输入字节流
            InputStream in = socket.getInputStream();
            // 字节流转字符流
            InputStreamReader reader = new InputStreamReader(in);
            // 字符流添加进缓冲
            BufferedReader br = new BufferedReader(reader);
            String msg = br.readLine();
            while (msg != null) {
                System.out.println("我是客户端，服务器说：" + msg);
                msg = br.readLine();
            }
            br.close();
            reader.close();
            in.close();
            oos.close();
            os.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Student implements Serializable {
        private int id;
        private String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
