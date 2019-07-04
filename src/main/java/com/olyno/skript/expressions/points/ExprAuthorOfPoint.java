package com.olyno.skript.expressions.points;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.types.Point;
import org.bukkit.entity.Player;

@Name("Author of Points")
@Description("Returns the author of target points.")
@Examples({
		"command /points:\n" +
				"\ttrigger:\n" +
				"\t\tcreate game \"test\"\n" +
				"\t\tcreate team \"red\" in game \"test\"\n" +
				"\t\tadd 5 points to team \"red\" in game \"test\"\n" +
				"\t\tset {_points} to author of points of team \"red\" in game \"test\"\n" +
				"\t\tbroadcast \"%{_points}%\""
})
@Since("2.2")

public class ExprAuthorOfPoint extends SimplePropertyExpression<Point, Player> {

	static {
		register(ExprAuthorOfPoint.class, Player.class,
				"author", "point"
		);
	}

	@Override
	public Player convert(Point point) {
		return point.getWho();
	}

	@Override
	protected String getPropertyName() {
		return "author";
	}

	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}
}
