package com.game.task2.models.util;

import com.game.task2.models.factory.dwarf.DwarfFactory;
import com.game.task2.models.factory.elf.ElfFactory;
import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.factory.unit.UnitFactory;
import com.game.task2.models.factory.warrior.WarriorFactory;
import com.game.task2.models.other.Vector2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UnitGenerationMethods {
    private UnitGenerationMethods() {
    }

    public static List<Unit> createUnitsByRandomSeeds(Canvas mainCanvas, int countUnits, int minGroups,
                                               int maxGroups, double spawnRadius, Color color) {
        Random rnd = new Random();
        List<Unit> units = new ArrayList<>(countUnits);

        double totalWidth = mainCanvas.getWidth();
        double totalHeight = mainCanvas.getHeight();

        // Список фабрик для різних типів юнітів
        List<UnitFactory> factories = List.of(new WarriorFactory(), new ElfFactory(), new DwarfFactory());
        int rndGroups = rnd.nextInt(minGroups, maxGroups);
        List<Vector2D> seeds = new ArrayList<>(rndGroups); // "Зерна" для спавну груп


        // Знаходимо зерна для спавну
        for (int i = 0; i < rndGroups; i++) {
            Vector2D vector = new Vector2D(rnd.nextInt(50, (int) totalWidth), rnd.nextInt(50, (int) totalHeight));
            seeds.add(vector);
        }

        // Розподіл юнітів по групах
        for (int i = 0; i < rndGroups; i++) {
            int unitsInGroup;
            if (i == rndGroups - 1) {
                unitsInGroup = countUnits; // оставшиеся единицы
            } else {
                unitsInGroup = rnd.nextInt(0, countUnits - (rndGroups - i - 1) + 1);
                countUnits -= unitsInGroup;
            }

            Vector2D seed = seeds.get(i);
            var factory = factories.get(rnd.nextInt(0, factories.size())); // Випадкова фабрика

            for (int k = 0; k < unitsInGroup; k++) {
                // Випадкове зміщення навколо seed
                double angle = rnd.nextDouble() * 2 * Math.PI;
                double distance = rnd.nextDouble() * spawnRadius;

                double offsetX = Math.cos(angle) * distance;
                double offsetY = Math.sin(angle) * distance;

                Vector2D spawnPos = new Vector2D(seed.getX() + offsetX, seed.getY() + offsetY);

                Unit newUnit = factory.createUnit(spawnPos); // Створення юніта
                newUnit.setColor(color);
                units.add(newUnit);
            }
        }

        return units;
    }
}
