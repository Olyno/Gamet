package com.alexlew.gameapi.skript.expressions.game;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.event.Event;

@Name("Winner of game")
@Description("Returns winner of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset winner of {_game} to team \"red\" of {_game}",
        "\t\tbroadcast \"The winner of %{_game}% game is the red team!"
})
@Since("1.0")

public class ExprWinnerOfGame extends SimplePropertyExpression<Game, Team> {

    static {
        register(ExprWinnerOfGame.class, Team.class,
                "[the] winner", "game");
    }

    @Override
    public Team convert(Game game) {
        return game.getWinner();
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class[]{Team.class};
        }
        return null;
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        for (Game game : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    game.setWinner((Team) delta[0]);
                    break;
                case RESET:
                case DELETE:
                    game.setWinner(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "winner";
    }


    @Override
    public Class<? extends Team> getReturnType() {
        return Team.class;
    }
}
