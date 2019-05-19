package com.alexlew.gameapi;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.alexlew.gameapi.commands.CommandGameSpigot;
import com.alexlew.gameapi.commands.CommandTeamSpigot;
import com.alexlew.gameapi.events.*;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import com.alexlew.gameapi.util.BountifulAPI;
import com.alexlew.gameapi.util.Metrics;
import com.alexlew.gameapi.util.Registration;
import com.jcabi.aspects.Async;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class GameAPI extends JavaPlugin {

	public static Boolean messages = true;
	public static Boolean manage_automatically = true;
	static GameAPI instance;
	private static File gameapiFolder;
	public List<Registration> expressions = new ArrayList<>();
	SkriptAddon addon;
	private File gamesFolder = new File(getDataFolder(), "Games");
	private File usersFolder = new File(getDataFolder(), "Users");

	public static void error(String error) {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[GameAPI] " + error + " ");
	}

	public static void info(String info) {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[GameAPI] " + info + " ");
	}

	public static GameAPI getInstance() {
		return instance;
	}

	public static String readToString(String targetURL) throws IOException {
		URL url = new URL(targetURL);
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(url.openStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String inputLine;
		while ((inputLine = bufferedReader.readLine()) != null) {
			stringBuilder.append(inputLine);
			stringBuilder.append(System.lineSeparator());
		}
		bufferedReader.close();
		return stringBuilder.toString().trim();
	}

	public SkriptAddon getAddonInstance() {
		return addon;
	}

	public void onDisable() {
		for (Game game : Game.getGames().values()) {
			saveAsYaml(game);
		}
	}

	/**
	 * Save your minis games as yaml file
	 *
	 * @param game The mini game you want to save
	 */
	// Save all games
	public void saveAsYaml(Game game) {
		if (!getDataFolder().exists()) getDataFolder().mkdir();
		if (!gamesFolder.exists()) gamesFolder.mkdir();
		try {
			File gameAsFile = new File(getDataFolder(), "Games/" + game.getName() + ".yml");
			if (gameAsFile.exists()) {
				gameAsFile.delete();
			}
			gameAsFile.createNewFile();
			FileConfiguration gameAsYaml = YamlConfiguration.loadConfiguration(gameAsFile);
			gameAsYaml.set("name", game.getName());
			gameAsYaml.set("display_name", game.getDisplayName());
			gameAsYaml.set("players.minimum", game.getMinPlayer());
			gameAsYaml.set("players.maximum", game.getMaxPlayer());
			gameAsYaml.set("timer.time", game.getTimer());
			gameAsYaml.set("timer.messages_as", game.getTimerMessageAs());
			for (int index = game.getTimer(); index > 0; index--) {
				gameAsYaml.set("timer.messages." + index, game.getTimerMessages().get(index));
			}
			gameAsYaml.set("messages.join_message.global", game.getJoinMessage().get("global"));
			gameAsYaml.set("messages.join_message.player", game.getJoinMessage().get("player"));
			gameAsYaml.set("messages.leave_message.global", game.getLeaveMessage().get("global"));
			gameAsYaml.set("messages.leave_message.player", game.getLeaveMessage().get("player"));
			gameAsYaml.set("lobby.WORLD", game.getLobby().getWorld().getName());
			gameAsYaml.set("lobby.X", game.getLobby().getX());
			gameAsYaml.set("lobby.Y", game.getLobby().getY());
			gameAsYaml.set("lobby.Z", game.getLobby().getZ());
			gameAsYaml.set("spawn.WORLD", game.getSpawn().getWorld().getName());
			gameAsYaml.set("spawn.X", game.getSpawn().getX());
			gameAsYaml.set("spawn.Y", game.getSpawn().getY());
			gameAsYaml.set("spawn.Z", game.getSpawn().getZ());
			for (Team team : game.getTeams().values()) {
				gameAsYaml.set("teams." + team.getName() + ".players.minimum", team.getMinPlayer());
				gameAsYaml.set("teams." + team.getName() + ".players.maximum", team.getMaxPlayer());
				gameAsYaml.set("teams." + team.getName() + ".messages.join_message.global", team.getJoinMessage().get("global"));
				gameAsYaml.set("teams." + team.getName() + ".messages.join_message.player", team.getJoinMessage().get("player"));
				gameAsYaml.set("teams." + team.getName() + ".messages.leave_message.global", team.getLeaveMessage().get("global"));
				gameAsYaml.set("teams." + team.getName() + ".messages.leave_message.player", team.getLeaveMessage().get("player"));
				gameAsYaml.set("teams." + team.getName() + ".messages.win_point_message.global", team.getWinPointMessage().get("global"));
				gameAsYaml.set("teams." + team.getName() + ".messages.win_point_message.player", team.getWinPointMessage().get("player"));
				gameAsYaml.set("teams." + team.getName() + ".messages.lose_point_message.global", team.getLosePointMessage().get("global"));
				gameAsYaml.set("teams." + team.getName() + ".messages.lose_point_message.player", team.getLosePointMessage().get("player"));
				gameAsYaml.set("teams." + team.getName() + ".spawn.WORLD", team.getSpawn().getWorld().getName());
				gameAsYaml.set("teams." + team.getName() + ".spawn.X", team.getSpawn().getX());
				gameAsYaml.set("teams." + team.getName() + ".spawn.Y", team.getSpawn().getY());
				gameAsYaml.set("teams." + team.getName() + ".spawn.Z", team.getSpawn().getZ());
			}
			gameAsYaml.save(gameAsFile);
			info("Game \"" + game.getName() + "\" has been saved!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load all games saved or added
	 */
	@Async
	public void loadGames() {
		File dir = new File(getDataFolder(), "Games");
		File[] listOfGames = dir.listFiles();
		for (File gameFile : listOfGames) {
			if (gameFile.isFile()) {
				String gameName = gameFile.getName().replaceAll(".yml", "");
				FileConfiguration gameAsYaml = YamlConfiguration.loadConfiguration(gameFile);
				Game game = new Game(gameName);
				String gameWorld = gameAsYaml.isSet("spawn.WORLD") ?
						(String) gameAsYaml.getValues(true).get("spawn.WORLD") : game.getSpawn().getWorld().getName();
				double gameSpawnX = gameAsYaml.isSet("spawn.X") ?
						(double) gameAsYaml.getValues(true).get("spawn.X") : game.getSpawn().getX();
				double gameSpawnY = gameAsYaml.isSet("spawn.Y") ?
						(double) gameAsYaml.getValues(true).get("spawn.Y") : game.getSpawn().getY();
				double gameSpawnZ = gameAsYaml.isSet("spawn.Z") ?
						(double) gameAsYaml.getValues(true).get("spawn.Z") : game.getSpawn().getZ();
				String gameLobbyWorld = gameAsYaml.isSet("lobby.WORLD") ?
						(String) gameAsYaml.getValues(true).get("lobby.WORLD") : game.getLobby().getWorld().getName();
				double gameLobbyX = gameAsYaml.isSet("lobby.X") ?
						(double) gameAsYaml.getValues(true).get("lobby.X") : game.getLobby().getX();
				double gameLobbyY = gameAsYaml.isSet("lobby.Y") ?
						(double) gameAsYaml.getValues(true).get("lobby.Y") : game.getLobby().getY();
				double gameLobbyZ = gameAsYaml.isSet("lobby.Z") ?
						(double) gameAsYaml.getValues(true).get("lobby.Z") : game.getLobby().getZ();

				// Register datas
				game.setName(gameAsYaml.isSet("name") ?
						(String) gameAsYaml.getValues(true).get("name") : game.getName());
				game.setDisplayName(gameAsYaml.isSet("display_name") ?
						(String) gameAsYaml.getValues(true).get("display_name") : game.getName());
				game.setWorld(Bukkit.getWorld(gameWorld));
				game.setLobby(new Location(Bukkit.getWorld(gameLobbyWorld), gameLobbyX, gameLobbyY, gameLobbyZ));
				game.setSpawn(new Location(Bukkit.getWorld(gameWorld), gameSpawnX, gameSpawnY, gameSpawnZ));
				game.setMinPlayer(gameAsYaml.isSet("players.minimum") ?
						(Integer) gameAsYaml.getValues(true).get("players.minimum") : game.getMinPlayer());
				game.setMaxPlayer(gameAsYaml.isSet("players.maximum") ?
						(Integer) gameAsYaml.getValues(true).get("game.players.maximum") : game.getMaxPlayer());

				// Messages
				game.getJoinMessage().put("global", gameAsYaml.isSet("messages.join_message.global") ?
						(String) gameAsYaml.getValues(true).get("messages.join_message.global") : game.getJoinMessage().get("global"));
				game.getJoinMessage().put("player", gameAsYaml.isSet("messages.join_message.player") ?
						(String) gameAsYaml.getValues(true).get("messages.join_message.player") : game.getJoinMessage().get("player"));
				game.getLeaveMessage().put("global", gameAsYaml.isSet("messages.leave_message.global") ?
						(String) gameAsYaml.getValues(true).get("messages.leave_message.global") : game.getJoinMessage().get("global"));
				game.getLeaveMessage().put("player", gameAsYaml.isSet("messages.leave_message.player") ?
						(String) gameAsYaml.getValues(true).get("messages.leave_message.player") : game.getJoinMessage().get("player"));

				// Timer
				game.setTimer(gameAsYaml.isSet("timer.time") ?
						(Integer) gameAsYaml.getValues(true).get("timer.time") : game.getTimer());
				game.setTimerMessageAs(gameAsYaml.isSet("timer.messages_as") ?
						(String) gameAsYaml.getValues(true).get("timer.messages_as") : game.getTimerMessageAs());
				if (gameAsYaml.isSet("timer.messages")) {
					List<Integer> indexes = new LinkedList<>();
					for (String time : gameAsYaml.getConfigurationSection("timer.messages").getKeys(false)) {
						indexes.add(Integer.parseInt(time));
					}
					Collections.sort(indexes);
					for (Integer index : indexes) {
						game.getTimerMessages().put(index, (String) gameAsYaml.getValues(true).get("timer.messages." + index.toString()));
					}
				}

				// Teams part
				if (gameAsYaml.isSet("teams")) {
					for (String key : gameAsYaml.getConfigurationSection("teams").getKeys(false)) {
						Team team = new Team(key, game);
						String teamWorld = gameAsYaml.isSet("teams." + key + ".spawn.WORLD") ?
								(String) gameAsYaml.getValues(true).get("teams." + key + ".spawn.WORLD") : team.getSpawn().getWorld().getName();
						double teamSpawnX = gameAsYaml.isSet("teams." + key + ".spawn.X") ?
								(double) gameAsYaml.getValues(true).get("teams." + key + ".spawn.X") : team.getSpawn().getX();
						double teamSpawnY = gameAsYaml.isSet("teams." + key + ".spawn.Y") ?
								(double) gameAsYaml.getValues(true).get("teams." + key + ".spawn.Y") : team.getSpawn().getY();
						double teamSpawnZ = gameAsYaml.isSet("teams." + key + ".spawn.Z") ?
								(double) gameAsYaml.getValues(true).get("teams." + key + ".spawn.Z") : team.getSpawn().getZ();
						team.setMinPlayer(gameAsYaml.isSet("teams." + key + ".players.minimum") ?
								(Integer) gameAsYaml.getValues(true).get("teams." + key + ".players.minimum") : team.getMinPlayer());
						team.setMaxPlayer(gameAsYaml.isSet("teams." + key + ".players.maximum") ?
								(Integer) gameAsYaml.getValues(true).get("teams." + key + ".players.maximum") : team.getMaxPlayer());

						// Messages
						team.getJoinMessage().put("global", gameAsYaml.isSet("teams." + key + ".messages.join_message.global") ?
								(String) gameAsYaml.getValues(true).get("teams." + key + ".messages.join_message.global") : game.getJoinMessage().get("global"));
						team.getJoinMessage().put("player", gameAsYaml.isSet("teams." + key + ".messages.join_message.player") ?
								(String) gameAsYaml.getValues(true).get("teams." + key + ".messages.join_message.player") : game.getJoinMessage().get("player"));
						team.getLeaveMessage().put("global", gameAsYaml.isSet("teams." + key + ".messages.leave_message.global") ?
								(String) gameAsYaml.getValues(true).get("teams." + key + ".messages.leave_message.global") : game.getJoinMessage().get("global"));
						team.getLeaveMessage().put("player", gameAsYaml.isSet("teams." + key + ".messages.leave_message.player") ?
								(String) gameAsYaml.getValues(true).get("teams." + key + ".messages.leave_message.player") : game.getJoinMessage().get("player"));
						team.getWinPointMessage().put("global", gameAsYaml.isSet("teams." + key + ".messages.win_points_message.global") ?
								(String) gameAsYaml.getValues(true).get("teams." + key + ".messages.win_points_message.global") : game.getJoinMessage().get("global"));
						team.getWinPointMessage().put("player", gameAsYaml.isSet("teams." + key + ".messages.win_points_message.player") ?
								(String) gameAsYaml.getValues(true).get("teams." + key + ".messages.lose_points_message.player") : game.getJoinMessage().get("player"));
						team.getLosePointMessage().put("global", gameAsYaml.isSet("teams." + key + ".messages.lose_points_message.global") ?
								(String) gameAsYaml.getValues(true).get("teams." + key + ".messages.leave_message.global") : game.getJoinMessage().get("global"));
						team.getLosePointMessage().put("player", gameAsYaml.isSet("teams." + key + ".messages.lose_points_message.player") ?
								(String) gameAsYaml.getValues(true).get("teams." + key + ".messages.leave_message.player") : game.getJoinMessage().get("player"));

						team.setSpawn(new Location(Bukkit.getWorld(teamWorld), teamSpawnX, teamSpawnY, teamSpawnZ));
						game.addTeam(team);
					}
				}
				Game.getGames().put(gameName, game);
				info("Game \"" + gameName + "\" has been loaded!");
			} else {
				error("Please don't put any folders in the \"Games\" folder: " + gameFile.getName());
			}
		}
	}

	public void onEnable() {
		instance = this;
		if (getServer().getPluginManager().getPlugin("Skript") != null) {
			addon = Skript.registerAddon(this);
			try {
				addon.loadClasses("com.alexlew.gameapi.skript");
				addon.loadClasses("com.alexlew.gameapi.types");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// GameAPI folder creation
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
			saveDefaultConfig();
		}

		// Games folder creation
		if (!gamesFolder.exists()) gamesFolder.mkdir();

		// Players folder creation
		if (!usersFolder.exists()) usersFolder.mkdir();

		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
		messages = (Boolean) config.get("messages");
		manage_automatically = (Boolean) config.get("manage_automatically");

		// Events Register
		new GameReady(this);
		new GameCanStart(this);
		new GameCreated(this);
		new GameDeleted(this);
		new TeamWinPoint(this);
		new TeamLosePoint(this);
		new TeamCreated(this);
		new TeamDeleted(this);
		new PlayerQuit(this);
		new PlayerJoinGame(this);
		new PlayerLeaveGame(this);
		new PlayerJoinTeam(this);
		new PlayerLeaveTeam(this);
		new Signs(this);

		// Commands register
		getCommand("game").setExecutor(new CommandGameSpigot());
		getCommand("team").setExecutor(new CommandTeamSpigot());

		// Load all games saved
		loadGames();

		// Register Metrics
		Metrics metrics = new Metrics(this);
		metrics.addCustomChart(new Metrics.SimplePie("used_language", () ->
				getConfig().getString("language", "en")));
		metrics.addCustomChart(new Metrics.SimplePie("skript_version", () ->
				Bukkit.getServer().getPluginManager().getPlugin("Skript").getDescription().getVersion()));
		metrics.addCustomChart(new Metrics.SimplePie("skemail_version", () ->
				this.getDescription().getVersion()));
		metrics.addCustomChart(new Metrics.DrilldownPie("java_version", () -> {
			Map<String, Map<String, Integer>> map = new HashMap<>();
			String javaVersion = System.getProperty("java.version");
			Map<String, Integer> entry = new HashMap<>();
			entry.put(javaVersion, 1);
			if (javaVersion.startsWith("1.7")) {
				map.put("Java 1.7", entry);
			} else if (javaVersion.startsWith("1.8")) {
				map.put("Java 1.8", entry);
			} else if (javaVersion.startsWith("1.9")) {
				map.put("Java 1.9", entry);
			} else {
				map.put("Other", entry);
			}
			return map;
		}));

		// Version checker
		if (getDescription().getVersion().contains("beta")) {
			info("You're using a BETA version of GameAPI. Be careful with it, features in can change in the future.");
		} else {
			try {
				String version = readToString("https://raw.githubusercontent.com/AlexLew95/GameAPI/master/version");
				if (!version.equals(getDescription().getVersion())) {
					info("A new version of GameAPI is available (GameAPI version " + version + "). You can download it here: https://github.com/AlexLew95/GameAPI/releases");
				}
			} catch (IOException e) {
				error("Got an error when reading a new version.");
			}
		}

		// Initialize BountifulAPI
		BountifulAPI.nmsver = Bukkit.getServer().getClass().getPackage().getName();
		BountifulAPI.nmsver = BountifulAPI.nmsver.substring(BountifulAPI.nmsver.lastIndexOf(".") + 1);

		if (BountifulAPI.nmsver.equalsIgnoreCase("v1_8_R1") || BountifulAPI.nmsver.equalsIgnoreCase("v1_7_")) { // Not sure if 1_7 works for the protocol hack?
			BountifulAPI.useOldMethods = true;
		}

	}

}

