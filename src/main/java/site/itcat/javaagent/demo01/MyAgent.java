package site.itcat.javaagent.demo01;

import java.lang.instrument.Instrumentation;

public class MyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Hi! JavaAgent " + agentArgs);
    }

    public static void premain(String agentArgs) {

    }

}
