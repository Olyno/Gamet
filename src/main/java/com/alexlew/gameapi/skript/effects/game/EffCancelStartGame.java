package com.alexlew.gameapi.skript.effects.game;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Cancel Start Game")
@Description("Cancels the launch of a game.")
@Examples({
		"command cancel:",
		"\ttrigger:",
		"\tstart game \"test\":",
		"\tcancel start of game \"test\"",
		"\tsend \"Ups wrong command :(\""
})
@Since("1.0")

public class EffCancelStartGame extends Effect {

	static {
		Skript.registerEffect(EffCancelStartGame.class,
				"cancel [the] start of %game%",
				"%game%'s cancel start"
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
		if (game.getSingle(e) == null) {
			GameAPI.error("Can't start a game \"null\"");
			return;
		}
		Game currentGame = game.getSingle(e);
		if (currentGame.getState() == 1) {
			currentGame.setState(0);
		} else {
			GameAPI.error("Can't start the game \"" + currentGame.getName() + "\": you don't have any team created.");
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		String gameName = game.getSingle(e) != null ? game.getSingle(e).getName() : "null";
		return "Start game \"" + gameName + "\"";
	}
}
