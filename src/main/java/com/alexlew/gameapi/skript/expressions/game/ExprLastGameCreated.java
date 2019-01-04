package com.alexlew.gameapi.skript.expressions.game;

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
import com.alexlew.gameapi.skript.effects.game.EffCreateGame;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Last game created")
@Description("Returns the last game created.")
@Examples({
        "command game:",
        "\ttrigger:",
        "\t\tcreate game \"Test\"",
        "\t\tset {_game} to last game created",
        "\t\tbroadcast \"You created a new game named %game name of {_game}%\""
})
@Since("1.0")

public class ExprLastGameCreated extends SimpleExpression<Game> {

    static {
        Skript.registerExpression(ExprLastGameCreated.class, Game.class, ExpressionType.SIMPLE,
                "[the] last [mini(-| )]game created"
        );
    }

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        return true;
    }

    @Override
    protected Game[] get( Event e ) {
        return new Game[]{EffCreateGame.lastCreatedGame};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Game> getReturnType() {
        return Game.class;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "the last game created \"" + EffCreateGame.lastCreatedGame.getName() + "\"";
    }

}
