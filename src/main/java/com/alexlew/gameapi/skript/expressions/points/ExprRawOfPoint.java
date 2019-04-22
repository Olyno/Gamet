package com.alexlew.gameapi.skript.expressions.points;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.gameapi.types.Point;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Raw Points")
@Description("Returns the number of points.")
@Examples({
		"command /points:\n" +
				"\ttrigger:\n" +
				"\t\tcreate game \"test\"\n" +
				"\t\tcreate team \"red\" in game \"test\"\n" +
				"\t\tadd 5 points to team \"red\" in game \"test\"\n" +
				"\t\tset {_points} to raw points of team \"red\" in game \"test\"\n" +
				"\t\tbroadcast \"%{_points}%\""
})
@Since("2.2")

public class ExprRawOfPoint extends SimpleExpression<Integer> {

	static {
		Skript.registerExpression(ExprRawOfPoint.class, Integer.class, ExpressionType.SIMPLE,
				"raw %point%"
		);
	}

	private Expression<Point> points;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		points = (Expression<Point>) expr[0];
		return true;
	}

	@Override
	protected Integer[] get(Event e) {
		try {
			return new Integer[]{points.getSingle(e).getPoints()};
		} catch (NullPointerException e1) {
			return null;
		}
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "Raw points of " + points.getSingle(e).getPoints();
	}
}
