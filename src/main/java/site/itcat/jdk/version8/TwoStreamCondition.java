package site.itcat.jdk.version8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class TwoStreamCondition {

    public static void main(String[] args) {
        List<Integer> all = Arrays.asList(3, 9);
        IntStream.range(1, 10).filter(i -> !all.contains(i)).forEach(System.out::println);
    }
}
