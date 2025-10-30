package com.game.task2.models.chainOfResponsibility;

import com.game.task2.models.command.Command;
import com.game.task2.models.factory.unit.Unit;

public interface CommandHandler {
    CommandHandler setNext(CommandHandler next);

    boolean handle(Command command, Unit unit);
}
