package site.itcat.concurrent.print;

import org.apache.commons.lang3.concurrent.ConcurrentException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author : xuebengang
 * @date : 2022/3/4
 * @description : 10个线程，第一个从1加到10，第二个从11加到20...
 */
public class TenThreadSum {


    public static class Sum implements Callable<Integer> {
        private int slot;
        private int sum = 0;

        public Sum(int slot) {
            this.slot = slot;
        }

        @Override
        public Integer call() throws Exception {

            for (int i = 1; i <= 10; i++) {
                sum += i + slot * 10;
            }
            return sum;
        }
    }


    public static void main(String[] args) {
        List<Future<Integer>> results = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            Future<Integer> submit = executorService.submit(new Sum(i));
            results.add(submit);
        }
        int result = results.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return 0;
        }).reduce(0, Integer::sum);
        System.out.println(result);
    }
}
