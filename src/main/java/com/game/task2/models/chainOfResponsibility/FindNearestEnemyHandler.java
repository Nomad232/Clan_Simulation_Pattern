package com.game.task2.models.chainOfResponsibility;

import com.game.task2.models.command.Command;
import com.game.task2.models.command.FindNearestEnemyCommand;
import com.game.task2.models.factory.unit.Unit;

public class FindNearestEnemyHandler extends  AbstractCommandHandler {
    @Override
    public boolean handle(Command command, Unit unit) {
        if (command instanceof FindNearestEnemyCommand findCmd) {
            findCmd.execute();
            return findCmd.getNearestTarget() != null;
        } else {
            return super.handle(command, unit);
        }
    }
}
