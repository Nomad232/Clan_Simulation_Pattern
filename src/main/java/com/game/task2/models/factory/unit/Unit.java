package com.game.task2.models.factory.unit;

import com.game.task2.models.Movable;
import com.game.task2.models.Renderable;
import com.game.task2.models.Vector2D;
import com.game.task2.models.WeaponType;

public interface Unit extends Cloneable, Renderable, Movable {
    boolean attack(Unit target);
    void spawn(Vector2D vector);
    void destroy();
    boolean isAlive();
    void takeDamage(int number);

    String getName();
    int getHealth();
    WeaponType getWeapon();

    Unit clone();
}
