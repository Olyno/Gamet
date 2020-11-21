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

@Name("Display name of team/game")
@Description("Returns the display name of a team or game. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tcreate team \"red\" for game \"Test\"" +
    "\t\tset display name of last team created to \"&c[Red]\"" +
    "\t\tbroadcast \"The display name of team %last team created% is %game display name of last team created%\""
})
@Since("1.0")

public class ExprDisplayName extends SimplePropertyExpression<Object, String> {

    static {
        register(ExprDisplayName.class, String.class,
            "display name", "game/team"
        );
    }

    @Override
    public String convert( Object o ) {
        if (o instanceof Game) {
            return ((Game) o).getDisplayName();
        } else if (o instanceof Team) {
            return ((Team) o).getDisplayName();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        switch (mode) {
            case SET:
                return new Class[]{Object.class};
            default:
                return new Class[0];
        }
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        String displayName = (String) delta[0];
        for (Object o : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    if (o instanceof Team) {
                        Team team = (Team) o;
                        team.setDisplayName(displayName);
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        game.setDisplayName(displayName);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "display name";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
