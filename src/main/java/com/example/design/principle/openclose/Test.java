package com.example.design.principle.openclose;

public class Test {

    public static void main(String[] args) {
        ICourse javaCourse = new JavaCourse(96, "Java从零到企业级电商开发", 338d);
        System.out.println("课程id：" + javaCourse.getId());
        System.out.println("课程名称：" + javaCourse.getName());
        System.out.println("课程价格：" + javaCourse.getPrice());
    }
}
