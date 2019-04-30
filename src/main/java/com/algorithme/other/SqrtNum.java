package com.algorithme.other;

import org.junit.Test;

//牛顿迭代法开方
//https://blog.csdn.net/chenrenxiang/article/details/78286599
public class SqrtNum {
    public double sqrt(double num) {

        double err = 1e-15;
        double x = num;
        while (Math.abs(num - x * x) > err) {
            x = (x + num / x) / 2;
        }
        return x;
    }

    @Test
    public void f11() {
        double sqrt = sqrt(2);
        System.out.println(sqrt);
    }
}
