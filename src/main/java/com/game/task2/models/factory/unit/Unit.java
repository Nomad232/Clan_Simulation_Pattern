package com.game.task2.models.factory.unit;

import com.game.task2.models.other.Attackable;
import com.game.task2.models.other.Movable;
import com.game.task2.models.other.Renderable;
import com.game.task2.models.other.Vector2D;

public interface Unit extends Cloneable, Renderable, Movable, Attackable<Unit> {
    void spawn(Vector2D vector);
    void destroy();
    boolean isAlive();

    String getName();
    int getHealth();

    Unit clone();
}
