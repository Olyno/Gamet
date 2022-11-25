package com.olyno.gamet.skript.expressions.team;

import java.util.ArrayList;
import java.util.Random;

import com.olyno.gami.models.Game;
import com.olyno.gami.models.Team;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

@Name("Random Team")
@Description("Return a random team from a game")
@Examples({
    "command random:" +
    "\ttrigger:" +
    "\t\tset {_random} to random team of game \"test\"" +
    "\t\tsend \"You joined the team %{_random}%\""
})

public class ExprRandomTeam extends SimplePropertyExpression<Game, Team> {

    static {
        register(ExprRandomTeam.class, Team.class,
            "random team", "game"
        );
    }

    @Override
    public Team convert( Game game ) {
        ArrayList<Team> teamsList = game.getTeams();
        Integer teamsListSize = teamsList.size();
        if (teamsListSize == 0) return null;
		Team[] teams = teamsList.toArray(new Team[teamsListSize]);
		Integer random = new Random().nextInt(teamsListSize);
        return teams[random];
    }

    @Override
    protected String getPropertyName() {
        return "random team";
    }

    @Override
    public Class<? extends Team> getReturnType() {
        return Team.class;
    }
    
}
