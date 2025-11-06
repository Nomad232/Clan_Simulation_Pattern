package com.game.task2.models.factory.unit;

import com.game.task2.models.command.Command;
import com.game.task2.models.other.ClothingType;
import com.game.task2.models.other.HeightType;
import com.game.task2.models.other.Vector2D;
import com.game.task2.models.other.WeaponType;
import com.game.task2.models.state.ClanUnitState;
import com.game.task2.models.state.NormalState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClanUnit implements Unit {
    protected String name;             // Ім'я юніта
    protected int health;              // Поточне здоров'я
    protected boolean alive;           // Чи живий юніт
    protected Vector2D position;       // Позиція на полі бою
    protected WeaponType weaponType;        // Тип зброї
    protected ClothingType clothingType;
    protected HeightType heightType;

    private ClanUnitState state = new NormalState();
    private ClanUnitState previousState = new NormalState();

    protected Color color;             // Колір для відображення

    public ClanUnit() {
        this.health = 100;
        this.name = "Empty";
        this.alive = true;
        this.position = new Vector2D();
        this.weaponType = WeaponType.NONE;
        this.clothingType = ClothingType.NONE;
        this.heightType = HeightType.AVERAGE;
        this.color = Color.BLUE;
    }

    public ClanUnit(String name, Color color, HeightType heightType, ClothingType clothingType,
                    WeaponType weaponType, Vector2D position, boolean alive, int health) {
        this.name = name;
        this.color = color;
        this.heightType = heightType;
        this.clothingType = clothingType;
        this.weaponType = weaponType;
        this.position = position;
        this.alive = alive;
        this.health = health;
    }

    // Конструктор за замовчуванням кольору (синій)
    public ClanUnit(String name, int health, WeaponType weaponType, ClothingType clothingType,
                    HeightType heightType, Vector2D position) {
        this.name = name;
        this.health = health;
        this.heightType = heightType;
        this.clothingType = clothingType;
        this.weaponType = weaponType;
        this.position = position;
        this.alive = true; // Юніт створюється живим
        color = Color.BLUE; // Колір за замовчуванням
    }

    // Реалізація інтерфейсу Unit:

    @Override
    public void move(Vector2D vector) {
        // Змінює поточну позицію на додавання вказаного вектора
        position = position.add(vector);
    }

    @Override
    public boolean attack(Unit target) {
        if (!isAlive()) {
            // Юніт мертвий і не може атакувати
            //System.out.println(name + " is dead and cannot attack!");
            return false;
        }

        if (target == null || !target.isAlive()) {
            // Ціль недійсна або мертва
            //System.out.println(name + " has no valid target!");
            return false;
        }

        // Юніт не може атакувати сам себе
        if (target.equals(this)) return false;

        // Розрахунок дистанції, дальності та шкоди
        double distance = position.distanceTo(target.getPosition());
        double range = weaponType.getRange();
        int damage = weaponType.getDamage();

        // Перевірка, чи ціль знаходиться в радіусі дії
        if (distance <= range) {
            System.out.println(name + " атакує " + target + " з " + weaponType + " і завдає " + damage + " шкоди!");
            target.takeDamage(damage); // Ціль отримує шкоду
            return true;
        } else {
            // Ціль поза радіусом
            //System.out.println(name + " can't reach the target (" + distance + " > " + range + ")");
            return false;
        }
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public void spawn(Vector2D vector) {
        alive = true;
        position = vector;
        System.out.println(name + " відродився у " + position);
    }

    @Override
    public void destroy() {
        alive = false;
        position = new Vector2D(-1, -1); // Відправляє юніт "поза карту"

        System.out.println(name + " був знищений!");
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void takeDamage(int number) {
        if (!isAlive()) return;
        health -= number;
        if (health <= 0) {
            health = 0;
            destroy(); // Здоров'я закінчилось - знищити юніт
        } else {
            System.out.println(name + " має " + health + " HP, що залишилось.");
        }
    }

    // Геттери (методи для отримання значень полів)
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public ClothingType getClothing() {
        return clothingType;
    }

    @Override
    public HeightType getHeight() {
        return heightType;
    }

    @Override
    public WeaponType getWeaponType() {
        return weaponType;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    // Реалізація патерну Prototype (Клонування)
    @Override
    public Unit clone() {
        try {
            // Поверхневе копіювання
            ClanUnit clonedUnit = (ClanUnit) super.clone();

            // Глибоке копіювання об'єкта позиції
            if (this.position != null) {
                clonedUnit.position = this.position.clone();
            }

            return clonedUnit;

        } catch (CloneNotSupportedException e) {
            // Цього не повинно статися, оскільки Unit має реалізацію Cloneable
            throw new IllegalStateException("Неможливо клонувати юніт.", e);
        }
    }

    @Override
    public void restoreFrom(Unit unit) {
        this.name = unit.getName();
        this.health = unit.getHealth();
        this.heightType = unit.getHeight();
        this.clothingType = unit.getClothing();
        this.weaponType = unit.getWeaponType();
        this.position = unit.getPosition();
        this.alive = unit.isAlive();
        this.color = unit.getColor();
    }

    // Перевизначення методу для зручного виведення в консоль
    @Override
    public String toString() {
        return name + " (" + health + " HP)";
    }

    // Метод для візуалізації юніта на екрані (за допомогою JavaFX GraphicsContext)
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(color);
        // Малює овал на позиції юніта
        gc.fillOval(position.getX(), position.getY(), UNIT_SIZE, UNIT_SIZE);
    }

    // Методи для роботи з кольором
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public void setState(ClanUnitState newState) {
        if (this.state != null) {
            this.previousState = this.state;
        }
        this.state = newState;
    }

    public void handleCommand(Command command) {
        state.handleCommand(this, command);
    }

    public ClanUnitState getPreviousState() {
        return previousState;
    }

    public ClanUnitState getState() {
        return state;
    }
}