package com.game.task2.models.factory.unit;

import com.game.task2.models.factory.Vector2D;
import com.game.task2.models.factory.WeaponType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClanUnit implements Unit {
    // Захищені поля (доступні в підкласах)
    protected String name;             // Ім'я юніта
    protected int health;              // Поточне здоров'я
    protected boolean alive;           // Чи живий юніт
    protected Vector2D position;       // Позиція на полі бою
    protected WeaponType weapon;        // Тип зброї
    protected Color color;             // Колір для відображення

    // Конструктор з повним набором параметрів
    public ClanUnit(String name, int health, WeaponType weapon, Vector2D position, Color color) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.position = position;
        this.alive = true; // Юніт створюється живим
        this.color = color;
    }

    // Конструктор за замовчуванням кольору (синій)
    public ClanUnit(String name, int health, WeaponType weapon, Vector2D position) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
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
        double range = weapon.getRange();
        int damage = weapon.getDamage();

        // Перевірка, чи ціль знаходиться в радіусі дії
        if (distance <= range) {
            System.out.println(name + " атакує " + target + " з " + weapon + " і завдає " + damage + " шкоди!");
            target.takeDamage(damage); // Ціль отримує шкоду
            return true;
        } else {
            // Ціль поза радіусом
            //System.out.println(name + " can't reach the target (" + distance + " > " + range + ")");
            return false;
        }
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
        position = new Vector2D(-1,-1); // Відправляє юніт "поза карту"

        System.out.println(name + " був знищений!");
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void takeDamage(int number) {
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

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public WeaponType getWeapon() {
        return weapon;
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
        gc.fillOval(position.getX(), position.getY(), UNIT_SIZE,UNIT_SIZE);
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
}