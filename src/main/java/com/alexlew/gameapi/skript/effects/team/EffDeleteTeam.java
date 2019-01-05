package com.alexlew.gameapi.skript.effects.team;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.TeamDeleted;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
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
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        team = (Expression<Team>) expr[0];
        return true;
    }

    @Override
    protected void execute( Event e ) {
        if (team.getSingle(e) == null) {
            GameAPI.error("Can't delete a team \"null\"");
        }
        Game.games.get(team.getSingle(e).getGame().getName()).removeTeam(team.getSingle(e).getName());
        lastDeletedTeam = team.getSingle(e);
        new TeamDeleted(team.getSingle(e));
    }

    @Override
    public String toString( Event e, boolean debug ) {
        String teamName = team.getSingle(e) != null ? team.getSingle(e).getName() : "null";
        return "Delete the team \"" + teamName + "\"";
    }
}
