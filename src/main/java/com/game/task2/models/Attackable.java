package com.game.task2.models;

public interface Attackable<T> {
    boolean attack(T target);
    void takeDamage(int number);
    WeaponType getWeapon();
}
