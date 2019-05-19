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

@Name("Start Game")
@Description("Start a game.")
@Examples({
		"command start:",
		"\ttrigger:",
		"\t\tstart game \"test\":",
		"\t\tgive sword to player",
		"\t\tsend \"Start fight!\""
})
@Since("1.0")

public class EffStartGame extends Effect {

	static {
		Skript.registerEffect(EffStartGame.class,
				"start %game%"
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
		if (currentGame.getTeams().size() > 0) {
			if (currentGame.getSpawn() != null) {
				if (currentGame.getLobby() != null) {
					currentGame.start();
				} else {
					GameAPI.error("Can't start the game \"" + currentGame.getName() + "\": lobby is not set.");
				}
			} else {
				GameAPI.error("Can't start the game \"" + currentGame.getName() + "\": spawn is not set.");
			}
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
