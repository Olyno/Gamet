package com.alexlew.gameapi.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Game exists?")
@Description("Check if a game exists")
@Examples({
        "command check:",
        "\ttrigger:",
        "\t\tif game \"test\" doesn't exist:",
        "\t\t\tcreate the game \"test\""
})
@Since("2.0")

public class CondGameExist extends Condition {

    static {
        Skript.registerCondition(CondGameExist.class,
                "[[mini[(-| )]]game] %string% exists",
                "[[mini[(-| )]]game] %string% does(n't| not) exist"
        );
    }

    private Expression<String> game;

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        game = (Expression<String>) expr[0];
        setNegated(matchedPattern == 1);
        return true;
    }

    @Override
    public boolean check( Event e ) {
        boolean exists = game.getSingle(e) != null && Game.games.containsKey(game.getSingle(e));
        return (isNegated() != exists);
    }

    @Override
    public String toString( Event e, boolean debug ) {
        String gameName = game.getSingle(e) != null ? game.getSingle(e) : "null";
        return "Game \"" + gameName + "\" existence";
    }
}
