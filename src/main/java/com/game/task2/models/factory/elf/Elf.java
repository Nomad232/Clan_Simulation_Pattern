package com.game.task2.models.factory.elf;

import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.other.ClothingType;
import com.game.task2.models.other.HeightType;
import com.game.task2.models.other.Vector2D;
import com.game.task2.models.other.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Elf extends ClanUnit {
    private static Image image = new Image(Elf.class
            .getResource("/com/game/task2/views/elf.png").toExternalForm());

    public Elf(Vector2D pos) {
        this(pos, WeaponType.BOW);
    }

    public Elf(Vector2D pos, WeaponType type) {
        super("Elf", 100, type, ClothingType.WOODEN, HeightType.TALL, pos);
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
