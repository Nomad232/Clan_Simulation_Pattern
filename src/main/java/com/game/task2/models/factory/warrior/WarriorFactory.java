package com.game.task2.models.factory.warrior;

import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.factory.unit.UnitFactory;
import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;

public class WarriorFactory implements UnitFactory {
    @Override
    public Unit createUnit(Vector2D pos) {
        return new Warrior(pos);
    }

    @Override
    public Unit createUnitWithWeapon(WeaponType type, Vector2D pos) {
        return new Warrior(pos, type);
    }
}
