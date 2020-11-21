package com.olyno.gamet.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.olyno.gami.models.Game;
import com.olyno.gami.models.Team;

import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Spawn of team/game")
@Description("Returns spawn of a team or game. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tset {_team} to team \"red\" from \"test\"" +
    "\t\tset spawn of {_team} to location of player" +
    "\t\tbroadcast \"The team %{_team}% has spawn in: %spawn of {_team}%\" "
})
@Since("1.0")

public class ExprSpawn extends SimplePropertyExpression<Object, Location> {

    static {
        register(ExprSpawn.class, Location.class,
            "[the] [game] spawn", "game/team"
        );
    }

    @Override
    public Location convert( Object o ) {
        if (o instanceof Game) {
            return (Location) ((Game) o).getSpawn();
        } else if (o instanceof Team) {
            return (Location) ((Team) o).getSpawn();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        switch (mode) {
            case SET:
            case DELETE:
                return new Class[]{Location.class};
            default:
                return new Class[0];
        }
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
