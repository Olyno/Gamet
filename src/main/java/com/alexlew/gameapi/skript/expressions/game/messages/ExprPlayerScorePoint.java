package com.alexlew.gameapi.skript.expressions.game.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Player Score Point Message of game")
@Description("Returns the player score point message of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset player score message of {_game} to \"You scored a point for your team!\"",
        "\t\tbroadcast player score message of {_game}"
})
@Since("1.0")

public class ExprPlayerScorePoint extends SimplePropertyExpression<Game, String> {

    static {
        register(ExprPlayerScorePoint.class, String.class,
                "[the] player (score|win) [point[s]] message", "game");
    }

    @Override
    public String convert( Game game ) {
        return game.getWinPointMessagePlayer();
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
                    game.setWinPointMessagePlayer((String) delta[0]);
                    break;
                case RESET:
                    game.setWinPointMessagePlayer("You scored a point for your ${team} team!");
                    break;
                case DELETE:
                    game.setWinPointMessagePlayer(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "player score point message";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
