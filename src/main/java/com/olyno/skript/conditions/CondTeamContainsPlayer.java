package com.olyno.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.olyno.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Team contains player?")
@Description("Check if a team contains a player")
@Examples({
        "command check:",
        "\ttrigger:",
        "\t\tif team \"red\" of game \"test\" contains player:",
        "\t\t\tsend \"You are in this team!\""
})
@Since("2.0")

public class CondTeamContainsPlayer extends Condition {

    static {
        Skript.registerCondition(CondTeamContainsPlayer.class,
                "%team% contains %player%",
                "%player% is in %team%",
                "%team% does('t| not) contain %player%",
                "%player% is(n't| not) in %team%"
        );
    }

    private Expression<Player> player;
    private Expression<Team> team;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        team = matchedPattern == 0 || matchedPattern == 2 ?
                (Expression<Team>) expr[0] : (Expression<Team>) expr[1];
        player = matchedPattern == 0 || matchedPattern == 2 ?
                (Expression<Player>) expr[1] : (Expression<Player>) expr[0];
        setNegated(matchedPattern == 2 || matchedPattern == 3);
        return true;
    }

    @Override
    public boolean check( Event e ) {
        boolean hasPlayer = team.getSingle(e) != null && player.getSingle(e) != null && team.getSingle(e).hasPlayer(player.getSingle(e));
		return isNegated() != hasPlayer;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "\"" + player.toString(e, debug) + "\"" + " is in team " + "\"" + team.toString(e, debug) + "\"";
    }

}
