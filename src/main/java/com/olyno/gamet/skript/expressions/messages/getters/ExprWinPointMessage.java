package com.olyno.gamet.skript.expressions.messages.getters;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;

import com.olyno.gamet.util.skript.MessageExpression;
import com.olyno.gami.enums.GameMessageType;
import com.olyno.gami.models.GameMessage;

@Name("Win Point Message of game/team")
@Description("Returns all win point message of a game or team. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tset {_game} to game \"test\"" +
    "\t\tset player win point message of {_game} to \"You won a point!\"" +
    "\t\tsend player win point message of {_game}"
})
@Since("3.0.0")

public class ExprWinPointMessage extends MessageExpression<GameMessage> {

    static {
        register(ExprWinPointMessage.class, GameMessage.class,
            "win point", "game/team"
        );
    }

    @Override
    protected GameMessageType getGameMessageType() {
        return GameMessageType.WIN_POINT;
    }

    @Override
    protected String getPropertyName() {
        return "win point";
    }

    @Override
    public Class<? extends GameMessage> getReturnType() {
        return GameMessage.class;
    }

}
