package com.alexlew.gameapi.types;

import com.alexlew.gameapi.events.*;
import com.alexlew.gameapi.util.GameManager;
import com.alexlew.gameapi.util.GameRunner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Game extends GameManager {

	private static HashMap<String, Game> games = new HashMap<>();
	private HashMap<String, Team> teams = new HashMap<>();

	// Messages
	private HashMap<String, String> joinMessage = new HashMap<>();
	private HashMap<String, String> leaveMessage = new HashMap<>();
	private HashMap<String, String> endMessage = new HashMap<>();
	private HashMap<Integer, String> timerMessages = new HashMap<>();

	private String name;
	private String displayName;
	private Integer minPlayer;
	private Integer maxPlayer;
	private Location lobby;
	private Location spawn;
	private Integer state;
	private Integer timer;
	private String timerMessageAs;
	private Team winner;
	private World world;
	private List<Player> players = new LinkedList<>();

	public Game(String name) {
		this.name = name;
		this.displayName = name;
		this.lobby = new Location(Bukkit.getWorld("world"), 0, 150, 0);
		this.spawn = new Location(Bukkit.getWorld("world"), 0, 150, 0);
		this.minPlayer = 1;
		this.maxPlayer = 2;
		this.state = 0;
		this.timer = 15;
		this.world = Bukkit.getWorld("world");
		this.joinMessage.put("global", "${player} joined the game!");
		this.joinMessage.put("player", "You joined the game ${game}");
		this.leaveMessage.put("global", "${player} left the game!");
		this.leaveMessage.put("player", "You left the game ${game}");
		this.endMessage.put("global", "The ${game} game is finished! The winner is the ${winner} team!");
		this.timerMessageAs = "title";
		this.timerMessages.put(20, "Game starts in ${time} seconds");
		this.timerMessages.put(15, "Game starts in ${time} seconds");
		for (int index = 1; index < 11; index++) {
			this.timerMessages.put(index, "Game starts in ${time} seconds");
		}
		if (!games.containsKey(name)) {
			games.put(name, this);
			new GameCreated(this);
		}
	}

	/**
	 * @retrun A hashmap of all existing games
	 */
	public static HashMap<String, Game> getGames() {
		return games;
	}

	/**
	 * Return the game where the player is.
	 *
	 * @param player The player that we need to know the game where he is.
	 * @return The game where is the player
	 */
	public static Game getGameOfPlayer(Player player) {
		for (Game game : getGames().values()) {
			for (Team team : game.getTeams().values()) {
				if (team.hasPlayer(player)) {
					return game;
				}
			}
			if (game.hasPlayer(player)) {
				return game;
			}
		}
		return null;
	}

	/**
	 * Return the team where the player is.
	 *
	 * @param player The player that we need to know the team where he is.
	 * @return The team where is the player
	 */
	public static Team getTeamOfPlayer(Player player) {
		for (Game game : getGames().values()) {
			for (Team team : game.getTeams().values()) {
				if (team.hasPlayer(player)) {
					return team;
				}
			}
		}
		return null;
	}

	public void start() {
		this.state = 1;
		new GameRunner(this).start();
	}

	public void stop() {
		this.state = 3;
		new GameStopped(this);
	}

	@SuppressWarnings("unchecked")
	public Game delete() {
		getGames().remove(this.name);
		File gameFile = new File(Bukkit.getServer().getPluginManager().getPlugin("GameAPI").getDataFolder(), "Games/" + this.name + ".yml");
		if (gameFile.exists()) gameFile.delete();
		new GameDeleted(this);
		return this;
	}

	/**
	 * @retrun A hashmap of all existing teams of this game
	 */
	public HashMap<String, Team> getTeams() {
		return teams;
	}

	/**
	 * @retrun The current state of the game
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * Set the state of the game:
	 * - State 0 --> Waiting
	 * - State 1 --> Launch the game
	 * - State 2 --> Started/In game
	 * - State 3 --> Ended
	 *
	 * @param state The new state of the game
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * Return a team from its name
	 *
	 * @param team The team which will be return
	 */
	public Team getTeam(String team) {
		return teams.getOrDefault(team, null);
	}

	/**
	 * Add a team to your game
	 *
	 * @param team The team which will be added
	 */
	public void addTeam(Team team) {
		teams.put(team.getName(), team);
	}

	/**
	 * @param team The team that you need to check existence
	 * @return If the team exists or not
	 */
	public Boolean teamExists(String team) {
		return teams.containsKey(team);
	}

	/**
	 * @return The world where the game is
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Set the world where the game is supposed to be in.
	 *
	 * @param world The world where the game will be play
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * @return The timer before the game starts (in seconds)
	 */
	public Integer getTimer() {
		return timer;
	}

	/**
	 * Set the timer before the game starts (in seconds)
	 *
	 * @param timer The new timer before the game starts (in seconds)
	 */
	public void setTimer(Integer timer) {
		this.timer = timer;
	}

	/**
	 * @return The timer message type (title or action bar)
	 */
	public String getTimerMessageAs() {
		return timerMessageAs;
	}

	/**
	 * Set the timer message as title or action bar
	 *
	 * @param timerMessageAs The new timer message type
	 */
	public void setTimerMessageAs(String timerMessageAs) {
		this.timerMessageAs = timerMessageAs;
	}

	/**
	 * @return The winner of the game
	 */
	public Team getWinner() {
		return winner;
	}

	/**
	 * Set the winner of the game
	 *
	 * @param winner The new winner of the game
	 */
	public void setWinner(Team winner) {
		this.winner = winner;
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
	 * The Timer Messages
	 *
	 * @return A hashmap of all messages during the timer
	 */
	public HashMap<Integer, String> getTimerMessages() {
		return timerMessages;
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
			if (players.size() == minPlayer) new GameCanStart(this);
			if (players.size() == maxPlayer) new GameReady(this);
			new PlayerJoinGame(this, player);
		}
	}

	@Override
	public void removePlayer(Player player) {
		if (players.contains(player)) {
			players.remove(player);
			new PlayerLeaveGame(this, player);
		}
	}

	@Override
	public Boolean hasPlayer(Player player) {
		return players.contains(player);
	}

	public HashMap<String, String> getEndMessage() {
		return endMessage;
	}
}