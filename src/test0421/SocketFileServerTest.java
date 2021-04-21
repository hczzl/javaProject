package test0421;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhongzhilong
 * @date 2021/4/21
 * @description socket传输文件-服务端接收来自客户端传输的文件
 * https://www.imooc.com/article/11793-参考
 */
public class SocketFileServerTest {
    public static void main(String[] args) {
        try {
            System.out.println("我是服务端，正在等待客户端的请求------");
            ServerSocket serverSocket = new ServerSocket(9300);
            Socket socket =serverSocket.accept();
            // socket获取输入流
            InputStream in = socket.getInputStream();
            // ObjectInputStream序列化输入流
            ObjectInputStream ois = new ObjectInputStream(in);
            String filePath = "F:/socket/copy.txt";
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            // 文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            // 输出流添加进缓冲
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            // 接收传递过来的文件
            byte[] buf = new byte[1024];
            int len;
            while ((len = ois.read(buf)) != -1) {
                bos.write(buf, 0, len);
                bos.flush();
            }
            socket.shutdownInput();
            bos.close();
            fos.close();
            ois.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
