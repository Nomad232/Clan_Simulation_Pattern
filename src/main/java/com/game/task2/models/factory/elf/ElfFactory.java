package com.game.task2.models.factory.elf;

import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.factory.unit.UnitFactory;
import com.game.task2.models.Vector2D;
import com.game.task2.models.WeaponType;

public class ElfFactory implements UnitFactory {
    @Override
    public Unit createUnit(Vector2D pos) {
        return new Elf(pos);
    }

    @Override
    public Unit createUnitWithWeapon(WeaponType type, Vector2D pos) {
        return new Elf(pos, type);
    }
}
