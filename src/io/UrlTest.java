package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlTest {
    // 生成main方法的快捷键是：输入main，然后按回车键即可
    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.baidu.com");
            // 根据openStream获取一个字节输入流
            InputStream in = url.openStream();
            // 将字节输入流转为字符输入流,且将编码改为utf-8
            InputStreamReader inr = new InputStreamReader(in, "utf-8");
            // 将字符输入流添加进缓冲
            BufferedReader br = new BufferedReader(inr);
            // 读取数据
            String data = br.readLine();
            while (data != null) {
                System.out.println(data);
                data = br.readLine();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
