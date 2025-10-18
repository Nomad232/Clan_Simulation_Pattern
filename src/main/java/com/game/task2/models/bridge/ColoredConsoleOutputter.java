package com.game.task2.models.bridge;

public class ColoredConsoleOutputter implements UnitOutputter{
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";

    @Override
    public void printHeader(String header) {
        System.out.println(GREEN + "--- "+ RESET + BLUE + header + GREEN +" ---" + RESET);
    }

    @Override
    public void printStat(String label, String value) {
        System.out.println(BLUE+  label + ": " + RESET + value );
    }

    @Override
    public void printFooter() {
        System.out.println(GREEN + "--------------------\n" + RESET);
    }
}
