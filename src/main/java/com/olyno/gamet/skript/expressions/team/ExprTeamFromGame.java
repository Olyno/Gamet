package com.olyno.gamet.skript.expressions.team;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.olyno.gami.models.Game;
import com.olyno.gami.models.Point;
import com.olyno.gami.models.Team;

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

@Name("Team from game")
@Description("Return a team of a game from its name")
@Examples({
    "set {_team} to team \"red\" of game \"Test\""
})
@Since("1.0")

public class ExprTeamFromGame extends SimpleExpression<Team> {

    static {
        Skript.registerExpression(ExprTeamFromGame.class, Team.class, ExpressionType.SIMPLE,
            "[the] team %string% (of|from|for|in) %game%"
        );
    }

    private Expression<String> team;
    private Expression<Game> game;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        team = (Expression<String>) expr[0];
        game = (Expression<Game>) expr[1];
        return true;
    }

    @Override
    protected Team[] get( Event e ) {
        String currentTeam = team.getSingle(e);
		Game currentGame = game.getSingle(e);
        return currentGame.getTeamByName(currentTeam)
            .map(teamFound -> new Team[]{ teamFound })
            .orElse(new Team[0]);
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        switch (mode) {
            case ADD:
            case REMOVE:
                return new Class[]{Object.class};
            default:
                return new Class[0];
        }
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        String currentTeam = team.getSingle(e);
        Game currentGame = game.getSingle(e);
        currentGame.getTeamByName(currentTeam).ifPresent(teamFound -> {
            for (Object o : delta) {
                switch (mode) {
                    case ADD:
                        if (o instanceof Player) {
                            Player player = (Player) o;
							if (teamFound.getMaxPlayer() > teamFound.getPlayers().size()) {
                                if (!teamFound.hasPlayer(player)) {
                                    teamFound.addPlayer(player);
                                }
                            }
                        } else if (o instanceof Point) {
                            Point point = (Point) o;
                            point.setAuthor(teamFound);
                            teamFound.addPoints(point);
                        }
                        break;
                    case REMOVE:
                        if (o instanceof Player) {
                            Player player = (Player) o;
                            teamFound.removePlayer(player);
                        } else if (o instanceof Point) {
                            Point point = (Point) o;
                            point.setAuthor(teamFound);
                            teamFound.removePoints(point);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Team> getReturnType() {
        return Team.class;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        return "team " + team.toString(e, debug) + " from the game " + game.toString(e, debug);
    }

}
