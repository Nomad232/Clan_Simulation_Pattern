package com.game.task2.models.chainOfResponsibility;

import com.game.task2.models.command.AttackCommand;
import com.game.task2.models.command.Command;
import com.game.task2.models.factory.unit.Unit;

public class AttackHandler extends AbstractCommandHandler {
    @Override
    public boolean handle(Command command, Unit unit) {
        if (command instanceof AttackCommand attackCmd) {
            attackCmd.execute();
            return true;
        } else {
            return super.handle(command, unit);
        }
    }
}
