package com.olyno.gamet.skript.expressions.game;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.gami.models.Game;
import com.olyno.gami.models.Team;

import org.bukkit.event.Event;

@Name("Winner of game")
@Description("Returns winner of a game. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tset {_game} to game \"test\"" +
    "\t\tset winner of {_game} to team \"red\" of {_game}" +
    "\t\tbroadcast \"The winner of %{_game}% game is the red team!"
})
@Since("1.0")

public class ExprWinnerOfGame extends SimplePropertyExpression<Game, Team> {

    static {
        register(ExprWinnerOfGame.class, Team.class,
            "winner", "game"
        );
    }

    @Override
    public Team convert(Game game) {
        return game.getWinner();
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        switch (mode) {
            case SET:
            case DELETE:
                return new Class[]{Team.class};
            default:
                return new Class[0];
        }
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        for (Game game : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    game.setWinner((Team) delta[0]);
                    break;
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
