package test0430;

/**
 * @author zhongzhilong
 * @date 2021/4/30
 * @description
 */
public class ThreadTest {
    private static int key = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread thread= new Thread(new MyThread(i));
            thread.start();

        }
    }

    static class MyThread implements Runnable {
        private int temp;

        public MyThread(int temp) {
            this.temp = temp;
        }

        @Override
        public void run() {
            key = temp;
            System.out.println("key的值为：" + key);
        }
    }
}
