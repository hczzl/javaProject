package jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhongzhilong
 * @date 2021/5/3
 * @description
 */
public class TimeHandler implements InvocationHandler {
    private Object target;
    public TimeHandler(Object target){
        super();
        this.target = target;
    }

    /**
     * 参数
     * @param proxy  被代理对象
     * @param method 被代理对象的方法
     * @param args   方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long starttime = System.currentTimeMillis();
        System.out.println("汽车开始行驶....");
        method.invoke(target);
        long endtime = System.currentTimeMillis();
        System.out.println("汽车结束行驶....  汽车行驶时间："
                + (endtime - starttime) + "毫秒！");
        return null;
    }
}
