package com.game.task2.models.aiController;

import com.game.task2.models.chainOfResponsibility.CommandHandler;
import com.game.task2.models.command.AttackCommand;
import com.game.task2.models.command.Command;
import com.game.task2.models.command.FindNearestEnemyCommand;
import com.game.task2.models.command.MoveCommand;
import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.other.Vector2D;

import java.util.Collection;

public class AiController {
    public final double SPEED = 10;

    private final CommandHandler commandExecutor;

    public AiController(CommandHandler commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public void makeDecision(Unit unit, Collection<Unit> allUnits, double deltaTime) {
        if (!unit.isAlive()) {
            return;
        }

        // Поиск врага
        FindNearestEnemyCommand findCmd = new FindNearestEnemyCommand(unit, allUnits);
        commandExecutor.handle(findCmd, unit);

        Unit targetEnemy = findCmd.getNearestTarget();

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
                nextAction = new MoveCommand(unit, targetEnemy.getPosition(), deltaTime*SPEED);
            }

        } else {
            // Патрулировать! (Врагов нет)
            Vector2D patrolPoint = getPatrolPoint(unit, deltaTime*SPEED);
            nextAction = new MoveCommand(unit, patrolPoint, deltaTime*SPEED);
        }

        commandExecutor.handle(nextAction, unit);
    }

    // public void makeDecisionByLeader

    private Vector2D getPatrolPoint(Unit unit, double deltaTime) {
        double currentX = unit.getPosition().getX();
        double currentY = unit.getPosition().getY();
        if (currentX >= 100 && currentY >= 100) {
            return new Vector2D(0, 0);
        }
        return new Vector2D(100 * deltaTime, 100 * deltaTime);
    }
}
