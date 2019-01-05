package com.alexlew.gameapi.skript.effects.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.GameStopped;
import com.alexlew.gameapi.skript.expressions.game.ExprGame;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import com.alexlew.gameapi.util.EffectSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Stop Game as section")
@Description("Stop a game as section.")
@Examples({
        "command stop:",
        "\ttrigger:",
        "\t\tstop game \"test\":",
        "\t\t\tclear inventory of player",
        "\t\t\tsend \"Stop fight!\""
})
@Since("1.0")

public class SecStopGame extends EffectSection {

    static {
        Skript.registerCondition(SecStopGame.class,
                "stop %game%"
        );
    }

    private Expression<Game> game;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        if (checkIfCondition())
            return false;
        if (!hasSection()) {
            Skript.error("A game start scope is useless without any content!");
            return false;
        }
        game = (Expression<Game>) expr[0];
        loadSection(true);
        return true;
    }

    @Override
    protected void execute( Event e ) {
        if (game.getSingle(e)== null) {
            GameAPI.error("Can't start a game \"null\"");
            return;
        }
        Game mg = game.getSingle(e);
        ExprGame.lastGame = mg;
        if (mg == null) {
            return;
        }
        if (mg.getTeams().length > 0) {
            if (mg.getLobby() != null) {
                for (Team team : mg.getTeams()) {
                    for (Player player : team.getPlayers()) {
                        player.teleport(mg.getLobby());
                    }
                }
                mg.setCurrentState(1);
                new GameStopped(mg);
                runSection(e);
            } else {
                GameAPI.error("Can't start the game " + mg.getName() + ": lobby is not set.");
            }
        } else {
            GameAPI.error("Can't start the game " + mg.getName() + ": you don't have any team created.");
        }
    }

    @Override
    public String toString( Event e, boolean debug ) {
        String gameName = game.getSingle(e) != null ? game.getSingle(e).getName() : "null";
        return "Scope start game \"" + gameName + "\"";
    }
}
