package com.olyno.gamet.skript.effects.sections;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.skript.expressions.game.ExprGame;
import com.olyno.gamet.util.skript.sections.EffectSection;
import com.olyno.gami.models.Game;

import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

@Name("Start Game")
@Description("Start a game.")
@Examples({
    "command start:" +
    "\ttrigger:" +
    "\t\tstart game \"test\":" +
    "\t\t\tgive sword to player" +
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
		Game currentGame = game.getSingle(e);
        ExprGame.lastGame = currentGame;
        if (currentGame.getTeams().size() > 0) {
			if (currentGame.getSpawn() != null && currentGame.getSpawn() instanceof Location) {
				if (currentGame.getLobby() != null && currentGame.getLobby() instanceof Location) {
					currentGame.start();
                    runSection(e);
				} else {
					Gamet.error("Can't start the game \"" + currentGame.getName() + "\": lobby is not set.");
				}
			} else {
				Gamet.error("Can't start the game \"" + currentGame.getName() + "\": spawn is not set.");
			}
		} else {
			Gamet.error("Can't start the game \"" + currentGame.getName() + "\": you don't have any team created.");
		}
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "scope start game " + game.toString(e, debug);
    }
}
