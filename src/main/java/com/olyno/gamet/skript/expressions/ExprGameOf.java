package com.olyno.gamet.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import java.util.Optional;

import com.olyno.gamet.Gamet;
import com.olyno.gami.Gami;
import com.olyno.gami.models.Game;
import com.olyno.gami.models.Team;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Game of")
@Description("Returns the game of target points, player, or team.")
@Examples({
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
			"[mini[(-| )]]game", "team/player"
		);
	}

	@Override
	public Game convert(Object o) {
		if (o instanceof Team) {
			return ((Team) o).getGame().get();
		} else if (o instanceof Player) {
			return Gami.getGameOfPlayer((Player) o).get();
		}
		return null;
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		switch (mode) {
			case SET:
			case ADD:
			case REMOVE:
				return new Class[]{Object.class};
			default:
				return new Class[0];
		}
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		for (Object o : getExpr().getArray(e)) {
			if (o instanceof Player) {
				Player player = (Player) o;
				Optional<Game> game = Gami.getGameOfPlayer(player);
				if (game.isPresent()) {
					switch (mode) {
						case SET:
							if (delta[0] instanceof Game) {
								game.get().removePlayer(player);
								((Game) delta[0]).addPlayer(player);
							}
							break;
						case ADD:
							if (delta[0] instanceof Player) {
								Player otherPlayer = (Player) delta[0];
								Optional<Game> gameOtherPlayer = Gami.getGameOfPlayer(otherPlayer);
								if (gameOtherPlayer.isPresent()) {
									gameOtherPlayer.get().removePlayer(otherPlayer);
								}
								game.get().addPlayer(otherPlayer);
							}
							break;
						case REMOVE:
							if (delta[0] instanceof Player) {
								Player otherPlayer = (Player) delta[0];
								Optional<Game> gameOtherPlayer = Gami.getGameOfPlayer(otherPlayer);
								if (gameOtherPlayer.isPresent()) {
									gameOtherPlayer.get().removePlayer(otherPlayer);
								}
							}
							break;
						default:
							break;
					}	
				}
			} else if (o instanceof Team) {
				Team team = (Team) o;
				Optional<Game> game = team.getGame();
				if (game.isPresent()) {
					switch (mode) {
						case SET:
							if (delta[0] instanceof Game) {
								game.get().removeTeam(team);
								((Game) delta[0]).addTeam(team);
							}
							break;
						default:
							break;
					}
				}
			} else {
				Gamet.error("You can only set/add/remove a player or a team to a game, not a " + o.getClass().getName());
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
