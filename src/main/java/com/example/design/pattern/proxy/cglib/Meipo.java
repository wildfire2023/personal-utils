package com.example.design.pattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 通过cglib继承的方式来执行动态代理
 *
 * @author Monster
 * @date 2018/12/12 11:06
 * @since 1.8
 */
public class Meipo implements MethodInterceptor {

    /**
     * 通过子类继承父类，调用父类方法来实现
     * @param object
     * @return
     */
    public Object newInstance(Object object) {
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 调用方法前
        Object res = methodProxy.invokeSuper(o, objects);
        // 调用方法后
        return res;
    }
}
