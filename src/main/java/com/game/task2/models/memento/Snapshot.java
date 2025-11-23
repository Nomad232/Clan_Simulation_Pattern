package com.game.task2.models.memento;

import com.game.task2.models.factory.unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class Snapshot<T> {
    // todo: generic types?
    private final T copies;

    public Snapshot(T data){
        copies = data;
    }

    public T getData(){
        return copies;
    }
}
