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
import com.alexlew.gameapi.events.GameDeleted;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Delete Game")
@Description("Delete a game.")
@Examples({
        "command delete <text>:",
        "\ttrigger:",
        "\t\tdelete game arg-1",
        "\t\tbroadcast \"CommandGameSpigot %arg-1% has been deleted!\""
})
@Since("1.0")

public class EffDeleteGame extends Effect {

    public static Game lastDeletedGame;

    static {
        Skript.registerEffect(EffDeleteGame.class,
                "delete %game%"
        );
    }

    private Expression<Game> game;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        game = (Expression<Game>) expr[0];
        return true;
    }

    @Override
    protected void execute( Event e ) {
        Game mg = game.getSingle(e);
        if (Game.games.containsKey(mg.getName())) {
            lastDeletedGame = Game.games.get(mg.getName());
            Game.games.remove(mg.getName());
            new GameDeleted(lastDeletedGame);
        } else {
            GameAPI.error("This game doesn't exist: " + mg.getName());
        }
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "delete the game \"" + game.getSingle(e).getName() + "\"";
    }

}
