package com.basis.java8.concurrent;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MyShop {
    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"),
            new Shop("YangYang"),
            //new Shop("HuaHua1"), new Shop("HuaHua2"), new Shop("HuaHua3"), new Shop("HuaHua4"), new Shop("HuaHua5"),
            //new Shop("MeiMei1"), new Shop("MeiMei2"), new Shop("MeiMei3"), new Shop("MeiMei4"), new Shop("MeiMei5"),
            new Shop("GuangTouqiang"));

    public static void main(String[] args) {
        MyShop myShop = new MyShop();
        long start = System.nanoTime();
//        myShop.findPricesSync("iphoneSE2");
        myShop.test();
//        List<String> list = myShop.findPricesAsync("iphoneSE2");
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

}
