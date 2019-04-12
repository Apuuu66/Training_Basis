package com.basis.java8.stream;

import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class StreamBasic {

    @Test
    public void threeHighCaloricDishName() {
        List<String> list = Dish.menu.stream()              //获取流
                .filter(d -> d.getCalories() > 300) //建立流水线，首先选出高热量的菜肴
                .map(Dish::getName)     //获取菜名
                .limit(3)               //获取3个
                .collect(toList());     //将结果保存在另一个list中
        System.out.println(list);
    }

    @Test
    public void printOperator() {
        List<String> list = Dish.menu.stream()
                .filter(d -> {
                    System.out.println("filter->"+d.getName());
                    return d.getCalories() > 300;
                })
                .map(d -> {
                    System.out.println("map->"+d.getName());
                    return d.getName();
                })
                .limit(3)
                .collect(toList());
        System.out.println(list);
    }
}

