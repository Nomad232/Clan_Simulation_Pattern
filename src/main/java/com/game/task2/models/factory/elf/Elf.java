package com.game.task2.models.factory.elf;

import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Elf extends ClanUnit {

    public Elf(Vector2D pos) {
        super("Elf", 100, WeaponType.BOW, pos);
    }

    public Elf(Vector2D pos, WeaponType type) {
        super("Elf", 100, type, pos);
    }

    @Override
    public void render(GraphicsContext gc) {
        double x = position.getX();
        double y = position.getY();

// Небольшая коррекция вправо (можно подобрать визуально)
        double offsetX = 4;
        double offsetY = UNIT_SIZE / 2; // опускаем треугольник чуть вниз

        double[] xPoints = {
                x + offsetX,
                x + UNIT_SIZE / 2 + offsetX,
                x - UNIT_SIZE / 2 + offsetX
        };

        double[] yPoints = {
                y - UNIT_SIZE / 2 + offsetY,
                y + UNIT_SIZE / 2 + offsetY,
                y + UNIT_SIZE / 2 + offsetY
        };

        gc.setFill(color);
        gc.fillPolygon(xPoints, yPoints, 3);
    }
}
