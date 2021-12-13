package com.servyou.test.demo.test;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public class SteamTest {

    public static void main(String[] args) {
          Instant start = Instant.now();
          //过滤
//        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
//        List<String> collect = strings.parallelStream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
//        System.out.println(strings);
//        System.out.println(collect);

        //
//        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
//        // 获取对应的平方数
//        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
//        System.out.println(squaresList);
//        System.out.println(numbers);

//        Random random = new Random();
//        random.ints().limit(10).sorted().forEach(System.out::println);
//
//
//        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
//
//        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
//
//        System.out.println("yuan : " + stats);
//        System.out.println("列表中最大的数 : " + stats.getMax());
//        System.out.println("列表中最小的数 : " + stats.getMin());
//        System.out.println("所有数之和 : " + stats.getSum());
//        System.out.println("平均数 : " + stats.getAverage());
//
//        Instant finish = Instant.now();
//        log.info("完成{}，耗时{}ms", finish, Duration.between(start, finish).toMillis() );

        log.info("CPU核数{}",Runtime.getRuntime().availableProcessors());
    }
}
