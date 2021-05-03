package test0429;


import lombok.Data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhongzhilong
 * @date 2021/4/29
 * @description 服务端
 */
@Data
public class DelongServerSocket {

    private Integer port;
    private boolean started;
    private ServerSocket ss;
    /**
     * 存储当前已建立的socket
     */
    public static ConcurrentHashMap<String, ClientSocket> clientsMap = new ConcurrentHashMap<>();
    /**
     * 线程池
     */
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        new DelongServerSocket().start(10086);
    }

    public void start() {
        start(null);
    }

    public void start(Integer port) {
        try {
            ss = new ServerSocket(port == null ? this.port : port);
            started = true;
            System.out.println("端口已开启,占用10086端口号....");
        } catch (Exception e) {
            System.out.println("端口使用中....");
            System.out.println("请关掉相关程序并重新运行服务器！");
            e.printStackTrace();
            System.exit(0);
        }
        try {
            while (started) {
                Socket socket = ss.accept();
                socket.setKeepAlive(true);
                ClientSocket register = ClientSocket.register(socket);
                System.out.println("a client connected!");
                if (register != null) {
                    executorService.submit(register);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
