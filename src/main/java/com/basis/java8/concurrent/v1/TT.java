package com.basis.java8.concurrent.v1;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class TT {
    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"),
            new Shop("YangYang"),
            new Shop("HuaHua1"), new Shop("HuaHua2"), new Shop("HuaHua3"), new Shop("HuaHua4"), new Shop("HuaHua5"),
            new Shop("MeiMei1"), new Shop("MeiMei2"), new Shop("MeiMei3"), new Shop("MeiMei4"), new Shop("MeiMei5"),
            new Shop("GuangTouqiang"));

    public static void main(String[] args) {
        TT tt = new TT();
        long start = System.nanoTime();
        tt.findPrices();
//        tt.f2();
//        tt.findPrices("iphone");
//        tt.findPrice("iphone");
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    @Test
    public void findPrices() {
        String product = "iphone";
        System.out.println(shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList()));
    }

    @Test
    public void f2() {
        String product = "iphone";
        List<CompletableFuture<String>> list = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());
        List<String> collect = list.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    public List<String> findPrices(String product) {
        List<CompletableFuture<String>> list = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());
        return list.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private final Executor executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    public List<String> findPrice(String product) {
        List<CompletableFuture<String>> list = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)),executor))
                .collect(Collectors.toList());
        return list.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    @Test
    public void f83(){
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
    }
}
