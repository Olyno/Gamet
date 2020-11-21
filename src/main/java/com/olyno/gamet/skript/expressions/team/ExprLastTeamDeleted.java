package com.olyno.gamet.skript.expressions.team;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.olyno.gamet.skript.effects.team.EffDeleteTeam;
import com.olyno.gami.models.Team;

import org.bukkit.event.Event;

@Name("Last team deleted")
@Description("Returns the last team deleted.")
@Examples({
        "command team:" +
        "\ttrigger:" +
        "\t\tdelete team \"red\" from game \"Test\"" +
        "\t\tset {_team} to last team deleted" +
        "\t\tbroadcast \"You deleted the team named %{_team}%\""
})
@Since("1.0")

public class ExprLastTeamDeleted extends SimpleExpression<Team> {

    static {
        Skript.registerExpression(ExprLastTeamDeleted.class, Team.class, ExpressionType.SIMPLE,
            "[the] last team deleted"
        );
    }

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        return true;
    }

    @Override
    protected Team[] get( Event e ) {
        return new Team[]{EffDeleteTeam.lastDeletedTeam};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Team> getReturnType() {
        return Team.class;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "last team deleted";
    }

}
