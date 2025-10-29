package com.task1;

public class Main {
    public static void main(String[] args) {
        Calculator calc = new LightCalculatorProxy(new FullCalculator());
        var res = calc.add(5,5);
        System.out.println(res);
    }
}
