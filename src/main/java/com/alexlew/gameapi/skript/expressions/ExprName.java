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

@Name("Name of team/game")
@Description("Returns the name of a team or game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tcreate team \"red\" for game \"Test\"",
        "\t\tbroadcast \"The name of team %last team created% is %team name of last team created%\""
})
@Since("1.0")

public class ExprName extends SimplePropertyExpression<Object, String> {

    static {
        register(ExprName.class, String.class,
                "[the] (game|team) name", "object");
    }

    @Override
    public String convert( Object o ) {
        if (o instanceof Team) {
            Team team = (Team) o;
            return team.getName();
        } else if (o instanceof Game) {
            Game game = (Game) o;
            return game.getName();
        } else {
            return null;
        }

    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
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
                        String name = (String) delta[0];
                        if (name.replaceAll(" ", "").equals("")) {
                            team.setName((String) delta[0]);
                        }
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        String name = (String) delta[0];
                        if (name.replaceAll(" ", "").equals("")) {
                            game.setName((String) delta[0]);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "name";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
