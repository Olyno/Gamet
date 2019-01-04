package com.alexlew.gameapi.skript.expressions.game.messages.states;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Waiting State of game")
@Description("Returns the waiting state of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset waiting state of {_game} to \"Wait!!!\"",
        "\t\tbroadcast \"You need to %waiting state of {_game}%\" "
})
@Since("1.0")

public class ExprWaitingStateOfGame extends SimplePropertyExpression<Game, String> {

    static {
        register(ExprWaitingStateOfGame.class, String.class,
                "[the] waiting state", "game");
    }

    @Override
    public String convert( Game game ) {
        return game.getWaitingState();
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
                    game.setWaitingState((String) delta[0]);
                    break;
                case RESET:
                    game.setWaitingState("waiting players");
                    break;
                case DELETE:
                    game.setWaitingState(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "waiting state";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
