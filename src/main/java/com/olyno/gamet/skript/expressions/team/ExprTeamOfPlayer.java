package com.olyno.gamet.skript.expressions.team;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.olyno.gami.Gami;
import com.olyno.gami.models.Team;

import org.bukkit.entity.Player;

@Name("Team of Player")
@Description("Return the team of the player")
@Examples({
    "command team:" +
    "\ttrigger:" +
    "\t\tsend \"You are in the %team of player% team\""
})
@Since("1.2")

public class ExprTeamOfPlayer extends SimplePropertyExpression<Player, Team> {

    static {
        register(ExprTeamOfPlayer.class, Team.class,
            "team", "player"
        );
    }

    @Override
    protected String getPropertyName() {
        return "team";
    }

    @Override
    public Team convert( Player player ) {
		return Gami.getTeamOfPlayer(player).get();
    }

    @Override
    public Class<? extends Team> getReturnType() {
        return Team.class;
    }
    
}
