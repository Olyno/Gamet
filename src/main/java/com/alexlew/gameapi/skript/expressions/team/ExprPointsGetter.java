package com.alexlew.gameapi.skript.expressions.team;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Point;
import com.alexlew.gameapi.types.Team;

public class ExprPointsGetter extends SimplePropertyExpression<Team, Point> {

	static {
		register(ExprPointsGetter.class, Point.class,
				"point[s]", "team"
		);
	}

	@Override
	public Point convert(Team team) {
		return team.getPoints();
	}

	@Override
	protected String getPropertyName() {
		return "points";
	}

	@Override
	public Class<? extends Point> getReturnType() {
		return Point.class;
	}
}
