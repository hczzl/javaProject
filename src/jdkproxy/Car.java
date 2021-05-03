package jdkproxy;

import java.util.Random;

/**
 * @author zhongzhilong
 * @date 2021/5/3
 * @description
 */
public class Car implements Moveable {
    @Override
    public void move() {
        // 实现开车
        try {
            Thread.sleep(new Random().nextInt(1000));
            System.out.println("汽车行驶中....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
