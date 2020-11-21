package com.olyno.gamet.skript.expressions.messages.getters;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;

import com.olyno.gamet.util.skript.MessageExpression;
import com.olyno.gami.enums.GameMessageType;
import com.olyno.gami.models.GameMessage;

@Name("Join Message of game/team")
@Description("Returns all join message of a game or team. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tset {_game} to game \"test\"" +
    "\t\tset player join messages of {_game} to \"You joined the game %{_game}%!\"" +
    "\t\tsend player join messages of {_game}"
})
@Since("3.0.0")

public class ExprJoinMessage extends MessageExpression<GameMessage> {

    static {
        register(ExprJoinMessage.class, GameMessage.class,
            "join", "game/team"
        );
    }

    @Override
    protected GameMessageType getGameMessageType() {
        return GameMessageType.JOIN;
    }

    @Override
    protected String getPropertyName() {
        return "join message";
    }

    @Override
    public Class<? extends GameMessage> getReturnType() {
        return GameMessage.class;
    }

}
