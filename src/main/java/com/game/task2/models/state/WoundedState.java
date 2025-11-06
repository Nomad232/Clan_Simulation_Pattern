package com.game.task2.models.state;

import com.game.task2.models.command.AttackCommand;
import com.game.task2.models.command.Command;
import com.game.task2.models.command.MoveCommand;
import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.unit.Unit;

import java.util.Random;

public class WoundedState implements ClanUnitState {
    private Random random = new Random();

    @Override
    public void handleCommand(ClanUnit unit, Command command) {
            if (command instanceof MoveCommand || command instanceof AttackCommand) {
                if (random.nextDouble() < 0.2) { // ще раз влучили
                    unit.setState(new OutOfBattleState());
                    System.out.println(unit.getName() + " вибув з бою!");
                    return;
                } else {
                    unit.setState(new RetreatState());
                }

            command.execute();
        }
    }

    @Override
    public String getName() {
        return "Wounded";
    }
}
