package com.olyno.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.types.Game;
import com.olyno.types.Team;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Spawn of team/game")
@Description("Returns spawn of a team or game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_team} to team \"red\" from \"test\"",
        "\t\tset spawn of {_team} to location of player",
        "\t\tbroadcast \"The team %{_team}% has spawn in: %spawn of {_team}%\" "
})
@Since("1.0")

public class ExprSpawn extends SimplePropertyExpression<Object, Location> {

    static {
        register(ExprSpawn.class, Location.class,
                "[the] [game] spawn", "object");
    }

    @Override
    public Location convert( Object o ) {
        if (o instanceof Game) {
            Game game = (Game) o;
            return game.getSpawn();
        } else if (o instanceof Team) {
            Team team = (Team) o;
            return team.getSpawn();
        } else {
            return null;
        }
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class[]{Location.class};
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
                        team.setSpawn((Location) delta[0]);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setSpawn((Location) delta[0]);
                    }
                    break;
                case RESET:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setSpawn(null);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setSpawn(null);
                    }
                    break;
                case DELETE:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setSpawn(null);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setSpawn(null);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "spawn";
    }


    @Override
    public Class<? extends Location> getReturnType() {
        return Location.class;
    }
}
