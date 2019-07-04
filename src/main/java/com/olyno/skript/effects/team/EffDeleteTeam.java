package com.olyno.skript.effects.team;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.olyno.GameAPI;
import com.olyno.types.Team;
import org.bukkit.event.Event;

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
		if (team.getSingle(e) == null) {
			GameAPI.error("Can't delete a team \"null\"");
		}
		lastDeletedTeam = team.getSingle(e).delete();
	}

	@Override
	public String toString(Event e, boolean debug) {
		String teamName = team.getSingle(e) != null ? team.getSingle(e).getName() : "null";
		return "Delete the team \"" + teamName + "\"";
	}
}
