package test0429;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

/**
 * @author zhongzhilong
 * @date 2021/4/29
 * @description
 */
public class ChatClient {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 10086;
        // 与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);
        // 建立连接后获得输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        outputStream.write(uuid.getBytes());
        //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据

        //InputStream inputStreams = new FileInputStream("F:/socket/test.txt");
        //byte[] bytes = new byte[inputStreams.available()];
        //inputStreams.read(bytes);
        DataInputStream inputStream2 = new DataInputStream(socket.getInputStream());
        while (true) {
            byte[] bytess = new byte[1024];
            inputStream2.read(bytess);
            String info = new String(bytess, "utf-8");
            System.out.println("我是客户端，服务器说："+ info);
        }
    }
}
