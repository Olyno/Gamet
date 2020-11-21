package com.olyno.gamet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.olyno.gamet.commands.CommandSpigot;
import com.olyno.gamet.events.GameEvents;
import com.olyno.gamet.events.TeamEvents;
import com.olyno.gamet.util.BountifulAPI;
import com.olyno.gamet.util.Metrics;
import com.olyno.gamet.util.PackageLoader;
import com.olyno.gamet.util.Registration;
import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;
import com.olyno.gami.enums.FileFormat;
import com.olyno.gami.models.Game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class Gamet extends JavaPlugin {

	public static Boolean messages = true;
	public static Boolean manage_automatically = true;
	public static ArrayList<GameCommand> commands;

	public static Gamet instance;
	public List<Registration> expressions = new ArrayList<>();
	SkriptAddon addon;

	private final String dataFolderPath = getDataFolder().toPath().toString();

	private final Path gamesFolder = Paths.get(dataFolderPath, "games");
	private final Path playersFoler = Paths.get(dataFolderPath, "players");

	public void onEnable() {
		instance = this;
		if (getServer().getPluginManager().getPlugin("Skript") != null) {
			addon = Skript.registerAddon(this);
			try {
				addon.loadClasses("com.olyno.gamet.skript");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// GameAPI folder creation
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
			saveDefaultConfig();
		}

		try {
			// Games folder creation
			if (!Files.exists(gamesFolder))
				Files.createDirectory(gamesFolder);

			// Players folder creation
			if (!Files.exists(playersFoler))
				Files.createDirectory(playersFoler);
		} catch (IOException ex) {
			error("Can't create the game folder or the players folder. Please create an issue with this error:");
			ex.printStackTrace();
		}

		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
		messages = (Boolean) config.get("messages");
		manage_automatically = (Boolean) config.get("manage_automatically");

		// Events Register
		Gami.registerGameListener(new GameEvents());
		Gami.registerTeamListener(new TeamEvents());
		new PackageLoader<Listener>("com.olyno.gamet.events.bukkit", "register events").getList()
			.thenAccept(events -> {
				for (Listener evt : events) {
					getServer().getPluginManager().registerEvents(evt, this);
				}
			});

		// Commands register
		CommandSpigot commands = new CommandSpigot();
		getCommand("gamet").setExecutor(commands);
		getCommand("game").setExecutor(commands);
		getCommand("team").setExecutor(commands);

		// Load all games saved
		loadGames();

		// Register Metrics
		Metrics metrics = new Metrics(this);
		metrics.addCustomChart(new Metrics.SimplePie("used_language", () ->
				getConfig().getString("language", "en")));
		metrics.addCustomChart(new Metrics.SimplePie("skript_version", () ->
				Bukkit.getServer().getPluginManager().getPlugin("Skript").getDescription().getVersion()));
		metrics.addCustomChart(new Metrics.SimplePie("gamet_version", () ->
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
			info("You're using a BETA version of Gamet. Be careful with it, features in can change in the future.");
		} else {
			try {
				String version = readToString("https://raw.githubusercontent.com/Olyno/Gamet/master/version");
				if (!version.equals(getDescription().getVersion())) {
					info("A new version of Gamet is available (Gamet version " + version + "). You can download it here: https://github.com/Olyno/Gamet/releases");
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

	public void onDisable() {
		for (Game game : Gami.getGames().values()) {
			game.save(Paths.get(dataFolderPath, "games", game.getName()), FileFormat.YAML);
		}
	}

	/**
	 * Load all games saved or added
	 */
	public void loadGames() {
		try {
			Files.walkFileTree(Paths.get(dataFolderPath, "games"), new SimpleFileVisitor<Path>(){
			
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					String extension = file.getFileName().toString().split(".")[1];
					if (extension == FileFormat.YAML.name().toLowerCase()) {
						Gami.loadGame(file);
					}
					return FileVisitResult.CONTINUE;
				}
	
			});
		} catch (IOException ex) {
			error("Something was wrong when trying to load games. Please create an issue with this error:");
			ex.printStackTrace();
		}
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

	public static void error(String error) {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Gamet] " + error + " ");
	}

	public static void info(String info) {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[Gamet] " + info + " ");
	}

}

