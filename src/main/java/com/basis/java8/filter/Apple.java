package com.basis.java8.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apple {
    private int weight = 0;
    private String color = "";

    public Apple(Integer integer) {

    }
}
