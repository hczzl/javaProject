package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author zhongzhilong
 * @date 2021/4/18
 * @decription 客户端
 */
public class Client {
    public static void main(String[] args) {
        try {
            // 127.0.0.1 和 localhost是一样的
            Socket socket =new Socket("192.168.0.3",9089);
            // 字节输出流
            OutputStream outputStream=socket.getOutputStream();
            // 字节输出流转打印流
            PrintWriter pr = new PrintWriter(outputStream);
            pr.write("你好，我是客户端！");
            // 刷新缓存
            pr.flush();
            socket.shutdownOutput();
            pr.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
