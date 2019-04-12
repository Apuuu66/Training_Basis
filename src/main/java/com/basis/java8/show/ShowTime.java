package com.basis.java8.show;

import com.basis.java8.filter.Apple;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ShowTime {
    List<Orange> list = Arrays.asList(new Orange(2, "blue"), new Orange(1, "red"), new Orange(3, "yellow"));

    @Test
    //构造函数引用
    public void f1() {
        Supplier<Orange> c1 = Orange::new;
        Orange o1 = c1.get();
        //等价于
        Supplier<Orange> c2 = () -> new Orange();
        Orange o2 = c2.get();
        Function<Integer, Orange> c3 = Orange::new;
        Orange o3 = c3.apply(1);

        BiFunction<Integer, String, Orange> c4 = Orange::new;
        Orange o4 = c4.apply(1, "red");

        HashMap<String, Function<Integer, Object>> map = new HashMap<>();
        map.put("apple", Apple::new);
        map.put("orange", Orange::new);
        Object apple = map.get("apple").apply(1);
    }

    @Test
    //复合lambda表达式
    //比较器复合
    public void f2() {
        list.sort((a, b) -> a.weight - b.weight);
        list.sort(Comparator.comparing(Orange::getWeight));
        list.sort(Comparator.comparingInt(a -> a.weight));
    }

    @Test
    //比较器链
    public void f3() {
        //按重量逆序
        list.sort(Comparator.comparing(Orange::getWeight).reversed());
        //当一样重时，按hash值排序
        list.sort(Comparator.comparing(Orange::getWeight).reversed().thenComparing(Orange::hashCode));
    }

    @Test
    //谓词复合
    public void f4() {
        Predicate<Orange> red = (orange -> orange.color.equals("red"));
        Predicate<Orange> weight = (orange -> orange.weight > 10);

        Predicate<Orange> negate = red.negate();
        Predicate<Orange> and = red.and(weight);
        Predicate<Orange> or = and.or(red);
        //注意and和or方法时按照在表达式链中的位置，从左到右确定优先级的。
        //a.or(b).and(c)可以看作（a||b）&&c
    }

    @Test
    //函数复合
    public void f5() {
        //（x -> x + 1）
        Function<Integer, Integer> f = (x -> x + 1);
        Function<Integer, Integer> g = (y -> y * 2);
        Function<Integer, Integer> h = f.andThen(g);
        Integer result1 = h.apply(1); //4
        //compose,先把给定函数用作compose的参数里面给的那个函数，然后再把函数本身用于结果。
        //在上一个例子代表f(g(x)),二andThen表示g(f(x)).
        Function<Integer, Integer> m = f.compose(g);
        Integer result2 = m.apply(1); //3
    }

}


