package com.olyno.types;

import com.olyno.GameAPI;
import com.olyno.events.*;
import com.olyno.util.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Team extends GameManager {

	private Point points;
	private Game game;
	private String name;
	private String displayName;
	private Integer minPlayer;
	private Integer maxPlayer;
	private Integer objective;
	private Location lobby;
	private Location spawn;
	private List<Player> players = new LinkedList<>();

	// Messages
	private HashMap<String, String> joinMessage = new HashMap<>();
	private HashMap<String, String> leaveMessage = new HashMap<>();
	private HashMap<String, String> winPointMessage = new HashMap<>();
	private HashMap<String, String> losePointMessage = new HashMap<>();

    /**
     * Create a new team
     * @param name The name of the team
     */
	public Team(String name, Game game) {
		this.name = name;
		this.displayName = name;
		this.lobby = new Location(Bukkit.getWorld("world"), 0, 150, 0);
		this.spawn = new Location(Bukkit.getWorld("world"), 0, 150, 0);
		this.minPlayer = 1;
		this.maxPlayer = 2;
        this.points = new Point(0);
		this.game = game;
		this.objective = 5;
		this.joinMessage.put("global", "${player} joined the ${team} team!");
		this.joinMessage.put("player", "You joined the ${team} team!");
		this.leaveMessage.put("global", "${player} left the ${team} team!");
		this.leaveMessage.put("player", "You left the ${team} team!");
		this.winPointMessage.put("global", "${player} scored a point for the ${team} team!");
		this.winPointMessage.put("player", "You scored a point for your team!");
		this.losePointMessage.put("global", "${player} lost a point for the ${team} team");
		this.losePointMessage.put("player", "The opponent team won a point!");
		if (!game.getTeams().containsKey(name)) {
			game.addTeam(this);
			new TeamCreated(this);
		}
    }

	/**
	 * @return The Objective of the team
	 */
	public Integer getObjective() {
		return objective;
	}

	/**
	 * Set the objective of the team
	 *
	 * @param objective The new objective of the team
	 */
	public void setObjective(Integer objective) {
		this.objective = objective;
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
	public void addPoints( Point points ) {
		this.points = points;
		new TeamWinPoint(points);
	}

    /**
     * Remove points from the team
     * @param points The point removed from the team
	 */
	public void removePoints( Point points ) {
		this.points = points;
		new TeamLosePoint(points);
	}

    /**
     * Return the game which is the parent of the team
     *
     * @return The parent game of a team
     */
    public Game getGame() {
		for (Game game : Game.getGames().values()) {
			for (Team team : game.getTeams().values()) {
				if (team == this) {
					return game;
				}
			}
		}
		GameAPI.error("Something is weird with this team: " + this.getName());
		return null;
	}

	/**
	 * The Join Message
	 *
	 * @return A hashmap of the Join Message
	 */
	public HashMap<String, String> getJoinMessage() {
		return joinMessage;
	}

	/**
	 * The Leave Message
	 *
	 * @return A hashmap of the Leave Message
	 */
	public HashMap<String, String> getLeaveMessage() {
		return leaveMessage;
	}

	/**
	 * The Win Point Message
	 *
	 * @return A hashmap of the Win Point Message
	 */
	public HashMap<String, String> getWinPointMessage() {
		return winPointMessage;
	}

	/**
	 * The Lose Point Message
	 *
	 * @return A hashmap of the Lose Point Message
	 */
	public HashMap<String, String> getLosePointMessage() {
		return losePointMessage;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = Pattern.compile("^\\S[a-z0-9]+").matcher(name).find() ? name : this.name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public Integer getMinPlayer() {
		return minPlayer;
	}

	@Override
	public void setMinPlayer(Integer minPlayer) {
		this.minPlayer = minPlayer;
	}

	@Override
	public Integer getMaxPlayer() {
		return maxPlayer;
	}

	@Override
	public void setMaxPlayer(Integer maxPlayer) {
		this.maxPlayer = minPlayer;
	}

	@Override
	public Location getLobby() {
		return lobby;
	}

	@Override
	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	@Override
	public Location getSpawn() {
		return spawn;
	}

	@Override
	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public void addPlayer(Player player) {
		if (!players.contains(player)) {
			players.add(player);
			new PlayerJoinTeam(this, player);
		}
	}

	@Override
	public void removePlayer(Player player) {
		if (players.contains(player)) {
			players.remove(player);
			new PlayerLeaveTeam(this, player);
		}

	}

	@Override
	public Boolean hasPlayer(Player player) {
		return players.contains(player);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Team delete() {
		game.getTeams().remove(this.getName());
		new TeamDeleted(this);
		return this;
	}
}
