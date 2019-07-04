package com.olyno.skript.effects.game;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.olyno.GameAPI;
import com.olyno.types.Game;
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
		Game currentGame = game.getSingle(e);
		if (currentGame == null) {
			GameAPI.error("Can't start a \"null\" game");
			return;
		}
		if (currentGame.isSession()) {
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
		} else {
			GameAPI.error("Can't start the game \"" + currentGame.getName() + "\": you can't start a game itself, you must to start a session of this game.");
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "start game \"" + game.toString(e, debug) + "\"";
	}
}
