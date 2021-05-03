package test0426;

/**
 * @author zhongzhilong
 * @date 2021/4/26
 * @description 二维数组大小的测试
 */
public class ArrTest {
    public static void main(String[] args) {
        int[][] arr = new int[3][4];
        int flag = 0;
        System.out.println("size：" + arr.length);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                arr[i][j] = flag;
                flag++;
            }
        }
        System.out.println("二维数组内容如下：");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
               int temp = arr[i][j];
                System.out.print(temp+" ");
            }
            System.out.println("\n");
        }
    }
}
