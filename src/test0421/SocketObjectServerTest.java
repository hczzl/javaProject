package test0421;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhongzhilong
 * @date 2021/4/21
 * @description socket传输对象-服务端
 */
public class SocketObjectServerTest {
    public static void main(String[] args) {
        try {
            System.out.println("----服务端已连接-----");
            ServerSocket serverSocket = new ServerSocket(9200);
            Socket socket = serverSocket.accept();
            // socket获取输入流
            InputStream in = socket.getInputStream();
            // ObjectInputStream序列化输入流
            ObjectInputStream ois = new ObjectInputStream(in);
            // Student为传递对象
            SocketObjectClientTest.Student student = (SocketObjectClientTest.Student) ois.readObject();
            String msg = "id: " + student.getId() + ",name: " + student.getName();
            System.out.println("我是服务端，客户端说：" + msg);
            socket.shutdownInput();
            // socket获取输出流
            OutputStream os = socket.getOutputStream();
            // 输出流转打印流
            PrintWriter pw = new PrintWriter(os);
            pw.write("已收到消息");
            // 刷新缓存
            pw.flush();
            socket.shutdownOutput();
            pw.close();
            os.close();
            ois.close();
            in.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
