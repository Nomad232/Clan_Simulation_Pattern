package com.game.task2.models.command;

import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.other.Renderable;
import com.game.task2.models.other.Vector2D;

public class MoveCommand implements Command{
    private final Unit object;
    private Unit copy;
    private final Vector2D newVector2D;
    private final double speed;

    public MoveCommand(Unit object, Vector2D newVector2D, double speed) {
        this.object = object;
        this.newVector2D = newVector2D;
        this.speed = speed;
    }

    @Override
    public void execute() {
        copy = object.clone(); // Сохраняем состояние *до* шага

        Vector2D currentPos = object.getPosition();

        // !!! ПРЕДПОЛОЖЕНИЕ:
        // У вашего класса Unit есть метод getSpeed()
        if (speed <= 0) return; // Не двигаемся, если скорость 0

        double distanceSq = currentPos.distanceSq(newVector2D);

        // Если мы уже на месте (или очень близко)
        if (distanceSq < Renderable.UNIT_SIZE*Renderable.UNIT_SIZE) {
            return;
        }

        double speedSq = speed * speed;

        if (speedSq >= distanceSq) {
            // Нашей скорости хватает, чтобы дойти (или перешагнуть)
            // за этот тик. Просто ставим юнита в конечную точку.
            object.setPosition(newVector2D);
        } else {
            // Скорости не хватает. Делаем один шаг в направлении цели.

            // !!! ПРЕДПОЛОЖЕНИЕ:
            // У Vector2D есть методы subtract, normalize, multiply, add

            // 1. Находим вектор направления (Цель - ТекущаяПозиция)
            Vector2D direction = newVector2D.subtract(currentPos);

            // 2. Нормализуем его (получаем вектор_направления_длиной_1)
            direction = direction.normalize();

            // 3. Умножаем на скорость, чтобы получить вектор_шага
            Vector2D moveStep = direction.multiply(speed);

            // 4. Прибавляем вектор_шага к текущей позиции
            Vector2D newPos = currentPos.add(moveStep);

            object.setPosition(newPos);
        }
    }

    @Override
    public void undo() {
        if(copy != null){
            object.restoreFrom(copy);
        }
    }

    public Unit getObject() {
        return object;
    }

    public Unit getCopy() {
        return copy;
    }

    public Vector2D getNewVector2D() {
        return newVector2D;
    }
}
