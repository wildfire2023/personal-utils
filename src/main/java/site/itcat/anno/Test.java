package site.itcat.anno;

import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) {
        Class<Child> clazz = Child.class;
        // 只继承父类注解
        if(clazz.isAnnotationPresent(InheritedTest.class)){
            System.out.println(clazz.getAnnotation(InheritedTest.class).value());
        }


        // fang fa ceshi
        // 不继承父类方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(InheritedTest.class)) {

                System.out.println(clazz.getAnnotation(InheritedTest.class).value());
            }
        }
    }
}
