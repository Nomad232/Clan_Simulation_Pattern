package com.game.task2.models.factory;

import javafx.scene.canvas.GraphicsContext;

public abstract class AbstractUnit implements Unit {
    protected String name;
    protected int health;
    protected boolean alive;
    protected Vector2D position;
    protected WeaponType weapon;

    public AbstractUnit(String name, int health, WeaponType weapon, Vector2D position) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.position = position;
        this.alive = true;
    }

    @Override
    public void move(Vector2D vector) {
        position = position.add(vector);
    }

    @Override
    public void attack(Unit target) {
        if (!isAlive()) {
            //System.out.println(name + " is dead and cannot attack!");
            return;
        }

        if (target == null || !target.isAlive()) {
            //System.out.println(name + " has no valid target!");
            return;
        }

        if (target.equals(this)) return;

        double distance = position.distanceTo(target.getPosition());
        double range = weapon.getRange();
        int damage = weapon.getDamage();

        if (distance <= range) {
            System.out.println(name + " attacks " + target + " with " + weapon + " for " + damage + " damage!");
            target.takeDamage(damage);
        } else {
            //System.out.println(name + " can't reach the target (" + distance + " > " + range + ")");
        }
    }

    @Override
    public void spawn(Vector2D vector) {
        alive = true;
        position = vector;
        System.out.println(name + " spawned at " + position);
    }

    @Override
    public void destroy() {
        alive = false;
        position = new Vector2D(-1,-1);
        System.out.println(name + " was destroyed!");
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
            destroy();
        } else {
            System.out.println(name + " has " + health + " HP left.");
        }
    }

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

    @Override
    public Unit clone() {
        try {
            AbstractUnit clonedUnit = (AbstractUnit) super.clone();

            if (this.position != null) {
                clonedUnit.position = this.position.clone();
            }

            return clonedUnit;

        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Cannot clone unit.", e);
        }
    }

    @Override
    public String toString() {
        return name + " (" + health + " HP)";
    }
}
