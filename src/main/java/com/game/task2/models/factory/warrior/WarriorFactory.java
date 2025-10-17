package com.game.task2.models.factory.warrior;

import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.factory.unit.UnitFactory;
import com.game.task2.models.other.Vector2D;
import com.game.task2.models.other.WeaponType;

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
