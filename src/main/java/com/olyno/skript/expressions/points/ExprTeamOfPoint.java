package com.olyno.skript.expressions.points;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.types.Point;
import com.olyno.types.Team;

@Name("Team of Points")
@Description("Returns the team of target points.")
@Examples({
		"command /points:\n" +
				"\ttrigger:\n" +
				"\t\tcreate game \"test\"\n" +
				"\t\tcreate team \"red\" in game \"test\"\n" +
				"\t\tadd 5 points to team \"red\" in game \"test\"\n" +
				"\t\tset {_points} to team of points of team \"red\" in game \"test\"\n" +
				"\t\tbroadcast \"%{_points}%\""
})
@Since("2.2")

public class ExprTeamOfPoint extends SimplePropertyExpression<Point, Team> {

	static {
		register(ExprTeamOfPoint.class, Team.class,
				"team", "point"
		);
	}

	@Override
	public Team convert(Point point) {
		return point.getTeam();
	}

	@Override
	protected String getPropertyName() {
		return "team";
	}

	@Override
	public Class<? extends Team> getReturnType() {
		return Team.class;
	}
}
