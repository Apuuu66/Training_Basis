package com.basis.java8.exercise;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Practice {
    public static List<Transaction> transactions;

    @Before
    public void before() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        Trader bob = new Trader("Bob", "San Francisco");
        Trader helen = new Trader("Helen", "Finchley");
        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950),
                new Transaction(bob, 2009, 100),
                new Transaction(helen, 2018, 5000));
    }

    @Test
    public void f1() {
        //找出2011年发生的所有交易，并按交易额排序（高到低）
        List<Transaction> list = transactions.stream()
                .filter(o -> o.getYear() == 2011)
                .sorted(comparing(Transaction::getValue).reversed())
                .collect(toList());
        System.out.println(list);
    }

    @Test
    public void f2() {
        //交易员都在那些不同的城市工作过？
        List<String> list = transactions.stream()
                .map(o -> o.getTrader().getCity())
                .distinct()
                .collect(toList());
        System.out.println(list);
    }

    @Test
    public void f3() {
        //查找所有来自剑桥的交易员，并按姓名排序
        List<Trader> list = transactions.stream()
                .map(Transaction::getTrader)
                .filter(o -> o.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
        System.out.println(list);
    }

    @Test
    public void f4() {
        //返回所有交易员的姓名字符串，按字母顺序排序
        List<String> list = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted(String::compareTo)
                .collect(toList());
        System.out.println(list);

        Optional<String> reduce = transactions.stream()
                .map(o -> o.getTrader().getName())
                .distinct()
                .sorted()
                .reduce(String::concat);
        System.out.println(reduce);
        String s = transactions.stream()
                .map(o -> o.getTrader().getName())
                .distinct()
                .sorted()
                .collect(joining(","));
        System.out.println(s);
    }

    @Test
    public void f5() {
        //有没有交易员是在米兰工作的
        Optional<Trader> trader = transactions.stream()
                .map(Transaction::getTrader)
                .filter(o -> o.getCity().equalsIgnoreCase("Milan"))
                .distinct()
                .findAny();
        System.out.println(trader);
        boolean milan = transactions.stream()
                .anyMatch(o -> o.getTrader().getCity().equalsIgnoreCase("Milan"));
        System.out.println(milan);
    }

    @Test
    public void f6() {
        //打印生活在剑桥的交易员的所有交易额
        transactions.stream()
                .filter(o -> o.getTrader().getCity().equalsIgnoreCase("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    @Test
    public void f7() {
        //所有交易中，最高的交易额是多少？
        Optional<Integer> reduce = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println(reduce);
    }

    @Test
    public void f8() {
        //找到交易额最小的交易
        Optional<Integer> reduce = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);
        System.out.println(reduce);
    }

    @Test
    public void f9() {
        int sum = transactions.stream()
                .mapToInt(Transaction::getValue).sum();
        System.out.println(sum);
        IntStream intStream = transactions.stream().mapToInt(Transaction::getValue);
        Stream<Integer> boxed = intStream.boxed();
        OptionalInt max = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max();
        max.orElse(100);
    }

    @Test
    public void f10() {
        String s1 = IntStream.range(1, 5).boxed().map(String::valueOf).collect(joining(","));

        String s2 = IntStream.rangeClosed(1, 5).boxed().map(String::valueOf).collect(joining(","));
        System.out.println(s1);
        System.out.println(s2);
    }

    @Test
    public void f11() {
        int a = 3;
        IntStream.rangeClosed(1, 10)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .mapToObj(b -> new int[]{a, b, (int) (Math.sqrt(a * a + b * b))})
                .forEach(o -> {
                    System.out.println("(" + o[0] + "," + o[1] + "," + o[2] + ")");
                });
    }

    @Test
    public void f12() {
        IntStream.rangeClosed(1, 20).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int) (Math.sqrt(a * a + b * b))})
                ).forEach(o -> {
            System.out.println("(" + o[0] + "," + o[1] + "," + o[2] + ")");
        });
    }

    @Test
    public void f13() {
        int a = 3;
        Stream<int[]> stream = IntStream.rangeClosed(1, 10)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .mapToObj(b -> new int[]{a, b, (int) (Math.sqrt(a * a + b * b))});

        IntStream intStream = IntStream.rangeClosed(1, 10)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0);

        Stream<Integer> boxed = IntStream.rangeClosed(1, 10)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed();

        Stream<int[]> stream1 = boxed
                .map(b -> new int[]{a, b, (int) (Math.sqrt(a * a + b * b))});
    }

    @Test
    public void f14() {
        IntStream.rangeClosed(1, 10).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 10)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0)
                ).forEach(o -> {
            System.out.println("(" + (int)o[0] + "," + (int)o[1] + "," + (int)o[2] + ")");
        });
    }
}
