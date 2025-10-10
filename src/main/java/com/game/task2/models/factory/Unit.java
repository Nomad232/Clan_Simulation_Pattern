package com.game.task2.models.factory;

import javafx.scene.canvas.GraphicsContext;

public interface Unit extends Cloneable, Renderable{
    void move(Vector2D vector);
    void attack(Unit target);
    void spawn(Vector2D vector);
    void destroy();
    boolean isAlive();
    void takeDamage(int number);

    String getName();
    int getHealth();
    WeaponType getWeapon();
    Vector2D getPosition();

    Unit clone();
}
