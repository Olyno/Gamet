package com.alexlew.gameapi;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.alexlew.gameapi.commands.CommandGameSpigot;
import com.alexlew.gameapi.commands.CommandTeamSpigot;
import com.alexlew.gameapi.events.*;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import com.alexlew.gameapi.util.Registration;
import com.jcabi.aspects.Async;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameAPI extends JavaPlugin {

    private static File gameapiFolder;
    public static Boolean manageAutomatically = true;
    private File gamesFolder = new File(getDataFolder(), "Games");
    private File usersFolder = new File(getDataFolder(), "Users");

    static GameAPI instance;
    public List<Registration> expressions = new ArrayList<>();
    SkriptAddon addon;

    public static void error(String error) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[GameAPI] " + error + " ");
    }

    public static void info( String info ) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[GameAPI] " + info + " ");
    }

    public static GameAPI getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
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
        manageAutomatically = (Boolean) config.get("manage_automatically");

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
		new PlayerDie(this);
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

    }

    public void onDisable() {
        for (String mg : Game.games.keySet()) {
            saveAsYaml(Game.games.get(mg));
        }
    }

    /**
     * Save your minis games as yaml file
     * @param game The mini game you want to save
     */
    // Save all games
    public void saveAsYaml( Game game ) {
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        if (!gamesFolder.exists()) gamesFolder.mkdir();
        try {
            File gameAsFile = new File(getDataFolder(), "Games/" + game.getName() + ".yml");
            if (gameAsFile.exists()) {
                gameAsFile.delete();
            }
            gameAsFile.createNewFile();
            FileConfiguration gameAsYaml = YamlConfiguration.loadConfiguration(gameAsFile);
            gameAsYaml.set("game.name", game.getName());
            gameAsYaml.set("game.display_name", game.getDisplayName());
            gameAsYaml.set("game.players.minimum", game.getMinPlayer());
            gameAsYaml.set("game.players.maximum", game.getMaxPlayer());
            gameAsYaml.set("game.messages.join_message.all_players", game.getJoinMessageAllPlayers());
            gameAsYaml.set("game.messages.join_message.player", game.getJoinMessagePlayer());
            gameAsYaml.set("game.messages.leave_message.all_players", game.getLeaveMessageAllPlayers());
            gameAsYaml.set("game.messages.leave_message.player", game.getLeaveMessagePlayer());
            gameAsYaml.set("game.messages.states.started", game.getStartedState());
            gameAsYaml.set("game.messages.states.waiting", game.getWaitingState());
            gameAsYaml.set("game.messages.states.unavailable", game.getUnavailableState());
            gameAsYaml.set("game.world.spawns.lobby.WORLD", game.getLobby().getWorld().getName());
            gameAsYaml.set("game.world.spawns.lobby.X", game.getLobby().getX());
            gameAsYaml.set("game.world.spawns.lobby.Y", game.getLobby().getY());
            gameAsYaml.set("game.world.spawns.lobby.Z", game.getLobby().getZ());
            gameAsYaml.set("game.world.spawns.game.WORLD", game.getSpawn().getWorld().getName());
            gameAsYaml.set("game.world.spawns.game.X", game.getSpawn().getX());
            gameAsYaml.set("game.world.spawns.game.Y", game.getSpawn().getY());
            gameAsYaml.set("game.world.spawns.game.Z", game.getSpawn().getZ());
            for (Team team : game.getTeams()) {
                gameAsYaml.set("game.team_" + team.getName() + ".players.minimum", team.getMinPlayer());
                gameAsYaml.set("game.team_" + team.getName() + ".players.maximum", team.getMaxPlayer());
                gameAsYaml.set("game.world.spawns.team_" + team.getName() + ".WORLD", team.getSpawn().getWorld().getName());
                gameAsYaml.set("game.world.spawns.team_" + team.getName() + ".X", team.getSpawn().getX());
                gameAsYaml.set("game.world.spawns.team_" + team.getName() + ".Y", team.getSpawn().getY());
                gameAsYaml.set("game.world.spawns.team_" + team.getName() + ".Z", team.getSpawn().getZ());
            }
            gameAsYaml.save(gameAsFile);
            GameAPI.info("Game \"" + game.getName() + "\" has been saved!");

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
                String gameWorld = gameAsYaml.isSet("game.world.spawns.game.WORLD") ?
                        (String) gameAsYaml.getValues(true).get("game.world.spawns.game.WORLD") : game.getSpawn().getWorld().getName();
                double gameX = gameAsYaml.isSet("game.world.spawns.game.X") ?
                        (double) gameAsYaml.getValues(true).get("game.world.spawns.game.X") : game.getSpawn().getX();
                double gameY = gameAsYaml.isSet("game.world.spawns.game.Y") ?
                        (double) gameAsYaml.getValues(true).get("game.world.spawns.game.Y") : game.getSpawn().getY();
                double gameZ = gameAsYaml.isSet("game.world.spawns.game.Z") ?
                        (double) gameAsYaml.getValues(true).get("game.world.spawns.game.Z") : game.getSpawn().getZ();
                String lobbyWorld = gameAsYaml.isSet("game.world.spawns.lobby.WORLD") ?
                        (String) gameAsYaml.getValues(true).get("game.world.spawns.lobby.WORLD") : game.getLobby().getWorld().getName();
                double lobbyX = gameAsYaml.isSet("game.world.spawns.lobby.X") ?
                        (double) gameAsYaml.getValues(true).get("game.world.spawns.lobby.X") : game.getLobby().getX();
                double lobbyY = gameAsYaml.isSet("game.world.spawns.lobby.Y") ?
                        (double) gameAsYaml.getValues(true).get("game.world.spawns.lobby.Y") : game.getLobby().getY();
                double lobbyZ = gameAsYaml.isSet("game.world.spawns.lobby.Z") ?
                        (double) gameAsYaml.getValues(true).get("game.world.spawns.lobby.Z") : game.getLobby().getZ();

                // Register datas
                game.setName(gameAsYaml.isSet("game.name") ?
                        (String) gameAsYaml.getValues(true).get("game.name") : game.getName());
                game.setDisplayName(gameAsYaml.isSet("game.display_name") ?
                        (String) gameAsYaml.getValues(true).get("game.display_name") : game.getName());
                game.setWorld(Bukkit.getWorld(gameWorld));
                game.setLobby(new Location(Bukkit.getWorld(lobbyWorld), lobbyX, lobbyY, lobbyZ));
                game.setSpawn(new Location(Bukkit.getWorld(gameWorld), gameX, gameY, gameZ));
                game.setMinPlayer(gameAsYaml.isSet("game.players.minimum") ?
                        (Integer) gameAsYaml.getValues(true).get("game.players.minimum") : game.getMinPlayer());
                game.setMaxPlayer(gameAsYaml.isSet("game.players.maximum") ?
                        (Integer) gameAsYaml.getValues(true).get("game.players.maximum") : game.getMaxPlayer());
                game.setJoinMessageAllPlayers(gameAsYaml.isSet("game.messages.join_message.all_players") ?
                        (String) gameAsYaml.getValues(true).get("game.messages.join_message.all_players") : game.getJoinMessageAllPlayers());
                game.setJoinMessagePlayer(gameAsYaml.isSet("game.messages.join_message.player") ?
                        (String) gameAsYaml.getValues(true).get("game.messages.join_message.player") : game.getJoinMessagePlayer());
                game.setLeaveMessageAllPlayers(gameAsYaml.isSet("game.messages.leave_message.all_players") ?
                        (String) gameAsYaml.getValues(true).get("game.messages.leave_message.all_players") : game.getLeaveMessageAllPlayers());
                game.setLeaveMessagePlayer(gameAsYaml.isSet("game.messages.leave_message.player") ?
                        (String) gameAsYaml.getValues(true).get("game.messages.leave_message.player") : game.getLeaveMessagePlayer());
                game.setStartedState(gameAsYaml.isSet("game.messages.states.started") ?
                        (String) gameAsYaml.getValues(true).get("game.messages.states.started") : game.getStartedState());
                game.setWaitingState(gameAsYaml.isSet("game.messages.states.waiting") ?
                        (String) gameAsYaml.getValues(true).get("game.messages.states.waiting") : game.getWaitingState());
                game.setUnavailableState(gameAsYaml.isSet("game.messages.states.unavailable") ?
                        (String) gameAsYaml.getValues(true).get("game.messages.states.unavailable") : game.getUnavailableState());

                // Teams part
                if (gameAsYaml.isSet("game.world.spawns")) {
                    for (String key : gameAsYaml.getConfigurationSection("game.world.spawns").getKeys(false)) {
                        String teamName = key.replaceAll("team_", "");
                        Team team = new Team(teamName);
                        String world = gameAsYaml.isSet("game.world.spawns." + key + ".WORLD") ?
                                (String) gameAsYaml.getValues(true).get("game.world.spawns." + key + ".WORLD") : team.getSpawn().getWorld().getName();
                        double spawnX1 = gameAsYaml.isSet("game.world.spawns." + key + ".X") ?
                                (double) gameAsYaml.getValues(true).get("game.world.spawns." + key + ".X") : team.getSpawn().getX();
                        double spawnY1 = gameAsYaml.isSet("game.world.spawns." + key + ".Y") ?
                                (double) gameAsYaml.getValues(true).get("game.world.spawns." + key + ".Y") : team.getSpawn().getY();
                        double spawnZ1 = gameAsYaml.isSet("game.world.spawns." + key + ".Z") ?
                                (double) gameAsYaml.getValues(true).get("game.world.spawns." + key + ".Z") : team.getSpawn().getZ();
                        Integer minPlayer = gameAsYaml.isSet("game." + key + ".players.minimum") ?
                                (Integer) gameAsYaml.getValues(true).get("game." + key + ".players.minimum") : team.getMinPlayer();
                        Integer maxPlayer = gameAsYaml.isSet("game." + key + ".players.maximum") ?
                                (Integer) gameAsYaml.getValues(true).get("game." + key + ".players.maximum") : team.getMaxPlayer();

                        team.setSpawn(new Location(Bukkit.getWorld(world), spawnX1, spawnY1, spawnZ1));
                        team.setMinPlayer(minPlayer);
                        team.setMaxPlayer(maxPlayer);
                        game.addTeam(team);
                    }
                }
                Game.games.put(gameName, game);
				info("Game \"" + gameName + "\" has been loaded!");
            } else {
                error("Please don't put any folders in the \"Games\" folder: " + listOfGames[0].getName());
            }
        }
    }

}

