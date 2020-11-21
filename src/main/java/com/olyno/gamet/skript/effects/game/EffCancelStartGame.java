package com.olyno.gamet.skript.effects.game;

import com.olyno.gami.enums.GameState;
import com.olyno.gami.models.Game;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

@Name("Cancel Start Game")
@Description("Cancels the launch of a game.")
@Examples({
	"command cancel:" +
	"\ttrigger:" +
	"\tstart game \"test\":" +
	"\tcancel start of game \"test\"" +
	"\tsend \"Ups wrong command :(\""
})
@Since("1.0")

public class EffCancelStartGame extends Effect {

	static {
		Skript.registerEffect(EffCancelStartGame.class,
			"cancel [the] [start of] %game%"
		);
	}

	private Expression<Game> game;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		game = (Expression<Game>) expr[0];
		return true;
	}

	@Override
	protected void execute(Event e) {
		Game currentGame = game.getSingle(e);
		if (currentGame.getState() == GameState.START) {
			currentGame.setState(GameState.WAITING);
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "cancel start game " + game.toString(e, debug);
	}
}
