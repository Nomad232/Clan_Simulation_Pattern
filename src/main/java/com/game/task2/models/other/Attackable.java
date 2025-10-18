package com.game.task2.models.other;

public interface Attackable<T> {
    boolean attack(T target);
    void takeDamage(int number);
    WeaponType getWeaponType();
}
