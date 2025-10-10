package com.game.task2.models.factory.elf;

import com.game.task2.models.factory.Unit;
import com.game.task2.models.factory.UnitFactory;
import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;

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
