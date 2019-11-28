package com.olyno.gamet.skript.expressions.game.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.gamet.types.Team;
import org.bukkit.event.Event;

@Name("Global Score Point Message of game")
@Description("Returns the global score point message of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset global score message of {_game} to \"%player% scored a point for his team!\"",
        "\t\tbroadcast global score message of {_game}"
})
@Since("1.0")

public class ExprGlobalWinPoint extends SimplePropertyExpression<Team, String> {

    static {
		register(ExprGlobalWinPoint.class, String.class,
                "[the] global (score|win) [point[s]] message", "game");
    }

    @Override
	public String convert(Team team) {
		return team.getWinPointMessage().get("global");
    }

    @Override
    public Class<?>[] acceptChange( final Changer.ChangeMode mode ) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode ) {
		for (Team team : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
					team.getWinPointMessage().put("global", (String) delta[0]);
                    break;
                case RESET:
					team.getWinPointMessage().put("global", "${player} scored a point for the ${team} team!");
                    break;
                case DELETE:
					team.getWinPointMessage().remove("global");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "global score point message";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
