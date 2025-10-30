package com.game.task2.models.chainOfResponsibility;

import com.game.task2.models.command.Command;
import com.game.task2.models.factory.unit.Unit;

public abstract class AbstractCommandHandler implements CommandHandler {
    protected CommandHandler next;

    @Override
    public CommandHandler setNext(CommandHandler next) {
        this.next = next;
        return this;
    }

    @Override
    public boolean handle(Command command, Unit unit) {
        if (next != null) {
            return next.handle(command, unit);
        }
        return false;
    }
}
