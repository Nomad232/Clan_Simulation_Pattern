package com.game.task2.models.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Renderable {
    final int UNIT_SIZE = 10;
    void render(GraphicsContext gc, Color color);
}

