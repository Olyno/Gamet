package com.alexlew.gameapi.skript.expressions.game;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.alexlew.gameapi.types.Game;
import org.bukkit.entity.Player;

@Name("Game of Player")
@Description("Return the game of the player")
@Examples({
        "command game:",
        "\ttrigger:",
        "\t\tbroadcast \"%player% is in the game %game of player%\""
})
@Since("2.0")

public class ExprGameOfPlayer extends SimplePropertyExpression<Player, Game> {

    static {
        register(ExprGameOfPlayer.class, Game.class,
                "[the] [mini[(-| )]]game", "player"
        );
    }

    @Override
    protected String getPropertyName() {
        return "game";
    }

    @Override
    public Game convert( Player player ) {
        return Game.getGameOfPlayer(player);
    }

    @Override
    public Class<? extends Game> getReturnType() {
        return Game.class;
    }
}
