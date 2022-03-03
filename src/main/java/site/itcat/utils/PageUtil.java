package site.itcat.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageUtil {


    /**
     * 大列表拆解方法
     * @param origin
     * @param size
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> divide(List<T> origin, int size) {
        if (origin == null || origin.size() == 0) {
            return Collections.emptyList();
        }

        int block = (origin.size() + size - 1) / size;
        return IntStream.range(0, block)
                .boxed().map(i -> {
                    int start = i * size;
                    int end = Math.min(origin.size(), start + size);
                    return origin.subList(start, end);
                }).collect(Collectors.toList());
    }
}
