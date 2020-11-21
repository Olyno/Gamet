package com.olyno.gamet.skript.effects.team;

import com.olyno.gami.models.Team;

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

@Name("Delete Team")
@Description("Delete a team from a game")
@Examples({
	"command delete <text>:" +
	"\ttrigger:" +
	"\t\tdelete team \"red\" from game \"Test\""
})
@Since("1.0")

public class EffDeleteTeam extends Effect {

	public static Team lastDeletedTeam;

	static {
		Skript.registerEffect(EffDeleteTeam.class,
			"delete %team%"
		);
	}

	private Expression<Team> team;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		team = (Expression<Team>) expr[0];
		return true;
	}

	@Override
	protected void execute(Event e) {
		Team currentTeam = team.getSingle(e);
		lastDeletedTeam = currentTeam;
		currentTeam.delete();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "delete the team " + team.toString(e, debug);
	}
}
