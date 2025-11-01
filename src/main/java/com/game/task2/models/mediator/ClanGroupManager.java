package com.game.task2.models.mediator;

import com.game.task2.models.chainOfResponsibility.AttackHandler;
import com.game.task2.models.chainOfResponsibility.CommandHandler;
import com.game.task2.models.chainOfResponsibility.FindNearestEnemyHandler;
import com.game.task2.models.chainOfResponsibility.MoveHandler;
import com.game.task2.models.command.AttackCommand;
import com.game.task2.models.command.Command;
import com.game.task2.models.command.FindNearestEnemyCommand;
import com.game.task2.models.command.MoveCommand;
import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.other.Vector2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class ClanGroupManager implements GroupManager {
    private final double SPEED = 15.0;
    private CommandHandler commandExecutor;
    private List<Unit> units;

    public ClanGroupManager() {
        units = new ArrayList<>();

        CommandHandler findNearestEnemyHandler = new FindNearestEnemyHandler();
        CommandHandler attackHandler = new AttackHandler();
        CommandHandler moveHandler = new MoveHandler();

        findNearestEnemyHandler.setNext(moveHandler);
        moveHandler.setNext(attackHandler);
        commandExecutor = findNearestEnemyHandler;
    }

    public ClanGroupManager(List<Unit> units) {
        this();
        this.units = units;
    }

    public ClanGroupManager(CommandHandler handler) {
        this();
        this.commandExecutor = handler;
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
            Command moveCmd = new MoveCommand(unit, targetCord, deltaTime * SPEED);
            commandExecutor.handle(moveCmd, unit);
        }
    }

    @Override
    public boolean moveToTarget(Unit target, double deltaTime) {
        for (Unit unit : units) {
            Command moveCmd = new MoveCommand(unit, target.getPosition(), deltaTime * SPEED);
            commandExecutor.handle(moveCmd, unit);
        }
        return true;
    }

    @Override
    public boolean moveOrAttackNearestTarget(List<Unit> targets, double deltaTime) {
        if (units.isEmpty() || targets.isEmpty()) return false;
        FindNearestEnemyCommand findCmd = new FindNearestEnemyCommand(units.getFirst(),List.of(targets.getFirst()));
        commandExecutor.handle(findCmd, null);

        Unit targetEnemy = findCmd.getNearestTarget();
        System.out.println(targetEnemy);
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

                commandExecutor.handle(nextAction, unit);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean moveOrAttackRandomTarget(List<Unit> targets, double deltaTime) {
        if (units.isEmpty() || targets.isEmpty()) return false;
        int random = new Random().nextInt(0, targets.size());
        FindNearestEnemyCommand findCmd = new FindNearestEnemyCommand(units.getFirst(), List.of(targets.get(random)));
        commandExecutor.handle(findCmd, null);

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

                commandExecutor.handle(nextAction, unit);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void patrol(double deltaTime) {
        for (Unit unit : units) {
            Vector2D patrolPoint = getPatrolPoint(unit, deltaTime * SPEED);
            Command moveCmd = new MoveCommand(unit, patrolPoint, deltaTime * SPEED);
            commandExecutor.handle(moveCmd, unit);
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
}
