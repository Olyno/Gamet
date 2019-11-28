package com.olyno.gamet.skript.effects.game;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.olyno.gamet.Gamet;
import com.olyno.gamet.types.Game;
import org.bukkit.event.Event;

@Name("Stop Game")
@Description("Stop a game.")
@Examples({
        "command stop:",
        "\ttrigger:",
        "\t\tstop game \"test\"",
        "\t\tclear inventory of player",
        "\t\tsend \"Stop fight!\""
})
@Since("1.0")

public class EffStopGame extends Effect {

    static {
        Skript.registerEffect(EffStopGame.class,
                "stop %game%"
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
		Game currentGame = game.getSingle(e);
        if (currentGame == null) {
            Gamet.error("Can't stop a \"null\" game");
            return;
        }
        if (currentGame.getLobby() != null) {
            currentGame.stop();
        } else {
            Gamet.error("Can't stop the game " + currentGame.getName() + ": lobby is not set.");
        }
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "stop game \"" + game.toString(e, debug) + "\"";
    }
}
