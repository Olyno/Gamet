package com.olyno.gamet.skript.expressions.messages.getters;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;

import com.olyno.gamet.util.skript.MessageExpression;
import com.olyno.gami.enums.GameMessageType;
import com.olyno.gami.models.GameMessage;

@Name("End Message of game/team")
@Description("Returns all end message of a game or team. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tset {_game} to game \"test\"" +
    "\t\tset global end messages of {_game} to \"The game is finished!\"" +
    "\t\tbroadcast global end messages of {_game}"
})
@Since("3.0.0")

public class ExprEndMessage extends MessageExpression<GameMessage> {

    static {
        register(ExprEndMessage.class, GameMessage.class,
            "end", "game/team"
        );
    }

    @Override
    protected GameMessageType getGameMessageType() {
        return GameMessageType.END;
    }

    @Override
    protected String getPropertyName() {
        return "end";
    }

    @Override
    public Class<? extends GameMessage> getReturnType() {
        return GameMessage.class;
    }

}
