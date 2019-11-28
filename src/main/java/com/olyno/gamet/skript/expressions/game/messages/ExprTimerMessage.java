package com.olyno.gamet.skript.expressions.game.messages;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import com.olyno.gamet.types.Game;
import org.bukkit.event.Event;

@Name("Timer message of game")
@Description("Returns a timer message of a game. Can be set.")
@Examples({
		"command msg:\n" +
				"\ttrigger:\n" +
				"\t\tset timer message at 2 seconds of game \"test\" to \"Game start in 2 seconds!\""
})
@Since("1.0")

public class ExprTimerMessage extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprTimerMessage.class, String.class, ExpressionType.SIMPLE,
				"[the] timer message[s] [at %-timespan%] of %game%",
				"%game%'s timer message[s] [at %-timespan%]"
		);
	}

	private Expression<Timespan> time;
	private Expression<Game> game;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		time = matchedPattern == 0 ? (Expression<Timespan>) expr[0] : (Expression<Timespan>) expr[1];
		game = matchedPattern == 0 ? (Expression<Game>) expr[1] : (Expression<Game>) expr[0];
		return true;
	}

	@Override
	protected String[] get(Event e) {
		Game currentGame = game.getSingle(e);
		if (time != null) {
			Integer index = time.getSingle(e).getTicks() / 20;
			return currentGame.getTimerMessages().containsKey(index) ?
					new String[]{currentGame.getTimerMessages().get(index)}
					: null;
		} else {
			return currentGame.getTimerMessages().values().toArray(new String[0]);
		}
	}


	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
				mode == Changer.ChangeMode.DELETE) {
			return new Class[]{String.class};
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		switch (mode) {
			case SET:
				game.getSingle(e).getTimerMessages().put(time.getSingle(e).getTicks() / 20, (String) delta[0]);
				break;
			case RESET:
				game.getSingle(e).getTimerMessages().put(time.getSingle(e).getTicks() / 20, "Game starts in ${time} seconds");
				break;
			case DELETE:
				game.getSingle(e).getTimerMessages().remove(time.getSingle(e).getTicks() / 20);
				break;
			default:
				break;
		}
	}

	@Override
	public boolean isSingle() {
		return time != null;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "timer messages of game " + game.getSingle(e);
	}
}
