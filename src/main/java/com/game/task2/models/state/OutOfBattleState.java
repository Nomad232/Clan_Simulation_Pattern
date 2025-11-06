package com.game.task2.models.state;

import com.game.task2.models.command.Command;
import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.unit.Unit;

public class OutOfBattleState implements ClanUnitState {
    @Override
    public void handleCommand(ClanUnit unit, Command command) {
        unit.takeDamage(100000);
    }

    @Override
    public String getName() {
        return "OutOfBattle";
    }
}
