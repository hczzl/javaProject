package io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhongzhilong
 * @date 2021/4/18
 * @decription 服务端
 */
public class Server {
    public static void main(String[] args) {
        try {
            // 根据端口号new 一个ServerSocket对象
            ServerSocket serverSocket = new ServerSocket(9088);
            System.out.println("服务端已启动，等待客户端连接");
            // 监听客户端的请求
            Socket socket = serverSocket.accept();
            // 字节输入流
            InputStream inputStream = socket.getInputStream();
            // 字节输入流转字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            // 将字符输入流添加进缓冲
            BufferedReader br = new BufferedReader(inputStreamReader);
            // 输出信息
            String msg = "";
            while ((msg = br.readLine()) != null) {
                System.out.println("客户端说：" + msg);
            }
            // 关闭字节输入流
            socket.shutdownInput();
            br.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
