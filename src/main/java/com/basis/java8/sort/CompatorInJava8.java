package com.basis.java8.sort;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Data
public class CompatorInJava8 {
    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple(1), new Apple(3), new Apple(2));
        list.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(list);
    }
}

@Data
@AllArgsConstructor
class Apple {
    int weight;
}

