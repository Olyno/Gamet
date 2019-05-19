package com.alexlew.gameapi.skript.expressions.game.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Global Join Message of game")
@Description("Returns the global join message of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset global join message of {_game} to \"You joined the game %{_game}%!\"",
        "\t\tbroadcast global join message of {_game}"
})
@Since("1.0")

public class ExprGlobalJoin extends SimplePropertyExpression<Game, String> {

    static {
        register(ExprGlobalJoin.class, String.class,
                "[the] global (enter|join) message", "game");
    }

    @Override
    public String convert( Game game ) {
		return game.getJoinMessage().get("global");
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
					game.getJoinMessage().put("global", (String) delta[0]);
                    break;
                case RESET:
					game.getJoinMessage().put("global", "${player} joined the game!");
                    break;
                case DELETE:
					game.getJoinMessage().remove("global");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "global join message";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
