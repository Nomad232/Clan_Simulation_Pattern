package com.game.task2.models.factory.dwarf;

import com.game.task2.models.factory.AbstractUnit;
import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Dwarf extends AbstractUnit{

    public Dwarf(Vector2D pos) {
        super("Dwarf", 50, WeaponType.STAFF, pos);
    }

    public Dwarf(Vector2D pos, WeaponType type) {
        super("Dwarf", 50, type, pos);
    }

    @Override
    public void render(GraphicsContext gc, Color color) {
        gc.setFill(color);
        gc.fillOval(position.getX(), position.getY(), UNIT_SIZE,UNIT_SIZE);
    }
}
