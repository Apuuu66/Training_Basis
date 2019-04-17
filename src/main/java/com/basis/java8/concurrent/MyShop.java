package com.basis.java8.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyShop {
    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
//            new Shop("LetsSaveBig"),
//            new Shop("MyFavoriteShop"),
//            new Shop("BuyItAll"),
//            new Shop("ShopEasy"),
//            new Shop("YangYang"),
            //new Shop("HuaHua1"), new Shop("HuaHua2"), new Shop("HuaHua3"), new Shop("HuaHua4"), new Shop("HuaHua5"),
            //new Shop("MeiMei1"), new Shop("MeiMei2"), new Shop("MeiMei3"), new Shop("MeiMei4"), new Shop("MeiMei5"),
            new Shop("GuangTouqiang")
    );

    public static void main(String[] args) {
        MyShop myShop = new MyShop();
        long start = System.nanoTime();
//        myShop.findPricesSync("iphoneSE2");
//        myShop.test();
//        List<String> list = myShop.findPricesAsync("iphoneSE2");
        myShop.f72();
        System.out.println((System.nanoTime() - start) / 1_000_000);
//        System.out.println(list);
    }

    public List<String> findPricesSync(String product) {
        List<String> list = shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
        return list;
    }

    public List<String> findPricesAsync(String product) {
        ExecutorService executor = Executors.newFixedThreadPool(shops.size(), (r) -> {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                }
        );
        List<CompletableFuture<String>> list = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(f -> f.thenApply(Quote::parse))
                .map(f -> f.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                .collect(Collectors.toList());
        return list.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    @Test
    public void f59() {
        Stream<CompletableFuture<Double>> completableFutureStream = shops.stream().map(
                shop -> CompletableFuture
                        .supplyAsync(() -> shop.getPrice("123"))
                        .thenCombine(CompletableFuture.supplyAsync(
                                () -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),
                                (a, b) -> Double.parseDouble(a) * b));
        List<Double> collect = completableFutureStream.map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void f72() {
        ExecutorService executor = Executors.newFixedThreadPool(shops.size(), (r) -> {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                }
        );
        String product = "123";
        Stream<CompletableFuture<Double>> completableFutureStream = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(f -> {
                    return f.thenApply((a) -> {
                        Quote parse = Quote.parse(a);
                        return parse.getPrice();
                    }).thenCombine(CompletableFuture.supplyAsync(
                            () -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),
                            (a, b) -> a * b);
                });
        List<Double> collect = completableFutureStream.map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println(collect);

    }

    @Test
    public void f1() {
        long start = System.nanoTime();
        MyShop myShop = new MyShop();
        List<CompletableFuture<String>> iphone = myShop.shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice("iphone"),
                        Executors.newFixedThreadPool(myShop.shops.size(), (r) -> {
                            Thread t = new Thread(r);
                            t.setDaemon(true);
                            return t;
                        }))).collect(Collectors.toList());
        List<String> collect = iphone.stream().map(CompletableFuture::join).collect(Collectors.toList());
        long x = System.nanoTime() - start;
        System.out.println(x / 1_000_000);
    }

    @Test
    public void test1() {
        long start = System.currentTimeMillis();
        // 结果集
        List<String> list = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Integer> taskList = Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
        // 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
        CompletableFuture[] cfs = taskList.stream()
                .map(integer -> CompletableFuture.supplyAsync(() -> calc(integer), executorService)
                        .thenApply(h -> Integer.toString(h))
                        .whenComplete((s, e) -> {
                            System.out.println("任务" + s + "完成!result=" + s + "，异常 e=" + e + "," + new Date());
                            list.add(s);
                        })
                ).toArray(CompletableFuture[]::new);
        // 封装后无返回值，必须自己whenComplete()获取
        CompletableFuture.allOf(cfs).join();
        System.out.println("list=" + list + ",耗时=" + (System.currentTimeMillis() - start));
    }

    public int calc(Integer i) {
        try {
            if (i == 1) {
                Thread.sleep(3000);//任务1耗时3秒
            } else if (i == 5) {
                Thread.sleep(2000);//任务5耗时2秒
            } else {
                Thread.sleep(1000);//其它任务耗时1秒
            }
            System.out.println("task线程：" + Thread.currentThread().getName()
                    + "任务i=" + i + ",完成！+" + new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }


}
