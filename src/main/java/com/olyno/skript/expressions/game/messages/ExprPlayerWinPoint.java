package com.olyno.skript.expressions.game.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.types.Team;
import org.bukkit.event.Event;

@Name("Player Score Point Message of game")
@Description("Returns the player score point message of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset player score message of {_game} to \"You scored a point for your team!\"",
        "\t\tbroadcast player score message of {_game}"
})
@Since("1.0")

public class ExprPlayerWinPoint extends SimplePropertyExpression<Team, String> {

    static {
		register(ExprPlayerWinPoint.class, String.class,
                "[the] player (score|win) [point[s]] message", "game");
    }

    @Override
	public String convert(Team team) {
		return team.getWinPointMessage().get("player");
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
					team.getWinPointMessage().put("player", (String) delta[0]);
                    break;
                case RESET:
					team.getWinPointMessage().put("player", "You scored a point for your ${team} team!");
                    break;
                case DELETE:
					team.getWinPointMessage().remove("player");
					break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "player score point message";
    }


    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
