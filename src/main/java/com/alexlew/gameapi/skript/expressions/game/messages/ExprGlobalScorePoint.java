package com.alexlew.gameapi.skript.expressions.game.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Global Score Point Message of game")
@Description("Returns the global score point message of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset global score message of {_game} to \"%player% scored a point for his team!\"",
        "\t\tbroadcast global score message of {_game}"
})
@Since("1.0")

public class ExprGlobalScorePoint extends SimplePropertyExpression<Game, String> {

    static {
        register(ExprGlobalScorePoint.class, String.class,
                "[the] global (score|win) [point[s]] message", "game");
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
                    game.setWinPointMessageAllPlayers((String) delta[0]);
                    break;
                case RESET:
                    game.setWinPointMessageAllPlayers("${player} scored a point for the ${team} team!");
                    break;
                case DELETE:
                    game.setWinPointMessageAllPlayers(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "global score point message";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
