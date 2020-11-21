package com.olyno.gamet.skript.expressions.messages.getters;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;

import com.olyno.gamet.util.skript.MessageExpression;
import com.olyno.gami.enums.GameMessageType;
import com.olyno.gami.models.GameMessage;

@Name("Leave Message of game/team")
@Description("Returns all leave message of a game or team. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tset {_game} to game \"test\"" +
    "\t\tset player leave message of {_game} to \"You left the game %{_game}%!\"" +
    "\t\tsend player leave message of {_game}"
})
@Since("3.0.0")

public class ExprLeaveMessage extends MessageExpression<GameMessage> {

    static {
        register(ExprLeaveMessage.class, GameMessage.class,
            "(exit|leave)", "game/team"
        );
    }

    @Override
    protected GameMessageType getGameMessageType() {
        return GameMessageType.LEAVE;
    }

    @Override
    protected String getPropertyName() {
        return "leave";
    }

    @Override
    public Class<? extends GameMessage> getReturnType() {
        return GameMessage.class;
    }

}
