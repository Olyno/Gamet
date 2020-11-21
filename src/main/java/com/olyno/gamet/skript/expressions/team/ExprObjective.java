package com.olyno.gamet.skript.expressions.team;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.olyno.gami.models.Team;

import org.bukkit.event.Event;

@Name("Objective of Team")
@Description("Return the objective of the team (how many point the team must to have to win). Can be set.")
@Examples({
	"command objective:" +
	"\ttrigger:" +
	"\t\tset objective of team of player to 3" +
	"\t\tsend \"The new objective of the %team of player% team is 3!\""
})
@Since("2.3")

public class ExprObjective extends SimplePropertyExpression<Team, Number> {

	static {
		register(ExprObjective.class, Number.class,
			"(objective|goal)", "team"
		);
	}

	@Override
	public Number convert(Team team) {
		return team.getGoal();
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		switch (mode) {
			case SET:
			case RESET:
			case DELETE:
				return new Class[]{Number.class};
			default:
				return new Class[0];
		}
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		for (Team team : getExpr().getArray(e)) {
			switch (mode) {
				case SET:
					team.setGoal(((Number) delta[0]).intValue());
					break;
				case RESET:
					team.setGoal(5);
					break;
				case DELETE:
					team.setGoal(0);
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
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	
}
