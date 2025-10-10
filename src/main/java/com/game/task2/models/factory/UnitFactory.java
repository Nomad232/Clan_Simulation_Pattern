package com.game.task2.models.factory;

public interface UnitFactory {
    Unit createUnit(Vector2D pos);
    Unit createUnitWithWeapon(WeaponType type, Vector2D pos);
}
