package com.example.design.pattern.proxy.dynamicproxy;

public class Test {

    public static void main(String[] args) throws Exception {
        Person person = (Person) new Woman().newInstance(new Laowang());
        person.doSomething();
    }
}
