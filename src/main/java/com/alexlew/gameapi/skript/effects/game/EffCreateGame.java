package com.alexlew.gameapi.skript.effects.game;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Create Game")
@Description("Create a game.")
@Examples({
        "command create <text>:",
        "\ttrigger:",
        "\t\tcreate game arg-1",
        "\t\tbroadcast \"Game %arg-1% has been created!\""
})
@Since("1.0")

public class EffCreateGame extends Effect {

    public static Game lastCreatedGame;

    static {
        Skript.registerEffect(EffCreateGame.class,
                "create [(the|a)] [mini[(-| )]]game %string%"
        );
    }

    private Expression<String> game;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        game = (Expression<String>) expr[0];
        return true;
    }

    @Override
    protected void execute( Event e ) {
        if (game.getSingle(e) == null) {
            GameAPI.error("Can't create a game \"null\"");
            return;
        }
        String mgName = game.getSingle(e);
        if (!mgName.replaceAll(" ", "").isEmpty()) {
            if (!Game.games.containsKey(mgName)) {
                Game.games.put(game.getSingle(e), new Game(mgName));
                lastCreatedGame = Game.games.get(mgName);
            }
        } else {
            GameAPI.error("A game can't have a empty name (Current name: " + game.getSingle(e) + ")");
        }
    }

    @Override
    public String toString( Event e, boolean debug ) {
        String gameName = game.getSingle(e) != null ? game.getSingle(e) : "null";
        return "Create the game \"" + gameName + "\"";
    }

}
