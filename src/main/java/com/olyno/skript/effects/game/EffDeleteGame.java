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
        Game currentGame = game.getSingle(e);
        if (currentGame == null) {
            GameAPI.error("Can't start a \"null\" game");
			return;
		}
        if (Game.getGames().containsKey(currentGame.getName())) {
            lastDeletedGame = Game.getGames().get(currentGame.getName()).delete();
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
        return "delete the game \"" + game.toString(e, debug) + "\"";
	}

}
