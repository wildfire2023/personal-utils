package site.itcat.design.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    public Object newInstance(Object obj) {
        return Enhancer.create(obj.getClass(), this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("被cglib代理了");
        return methodProxy.invokeSuper(o, objects);
    }
}
