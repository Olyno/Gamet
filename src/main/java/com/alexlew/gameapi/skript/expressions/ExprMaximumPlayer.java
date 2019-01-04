package com.alexlew.gameapi.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.event.Event;

@Name("Maximum player of team/game")
@Description("Returns the maximum player of a team or game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tcreate team \"red\" for game \"Test\"",
        "\t\tset maximum player of last team created to 5",
        "\t\tbroadcast \"The maximum player count is %gameapi maximum player of last team created%\""
})
@Since("1.0")

public class ExprMaximumPlayer extends SimplePropertyExpression<Object, Integer> {

    static {
        register(ExprMaximumPlayer.class, Integer.class,
                "[the] max[imum] player[s] [count]", "object");
    }

    @Override
    public Integer convert( Object o ) {
        if (o instanceof Team) {
            Team team = (Team) o;
            return team.getMaxPlayer();
        } else if (o instanceof Game) {
            Game game = (Game) o;
            return game.getMaxPlayer();
        } else {
            return 0;
        }
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE ||
                mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
            return new Class[]{Object.class};
        }
        return null;
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        for (Object o : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMaxPlayer((Integer) delta[0]);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMaxPlayer((Integer) delta[0]);
                    }
                    break;
                case ADD:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMaxPlayer(team.getMaxPlayer() + (Integer) delta[0]);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMaxPlayer(game.getMaxPlayer() + (Integer) delta[0]);
                    }
                    break;
                case REMOVE:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMaxPlayer(team.getMaxPlayer() - (Integer) delta[0]);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMaxPlayer(game.getMaxPlayer() - (Integer) delta[0]);
                    }
                    break;
                case RESET:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setMaxPlayer(0);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setMaxPlayer(0);
                    }
                    break;
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
        return "minimum player count";
    }


    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }
}
