package com.olyno.gamet.skript.expressions.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.olyno.gami.models.GameTimerMessage;

import org.bukkit.event.Event;

@Name("Target of GameMessage")
@Description("Return the target of a game message. Can be set.")
@Examples({
	"command target:" +
	"\ttrigger:" +
	"\t\tset game target of player join message of team \"red\" from game \"test\" to player"
})
@Since("3.0.0")

public class ExprTimeOfGameTimerMessage extends SimplePropertyExpression<GameTimerMessage, Number> {

	static {
		register(ExprTimeOfGameTimerMessage.class, Number.class,
			"[game] timer", "gametimermessage"
		);
	}

	@Override
	public Number convert(GameTimerMessage message) {
		return message.getTime();
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return new Class[]{Number.class};
		}
		return new Class[0];
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		GameTimerMessage message = getExpr().getSingle(e);
		Number time = (Number) delta[0];
		switch (mode) {
			case SET:
				message.setTime(time.intValue());
				break;
			default:
				break;
		}
	}

	@Override
	protected String getPropertyName() {
		return "time";
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	
}
