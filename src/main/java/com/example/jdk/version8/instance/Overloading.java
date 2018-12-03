package com.example.jdk.version8.instance;

import java.util.function.Predicate;

public interface Overloading {
    boolean check(Predicate<Integer> predicate) ;

    boolean check(IntPred predicate);
}
