package test0426;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhongzhilong
 * @date 2021/4/26
 * @description
 */
public class Test {
    public static void main(String[] args) {
        // 1、字符串转为List<String>
        List<String> resultList = Arrays.asList("1", "2", "3");
        System.out.println("方法一");
        resultList.forEach(e -> {
            System.out.print(e + " ");
        });
        System.out.println("");
        System.out.println("方法二");
        String msg = "4,5,6";
        // 2、字符串转为List<String>
        List<String> msgList = Arrays.asList(msg.split(","));
        msgList.forEach(e -> {
            System.out.print(e + " ");
        });
        System.out.println("");
        // 3、List<String>转String
        List<String> msgList2 =new ArrayList<>();
        msgList2.add("a");
        msgList2.add("b");
        msgList2.add("c");
        String msg2 =String.join(",",msgList2);
        System.out.println("msg2="+msg2);
    }
}
