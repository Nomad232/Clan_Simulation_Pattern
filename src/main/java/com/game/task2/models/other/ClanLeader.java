package com.game.task2.models.other;

import com.game.task2.models.decorator.CustomRenderForUnit;
import com.game.task2.models.factory.unit.Unit;
import com.game.task2.models.mediator.ClanGroupManager;
import com.game.task2.models.mediator.GroupManager;
import com.game.task2.models.memento.Savable;
import com.game.task2.models.memento.Snapshot;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class ClanLeader implements Savable<ClanLeader>, Renderable {
    private Unit leader;
    private GroupManager mediator;
    private double changeStateDelay = 0; // 5 сек
    private static final Random RAND = new Random();
    private State currentState;


    @Override
    public Snapshot<ClanLeader> save() {
        GroupManager mediatorCopy = new ClanGroupManager(mediator);
        return new Snapshot<>(new ClanLeader(
                leader.clone(),
                mediatorCopy,
                changeStateDelay,
                currentState
        ));
    }

    @Override
    public void restore(Snapshot<ClanLeader> copy) {
        ClanLeader oldLeader = copy.getData();
        this.leader = oldLeader.leader;
        this.mediator = oldLeader.mediator;
        this.changeStateDelay = oldLeader.changeStateDelay;
        this.currentState = oldLeader.currentState;
    }

    @Override
    public void render(GraphicsContext gc) {
        List<Unit> units = mediator.getUnits();
        CustomRenderForUnit customRender = new CustomRenderForUnit(leader);

        if (leader.isAlive()) {
            customRender.render(gc);
        }

        for (var unit : units) {
            if (!unit.isAlive()) continue;
            unit.render(gc);
        }
    }

    @Override
    public void setColor(Color color) {

    }

    @Override
    public Color getColor() {
        return null;
    }

    public enum State {
        ATTACK,
        DEFFENCE,
        PATROL
    }

    public ClanLeader(Unit leader, GroupManager mediator) {
        this.leader = leader;
        this.mediator = mediator;
        leader.setName(leader.getName() + " Лідер");
        currentState = getRandomState();
    }

    private ClanLeader(Unit leader, GroupManager mediator, double changeStateDelay, State currentState) {
        this.leader = leader;
        this.mediator = mediator;
        this.changeStateDelay = changeStateDelay;
        this.currentState = currentState;
    }

    public void randomUpdate(List<Unit> otherUnits, double deltaTime) {
        List<Unit> liveUnits = mediator.getUnits().stream().filter(Unit::isAlive).toList();

        if (liveUnits.isEmpty()) return;
        if (!leader.isAlive()) {
            Unit newLeader = liveUnits.getFirst();
            newLeader.setName(newLeader.getName() + " новий лідер");
        }

        changeStateDelay += deltaTime;
        if (changeStateDelay >= 2) {
            currentState = getRandomState();
            changeStateDelay = 0;
            System.out.println("Команда: [" + currentState + "] від лідера: " + leader);
        }

        switch (currentState) {
            case ATTACK -> mediator.moveOrAttackNearestTarget(otherUnits, deltaTime);
            case PATROL -> mediator.patrol(deltaTime * 0.25);
            case DEFFENCE -> mediator.moveToTarget(mediator.getUnits().getFirst(), deltaTime);
            default -> throw new RuntimeException("STATE");
        }
    }

    private State getRandomState() {
        State[] states = State.values();
        return states[RAND.nextInt(states.length)];
    }

    public Unit getLeader() {
        return leader;
    }
}
