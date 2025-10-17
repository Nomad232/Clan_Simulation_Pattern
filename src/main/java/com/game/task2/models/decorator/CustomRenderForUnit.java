package com.game.task2.models.decorator;

import com.game.task2.models.other.Renderable;
import com.game.task2.models.factory.dwarf.Dwarf;
import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.unit.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class CustomRenderForUnit implements Renderable {
    private static final Image image = new Image(Dwarf.class
            .getResource("/com/game/task2/views/crown.png").toExternalForm());
    private Unit leader;

    public CustomRenderForUnit() {
        leader = new ClanUnit();
    }

    public CustomRenderForUnit(Unit leader) {
        this.leader = leader;
    }

    @Override
    public void render(GraphicsContext gc) {
        double x = leader.getPosition().getX();
        double y = leader.getPosition().getY();
        Color color = leader.getColor();

        gc.setFill(color);
//        gc.fillRect(position.getX(), position.getY(), UNIT_SIZE, UNIT_SIZE);
        gc.drawImage(image, x, y, UNIT_SIZE, UNIT_SIZE);
    }

    @Override
    public void setColor(Color color) {
        leader.setColor(color);
    }

    @Override
    public Color getColor() {
        return leader.getColor();
    }
}
