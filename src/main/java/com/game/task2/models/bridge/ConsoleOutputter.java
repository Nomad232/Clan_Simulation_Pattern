package com.game.task2.models.bridge;

public class ConsoleOutputter implements UnitOutputter{
    @Override
    public void printHeader(String header) {
        System.out.println("--- " + header + " ---");
    }

    @Override
    public void printStat(String label, String value) {
        System.out.println(label + ": " + value);
    }

    @Override
    public void printFooter() {
        System.out.println("--------------------\n");
    }
}
