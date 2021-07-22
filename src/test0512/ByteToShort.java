package test0512;

/**
 * @author zhongzhilong
 * @date 2021/5/12
 * @description byte和short之间的转换
 */
public class ByteToShort {
    public static void main(String[] args) {
        byte[] bytes = new byte[2];
        int s = 25;
        bytes[1] = (byte) (s >> 8);
        bytes[0] = (byte) (s >> 0);
        System.out.println(bytes);
    }
}
