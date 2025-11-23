package com.game.task2.models.mediator;

import com.game.task2.models.command.AttackCommand;
import com.game.task2.models.command.Command;
import com.game.task2.models.command.FindNearestEnemyCommand;
import com.game.task2.models.command.MoveCommand;
import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.memento.Savable;
import com.game.task2.models.other.Vector2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ClanGroupManager implements GroupManager {
    private final double SPEED = 25.0;
    private List<Unit> units = new ArrayList<>();

    public ClanGroupManager() {
    }

    public ClanGroupManager(List<Unit> units) {
        this.units = units;
    }

    public ClanGroupManager(GroupManager manager) {
        for (var item : manager.getUnits()){
            units.add(item.clone());
        }
    }

    @Override
    public void addUnit(Unit unit) {
        units.add(unit);
    }

    @Override
    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    @Override
    public List<Unit> getUnits() {
        return units;
    }

    @Override
    public void move(Vector2D targetCord, double deltaTime) {
        for (Unit unit : units) {
            if (unit instanceof ClanUnit clanUnit) {
                Command moveCmd = new MoveCommand(unit, targetCord, deltaTime * SPEED);
                clanUnit.handleCommand(moveCmd);
            }
        }
    }

    @Override
    public boolean moveToTarget(Unit target, double deltaTime) {
        for (Unit unit : units) {
            if (unit instanceof ClanUnit clanUnit) {
                Command moveCmd = new MoveCommand(unit, target.getPosition(), deltaTime * SPEED);
                clanUnit.handleCommand(moveCmd);
            }

        }
        return true;
    }

    @Override
    public boolean moveOrAttackNearestTarget(List<Unit> targets, double deltaTime) {
        if (units.isEmpty() || targets.isEmpty()) return false;
        List<Unit> enemies = getEnemies(targets);
        Unit searcher = units.stream()
                .filter(Unit::isAlive)
                .findFirst()
                .orElse(null);
        if (enemies.isEmpty() || searcher == null) return false;

        FindNearestEnemyCommand findCmd = new FindNearestEnemyCommand(searcher, enemies);

        if (searcher instanceof ClanUnit clanUnit) {
            clanUnit.handleCommand(findCmd);


            Unit targetEnemy = findCmd.getNearestTarget();
            for (Unit unit : units) {

                Command nextAction;
                if (targetEnemy != null) {
                    // Враг найден! Проверяем дистанцию.

                    double attackRange = unit.getWeaponType().getRange();
                    double attackRangeSq = attackRange * attackRange; // Сравниваем квадраты

                    // Пересчитываем расстояние
                    double distanceToEnemySq = unit.getPosition().distanceSq(targetEnemy.getPosition());

                    if (distanceToEnemySq <= attackRangeSq) {
                        // Атаковать! (Враг в зоне досягаемости)
                        nextAction = new AttackCommand(unit, targetEnemy);

                    } else {
                        // Двигаться к врагу! (Враг далеко)
                        nextAction = new MoveCommand(unit, targetEnemy.getPosition(), deltaTime * SPEED);
                    }

                    clanUnit.handleCommand(nextAction);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void patrol(double deltaTime) {
        for (Unit unit : units) {
            if (unit instanceof ClanUnit clanUnit) {
                Vector2D patrolPoint = getPatrolPoint(unit, deltaTime * SPEED);
                Command moveCmd = new MoveCommand(unit, patrolPoint, deltaTime * SPEED);
                clanUnit.handleCommand(moveCmd);
            }

        }
    }

    private Vector2D getPatrolPoint(Unit unit, double deltaTime) {
        double currentX = unit.getPosition().getX();
        double currentY = unit.getPosition().getY();
        if (currentX >= 100 && currentY >= 100) {
            return new Vector2D(0, 0);
        }
        return new Vector2D(100 * deltaTime, 100 * deltaTime);
    }

    private List<Unit> getEnemies(List<Unit> targets) {
        if (units.isEmpty() || targets.isEmpty()) return new ArrayList<>();

        Color friendlyColor = units.getFirst().getColor();

        return targets.stream()
                .filter(unit -> unit.getColor() != friendlyColor && unit.isAlive())
                .toList();
    }
}
