package com.alexlew.gameapi.skript.expressions.game.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Player Lose Point Message of game")
@Description("Returns the player lose point message of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset player lose point message of {_game} to \"You lost the point for your team!\"",
        "\t\tbroadcast player lose point message of {_game}"
})
@Since("1.0")

public class ExprPlayerLosePoint extends SimplePropertyExpression<Game, String> {

    static {
        register(ExprPlayerLosePoint.class, String.class,
                "[the] player lose point[s] message", "game");
    }

    @Override
    public String convert( Game game ) {
        return game.getWinPointMessageAllPlayers();
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
                    game.setLosePointMessageAllPlayers((String) delta[0]);
                    break;
                case RESET:
                    game.setLosePointMessageAllPlayers("${player} lost a point for the ${team} team!");
                    break;
                case DELETE:
                    game.setLosePointMessageAllPlayers(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "global lose point message";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
