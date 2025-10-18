package com.game.task2.models.bridge;

import com.game.task2.models.factory.unit.Unit;

public class ShortUnitReport extends UnitReport {
    public ShortUnitReport(UnitOutputter outputter) {
        super(outputter);
    }

    @Override
    public void display(Unit unit) {
        outputter.printHeader("Report: " + unit.getName());
        outputter.printStat("Health", String.valueOf(unit.getHealth()));
        outputter.printStat("Damage", String.valueOf(unit.getWeaponType().getDamage()));
        outputter.printStat("Clothing", String.valueOf(unit.getClothing()));
        outputter.printFooter();
    }
}
