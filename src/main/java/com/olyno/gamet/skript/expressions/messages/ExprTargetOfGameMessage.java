package com.olyno.gamet.skript.expressions.messages;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.olyno.gami.enums.GameMessageTarget;
import com.olyno.gami.models.GameMessage;
import org.bukkit.event.Event;

@Name("Target of GameMessage")
@Description("Return the target of a game message. Can be set.")
@Examples({
	"command target:" +
	"\ttrigger:" +
	"\t\tset game target of player join message of team \"red\" from game \"test\" to player"
})
@Since("3.0.0")

public class ExprTargetOfGameMessage extends SimplePropertyExpression<GameMessage, String> {

	static {
		register(ExprTargetOfGameMessage.class, String.class,
			"[game] target", "gamemessage"
		);
	}

	@Override
	public String convert(GameMessage message) {
		return message.getTarget().name().replaceAll("_", " ");
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return new Class[]{String.class};
		}
		return new Class[0];
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		GameMessage message = getExpr().getSingle(e);
		String target = (String) delta[0];
		switch (mode) {
			case SET:
				message.setTarget(GameMessageTarget.valueOf(target.toUpperCase().replaceAll(" ", "_")));
				break;
			default:
				break;
		}
	}

	@Override
	protected String getPropertyName() {
		return "target";
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
}
