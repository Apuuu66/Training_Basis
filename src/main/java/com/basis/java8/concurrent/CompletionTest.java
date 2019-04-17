package com.basis.java8.concurrent;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class CompletionTest {
    private static final Random random = new Random();
    private static final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
//            new Shop("MyFavoriteShop"),
//            new Shop("BuyItAll"),
            new Shop("ShopEasy"),
            new Shop("YangYang"),
            //new Shop("HuaHua1"), new Shop("HuaHua2"), new Shop("HuaHua3"), new Shop("HuaHua4"), new Shop("HuaHua5"),
            //new Shop("MeiMei1"), new Shop("MeiMei2"), new Shop("MeiMei3"), new Shop("MeiMei4"), new Shop("MeiMei5"),
            new Shop("GuangTouqiang")
    );

    public void randomDealy() {
        int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        ExecutorService executor = Executors.newFixedThreadPool(shops.size(), (r) -> {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                }
        );
        Stream<CompletableFuture<String>> completableFutureStream = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
        return completableFutureStream;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletionTest completionTest = new CompletionTest();
        CompletableFuture[] completableFutures = completionTest.findPricesStream("qqq")
                .map(f -> f.thenAccept(System.out::println))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.anyOf(completableFutures).join();

    }

    @Test
    public void f57() {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesStream("iphone18")
                .map(f -> f.thenAccept(
                        s -> System.out.println(s + " (done in " + ((System.nanoTime() - start)) / 1_000_000 + " ms)")
                )).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responed in " + ((System.nanoTime() - start) / 1_000_000) + " ms");
    }

    @Test
    public void f69() {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesStream("iphone18")
                .map(f -> f.thenAccept(
                        s -> System.out.println(s + " (done in " + ((System.nanoTime() - start)) / 1_000_000 + " ms)")
                )).toArray(CompletableFuture[]::new);
        CompletableFuture.anyOf(futures).join();
        System.out.println("All shops have now responed in " + ((System.nanoTime() - start) / 1_000_000) + " ms");

    }
}
