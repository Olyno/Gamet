package com.olyno.gamet.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Utils;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.types.Team;
import org.bukkit.event.Event;

@Name("Display name of team/game")
@Description("Returns the display name of a team or game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tcreate team \"red\" for game \"Test\"",
        "\t\tset display name of last team created to \"&c[Red]\"",
        "\t\tbroadcast \"The display name of team %last team created% is %game display name of last team created%\""
})
@Since("1.0")

public class ExprDisplayName extends SimplePropertyExpression<Object, String> {

    static {
        register(ExprDisplayName.class, String.class,
                "[the] display name", "object");
    }

    @Override
    public String convert( Object o ) {
        if (o instanceof Game) {
            Game game = (Game) o;
            return game.getDisplayName();
        } else if (o instanceof Team) {
            Team team = (Team) o;
            return team.getDisplayName();
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
                        if (!name.replaceAll(" ", "").equals("")) {
                            team.setDisplayName(Utils.replaceChatStyles((String) delta[0]));
                        }
                    } else if (o instanceof Game) {
                        Game game = (Game) o;
                        String name = (String) delta[0];
                        if (!name.replaceAll(" ", "").equals("")) {
                            game.setDisplayName(Utils.replaceChatStyles((String) delta[0]));
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
