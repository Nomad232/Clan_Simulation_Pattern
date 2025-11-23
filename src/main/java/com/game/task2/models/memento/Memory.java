package com.game.task2.models.memento;

import java.util.Stack;

public class Memory<T> {
    private final Stack<Snapshot<T>> history;

    public Memory(){
        history = new Stack<>();
    }

    public void save(Savable<T> data){
        history.push(data.save());
    }

    public void undo(Savable<T> data){
        if (history.isEmpty()) return;
        data.restore(history.pop());
    }
}
