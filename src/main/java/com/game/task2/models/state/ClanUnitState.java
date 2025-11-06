package com.game.task2.models.state;

import com.game.task2.models.command.Command;
import com.game.task2.models.factory.unit.ClanUnit;


public interface ClanUnitState {
    void handleCommand(ClanUnit unit, Command command);
    String getName();
}
