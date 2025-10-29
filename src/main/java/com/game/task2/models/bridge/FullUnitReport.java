package com.game.task2.models.bridge;

import com.game.task2.models.factory.unit.Unit;

public class FullUnitReport extends UnitReport {
    public FullUnitReport(UnitOutputter outputter) {
        super(outputter);
    }

    @Override
    public void display(Unit unit) {
        outputter.printHeader("Characteristics: " + unit.getName());
        outputter.printStat("Health", String.valueOf(unit.getHealth()));
        outputter.printStat("Height", String.valueOf(unit.getHeight()));
        outputter.printStat("Color", String.valueOf(unit.getColor()));
        outputter.printHeader("Equipment: " + unit.getName());
        outputter.printStat("Weapon", String.valueOf(unit.getWeaponType()));
        outputter.printStat("Damage", String.valueOf(unit.getWeaponType().getDamage()));
        outputter.printStat("Attack range", String.valueOf(unit.getWeaponType().getRange()));
        outputter.printStat("Clothing", String.valueOf(unit.getClothing()));
        outputter.printFooter();
    }
}