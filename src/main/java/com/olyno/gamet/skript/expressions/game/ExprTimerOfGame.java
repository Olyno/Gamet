package com.olyno.gamet.skript.expressions.game;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Timespan;
import com.olyno.gamet.types.Game;
import org.bukkit.event.Event;

@Name("Timer of game")
@Description("Returns timer of a game before it starts. Can be set.")
@Examples({
		"command timer:\n" +
				"\ttrigger:\n" +
				"\t\tset timer of game \"test\" to 20 seconds\n" +
				"\t\tbroadcast \"The game \"\"test\"\" will start in 20 seconds!\""
})
@Since("2.0.4")

public class ExprTimerOfGame extends SimplePropertyExpression<Game, Timespan> {

	static {
		register(ExprTimerOfGame.class, Timespan.class,
				"[the] timer", "game");
	}

	@Override
	public Timespan convert(Game game) {
		return new Timespan(game.getTimer() * 1000);
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
				mode == Changer.ChangeMode.DELETE) {
			return new Class[]{Timespan.class};
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		for (Game game : getExpr().getArray(e)) {
			Timespan time = (Timespan) delta[0];
			switch (mode) {
				case SET:
					game.setTimer(time.getTicks() / 20);
					break;
				case RESET:
					game.setTimer(20);
					break;
				case DELETE:
					game.setTimer(null);
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected String getPropertyName() {
		return "timer";
	}


	@Override
	public Class<? extends Timespan> getReturnType() {
		return Timespan.class;
	}
}
