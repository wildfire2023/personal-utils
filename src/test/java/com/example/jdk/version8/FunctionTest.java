package com.example.jdk.version8;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionTest {

    @FunctionalInterface
    public interface Adder {
        int add(int a, int b);
    }

    public interface SmartAdder extends Adder {
        int add(long a, long b);
    }

    @FunctionalInterface
    public interface Nothing extends Adder {

    }

    @Test
    public void flatMapTest() {
        List<Integer> collect = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4), collect);
    }

    @Test
    public void threadTest() {
        Thread thread = new Thread(() -> System.out.println(1));
        thread.start();
    }

    @Test
    public void lambdaUsage() {
        // lambda表达式在哪用?
        Runnable r = () -> System.out.println("Hello");

    }

}
