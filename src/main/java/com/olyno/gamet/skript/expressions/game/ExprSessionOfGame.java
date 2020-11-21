package com.olyno.gamet.skript.expressions.game;

import com.olyno.gamet.util.skript.MultiplePropertyExpression;
import com.olyno.gami.models.Game;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;

@Name("Sessions of game")
@Description("Returns a specific or all sessions of a game.")
@Examples({
    "command test:" +
    "\ttrigger:" +
    "\t\tset {_game} to game \"test\"" +
    "\t\tset {_sessions::*} to sessions of {_game}" +
    "\t\tbroadcast \"The game %{_game}% has some sessions: %{_sessions::*}%\" "
})
@Since("1.0")

public class ExprSessionOfGame extends MultiplePropertyExpression<Game, Game> {

    static {
        register(ExprSessionOfGame.class, Game.class,
            "session[s] [[with id] %-number%]", "game"
        );
    }

    @Override
    public Game[] convert( Game game ) {
        if (!this.usedExpressions.isEmpty()) {
            return new Game[]{ game.getSession((Integer) this.usedExpressions.get(0)).get() };
        }
        return game.getSessions().toArray(new Game[game.getSessions().size()]);
    }

    @Override
    protected String getPropertyName() {
        return "sessions";
    }

    @Override
    public Class<? extends Game> getReturnType() {
        return Game.class;
    }
}
