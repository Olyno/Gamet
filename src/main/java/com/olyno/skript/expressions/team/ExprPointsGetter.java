package com.olyno.skript.expressions.team;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.types.Point;
import com.olyno.types.Team;

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
