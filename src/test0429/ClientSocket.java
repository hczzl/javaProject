package test0429;

import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author zhongzhilong
 * @date 2021/4/29
 * @description
 */
@Data
public class ClientSocket implements Runnable {
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String key;
    private String message;


    /**
     * 注册socket到map里
     *
     * @param socket
     * @return
     */
    public static ClientSocket register(Socket socket) {
        ClientSocket client = new ClientSocket();
        try {
            client.setSocket(socket);
            client.setInputStream(new DataInputStream(socket.getInputStream()));
            client.setOutputStream(new DataOutputStream(socket.getOutputStream()));
            byte[] bytes = new byte[1024];
            System.out.println("开始阻塞");
            client.getInputStream().read(bytes);
            client.setKey(new String(bytes, "utf-8"));
            System.out.println("我是服务端，客户端说："+new String(bytes,"UTF-8"));
            System.out.println("结束阻塞");
            DelongServerSocket.clientsMap.put(client.getKey(), client);
            System.out.println(DelongServerSocket.clientsMap);
            OutputStream os =socket.getOutputStream();
            PrintWriter pw =new PrintWriter(os);
            pw.write("已收到消息");
            pw.flush();
            // os.flush();
            return client;
        } catch (IOException e) {
            client.logout();
        }
        return null;
    }

    /**
     * 发送数据
     *
     * @param str
     */
    public void send(String str) {
        try {
            outputStream.write(str.getBytes());
        } catch (IOException e) {
            logout();
        }
    }

    /**
     * 接收数据
     *
     * @return
     * @throws IOException
     */
    public String receive() {
        try {
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            String info = new String(bytes, "utf-8");
            System.out.println(info);
            return info;
        } catch (IOException e) {
            logout();
        }
        return null;
    }

    /**
     * 登出操作, 关闭各种流
     */
    public void logout() {
        if (DelongServerSocket.clientsMap.containsKey(key)) {
            DelongServerSocket.clientsMap.remove(key);
        }

        System.out.println(DelongServerSocket.clientsMap);
        try {
            socket.shutdownOutput();
            socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送数据包, 判断数据连接状态
     *
     * @return
     */
    public boolean isSocketClosed() {
        try {
            socket.sendUrgentData(1);
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    @Override
    public void run() {
        // 每过5秒连接一次客户端
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("心跳");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isSocketClosed()) {
                System.out.println("关闭");
                logout();
                break;
            }
        }

    }

    @Override
    public String toString() {
        return "Client{" +
                "socket=" + socket +
                ", inputStream=" + inputStream +
                ", outputStream=" + outputStream +
                ", key='" + key + '\'' +
                '}';
    }
}
