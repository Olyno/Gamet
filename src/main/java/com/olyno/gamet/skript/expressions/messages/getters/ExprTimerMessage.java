package com.olyno.gamet.skript.expressions.messages.getters;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;

import com.olyno.gamet.util.skript.MessageExpression;
import com.olyno.gami.enums.GameMessageType;
import com.olyno.gami.models.GameTimerMessage;

@Name("Lose Point Message of game/team")
@Description("Returns all lose point message of a game or team. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tset {_game} to game \"test\"" +
    "\t\tset player timer message of {_game} at 1 to \"Ready?\"" +
    "\t\tsend player timer message of {_game}"
})
@Since("3.0.0")

public class ExprTimerMessage extends MessageExpression<GameTimerMessage> {

    static {
        register(ExprTimerMessage.class, GameTimerMessage.class,
            "timer", "game/team"
        );
    }

    @Override
    protected GameMessageType getGameMessageType() {
        return GameMessageType.TIMER;
    }

    @Override
    protected String getPropertyName() {
        return "timer";
    }

    @Override
    public Class<? extends GameTimerMessage> getReturnType() {
        return GameTimerMessage.class;
    }

}
