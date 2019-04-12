package com.basis.java8.show;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orange {
    int weight;
    String color;

    public Orange(int weight) {
        this.weight = weight;
    }
}
