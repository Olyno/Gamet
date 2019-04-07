package com.alexlew.gameapi.skript.expressions.points;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Point;

@Name("Game of Points")
@Description("Returns the game of target points.")
@Examples({
		"command /points:\n" +
				"\ttrigger:\n" +
				"\t\tcreate game \"test\"\n" +
				"\t\tcreate team \"red\" in game \"test\"\n" +
				"\t\tadd 5 points to team \"red\" in game \"test\"\n" +
				"\t\tset {_points} to game of points of team \"red\" in game \"test\"\n" +
				"\t\tbroadcast \"%{_points}%\""
})
@Since("2.2")

public class ExprGameOfPoint extends SimplePropertyExpression<Point, Game> {

	static {
		register(ExprGameOfPoint.class, Game.class,
				"game", "point"
		);
	}

	@Override
	public Game convert(Point point) {
		return point.getGame();
	}

	@Override
	protected String getPropertyName() {
		return "game";
	}

	@Override
	public Class<? extends Game> getReturnType() {
		return Game.class;
	}
}
