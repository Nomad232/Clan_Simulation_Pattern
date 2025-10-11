package com.game.task2.models.factory.unit;

import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;

public interface UnitFactory {
    Unit createUnit(Vector2D pos);
    Unit createUnitWithWeapon(WeaponType type, Vector2D pos);
}
