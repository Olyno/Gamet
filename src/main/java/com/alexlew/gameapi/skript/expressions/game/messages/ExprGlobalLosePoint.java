package com.alexlew.gameapi.skript.expressions.game.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Team;
import org.bukkit.event.Event;

@Name("Global Lose Point Message of game")
@Description("Returns the global lose point message of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset global lose point message of {_game} to \"%player% lost the point for his team!\"",
        "\t\tbroadcast global lose point message of {_game}"
})
@Since("1.0")

public class ExprGlobalLosePoint extends SimplePropertyExpression<Team, String> {

    static {
        register(ExprGlobalLosePoint.class, String.class,
				"[the] global lose point[s] message", "team");
    }

    @Override
	public String convert(Team team) {
		return team.getLosePointMessage().get("global");
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
					team.getLosePointMessage().put("global", (String) delta[0]);
                    break;
                case RESET:
					team.getLosePointMessage().put("global", "${player} lost a point for the ${team} team!");
                    break;
                case DELETE:
					team.getLosePointMessage().remove("global");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "global lose point message";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
