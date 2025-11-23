package com.game.task2.models.factory.unit;

import com.game.task2.models.other.*;

public interface Unit extends Cloneable, Renderable, Movable, Attackable<Unit> {
    void spawn(Vector2D vector);
    void destroy();
    boolean isAlive();

    String getName();
    void setName(String s);
    int getHealth();

    ClothingType getClothing();
    HeightType getHeight();

    Unit clone();
    void restoreFrom(Unit unit);
}
