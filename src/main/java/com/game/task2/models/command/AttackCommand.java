package com.game.task2.models.command;

import com.game.task2.models.factory.unit.Unit;

public class AttackCommand implements Command {
    private final Unit object;
    private final Unit target;
    private Unit copy;  // Можно использовать структуру Stack

    public AttackCommand(Unit object, Unit target) {
        this.object = object;
        this.target = target;
    }

    @Override
    public void execute() {
        copy = target.clone();
        object.attack(target);
    }

    @Override
    public void undo() {
        if(copy != null){
            target.restoreFrom(copy);
        }
    }

    public Unit getObject() {
        return object;
    }

    public Unit getTarget() {
        return target;
    }

    public Unit getCopy() {
        return copy;
    }
}
