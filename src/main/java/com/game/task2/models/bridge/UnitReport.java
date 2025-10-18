package com.game.task2.models.bridge;

import com.game.task2.models.factory.unit.Unit;

public abstract class UnitReport {
    protected UnitOutputter outputter;

    public UnitReport(UnitOutputter outputter) {
        this.outputter = outputter;
    }

    public abstract void display(Unit unit);
}