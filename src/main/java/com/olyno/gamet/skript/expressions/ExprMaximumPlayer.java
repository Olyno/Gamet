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

@Name("Maximum player of team/game")
@Description("Returns the maximum player of a team or game. Can be set.")
@Examples({
        "command test:" +
        "\ttrigger:" +
        "\t\tcreate team \"red\" for game \"Test\"" +
        "\t\tset maximum player of last team created to 5" +
        "\t\tbroadcast \"The maximum player count is %gameapi maximum player of last team created%\""
})
@Since("1.0")

public class ExprMaximumPlayer extends SimplePropertyExpression<Object, Number> {

    static {
        register(ExprMaximumPlayer.class, Number.class,
            "max[imum] player[s] [count]", "game/player");
    }

    @Override
    public Number convert( Object o ) {
        if (o instanceof Team) {
            return ((Team) o).getMaxPlayer();
        } else if (o instanceof Game) {
            return ((Game) o).getMaxPlayer();
        } else {
            return 0;
        }
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
        Integer max = ((Number) delta[0]).intValue();
        for (Object o : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMaxPlayer(max);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMaxPlayer(max);
                    }
                    break;
                case ADD:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMaxPlayer(team.getMaxPlayer() + max);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMaxPlayer(game.getMaxPlayer() + max);
                    }
                    break;
                case REMOVE:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMaxPlayer(team.getMaxPlayer() - max);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMaxPlayer(game.getMaxPlayer() - max);
                    }
                    break;
                case RESET:
                case DELETE:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMaxPlayer(0);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMaxPlayer(0);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "maximum player count";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
