package com.game.task2.models.factory.dwarf;

import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.other.ClothingType;
import com.game.task2.models.other.HeightType;
import com.game.task2.models.other.Vector2D;
import com.game.task2.models.other.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Dwarf extends ClanUnit {
    private static Image image = new Image(Dwarf.class
            .getResource("/com/game/task2/views/dwarf.png").toExternalForm());

    public Dwarf(Vector2D pos) {
        this(pos, WeaponType.BOW);
    }

    public Dwarf(Vector2D pos, WeaponType type) {
        super("Dwarf", 50, type, ClothingType.LEATHER, HeightType.SHORT, pos);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY(), UNIT_SIZE, UNIT_SIZE);
        gc.setGlobalBlendMode(BlendMode.SRC_ATOP);
        gc.setFill(new Color(color.getRed(),color.getGreen(),color.getBlue(),0.4));
        gc.fillRect(position.getX(), position.getY(), UNIT_SIZE, UNIT_SIZE);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
    }
}
