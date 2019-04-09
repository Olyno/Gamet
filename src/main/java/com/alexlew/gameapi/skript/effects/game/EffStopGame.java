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
import com.alexlew.gameapi.events.GameStopped;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.entity.Player;
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
        if (game.getSingle(e) == null) {
            GameAPI.error("Can't stop a game \"null\"");
        }
        Game mg = game.getSingle(e);
        if (mg.getTeams().length > 0) {
            if (mg.getLobby() != null) {
                for (Team team : mg.getTeams()) {
                    for (Player player : team.getPlayers()) {
                        player.teleport(mg.getLobby());
                    }
                }
                mg.setCurrentState(1);
                new GameStopped(mg);
            } else {
                GameAPI.error("Can't stop the game " + mg.getName() + ": lobby is not set.");
            }
        } else {
            GameAPI.error("Can't stop the game " + mg.getName() + ": you don't have any team created.");
        }
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "Stop game \"" + game.getSingle(e).getName() + "\"";
    }
}
