package com.game.task2.models.factory.unit;

import com.game.task2.models.other.Vector2D;
import com.game.task2.models.other.WeaponType;

public interface UnitFactory {
    Unit createUnit(Vector2D pos);
    Unit createUnitWithWeapon(WeaponType type, Vector2D pos);
}
