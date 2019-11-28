package com.olyno.gamet.skript.expressions.game.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.gamet.types.Game;
import org.bukkit.event.Event;

@Name("Player Leave Message of game")
@Description("Returns the player leave message of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset player leave  message of {_game} to \"You joined the game %{_game}%!\"",
        "\t\tbroadcast player join message of {_game}"
})
@Since("1.0")

public class ExprPlayerLeave extends SimplePropertyExpression<Game, String> {

    static {
        register(ExprPlayerLeave.class, String.class,
                "[the] player (exit|leave) message", "game");
    }

    @Override
    public String convert( Game game ) {
		return game.getLeaveMessage().get("player");
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
					game.getLeaveMessage().put("player", (String) delta[0]);
                    break;
                case RESET:
					game.getLeaveMessage().put("player", "You left the game ${game}");
                    break;
                case DELETE:
					game.getLeaveMessage().remove("player");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "player leave message";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
