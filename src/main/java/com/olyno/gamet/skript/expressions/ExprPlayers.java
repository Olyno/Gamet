package com.olyno.gamet.skript.expressions;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.olyno.gamet.util.skript.MultiplePropertyExpression;
import com.olyno.gami.models.Game;
import com.olyno.gami.models.Team;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;

@Name("Players list of team/game")
@Description("Returns the players list of a team or game. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tcreate team \"red\" for game \"Test\"" +
    "\t\tadd player to last team created" +
    "\t\tbroadcast \"All players of team %last team created%: %players of last team created%"
})
@Since("1.0")

public class ExprPlayers extends MultiplePropertyExpression<Object, Player> {

    static {
        register(ExprPlayers.class, Player.class,
            "players", "team/game"
        );
    }

    @Override
    protected Player[] convert(Object context) {
        ArrayList<Player> players = new ArrayList<>();
        if (context instanceof Game) {
            Game game = (Game) context;
            players = game.getPlayers();
			return players.toArray(new Player[players.size()]);
        } else if (context instanceof Team) {
            Team team = (Team) context;
            players = team.getPlayers();
        }
        if (players.size() == 0) return new Player[0];
        return players.toArray(new Player[players.size()]);
    }

    @Override
    protected String getPropertyName() {
        return "players";
    }

    @Override
    public Class<? extends Player> getReturnType() {
        return Player.class;
    }

}
