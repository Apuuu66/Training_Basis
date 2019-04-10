package com.basis.java8.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ListSort {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("F", "a", "b", "C", "E");
        list.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        list.sort(Comparator.naturalOrder());
        list.sort(String::compareTo);
        System.out.println(list);
        //逆序
        list.sort(Comparator.comparing(String::hashCode).reversed());
        //比较器链 先按hashcode排序，如果hash一样，就按长度排序
        list.sort(Comparator.comparing(String::hashCode).reversed().thenComparing(String::length));

    }
}
