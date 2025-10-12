package com.game.task2.models.factory.unit;

import com.game.task2.models.factory.Movable;
import com.game.task2.models.factory.Renderable;
import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;

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
