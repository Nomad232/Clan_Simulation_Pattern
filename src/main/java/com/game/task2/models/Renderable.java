package com.game.task2.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Renderable {
    double UNIT_SIZE = 20.0;
    void render(GraphicsContext gc);
    void setColor(Color color);
    Color getColor();
}

