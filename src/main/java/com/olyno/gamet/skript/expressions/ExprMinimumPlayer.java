package com.olyno.gamet.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.olyno.gami.models.Game;
import com.olyno.gami.models.Team;

import org.bukkit.event.Event;

@Name("Minimum player of team/game")
@Description("Returns the minimum player of a team or game. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tcreate team \"red\" for game \"Test\"" +
    "\t\tset minimum player of last team created to 5" +
    "\t\tbroadcast \"The minimum player count is %gameapi minimum player of last team%\""
})
@Since("1.0")

public class ExprMinimumPlayer extends SimplePropertyExpression<Object, Number> {

    static {
        register(ExprMinimumPlayer.class, Number.class,
            "min[imum] player[s] [count]", "game/team"
        );
    }

    @Override
    public Number convert( Object o ) {
        if (o instanceof Team) {
            return ((Team) o).getMinPlayer();
        } else if (o instanceof Game) {
            return ((Game) o).getMinPlayer();
        }
        return 0;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        switch (mode) {
            case SET:
            case ADD:
            case REMOVE:
            case DELETE:
            case RESET:
                return new Class[]{Number.class};
            default:
                return new Class[0];
        }
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        Integer min = ((Number) delta[0]).intValue();
        for (Object o : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMinPlayer(min);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMinPlayer(min);
                    }
                    break;
                case ADD:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMinPlayer(team.getMinPlayer() + min);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMinPlayer(game.getMinPlayer() + min);
                    }
                    break;
                case REMOVE:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMinPlayer(team.getMinPlayer() - min);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMinPlayer(game.getMinPlayer() - min);
                    }
                    break;
                case RESET:
                case DELETE:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMinPlayer(0);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMinPlayer(0);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "minimum player count";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
    
}
