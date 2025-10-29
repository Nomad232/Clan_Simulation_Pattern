package com.game.task2.models.command;

import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.other.Vector2D;

public class MoveCommand implements Command{
    private final Unit object;
    private Unit copy;
    private final Vector2D newVector2D;

    public MoveCommand(Unit object, Vector2D newVector2D) {
        this.object = object;
        this.newVector2D = newVector2D;
    }

    @Override
    public void execute() {
        copy = object.clone();
        object.setPosition(newVector2D);
    }

    @Override
    public void undo() {
        if(copy != null){
            object.restoreFrom(copy);
        }
    }
}
