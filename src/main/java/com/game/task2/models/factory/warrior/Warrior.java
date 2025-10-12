package com.game.task2.models.factory.warrior;

import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.Vector2D;
import com.game.task2.models.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Warrior extends ClanUnit {
    private static Image image = new Image(Warrior.class
            .getResource("/com/game/task2/views/spartan.png").toExternalForm());

    public Warrior(Vector2D pos) {
        super("Warrior", 150, WeaponType.SWORD, pos);
    }

    public Warrior(Vector2D pos, WeaponType type) {
        super("Warrior", 150, type, pos);
    }

    @Override
    public void render(GraphicsContext gc) {
//        gc.setFill(color);
//        gc.fillOval(position.getX(), position.getY(), UNIT_SIZE,UNIT_SIZE);
        gc.drawImage(image, position.getX(), position.getY(), UNIT_SIZE, UNIT_SIZE);
        gc.setGlobalBlendMode(BlendMode.SRC_ATOP);
        gc.setFill(new Color(color.getRed(),color.getGreen(),color.getBlue(),0.4));
        gc.fillRect(position.getX(), position.getY(), UNIT_SIZE, UNIT_SIZE);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
    }
}
