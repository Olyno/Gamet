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
import com.alexlew.gameapi.skript.expressions.game.ExprGame;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.util.EffectSection;
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
        if (game.getSingle(e)== null) {
            GameAPI.error("Can't start a game \"null\"");
            return;
        }
		Game currentGame = game.getSingle(e);
		ExprGame.lastGame = currentGame;
		if (currentGame.getTeams().size() > 0) {
			if (currentGame.getSpawn() != null) {
				if (currentGame.getLobby() != null) {
					currentGame.start();
                    runSection(e);
                } else {
					GameAPI.error("Can't start the game " + currentGame.getName() + ": lobby is not set.");
                }
            } else {
				GameAPI.error("Can't start the game " + currentGame.getName() + ": spawn is not set.");
            }
        } else {
			GameAPI.error("Can't start the game " + currentGame.getName() + ": you don't have any team created.");
        }
    }

    @Override
    public String toString( Event e, boolean debug ) {
        String gameName = game.getSingle(e) != null ? game.getSingle(e).getName() : "null";
        return "Scope start game \"" + gameName + "\"";
    }
}
