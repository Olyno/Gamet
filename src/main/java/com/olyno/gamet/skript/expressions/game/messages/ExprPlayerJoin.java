package com.olyno.gamet.skript.expressions.game.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.gamet.types.Game;
import org.bukkit.event.Event;

@Name("Player Join Message of game")
@Description("Returns the player join message of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset player join message of {_game} to \"You joined the game %{_game}%!\"",
        "\t\tbroadcast player join message of {_game}"
})
@Since("1.0")

public class ExprPlayerJoin extends SimplePropertyExpression<Game, String> {

    static {
        register(ExprPlayerJoin.class, String.class,
                "[the] player (enter|join) message", "game");
    }

    @Override
    public String convert( Game game ) {
		return game.getJoinMessage().get("player");
    }

    @Override
    public Class<?>[] acceptChange( final Changer.ChangeMode mode ) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode ) {
        for (Game game : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
					game.getJoinMessage().put("player", (String) delta[0]);
                    break;
                case RESET:
					game.getJoinMessage().put("player", "You joined the game ${game}");
                    break;
                case DELETE:
					game.getJoinMessage().remove("player");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "player join message";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
