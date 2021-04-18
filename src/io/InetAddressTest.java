package io;

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
            // 1、根据getLocalhost()方法获取InetAddress对象
            InetAddress inetAddress =InetAddress.getLocalHost();
            System.out.println("主机名："+inetAddress.getHostName());
            System.out.println("主机地址："+inetAddress.getHostAddress());
            // 2、根据计算机名获取InetAddress对象
            InetAddress address = InetAddress.getByName("MS-QENFPQRDNSFR");
            System.out.println("计算机名—"+address.getHostName()+",计算机地址-"+address.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
