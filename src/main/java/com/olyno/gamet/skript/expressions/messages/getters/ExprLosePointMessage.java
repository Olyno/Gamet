package com.olyno.gamet.skript.expressions.messages.getters;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;

import com.olyno.gamet.util.skript.MessageExpression;
import com.olyno.gami.enums.GameMessageType;
import com.olyno.gami.models.GameMessage;

@Name("Lose Point Message of game/team")
@Description("Returns all lose point message of a game or team. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tset {_game} to game \"test\"" +
    "\t\tset player lose point message of {_game} to \"You lost a point!\"" +
    "\t\tsend player lose point message of {_game}"
})
@Since("3.0.0")

public class ExprLosePointMessage extends MessageExpression<GameMessage> {

    static {
        register(ExprLosePointMessage.class, GameMessage.class,
            "lose point", "game/team"
        );
    }

    @Override
    protected GameMessageType getGameMessageType() {
        return GameMessageType.LOSE_POINT;
    }

    @Override
    protected String getPropertyName() {
        return "lose point";
    }

    @Override
    public Class<? extends GameMessage> getReturnType() {
        return GameMessage.class;
    }

}
