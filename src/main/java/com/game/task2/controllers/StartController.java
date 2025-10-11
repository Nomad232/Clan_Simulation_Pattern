package com.game.task2.controllers;

import com.game.task2.models.factory.dwarf.DwarfFactory;
import com.game.task2.models.factory.elf.Elf;
import com.game.task2.models.factory.elf.ElfFactory;
import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.factory.unit.UnitFactory;
import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.warrior.WarriorFactory;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.game.task2.models.factory.Renderable.UNIT_SIZE;

public class StartController {
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

    private List<Unit> units = new ArrayList<>();

    private AnimationTimer gameLoop;

    private double attackThrottleTimer = 0.0;
    final double ATTACK_FREQUENCY = 2; // Атака раз на 1 секунди

    // Переменная для расчета времени между кадрами (delta time)
    private long lastTime = 0;

    public void initialize() {

        mainCanvas.widthProperty().bind(canvasPane.widthProperty());
        mainCanvas.heightProperty().bind(canvasPane.heightProperty());

        startButton.setOnAction(e -> {
            lastTime = 0;
            gameLoop.start();
        });
        stopButton.setOnAction(e -> gameLoop.stop());

    }

    @FXML
    private void newSimulation() {
        units.clear();
        createWarriorsInChunks(10, 10);
        setupGameLoop();
    }

    private void createWarriorsInChunks(int rows, int cols) {
        // Получаем текущие размеры холста (для динамического расчета чанков)
        double totalWidth = mainCanvas.getWidth();
        double totalHeight = mainCanvas.getHeight();

        // 1. Рассчитываем размер чанка
        double chunkWidth = totalWidth / cols; // 400 / 3 = 133.33
        double chunkHeight = totalHeight / rows; // 400 / 3 = 133.33

        UnitFactory warriorFactory = new WarriorFactory();
        UnitFactory elfFactory = new ElfFactory();
        UnitFactory dwarfFactory = new DwarfFactory();
        int count = 0; // Счетчик юнитов

        // 2. Двойной цикл для обхода сетки
        for (int row = 0; row < rows; row++) { // row: 0, 1, 2
            for (int col = 0; col < cols; col++) { // col: 0, 1, 2

                // 3. Расчет центральной позиции чанка

                // Левый край чанка + половина ширины чанка
                double centerX = (col * chunkWidth) + (chunkWidth / 2.0);

                // Верхний край чанка + половина высоты чанка
                double centerY = (row * chunkHeight) + (chunkHeight / 2.0);

                // Для центрирования круга (юнита) нужно вычесть половину его размера (UNIT_SIZE / 2)
                double unitOffsetX = UNIT_SIZE / 2.0;
                double unitOffsetY = UNIT_SIZE / 2.0;

                // Финальная позиция (верхний левый угол, где начнется отрисовка овала)
                Vector2D position = new Vector2D(centerX - unitOffsetX, centerY - unitOffsetY);

                // 4. Создание и добавление юнита

                List<Color> colors = List.of(Color.GREEN, Color.RED, Color.AQUA);

                List<Unit> unitList = List.of(warriorFactory.createUnit(position), elfFactory.createUnit(position),
                        dwarfFactory.createUnit(position));

                Unit rndUnit = unitList.get(new Random().nextInt(unitList.size()));
                rndUnit.setColor(colors.get(new Random().nextInt(unitList.size())));

                units.add(rndUnit.clone());

                count++;
                System.out.printf("Створений воін #%d в чанку (%d, %d) на позиції (%.2f, %.2f)\n",
                        count, col, row, position.getX(), position.getY());
            }
        }
    }

    private void setupGameLoop() {
        if (gameLoop != null) return;

        gameLoop = new AnimationTimer() {
            // 'now' — это текущее время в наносекундах
            @Override
            public void handle(long now) {
                // Рассчитываем время кадра (delta time) для плавного движения
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                // Время, прошедшее с предыдущего кадра в секундах
                double deltaTime = (now - lastTime) / 1_000_000_000.0;

                // 1. ОБНОВЛЕНИЕ ЛОГИКИ
                updateGame(deltaTime);

                // 2. ОТРИСОВКА
                draw();

                lastTime = now;
            }
        };

        gameLoop.start();
    }

    // --- ЛОГИКА ОБНОВЛЕНИЯ (UPDATE) ---
    private void updateGame(double deltaTime) {
        for (Unit unitA : units) {
            boolean canMove = true;
            attackThrottleTimer += deltaTime;
            boolean shouldAttack = attackThrottleTimer >= ATTACK_FREQUENCY;

            if (shouldAttack) {
                attackThrottleTimer = 0.0;
            }

            for (Unit unitB : units) {
                if (shouldAttack && unitA.attack(unitB)) {
                    break;
                }
            }

            if (canMove) {
                if (unitA.getPosition().getX() < mainCanvas.getWidth() - UNIT_SIZE
                        && unitA.getPosition().getY() < mainCanvas.getHeight() - UNIT_SIZE) {
                    if (unitA instanceof Elf) {
                        unitA.move(new Vector2D(40 * deltaTime, 0));
                    } else {
                        unitA.move(new Vector2D(30 * deltaTime, 0));
                    }
                }
            }
        }
    }


    // --- ОТРИСОВКА (RENDER) ---
    private void draw() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();

        // 1. Очистка
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        // Заливаем весь холст новым цветом. Это ваш фон.
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        // 2. Рисование юнитов
        for (Unit unit : units) {
            if (unit.isAlive()) {
                unit.render(gc);
            }
        }

        // Рисуем что-нибудь для примера
        gc.setFill(Color.BLUE);
        gc.fillText(String.format("Кількість unit: %s", units.stream().filter(Unit::isAlive).count()), 20, 30);
    }
}