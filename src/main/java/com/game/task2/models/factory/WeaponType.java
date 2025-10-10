package com.game.task2.models.factory;

public enum WeaponType {
    NONE(5,Renderable.UNIT_SIZE/2.0),
    SWORD(25,Renderable.UNIT_SIZE/1.5),
    BOW(15, Renderable.UNIT_SIZE*2),
    STAFF(20, Renderable.UNIT_SIZE*3);

    private final int damage;
    private final double range;

    WeaponType(int damage, double range) {
        this.damage = damage;
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }
}
