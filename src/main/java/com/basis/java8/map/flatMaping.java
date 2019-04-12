package com.basis.java8.map;

import com.basis.java8.stream.Dish;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class flatMaping {
    static String[] words = {"Hello", "Hello", "World", "java", "action"};
    static List<String> list = Arrays.asList("Hello", "Hello", "World", "java", "action");

    @Test
    public void f1() {
        List<String[]> collect = list.stream().map(o -> o.split("")).distinct().collect(toList());
        collect.forEach(System.out::println);
    }

    @Test
    public void f2() {
        List<String[]> collect = Dish.menu.stream()
                .map(Dish::getName)
                .map(word -> word.split(""))
                .distinct()
                .collect(toList());
        //收集了9个String数组
        System.out.println(collect);

        Stream<String[]> stream = Dish.menu.stream()
                .map(Dish::getName)
                .map(word -> word.split(""));
        List<Stream<String>> collect1 = Dish.menu.stream()
                .map(Dish::getName)
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(toList());
//        收集了9个流
        System.out.println(collect1);

        List<String> collect2 = Dish.menu.stream()
                .map(Dish::getName)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());
        System.out.println(Arrays.toString(collect2.toArray()));

    }

    @Test
    public void f3() {
        // flatMap
        List<Integer> numbers1 = Arrays.asList(1, 2);
        List<Integer> numbers2 = Arrays.asList(3);
        List<Integer> numbers3 = Arrays.asList(5, 6);
        List<int[]> pairs =
                numbers1.stream()
                        .flatMap(i -> numbers2.stream()
                                .flatMap(j -> numbers3.stream()
                                        .map(k -> new int[]{i, j, k}))
                        )
//                        .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                        .collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", "+ pair[1] + ", " + pair[2] + ")"));
    }
}
