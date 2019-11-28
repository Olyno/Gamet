package com.olyno.gamet.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.types.Point;
import com.olyno.gamet.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

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
		,
		"command /game:\n" +
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
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			return new Class[]{Player.class};
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		for (Object o : getExpr().getArray(e)) {
			switch (mode) {
				case SET:
					if (delta[0] instanceof Game) {
						Game game = (Game) delta[0];
						for (Game currentGame : Game.getGames().values()) {
							currentGame.removePlayer((Player) o);
						}
						game.addPlayer((Player) o);
					} else if (delta[0] instanceof Team) {
						Team team = (Team) delta[0];
						for (Game lastGame : Game.getGames().values()) {
							for (Team lastTeam : lastGame.getTeams().values()) {
								lastTeam.removePlayer((Player) o);
							}
						}
						team.addPlayer((Player) o);
					}
					break;
			}
		}
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
