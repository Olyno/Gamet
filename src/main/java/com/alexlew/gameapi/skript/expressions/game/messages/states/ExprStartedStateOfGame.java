package com.alexlew.gameapi.skript.expressions.game.messages.states;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Started State of game")
@Description("Returns the started state of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset started state of {_game} to \"Arg, the game started :(\"",
        "\t\tbroadcast started state of {_game}"
})
@Since("1.0")

public class ExprStartedStateOfGame extends SimplePropertyExpression<Game, String> {

    static {
        register(ExprStartedStateOfGame.class, String.class,
                "[the] started state", "game");
    }

    @Override
    public String convert( Game game ) {
        return game.getStartedState();
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        for (Game game : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    game.setStartedState((String) delta[0]);
                    break;
                case RESET:
                    game.setStartedState("game started");
                    break;
                case DELETE:
                    game.setStartedState(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "started state";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
