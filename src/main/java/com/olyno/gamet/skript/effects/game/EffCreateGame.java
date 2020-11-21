package com.olyno.gamet.skript.effects.game;

import com.olyno.gami.Gami;
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

@Name("Create Game")
@Description("Create a game.")
@Examples({
	"command create <text>:" +
	"\ttrigger:" +
	"\t\tcreate game arg-1" +
	"\t\tbroadcast \"Game %arg-1% has been created!\""
})
@Since("1.0")

public class EffCreateGame extends Effect {

	public static Game lastCreatedGame;

	static {
		Skript.registerEffect(EffCreateGame.class,
			"create [(the|a)] [mini[(-| )]]game %string%"
		);
	}

	private Expression<String> game;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		game = (Expression<String>) expr[0];
		return true;
	}

	@Override
	protected void execute(Event e) {
		String gameName = game.getSingle(e);
		if (!gameName.replaceAll(" ", "").isEmpty()) {
			Game currentGame = new Game(gameName);
			if (Gami.getGames().containsKey(currentGame.getName())) {
				lastCreatedGame = currentGame;
			}
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "create the game " + game.toString(e, debug);
	}

}
