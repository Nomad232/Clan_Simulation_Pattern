package com.game.task2.models.factory.dwarf;

import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.factory.unit.UnitFactory;
import com.game.task2.models.Vector2D;
import com.game.task2.models.WeaponType;

public class DwarfFactory implements UnitFactory {
    @Override
    public Unit createUnit(Vector2D pos) {
        return new Dwarf(pos);
    }

    @Override
    public Unit createUnitWithWeapon(WeaponType type, Vector2D pos) {
        return new Dwarf(pos, type);
    }
}
