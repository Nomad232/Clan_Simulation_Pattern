package com.game.task2.models.mediator;

import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.unit.Unit;

import java.util.List;
import java.util.Random;


public class ClanLeader {
    private final Unit leader;
    private final GroupManager mediator;
    private double changeStateDelay = 0; // 5 сек
    private static final Random RAND = new Random();
    private State currentState;

    private enum State {
        ATTACK,
        DEFFENCE,
        PATROL
    }

    public ClanLeader(Unit leader, GroupManager mediator) {
        this.leader = leader;
        this.mediator = mediator;
        if (leader instanceof ClanUnit clanUnit) {
            clanUnit.setName(leader.getName() + " Лідер");
        }
        currentState = getRandomState();
    }

    public void randomUpdate(List<Unit> otherUnits, double deltaTime) {
        if(mediator.getUnits().isEmpty()) return;

        changeStateDelay += deltaTime;
        if (changeStateDelay >= 2){
            currentState = getRandomState();
            changeStateDelay = 0;
            System.out.println("Команда: [" + currentState + "] від лідера: " + leader);
        }

        switch (currentState){
            case ATTACK -> mediator.moveOrAttackNearestTarget(otherUnits, deltaTime);
            case PATROL -> mediator.patrol(deltaTime*0.25);
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
