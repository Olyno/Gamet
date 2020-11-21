package com.olyno.gamet.skript.expressions.game;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.olyno.gami.models.Game;

import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Lobby of game")
@Description("Returns lobby of a game. Can be set.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tset {_game} to game \"test\"" +
    "\t\tset lobby of {_game} to location of player" +
    "\t\tbroadcast \"The game %{_game}% has lobby in: %lobby of {_game}%\" "
})
@Since("1.0")

public class ExprLobbyOfGame extends SimplePropertyExpression<Game, Location> {

    static {
        register(ExprLobbyOfGame.class, Location.class,
            "lobby", "game"
        );
    }

    @Override
    public Location convert( Game game ) {
        return (Location) game.getLobby();
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        switch (mode) {
            case SET:
            case RESET:
            case DELETE:
                return new Class[]{Location.class};
            default:
                return new Class[0];
        }
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        for (Game game : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    game.setLobby((Location) delta[0]);
                    break;
                case RESET:
                    game.setLobby(null);
                    break;
                case DELETE:
                    game.setLobby(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "lobby";
    }


    @Override
    public Class<? extends Location> getReturnType() {
        return Location.class;
    }
}
