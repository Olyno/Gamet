package com.alexlew.gameapi.skript.expressions.team;

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
import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Point;
import com.alexlew.gameapi.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

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
        String t = team.getSingle(e);
        Game mg = game.getSingle(e);
        if (mg == null) {return null;}
        if (!t.replaceAll(" ", "").equals("")) {
            if (Game.games.containsKey(mg.getName())) {
                if (mg.teamExists(team.getSingle(e))) {
                    return new Team[] {mg.getTeam(team.getSingle(e))};
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            GameAPI.error("A team can't have a empty name (Current name: \"" + team.getSingle(e) + "\")");
            return null;
        }
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
            return new Class[]{Object.class};
        }
        return null;
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        String t = team.getSingle(e);
        if (game.getSingle(e) == null) {
            return;
        }
        if (!Game.games.containsValue(game.getSingle(e))) {
            return;
        }
        if (game.getSingle(e).teamExists(t)) {
            Team team = game.getSingle(e).getTeam(t);
            for (Object obj : delta) {
                switch (mode) {
                    case SET:
                        if (obj instanceof Player) {
                            Player player = (Player) obj;
                            if (!team.hasPlayer(player)) {
                                team.addPlayer(player);
                            }
                        } else if (obj instanceof Point) {
                            Point point = (Point) obj;
                            point.setTeam(team);
                            point.setGame(team.getGame());
                            team.setPoints(point);
                        } else {
                            GameAPI.error("You can add only points or player to a team, not a " + obj.getClass());
                        }
                        break;
                    case ADD:
                        if (obj instanceof Player) {
                            Player player = (Player) obj;
                            if (team.getMaxPlayer() > team.getPlayers().length) {
                                if (!team.hasPlayer(player)) {
                                    team.addPlayer(player);
                                }
                            } else {
                                GameAPI.error("The team \"" + team.getName() + "\" can't add more players, you have already " + team.getPlayers().length + " players in this team and the maximum of players is " + team.getMaxPlayer());
                            }
                        } else if (obj instanceof Point) {
                            Point point = (Point) obj;
                            point.setTeam(team);
                            point.setGame(team.getGame());
                            team.addPoints(point);
                        } else {
                            GameAPI.error("You can add only points or player to a team, not a " + obj.getClass());
                        }
                        break;
                    case REMOVE:
                        if (obj instanceof Player) {
                            Player player = (Player) obj;
                            team.removePlayer(player);
                        } else if (obj instanceof Point) {
                            Point point = (Point) obj;
                            point.setTeam(team);
                            point.setGame(team.getGame());
                            team.removePoints(point);
                        } else {
                            GameAPI.error("You can add only points or player to a team, not a " + obj.getClass());
                        }
                        break;
                    default:
                        break;
                }
            }
        }
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
        String gameName = game.getSingle(e) != null ? game.getSingle(e).getName() : "null";
        return "The team \"" + team.getSingle(e) + "\" from the game \"" + gameName + "\"";
    }

}
