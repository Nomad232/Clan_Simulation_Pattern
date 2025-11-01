package com.game.task2.models.mediator;

import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.other.Vector2D;

import java.util.List;

public interface GroupManager {
    void addUnit(Unit unit);
    void removeUnit(Unit unit);
    List<Unit> getUnits();
    void move(Vector2D targetCord, double deltaTime);
    boolean moveToTarget(Unit target, double deltaTime);
    boolean moveOrAttackNearestTarget(List<Unit> targets, double deltaTime);
    boolean moveOrAttackRandomTarget(List<Unit> targets, double deltaTime);

    void patrol(double deltaTime);
}
