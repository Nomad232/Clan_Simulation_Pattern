package com.game.task2.controllers;

import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.mediator.ClanGroupManager;
import com.game.task2.models.other.Renderable;
import com.game.task2.models.decorator.CustomRenderForUnit;
import com.game.task2.models.mediator.ClanLeader;
import com.game.task2.models.factory.unit.Unit;
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
import java.util.stream.Collectors;

public class StartController {
    private static final Color FRIEND_COLOR = Color.BLUE; // Колір дружньої фракції
    private static final Color ENEMY_COLOR = Color.RED;   // Колір ворожої фракції
    private static final double SPAWN_RADIUS = 60.0;
    private final List<Unit> units = new ArrayList<>(); // Список усіх юнітів у симуляції
    private AnimationTimer gameLoop; // Головний ігровий цикл
    private long lastTime = 0; // Для розрахунку deltaTime

    private ClanGroupManager blueGroupManager;
    private ClanLeader blueLeader;
    private ClanGroupManager redGroupManager;
    private ClanLeader redLeader;

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

    public StartController(){
    }

    // Викликається при завантаженні FXML
    @FXML
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

    // NEW SIMULATION
    @FXML
    private void newSimulation() {
        int minGroups = minGroup.getValue();
        int maxGroups = maxGroup.getValue();
        int friendUnitsCount = (int) friendCountSlider.getValue();
        int enemyUnitsCount = (int) friendCountSlider.getValue();

        blueLeader = null;
        blueGroupManager = null;
        redLeader = null;
        redGroupManager = null;
        units.clear();      // Очищування списку

        List<Unit> friendUnits = UnitGenerationMethods.createUnitsByRandomSeeds(
                mainCanvas, friendUnitsCount, minGroups, maxGroups, SPAWN_RADIUS, FRIEND_COLOR);
        List<Unit> enemyUnits = UnitGenerationMethods.createUnitsByRandomSeeds(
                mainCanvas, enemyUnitsCount, minGroups, maxGroups, SPAWN_RADIUS, ENEMY_COLOR);

        units.addAll(friendUnits);
        units.addAll(enemyUnits);

        setupGameLoop(); // Налаштування та запуск циклу
    }

    // TIMER
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
                update(deltaTime);

                // 2. ВІДТВОРЕННЯ
                draw();

                lastTime = now;
            }
        };
        // Початковий запуск (якщо не був запущений кнопкою)
        gameLoop.start();
    }

    // UPDATE
    private void update(double deltaTime) {
        if (blueGroupManager == null){
            List<Unit> blueUnits = units.stream()
                    .filter(unit -> unit.getColor() == FRIEND_COLOR)
                    .toList();
            blueGroupManager = new ClanGroupManager(blueUnits);
        }

        if (blueLeader == null){
            Unit blueUnit = units.stream()
                    .filter(unit -> unit.getColor() == FRIEND_COLOR)
                    .findFirst()
                    .orElse(new ClanUnit());

            blueLeader = new ClanLeader(blueUnit, blueGroupManager);
        }

        if (redGroupManager == null){
            List<Unit> redUnits = units.stream()
                    .filter(unit -> unit.getColor() == ENEMY_COLOR)
                    .toList();
            redGroupManager = new ClanGroupManager(redUnits);
        }
        if (redLeader == null){
            Unit redUnit = units.stream()
                    .filter(unit -> unit.getColor() == ENEMY_COLOR)
                    .findFirst()
                    .orElse(new ClanUnit());
            redLeader = new ClanLeader(redUnit, redGroupManager);
        }

        redLeader.randomUpdate(units,deltaTime);
        blueLeader.randomUpdate(units, deltaTime);

        units.removeIf(unit -> !unit.isAlive());
    }

    // RENDER
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
                if (unit == blueLeader.getLeader() || unit == redLeader.getLeader()) {
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

        gc.fillText(String.format("Лідер: %s", blueLeader.getLeader().getName()), 10, 45);

        gc.setFill(ENEMY_COLOR);
        gc.fillText(String.format("Кількість RED: %s", units.stream().filter(
                unit -> unit.isAlive() && unit.getColor().equals(Color.RED)).count()), 10, 30);
    }
}