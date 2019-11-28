package com.olyno.gamet.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Players list of team/game")
@Description("Returns the players list of a team or game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tcreate team \"red\" for game \"Test\"",
        "\t\tadd player to last team created",
        "\t\tbroadcast \"All players of team %last team created%: %players of last team created%"
})
@Since("1.0")

public class ExprPlayers extends SimpleExpression<Player> {

    static {
        Skript.registerExpression(ExprPlayers.class, Player.class, ExpressionType.SIMPLE,
                "[the] players (of|in|from) (%team%|%game%)",
                "[the] (%team%|%game%)'s players"
        );
    }

    private Expression<Object> context;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        context = (Expression<Object>) expr[0];
        return true;
    }

    @Override
    protected Player[] get( Event e ) {
        Object o = context.getSingle(e);
        if (o instanceof Game) {
            Game game = (Game) o;
			return game.getPlayers().toArray(new Player[game.getPlayers().size()]);
        } else if (o instanceof Team) {
            Team team = (Team) o;
			return team.getPlayers().toArray(new Player[team.getPlayers().size()]);
        } else {
            return null;
        }
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.REMOVE_ALL || mode == Changer.ChangeMode.RESET) {
            return new Class[]{Player.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        for (Object o : delta) {
            if (mode == Changer.ChangeMode.REMOVE_ALL || mode == Changer.ChangeMode.RESET) {
                if (o instanceof Game) {
                    Game game = (Game) o;
                    for (Player player : game.getPlayers()) {
                        game.removePlayer(player);
                    }
                } else if (o instanceof Team) {
                    Team team = (Team) o;
                    for (Player player : team.getPlayers()) {
                        team.removePlayer(player);
                    }
                }
            }
        }
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Player> getReturnType() {
        return Player.class;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "players of " + context.toString(e, debug);
    }
}
