package com.alexlew.gameapi.skript.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Point;
import com.alexlew.gameapi.types.Team;
import org.bukkit.entity.Player;

@Name("Game of")
@Description("Returns the game of target points, player, or team.")
@Examples({
		"command /points:\n" +
				"\ttrigger:\n" +
				"\t\tcreate game \"test\"\n" +
				"\t\tcreate team \"red\" in game \"test\"\n" +
				"\t\tadd 5 points to team \"red\" in game \"test\"\n" +
				"\t\tset {_points} to game of points of team \"red\" in game \"test\"\n" +
				"\t\tbroadcast \"%{_points}%\""
		, "command /game:\n" +
		"\ttrigger:\n" +
		"\t\tcreate game \"test\"\n" +
		"\t\tcreate team \"red\" in game \"test\"\n" +
		"\t\tbroadcast game name of game of last team created"
})
@Since("2.2")

public class ExprGameOf extends SimplePropertyExpression<Object, Game> {

	static {
		register(ExprGameOf.class, Game.class,
				"[the] [mini[(-| )]]game", "object"
		);
	}

	@Override
	public Game convert(Object o) {
		if (o instanceof Point) {
			return ((Point) o).getGame();
		} else if (o instanceof Team) {
			return ((Team) o).getGame();
		} else if (o instanceof Player) {
			return Game.getGameOfPlayer((Player) o);
		}
		return null;
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
