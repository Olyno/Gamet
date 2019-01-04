package com.alexlew.gameapi.skript.expressions.game.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Global LeaveMessage of game")
@Description("Returns the global leave message of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset global leave message of {_game} to \"You joined the game %{_game}%!\"",
        "\t\tbroadcast global leave message of {_game}"
})
@Since("1.0")

public class ExprGlobalLeave extends SimplePropertyExpression<Game, String> {

    static {
        register(ExprGlobalLeave.class, String.class,
                "[the] global (exit|leave) message", "game");
    }

    @Override
    public String convert( Game game ) {
        return game.getLeaveMessageAllPlayers();
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
                    game.setLeaveMessageAllPlayers((String) delta[0]);
                    break;
                case RESET:
                    game.setLeaveMessageAllPlayers("${player} left the game!");
                    break;
                case DELETE:
                    game.setLeaveMessageAllPlayers(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "global leave message";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
