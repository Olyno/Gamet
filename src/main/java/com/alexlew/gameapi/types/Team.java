package com.alexlew.gameapi.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.alexlew.gameapi.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    static {
        Classes.registerClass(new ClassInfo<>(Team.class, "team")
                .defaultExpression(new EventValueExpression<>(Team.class))
                .user("team")
                .name("team")
                .description("The current team")
                .examples(
                        "on player join team:",
                        "\tbroadcast \"%event-player% joined the team %event-team%!\""
                )
                .since("2.0")
                .parser(new Parser<Team>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public Team parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(Team arg0, int arg1) {
                        return arg0.getName();
                    }

                    @Override
                    public String toVariableNameString(Team arg0) {
                        return arg0.getName();
                    }

                }));
    }

    private List<Player> players = new ArrayList<>();
    private String name;
    private String displayName;
    private Integer minPlayer;
    private Integer maxPlayer;
    private Location spawn;
    private Location lobby;
    private Point points;

    /**
     * Create a new team
     * @param name The name of the team
     */
    public Team(String name) {
        this.name = name;
        this.displayName = name;
        this.minPlayer = 1;
        this.maxPlayer = 2;
        this.points = new Point(0);
        this.spawn = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        this.lobby = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        new TeamCreated(this);
    }

    /**
     * @return The name of the team
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the team
     * @param name The new name of the team
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * @return The display name of the team
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set the display name of the team
     * @param displayName The new display name of the team
     */
    public void setDisplayName( String displayName ) {
        this.displayName = displayName;
    }

    /**
     * @return The minimum of player that your game require before start
     */
    public Integer getMinPlayer() {
        return minPlayer;
    }

    /**
     * Set the minimum of player that your game require before start
     * @param minPlayer The new minimum of player that your game require before start
     */
    public void setMinPlayer( Integer minPlayer ) {
        this.minPlayer = minPlayer;
    }

    /**
     * @return The maximum of player that your game require before start
     */
    public Integer getMaxPlayer() {
        return maxPlayer;
    }

    /**
     * Set the maximum of player that your game require before start
     * @param maxPlayer The new maximum of player that your game require before start
     */
    public void setMaxPlayer( Integer maxPlayer ) {
        this.maxPlayer = maxPlayer;
    }

    /**
     * @return The spawn of the team
     */
    public Location getSpawn() {
        return spawn;
    }

    /**
     * Set the spawn of the team
     * @param spawn The new spawn of the team
     */
    public void setSpawn( Location spawn ) {
        this.spawn = spawn;
    }

    /**
     * @return The points of the team
     */
    public Point getPoints() {
        return points;
    }

    /**
     * Set the points of the team
     * @param points The new points of the team
     */
    public void setPoints( Integer points ) {
        this.points = new Point(points);
    }

	/**
	 * Set the points of the team
	 *
	 * @param points The new points of the team
	 */
	public void setPoints(Point points) {
		this.points = points;
	}

    /**
     * Add points to the team
     * @param points The points added to the team
     */
    public void addPoints( Integer points ) {
        Point point = new Point(this.points.getPoints() + points);
		this.points = point;
        new TeamWinPoint(point);
	}

	/**
	 * Add points to the team
	 *
	 * @param points The points added to the team
	 */
	public void addPoints(Point points) {
		points.setPoints(points.getPoints() + this.points.getPoints());
		this.points = points;
		new TeamWinPoint(points);
	}

    /**
     * Add points to the team from a player
     *
     * @param points The points added to the team
     * @param player The player who scored the point
     */
    public void addPoints( Integer points, Player player ) {
        Point point = new Point(this.points.getPoints() + points, player);
        new TeamWinPoint(point);
    }

    /**
     * Remove points from the team
     * @param points The point removed from the team
     */
    public void removePoints( Integer points ) {
        Point point = new Point(this.points.getPoints() - points);
		this.points = point;
        new TeamLosePoint(point);
	}

	/**
	 * Remove points from the team
	 *
	 * @param points The point removed from the team
	 */
	public void removePoints(Point points) {
		points.setPoints(points.getPoints() - this.points.getPoints());
		this.points = points;
		new TeamLosePoint(points);
	}

	/**
	 * Remove points from the team
	 *
	 * @param points The point removed from the team
	 * @param player The player who lost the point
	 */
	public void removePoints(Integer points, Player player ) {
		Point point = new Point(this.points.getPoints() - points, player);
		this.points = point;
		new TeamLosePoint(point);
	}

    /**
     * @return List of players in the team
     */
    public Player[] getPlayers() {
        Player[] player = new Player[players.size()];
        return players.toArray(player);
    }

    /**
     * Add a player in the team
     * @param player The player added to the team
     */
    public void addPlayer( Player player ) {
        if (!players.contains(player)) {
            players.add(player);
            if (!this.getGame().hasPlayer(player)) {
                this.getGame().addPlayer(player);
            }
            new PlayerJoinTeam(this, player);
        }
    }

    /**
     * Remove a player from the team
     * @param player The player removed from the team
     */
    public void removePlayer( Player player ) {
		if (players.contains(player)) {
			new PlayerLeaveTeam(player);
		}
		players.remove(player);
    }

    /**
     * @param player The player to check if he is in the team
     * @return If the player is in the team or not
     */
    public Boolean hasPlayer( Player player ) {
        return players.contains(player);
    }

    /**
     * Return the game which is the parent of the team
     *
     * @return The parent game of a team
     */
    public Game getGame() {
        for (String game : Game.games.keySet()) {
            for (Team team : Game.games.get(game).getTeams()) {
                if (team == this) {
                    return Game.games.get(game);
                }
            }
        }
        return null;
    }

    /**
     * @return The lobby of the team. It's where players spawn when they join the team.
     */
    public Location getLobby() {
        return lobby;
    }

    /**
     * Set the lobby of the team. It's where players spawn when they join the team.
     *
     * @param lobby Where is the lobby.
     */
    public void setLobby( Location lobby ) {
        this.lobby = lobby;
    }
}
