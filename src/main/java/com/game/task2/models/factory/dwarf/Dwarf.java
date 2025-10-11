package com.game.task2.models.factory.dwarf;

import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;
import javafx.scene.canvas.GraphicsContext;

public class Dwarf extends ClanUnit {

    public Dwarf(Vector2D pos) {
        super("Dwarf", 50, WeaponType.STAFF, pos);
    }

    public Dwarf(Vector2D pos, WeaponType type) {
        super("Dwarf", 50, type, pos);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(position.getX(), position.getY(), UNIT_SIZE, UNIT_SIZE);
    }
}
