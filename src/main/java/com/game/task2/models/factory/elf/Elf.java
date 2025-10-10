package com.game.task2.models.factory.elf;

import com.game.task2.models.factory.AbstractUnit;
import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Elf extends AbstractUnit{

    public Elf(Vector2D pos) {
        super("Elf", 100, WeaponType.BOW, pos);
    }

    public Elf(Vector2D pos, WeaponType type) {
        super("Elf", 100, type, pos);
    }

    @Override
    public void render(GraphicsContext gc, Color color) {
        gc.setFill(color);
        gc.fillOval(position.getX(), position.getY(), UNIT_SIZE,UNIT_SIZE);
    }
}
