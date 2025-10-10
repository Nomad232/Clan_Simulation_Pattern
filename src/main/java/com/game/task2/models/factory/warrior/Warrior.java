package com.game.task2.models.factory.warrior;

import com.game.task2.models.factory.AbstractUnit;
import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Warrior extends AbstractUnit{

    public Warrior(Vector2D pos) {
        super("Warrior", 150, WeaponType.SWORD, pos);
    }

    public Warrior(Vector2D pos, WeaponType type) {
        super("Warrior", 150, type, pos);
    }

    @Override
    public void render(GraphicsContext gc, Color color) {
        gc.setFill(color);
        gc.fillOval(position.getX(), position.getY(), UNIT_SIZE,UNIT_SIZE);
    }
}
