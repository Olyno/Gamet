package com.olyno.gamet.skript.effects.game;

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

@Name("Create Session")
@Description("Create a game session.")
@Examples({
	"command session:" +
	"\ttrigger:" +
	"\t\tcreate session for game \"test\"" +
	"\t\tbroadcast \"Session has been created!\""
})
@Since("3.0.0")

public class EffCreateSession extends Effect {

	public static Game lastCreatedSession;

	static {
		Skript.registerEffect(EffCreateSession.class,
			"create [(the|a)] [[mini[(-| )]]game] session for %game%"
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
		Game g = game.getSingle(e);
		lastCreatedSession = g.createSession().get();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "create the game session for " + game.toString(e, debug);
	}

}
