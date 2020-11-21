package com.olyno.gamet.skript.conditions;

import com.olyno.gami.Gami;
import com.olyno.gami.models.Game;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

@Name("Game exists?")
@Description("Check if a game exists")
@Examples({
    "command check:" +
    "\ttrigger:" +
    "\t\tif game \"test\" doesn't exist:" +
    "\t\t\tcreate the game \"test\""
})
@Since("2.0")

public class CondGameExist extends Condition {

    static {
        Skript.registerCondition(CondGameExist.class,
            "%game% exists",
            "%game% does(n't| not) exist"
        );
    }

    private Expression<Game> game;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        game = (Expression<Game>) expr[0];
        setNegated(matchedPattern == 1);
        return true;
    }

    @Override
    public boolean check( Event e ) {
		boolean exists = game.getSingle(e) != null && Gami.getGames().containsKey(game.getSingle(e).getName());
        return isNegated() != exists;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "game " + game.toString(e, debug) + (isNegated() ? " doesn't exist" : " exists");
    }
}
