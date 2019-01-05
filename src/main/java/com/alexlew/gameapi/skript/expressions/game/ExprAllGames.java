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
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

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
        List<String> mg = new ArrayList<String>(Game.games.keySet());
        if (mg.size() == 0) {return null;}
        Game[] games = new Game[mg.size()];
        for (int i = 0; i < mg.size(); i++)
            games[i] = Game.games.get(mg.get(i));
        return games;
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
