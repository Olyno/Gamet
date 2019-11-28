package com.olyno.gamet.skript.expressions.team;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.gamet.types.Team;
import org.bukkit.event.Event;

@Name("Objective of Team")
@Description("Return the objective of the team (how many point the team must to have to win). Can be set.")
@Examples({
		"command objective:",
		"\ttrigger:",
		"\t\tset objective of team of player to 3",
		"\t\tsend \"The new objective of the %team of player% team is 3!\""
})
@Since("2.3")

public class ExprObjective extends SimplePropertyExpression<Team, Integer> {

	static {
		register(ExprObjective.class, Integer.class,
				"objective", "team"
		);
	}

	@Override
	public Integer convert(Team team) {
		return team.getObjective();
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
				mode == Changer.ChangeMode.DELETE) {
			return new Class[]{Integer.class};
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		for (Team team : getExpr().getArray(e)) {
			switch (mode) {
				case SET:
					team.setObjective((Integer) delta[0]);
					break;
				case RESET:
					team.setObjective(5);
					break;
				case DELETE:
					team.setObjective(0);
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected String getPropertyName() {
		return "objective";
	}

	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
}
