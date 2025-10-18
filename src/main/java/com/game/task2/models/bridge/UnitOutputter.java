package com.game.task2.models.bridge;

public interface UnitOutputter {
    void printHeader(String header);
    void printStat(String label, String value);
    void printFooter();
}
