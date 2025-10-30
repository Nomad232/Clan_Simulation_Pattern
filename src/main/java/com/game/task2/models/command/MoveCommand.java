package com.game.task2.models.command;

import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.other.Movable;
import com.game.task2.models.other.Renderable;
import com.game.task2.models.other.Vector2D;

public class MoveCommand implements Command{
    private final Movable object;
    private Vector2D oldPosition;
    private final Vector2D newVector2D;
    private final double speed;

    public MoveCommand(Movable object, Vector2D newVector2D, double speed) {
        this.object = object;
        this.newVector2D = newVector2D;
        this.speed = speed;
    }

    @Override
    public void execute() {
        oldPosition = object.getPosition();

        Vector2D currentPos = object.getPosition();

        if (speed <= 0) return;

        double distanceSq = currentPos.distanceSq(newVector2D);

        if (distanceSq < Renderable.UNIT_SIZE*Renderable.UNIT_SIZE) {
            return;
        }

        double speedSq = speed * speed;

        if (speedSq >= distanceSq) {
            object.setPosition(newVector2D);
        } else {
            Vector2D direction = newVector2D.subtract(currentPos);
            direction = direction.normalize();
            Vector2D moveStep = direction.multiply(speed);
            Vector2D newPos = currentPos.add(moveStep);
            object.setPosition(newPos);
        }
    }

    @Override
    public void undo() {
        if(oldPosition != null){
            object.setPosition(oldPosition);
        }
    }

    public Vector2D getOldPosition() {
        return oldPosition;
    }

    public Movable getObject() {
        return object;
    }

    public Vector2D getNewVector2D() {
        return newVector2D;
    }

    public double getSpeed() {
        return speed;
    }
}
