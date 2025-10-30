package com.game.task2.models.command;

import com.game.task2.models.factory.unit.Unit;
import javafx.scene.paint.Color;

import java.util.Collection;

public class FindNearestEnemyCommand implements Command{
    private final Unit object;
    private final Collection<Unit> targets;
    private Unit nearestTarget;  // Можно использовать структуру Stack

    public FindNearestEnemyCommand(Unit object, Collection<Unit> targets) {
        this.object = object;
        this.targets = targets;
    }

    @Override
    public void execute() {
        nearestTarget = findNearestEnemy();
    }

    @Override
    public void undo() {
    }

    public Unit getObject() {
        return object;
    }

    public Collection<Unit> getTargets() {
        return targets;
    }

    public Unit getNearestTarget() {
        if (nearestTarget != null){
            return nearestTarget;
        } else {
            return findNearestEnemy();
        }
    }

    private Unit findNearestEnemy() {
        if (!object.isAlive()) return null;
        Color friendColor = object.getColor();
        Unit nearestEnemy = null;
        double minDistanceSq = Double.MAX_VALUE; // Квадрат відстані

        for (Unit otherUnit : targets) {
            if (!otherUnit.isAlive()) continue;
            if (object != otherUnit && otherUnit.getColor() != friendColor) {
                double distSq = object.getPosition().distanceSq(otherUnit.getPosition());

                if (distSq < minDistanceSq) {
                    minDistanceSq = distSq;
                    nearestEnemy = otherUnit;
                }
            }
        }
        return nearestEnemy;
    }
}
