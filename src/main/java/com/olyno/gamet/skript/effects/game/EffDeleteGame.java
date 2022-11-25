package com.olyno.gamet.skript.effects.game;

import org.bukkit.event.Event;

import com.olyno.gami.Gami;
import com.olyno.gami.models.Game;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

@Name("Delete Game")
@Description("Delete a game.")
@Examples({
	"command delete <text>:" +
	"\ttrigger:" +
	"\t\tdelete game arg-1" +
	"\t\tbroadcast \"Game %arg-1% has been deleted!\""
})
@Since("1.0")

public class EffDeleteGame extends Effect {

	public static Game lastDeletedGame;

	static {
		Skript.registerEffect(EffDeleteGame.class,
			"delete %game%"
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
		Gami.getGameByName(currentGame.getName()).ifPresent(gameFound -> {
			lastDeletedGame = gameFound;
			gameFound.delete();
		});
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "delete the game " + game.toString(e, debug);
	}

}
