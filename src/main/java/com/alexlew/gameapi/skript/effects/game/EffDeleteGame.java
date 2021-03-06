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

@Name("Delete Game")
@Description("Delete a game.")
@Examples({
		"command delete <text>:",
		"\ttrigger:",
		"\t\tdelete game arg-1",
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
		if (game.getSingle(e) == null) {
			GameAPI.error("Can't delete a game \"null\"");
			return;
		}
		Game gameName = game.getSingle(e);
		if (Game.getGames().containsKey(gameName.getName())) {
			lastDeletedGame = Game.getGames().get(gameName.getName()).delete();
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		String gameName = game.getSingle(e) != null ? game.getSingle(e).getName() : "null";
		return "Delete the game \"" + gameName + "\"";
	}

}
