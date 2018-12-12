package com.example.design.pattern.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理对象
 *
 * @author Monster
 * @date 2018/12/11 12:17
 * @since
 */
public class Woman implements InvocationHandler {
    private Person person;

    /**
     * 返回的生成的代理类实例
     *
     * @param person
     * @return
     */
    public Object newInstance(Person person) throws Exception {
        this.person = person;
        Class clazz = person.getClass();
        // classLoader：被代理人的类加载器
        // 被代理人实现的接口
        // invocationHandler：执行者（代理人）
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
//        byte[] classByte = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{obj.getClass()});
//        FileOutputStream fos = new FileOutputStream("$Proxy0.class");
//        fos.write(classByte);
//        fos.close();

        return obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before proxy");
        // 执行person的方法
        Object res = method.invoke(person, args);
        System.out.println("after proxy");
        return res;
    }
}
