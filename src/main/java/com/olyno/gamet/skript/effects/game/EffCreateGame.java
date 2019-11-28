package com.olyno.gamet.skript.effects.game;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.olyno.gamet.Gamet;
import com.olyno.gamet.types.Game;
import org.bukkit.event.Event;

@Name("Create Game")
@Description("Create a game.")
@Examples({
		"command create <text>:",
		"\ttrigger:",
		"\t\tcreate game arg-1",
		"\t\tbroadcast \"Game %arg-1% has been created!\""
})
@Since("1.0")

public class EffCreateGame extends Effect {

	public static Game lastCreatedGame;

	static {
		Skript.registerEffect(EffCreateGame.class,
				"create [(the|a)] [mini[(-| )]]game %string%",
				"create [(the|a)] [mini[(-| )]]game %string% with [max[imum]] %integer% session[s]"
		);
	}

	private Expression<String> game;
	private Expression<Integer> sessions;
	private Boolean matched;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		game = (Expression<String>) expr[0];
		if (matchedPattern == 1) {
			sessions = (Expression<Integer>) expr[1];
			matched = true;
		}
		return true;
	}

	@Override
	protected void execute(Event e) {
		String gameName = game.getSingle(e);
		Game currentGame = new Game(gameName);
		if (!gameName.replaceAll(" ", "").isEmpty()) {
			if (Game.getGames().containsKey(currentGame.getName())) {
				lastCreatedGame = currentGame;
			}
		} else {
			Gamet.error("A game can't have a empty name (Current name: " + game.getSingle(e) + ")");
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "create the game \"" + game.toString(e, debug) + "\"" + (matched ? " with " + sessions.toString(e, debug) + " sessions" : "");
	}

}
