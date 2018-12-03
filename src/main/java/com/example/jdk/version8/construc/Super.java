package com.example.jdk.version8.construc;

public class Super {
    {
        System.out.println("super non static");
    }

    public Super(){
        System.out.println("super construct");
    }

    static {
        System.out.println("super static");
    }


}
