package com.game.task2.models.factory.elf;

import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;
import com.game.task2.models.factory.warrior.Warrior;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Elf extends ClanUnit {
    private static Image image = new Image(Elf.class
            .getResource("/com/game/task2/views/elf.png").toExternalForm());

    public Elf(Vector2D pos) {
        super("Elf", 100, WeaponType.BOW, pos);
    }

    public Elf(Vector2D pos, WeaponType type) {
        super("Elf", 100, type, pos);
    }

    @Override
    public void render(GraphicsContext gc) {
//        double x = position.getX();
//        double y = position.getY();
//
//// Небольшая коррекция вправо (можно подобрать визуально)
//        double offsetX = 4;
//        double offsetY = UNIT_SIZE / 2; // опускаем треугольник чуть вниз
//
//        double[] xPoints = {
//                x + offsetX,
//                x + UNIT_SIZE / 2 + offsetX,
//                x - UNIT_SIZE / 2 + offsetX
//        };
//
//        double[] yPoints = {
//                y - UNIT_SIZE / 2 + offsetY,
//                y + UNIT_SIZE / 2 + offsetY,
//                y + UNIT_SIZE / 2 + offsetY
//        };
//
//        gc.setFill(color);
//        gc.fillPolygon(xPoints, yPoints, 3);
        gc.drawImage(image, position.getX(), position.getY(), UNIT_SIZE, UNIT_SIZE);
        gc.setGlobalBlendMode(BlendMode.SRC_ATOP);
        gc.setFill(new Color(color.getRed(),color.getGreen(),color.getBlue(),0.4));
        gc.fillRect(position.getX(), position.getY(), UNIT_SIZE, UNIT_SIZE);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
    }
}
