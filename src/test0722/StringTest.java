package test0722;


public class StringTest {
    public static void main(String[]args){
        String name = "123";
        String msg = "123";
        // 判断msg子字符串在name字符串中第一次出现的序号，
        // 等于-1表示msg子字符串在name字符串中不存在;大于-1，表示包含关系
        System.out.println(name.indexOf(msg));
    }
}
