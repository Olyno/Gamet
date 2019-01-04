package com.alexlew.gameapi.skript.effects.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import com.alexlew.gameapi.util.EffectSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Start Game")
@Description("Start a game.")
@Examples({
        "command start:",
        "\ttrigger:",
        "\t\tstart game \"test\":",
        "\t\t\tgive sword to player",
        "\t\t\tsend \"Start fight!\""
})
@Since("1.0")

public class SecStartGame extends EffectSection {

    static {
        Skript.registerCondition(SecStartGame.class,
                "start %game%"
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
        Game mg = game.getSingle(e);
        if (mg.getTeams().length > 0) {
            if (mg.getSpawn() != null) {
                if (mg.getLobby() != null) {
                    for (Team team : mg.getTeams()) {
                        for (Player player : team.getPlayers()) {
                            player.teleport(team.getSpawn());
                        }
                    }
                    mg.setCurrentState(2);
                    runSection(e);
                } else {
                    GameAPI.error("Can't start the game " + mg.getName() + ": lobby is not set.");
                }
            } else {
                GameAPI.error("Can't start the game " + mg.getName() + ": spawn is not set.");
            }
        } else {
            GameAPI.error("Can't start the game " + mg.getName() + ": you don't have any team created.");
        }
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "Scope start game \"" + game.getSingle(e).getName() + "\"";
    }
}
