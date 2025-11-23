package com.game.task2.models.memento;

public interface Savable<T> {
    public Snapshot<T> save();
    public void restore(Snapshot<T> copy);
}
