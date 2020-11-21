package com.olyno.gamet.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Utils;

import com.olyno.gami.models.Game;
import com.olyno.gami.models.Team;

import org.bukkit.event.Event;

@Name("Name of team/game")
@Description("Returns the name of a team or game. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tcreate team \"red\" for game \"Test\"" +
    "\t\tbroadcast \"The name of team %last team created% is %team name of last team created%\""
})
@Since("1.0")

public class ExprName extends SimplePropertyExpression<Object, String> {

    static {
        register(ExprName.class, String.class,
            "(game|team) name", "game/team"
        );
    }

    @Override
    public String convert( Object o ) {
        if (o instanceof Team) {
            return ((Team) o).getName();
        } else if (o instanceof Game) {
            return ((Game) o).getName();
        } else {
            return null;
        }

    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == ChangeMode.SET) {
            return new Class[]{Object.class};
        }
        return new Class[0];
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        String name = (String) delta[0];
        for (Object o : getExpr().getArray(e)) {
            if (!name.replaceAll(" ", "").equals("")) {
                switch (mode) {
                    case SET:
                        if (o instanceof Team) {
                            Team team = (Team) o;
                            team.setName(Utils.replaceChatStyles(name));
                        } else if (o instanceof Game) {
                            Game game = (Game) o;
                            game.setName(Utils.replaceChatStyles(name));
                        }
                        break;
                    default:
                        break;
                }
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
