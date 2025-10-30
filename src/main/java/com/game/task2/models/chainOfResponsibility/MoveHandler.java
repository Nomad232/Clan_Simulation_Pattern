package com.game.task2.models.chainOfResponsibility;

import com.game.task2.models.command.Command;
import com.game.task2.models.command.MoveCommand;
import com.game.task2.models.factory.unit.Unit;

public class MoveHandler extends AbstractCommandHandler {
    @Override
    public boolean handle(Command command, Unit unit) {
        if (command instanceof MoveCommand moveCmd) {
            moveCmd.execute();
            return true;
        } else {
            return super.handle(command, unit);
        }
    }
}
