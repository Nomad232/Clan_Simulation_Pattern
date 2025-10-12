package com.game.task2.models.factory;

public interface Movable {
    Vector2D getPosition();
    void setPosition(Vector2D vector);
    void move(Vector2D vector);
}
