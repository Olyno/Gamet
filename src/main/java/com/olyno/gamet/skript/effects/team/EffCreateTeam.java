package com.olyno.gamet.skript.effects.team;

import com.olyno.gamet.Gamet;
import com.olyno.gami.models.Game;
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

@Name("Create Team")
@Description("Create a team for a game")
@Examples({
	"command create <text>:" +
	"\ttrigger:" +
	"\t\tcreate team \"red\" for game \"Test\""
})
@Since("1.0")

public class EffCreateTeam extends Effect {

	public static Team lastCreatedTeam;

	static {
		Skript.registerEffect(EffCreateTeam.class,
			"create [(the|a)] team %string% (for|of|from|in) %game%"
		);
	}

	private Expression<String> team;
	private Expression<Game> game;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		team = (Expression<String>) expr[0];
		game = (Expression<Game>) expr[1];
		return true;
	}

	@Override
	protected void execute(Event e) {
		String teamName = team.getSingle(e);
		Game currentGame = game.getSingle(e);
		if (teamName != null && currentGame != null) {
			if (!teamName.replaceAll(" ", "").isEmpty()) {
				if (currentGame.teamExists(team.getSingle(e))) {
					Gamet.error("The team " + teamName + " already exist in the game " + currentGame.getName());
				} else {
					lastCreatedTeam = new Team(teamName);
					currentGame.addTeam(lastCreatedTeam);
				}
			} else {
				Gamet.error("A team can't have a empty name (Current name: " + teamName + ")");
			}
		} else {
			Gamet.error("A team can't be null (Current name: " + teamName + ")");
		}

	}

	@Override
	public String toString(Event e, boolean debug) {
		return "create the team " + team.toString(e, debug) + " in the game " + game.toString(e, debug);
	}

}
