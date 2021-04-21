package test0421;

import java.io.*;
import java.net.Socket;

/**
 * @author zhongzhilong
 * @date 2021/4/21
 * @description socket传输文件-客户端传输文件至服务端
 */
public class SocketFileClientTest {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 9300);
            OutputStream os =socket.getOutputStream();
            ObjectOutputStream oos =new ObjectOutputStream(os);
            String filePath = "F:/socket/test.txt";
            File file =new File(filePath);
            FileInputStream fis =new FileInputStream(file);
            BufferedInputStream bufferedInputStream =new BufferedInputStream(fis);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
