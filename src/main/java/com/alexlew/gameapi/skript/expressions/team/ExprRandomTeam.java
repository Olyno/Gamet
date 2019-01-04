package com.alexlew.gameapi.skript.expressions.team;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;

import java.util.Random;

@Name("Random Team")
@Description("Return a random team from a game")
@Examples({
        "command random:",
        "\ttrigger:",
        "\t\tset {_random} to random team of game \"test\"",
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
        Team[] teams = game.getTeams();
        Integer random = new Random().nextInt(teams.length);
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
