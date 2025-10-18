package com.task1;

public class LightCalculatorProxy implements Calculator {

    private final Calculator fullCalculator;

    public LightCalculatorProxy(Calculator fullCalculator) {
        this.fullCalculator = fullCalculator;
    }

    @Override
    public double add(double a, double b) {
        if (a == 0) return b;
        if (b == 0) return a;
        return fullCalculator.add(a, b);
    }

    @Override
    public double subtract(double a, double b) {
        if (b == 0) return a;
        if (a == 0) return -b;
        return fullCalculator.subtract(a, b);
    }

    @Override
    public double multiply(double a, double b) {
        if (a == 0 || b == 0) return 0;
        return fullCalculator.multiply(a, b);
    }

    @Override
    public double divide(double a, double b) {
        if (b == 0) throw new ArithmeticException();
        if (a == 0) return 0;
        return fullCalculator.divide(a, b);
    }
}
