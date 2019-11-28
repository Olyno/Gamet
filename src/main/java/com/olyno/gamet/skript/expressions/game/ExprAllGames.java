package com.olyno.gamet.skript.expressions.game;

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
import com.olyno.gamet.types.Game;
import org.bukkit.event.Event;

@Name("All Games")
@Description("Return all games created.")
@Examples({
        "command list:",
        "\ttrigger:",
        "\t\tbroadcast \"List of all games created: %all games%\""
})
@Since("2.0")

public class ExprAllGames extends SimpleExpression<Game> {

    static {
        Skript.registerExpression(ExprAllGames.class, Game.class, ExpressionType.SIMPLE,
                "all [minis(-| )]games");
    }

    @Override
    public boolean init( Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        return true;
    }

    @Override
    protected Game[] get( Event e ) {
		if (Game.getGames().size() == 0) return null;
		return Game.getGames().values().toArray(new Game[Game.getGames().size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Game> getReturnType() {
        return Game.class;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "all games";
    }
}
