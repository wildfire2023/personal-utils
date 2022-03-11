package site.itcat.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : xuebengang
 * @date : 2022/3/11
 * @description : 代理对象
 */
public class JDKInvocationHandler implements InvocationHandler {

    private IUserService userService = new UserService();

    public static <T> T getProxy(Class<T> interfaceClass) {
        InvocationHandler handler = new JDKInvocationHandler();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return (T) Proxy.newProxyInstance(classLoader,new Class[]{interfaceClass}, handler);
    }

    /**
     * 代理对象的增强
     * aop：切面、连接点（可能存在切点）、增强（前置、后置、环绕）；日志、事务
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前置处理");
        Object result = method.invoke(userService, args);
        System.out.println("后置处理");
        return result;
    }
}
