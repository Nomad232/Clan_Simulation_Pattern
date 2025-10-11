package com.game.task2.models.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Renderable {
    double UNIT_SIZE = 10.0;
    void render(GraphicsContext gc);
    void setColor(Color color);
    Color getColor();
}

