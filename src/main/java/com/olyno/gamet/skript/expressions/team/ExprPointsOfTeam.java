package com.olyno.gamet.skript.expressions.team;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;

import java.util.LinkedList;

import com.olyno.gamet.util.skript.MultiplePropertyExpression;
import com.olyno.gami.models.Point;
import com.olyno.gami.models.Team;

import org.bukkit.event.Event;

@Name("Points of Team")
@Description("Return the points of the team. Can be set.")
@Examples({
	"command objective:" +
	"\ttrigger:" +
	"\t\tadd 1 point to points of team \"red\" from game \"test\"" +
	"\t\tsend \"1 point has been added to red team!\""
})
@Since("2.3")

public class ExprPointsOfTeam extends MultiplePropertyExpression<Team, Point> {

	static {
		register(ExprPointsOfTeam.class, Point.class,
			"points", "team"
		);
	}

	@Override
	public Point[] convert(Team team) {
		LinkedList<Point> points = team.getPoints();
		if (points.size() == 0) return new Point[0];
		return points.toArray(new Point[points.size()]);
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		switch (mode) {
			case SET:
			case ADD:
			case REMOVE:
			case RESET:
			case REMOVE_ALL:
				return new Class[]{Point.class};
			default:
				return new Class[0];
		}
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		Team team = getExpr().getSingle(e);
		switch (mode) {
			case SET:
				team.setPoints((Point) delta[0]);
				break;
			case ADD:
				team.addPoints((Point) delta[0]);
				break;
			case REMOVE:
				team.removePoints((Point) delta[0]);
				break;
			case RESET:
			case REMOVE_ALL:
				team.setPoints(0);
				break;
			default:
				break;
		}
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
