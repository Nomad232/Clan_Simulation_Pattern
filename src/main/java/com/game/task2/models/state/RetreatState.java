package com.game.task2.models.state;

import com.game.task2.models.command.Command;
import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.unit.Unit;

public class RetreatState implements ClanUnitState {
    @Override
    public void handleCommand(ClanUnit unit, Command command) {
        System.out.println(unit.getName() + " відступає...");
        command.execute();

            if (unit.getPreviousState() instanceof WoundedState) {
                unit.setState(new NormalState());
                System.out.println(unit.getName() + " відновився після відступу!");
            }
        // Якщо був поранений, відновлюється

    }

    @Override
    public String getName() {
        return "Retreat";
    }
}
