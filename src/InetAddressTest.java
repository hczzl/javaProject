import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhongzhilong
 * @date 2021-04-17
 * @description 学校InetAddress的使用
 */
public class InetAddressTest {
    public static void main(String[] args) {
        try {
            // 获取本机的主机信息
            InetAddress inetAddress =InetAddress.getLocalHost();
            System.out.println("主机名："+inetAddress.getHostName());
            System.out.println("主机地址："+inetAddress.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
