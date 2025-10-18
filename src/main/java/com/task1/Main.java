package com.task1;

public class Main {
    public static void main(String[] args) {
        Calculator calc = new LightCalculatorProxy(new FullCalculator());
        var d = calc.add(5,5);
    }
}
