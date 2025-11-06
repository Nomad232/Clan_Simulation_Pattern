package com.game.task2.models.state;

import com.game.task2.models.command.AttackCommand;
import com.game.task2.models.command.Command;
import com.game.task2.models.command.MoveCommand;
import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.unit.Unit;

import java.util.Random;

public class NormalState implements ClanUnitState {
    private Random random = new Random();

    @Override
    public void handleCommand(ClanUnit unit, Command command) {
            if (command instanceof MoveCommand || command instanceof AttackCommand) {
                if (random.nextDouble() < 0.003) { // 0.03% шанс поранення
                    unit.setState(new WoundedState());
                    System.out.println(unit.getName() + " поранений!");
                }
        }

        command.execute();
    }

    @Override
    public String getName() {
        return "Normal";
    }
}
