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
import com.alexlew.gameapi.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Start Game")
@Description("Start a game.")
@Examples({
        "command start:",
        "\ttrigger:",
        "\t\tstart game \"test\":",
        "\t\tgive sword to player",
        "\t\tsend \"Start fight!\""
})
@Since("1.0")

public class EffStartGame extends Effect {

    static {
        Skript.registerEffect(EffStartGame.class,
                "start %game%"
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
            GameAPI.error("Can't start a game \"null\"");
            return;
        }
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
        String gameName = game.getSingle(e) != null ? game.getSingle(e).getName() : "null";
        return "Start game \"" + gameName + "\"";
    }
}
