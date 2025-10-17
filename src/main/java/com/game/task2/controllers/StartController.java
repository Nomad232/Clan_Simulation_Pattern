package com.game.task2.controllers;

import com.game.task2.models.other.Renderable;
import com.game.task2.models.decorator.CustomRenderForUnit;
import com.game.task2.models.factory.elf.Elf;
import com.game.task2.models.singleton.ClanLeader;
import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.other.Vector2D;
import com.game.task2.models.util.UnitGenerationMethods;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.game.task2.models.other.Renderable.UNIT_SIZE;

// Головний контролер для керування симуляцією (FX-контролер)
public class StartController {
    private static final Color FRIEND_COLOR = Color.BLUE; // Колір дружньої фракції
    private static final Color ENEMY_COLOR = Color.RED;   // Колір ворожої фракції
    private static final double SPAWN_RADIUS = 60.0;
    private double attackThrottleTimer = 0.0; // Таймер для обмеження частоти атаки
    private final List<Unit> units = new ArrayList<>(); // Список усіх юнітів у симуляції
    private AnimationTimer gameLoop; // Головний ігровий цикл
    private long lastTime = 0; // Для розрахунку deltaTime

    @FXML
    private Spinner<Integer> minGroup;
    @FXML
    private Spinner<Integer> maxGroup;
    @FXML
    private Slider friendCountSlider;
    @FXML
    private Slider enemyCountSlider;
    @FXML
    private Button newSimulationButton;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Pane canvasPane; // Контейнер для холста
    @FXML
    private Canvas mainCanvas; // Сам холст

    // Викликається при завантаженні FXML
    public void initialize() {

        // Прив'язка розміру Canvas до розміру Pane
        mainCanvas.widthProperty().bind(canvasPane.widthProperty());
        mainCanvas.heightProperty().bind(canvasPane.heightProperty());

        // Обробники кнопок Start/Stop
        startButton.setOnAction(e -> {
            lastTime = 0;
            gameLoop.start();
        });
        stopButton.setOnAction(e -> gameLoop.stop());

    }

    // Скидає та створює нову симуляцію
    @FXML
    private void newSimulation() {
        int minGroups = minGroup.getValue();
        int maxGroups = maxGroup.getValue();
        int friendUnitsCount = (int) friendCountSlider.getValue();
        int enemyUnitsCount = (int) friendCountSlider.getValue();

        ClanLeader.reset(); // Скидання Singleton лідера
        units.clear();      // Очищування списку

        List<Unit> friendUnits = UnitGenerationMethods.createUnitsByRandomSeeds(
                mainCanvas, friendUnitsCount, minGroups, maxGroups, SPAWN_RADIUS, FRIEND_COLOR);
        List<Unit> enemyUnits = UnitGenerationMethods.createUnitsByRandomSeeds(
                mainCanvas, enemyUnitsCount, minGroups, maxGroups, SPAWN_RADIUS, ENEMY_COLOR);

        // Встановлення лідера клану (Singleton)
        ClanLeader.getInstance(friendUnits.get(new Random().nextInt(0, friendUnits.size())));

        units.addAll(friendUnits);
        units.addAll(enemyUnits);

        setupGameLoop();          // Налаштування та запуск циклу
    }

    // Шукає найближчого ворожого юніта
    private Unit findNearestEnemy(Unit currentUnit, List<Unit> allUnits) {
        if (!currentUnit.isAlive()) return null;
        Color friendColor = currentUnit.getColor();
        Unit nearestEnemy = null;
        double minDistanceSq = Double.MAX_VALUE; // Квадрат відстані

        for (Unit otherUnit : allUnits) {
            if (!otherUnit.isAlive()) continue;
            // Перевіряємо, що це не той самий юніт і що це ворог (інший колір)
            if (currentUnit != otherUnit && otherUnit.getColor() != friendColor) {
                double distSq = currentUnit.getPosition().distanceSq(otherUnit.getPosition());

                if (distSq < minDistanceSq) {
                    minDistanceSq = distSq;
                    nearestEnemy = otherUnit;
                }
            }
        }
        return nearestEnemy;
    }

    // Обмежує позицію юніта в межах холста
    private Vector2D clampPosition(Vector2D pos) {
        double minX = 0;
        double minY = 0;
        double maxX = mainCanvas.getWidth() - UNIT_SIZE;
        double maxY = mainCanvas.getHeight() - UNIT_SIZE;

        double newX = Math.max(minX, Math.min(pos.getX(), maxX));
        double newY = Math.max(minY, Math.min(pos.getY(), maxY));

        return new Vector2D(newX, newY);
    }

    // Налаштовує ігровий цикл (AnimationTimer)
    private void setupGameLoop() {
        if (gameLoop != null) return;

        gameLoop = new AnimationTimer() {
            // 'now' — це поточний час в наносекундах
            @Override
            public void handle(long now) {
                // Розрахунок часу кадру (delta time)
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                // Час, що минув з попереднього кадру в секундах
                double deltaTime = (now - lastTime) / 1_000_000_000.0;

                // 1. ОНОВЛЕННЯ ЛОГІКИ
                updateGame(deltaTime);

                // 2. ВІДТВОРЕННЯ
                draw();

                lastTime = now;
            }
        };
        // Початковий запуск (якщо не був запущений кнопкою)
        gameLoop.start();
    }

    // --- ЛОГІКА ОНОВЛЕННЯ (UPDATE) ---
    private void updateGame(double deltaTime) {
        // Швидкості для різних типів юнітів
        final double ELF_SPEED = 40.0;
        final double OTHER_SPEED = 30.0;

        attackThrottleTimer += deltaTime;
        double ATTACK_FREQUENCY = 1; // Атака раз на 1 секунду
        boolean shouldAttack = attackThrottleTimer >= ATTACK_FREQUENCY;

        if (shouldAttack) {
            attackThrottleTimer = 0.0;
        }

        // Обробка логіки для кожного юніта
        for (Unit unitA : units) {
            // 1. Пошук найближчого ворога
            Unit targetEnemy = findNearestEnemy(unitA, units);

            if (targetEnemy == null) {
                continue; // Ворогів немає
            }

            // 2. Розрахунок відстаней
            Vector2D currentPos = unitA.getPosition();
            Vector2D targetPos = targetEnemy.getPosition();
            double distanceSq = currentPos.distanceSq(targetPos); // Квадрат відстані

            // Квадрат радіуса атаки
            double attackRange = unitA.getWeapon().getRange();
            double attackRangeSq = attackRange * attackRange;

            // 3. Логіка атаки (якщо в ренжі та настав час)
            if (shouldAttack && distanceSq <= attackRangeSq) {
                if (unitA.attack(targetEnemy)) {
                    // Обробка смерті (якщо необхідно)
                }
            }

            // 4. Логіка руху (рух до ворога, ТІЛЬКИ якщо він поза ренжем)
            if (distanceSq > attackRangeSq) {

                double speed = unitA instanceof Elf ? ELF_SPEED : OTHER_SPEED; // Швидкість (елфи швидші)

                // Розрахунок вектора напрямку та руху
                Vector2D direction = targetPos.subtract(currentPos).normalize();
                Vector2D movementVector = direction.multiply(speed * deltaTime);

                // 5. Застосування руху
                Vector2D nextPosition = unitA.getPosition().add(movementVector);
                Vector2D clampedPosition = clampPosition(nextPosition); // Перевірка границь

                // Встановлення нової позиції
                unitA.setPosition(clampedPosition);

            } else {
                // Юніт знаходиться в ренжі, стоїть на місці
            }
        }
        units.removeIf(x -> !x.isAlive());
    }

    // --- ВІДТВОРЕННЯ (RENDER) ---
    private void draw() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();

        // 1. Очищення фону
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        gc.setFill(Color.TRANSPARENT);
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        // 2. Рисування юнітів
        for (Unit unit : units) {
            if (unit.isAlive()) {
                // Лідер клану виділяється жовтим
                if (unit == ClanLeader.getInstance().getLeader()) {
                    Renderable customRender = new CustomRenderForUnit(unit);
                    customRender.render(gc);
                } else {
                    unit.render(gc); // Звичайне відтворення
                }
            }
        }

        // 3. Виведення статистики
        gc.setFill(FRIEND_COLOR);
        gc.fillText(String.format("Кількість BLUE: %s", units.stream().filter(
                unit -> unit.isAlive() && unit.getColor().equals(FRIEND_COLOR)).count()), 10, 15);

        gc.fillText(String.format("Лідер: %s", ClanLeader.getInstance().getLeader().getName()), 10, 45);

        gc.setFill(ENEMY_COLOR);
        gc.fillText(String.format("Кількість RED: %s", units.stream().filter(
                unit -> unit.isAlive() && unit.getColor().equals(Color.RED)).count()), 10, 30);
    }
}