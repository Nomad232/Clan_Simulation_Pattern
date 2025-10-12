package com.game.task2.models.factory.unit;

import com.game.task2.models.Vector2D;
import com.game.task2.models.WeaponType;

public interface UnitFactory {
    Unit createUnit(Vector2D pos);
    Unit createUnitWithWeapon(WeaponType type, Vector2D pos);
}
