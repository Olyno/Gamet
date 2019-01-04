package com.alexlew.gameapi.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.alexlew.gameapi.events.GameCreated;
import com.alexlew.gameapi.events.PlayerJoinGame;
import com.alexlew.gameapi.events.PlayerLeaveGame;
import com.alexlew.gameapi.events.TeamDeleted;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {

    public static HashMap<String, Game> games = new HashMap<>();

    static {
        Classes.registerClass(new ClassInfo<>(Game.class, "game")
                .defaultExpression(new EventValueExpression<>(Game.class))
                .user("(mini(-| )?)?game")
                .name("CommandGameSpigot Type")
                .description("The current game")
                .examples(
                        "on player join game:",
                        "\tbroadcast \"%event-player% joined the game %event-game%!\""
                )
                .since("2.0")
                .parser(new Parser<Game>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public Game parse( String arg0, ParseContext arg1 ) {
                        return null;
                    }

                    @Override
                    public String toString( Game arg0, int arg1 ) {
                        return arg0.getName();
                    }

                    @Override
                    public String toVariableNameString( Game arg0 ) {
                        return arg0.getName();
                    }

                }));

    }

    private String name;
    private String displayName;
    private Integer minPlayer;
    private Integer maxPlayer;
    private Integer maxPoints;
    private Location lobby;
    private Location spawn;
    private World world;
    private List<Player> players = new ArrayList<>();
    private HashMap<String, Team> teams = new HashMap<>();

    // CommandGameSpigot States
    private String startedState;
    private String waitingState;
    private String unavailableState;
    private Integer currentState;

    // CommandGameSpigot events messages
    private String joinMessageAllPlayers;
    private String leaveMessageAllPlayers;
    private String joinMessagePlayer;
    private String leaveMessagePlayer;
    private String winPointMessageAllPlayers;
    private String winPointMessagePlayer;
    private String losePointMessageAllPlayers;
    private String losePointMessagePlayer;

    /**
     * Create a new game
     * @param name The name of your game
     */
    public Game( String name ) {
        this.name = name;
        this.displayName = name;
        this.world = Bukkit.getWorld("world");
        this.lobby = new Location(world, 0, 0, 0);
        this.spawn = new Location(world, 0, 0, 0);
        this.minPlayer = 1;
        this.maxPlayer = 2;
        this.startedState = "game started";
        this.waitingState = "waiting players";
        this.unavailableState = "game unavailable";
        this.currentState = 0;
        this.joinMessageAllPlayers = "${player} joined the game!";
        this.leaveMessageAllPlayers = "${player} left the game!";
        this.joinMessagePlayer = "You joined the game ${game}";
        this.leaveMessagePlayer = "You left the game ${game}";
        this.winPointMessageAllPlayers = "${player} scored a point for the ${team} team!";
        this.losePointMessageAllPlayers = "${player} lost a point for the ${team} team!";
        this.winPointMessagePlayer = "You scored a point for your ${team} team!";
        this.losePointMessagePlayer = "You lost a point for the opponent ${team} team!";
        new GameCreated(this);
    }

    /**
     * Return the game where the player is.
     * @param player The player that we need to know the game where he is.
     * @return The game where is the player
     */
    public static Game getGameOfPlayer( Player player ) {
        for (String gameName : Game.games.keySet()) {
            if (Game.games.get(gameName).hasPlayer(player)) {
                return Game.games.get(gameName);
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
    public Team getTeamOfPlayer( Player player ) {
        for (Team team : this.getTeams()) {
            if (team.hasPlayer(player)) {
                return team;
            }
        }
        return null;
    }

    /**
     * @return The name of the game
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of your game
     * @param name The name of your game
     */
    public void setName( String name ) {
        this.name = name.replaceAll(" ", "").equals("") ? this.name : name;
    }

    /**
     * @return The display name of your game
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set the display name of your game
     * @param displayName The display name of your game
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
     * @param minPlayer The minimum of player that your game require before start
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
     * @param maxPlayer The maximum of player that your game require before start
     */
    public void setMaxPlayer( Integer maxPlayer ) {
        this.maxPlayer = maxPlayer;
    }

    /**
     * @return Lobby of your game
     */
    public Location getLobby() {
        return lobby;
    }

    /**
     * Set the location of the lobby of your game.
     * It's where players join your game.
     * @param lobby Lobby of your game
     */
    public void setLobby( Location lobby ) {
        this.lobby = lobby;
    }

    /**
     * @return The spawn of your game
     */
    public Location getSpawn() {
        return spawn;
    }

    /**
     * Set the location of the spawn of your game.
     * It's where players will be teleported when they will join your game.
     * @param spawn The spawn of your game
     */
    public void setSpawn( Location spawn ) {
        this.spawn = spawn;
    }

    /**
     * @return Players in your game
     */
    public Player[] getPlayers() {
        Player[] player = new Player[players.size()];
        return players.toArray(player);
    }

    /**
     * Add a player to your game
     * @param player The player who will join the game
     */
    public void addPlayer( Player player ) {
        if (!players.contains(player)) {
            players.add(player);
            new PlayerJoinGame(player);
        }
    }

    public Team[] getTeams() {
        return teams.values().toArray(new Team[teams.values().size()]);
    }

    public Team getTeam( String team ) {
        if (teams.containsKey(team)) {
            return teams.get(team);
        } else {
            return null;
        }
    }

    /**
     * Remove a player from your game
     * @param player The player who will leave the game
     */
    public void removePlayer( Player player ) {
        if (players.contains(player)) {
            new PlayerLeaveGame(player);
        }
        players.remove(player);
    }

    /**
     * Add a team to your game
     * @param team The team which will be added
     */
    public void addTeam( Team team ) {
        teams.put(team.getName(), team);
    }

    /**
     * Remove a team from your game
     * @param team The team which will be removed from your game
     */
    public void removeTeam( String team ) {
        new TeamDeleted(teams.get(team));
        teams.remove(team);
    }

    /**
     * @param team The team that you need to check existence
     * @return If the team exists or not
     */
    public Boolean teamExists( String team ) {
        return teams.containsKey(team);
    }

    /**
     * @param player The player to check if he is in the game
     * @return If the player is in your game or not
     */
    public Boolean hasPlayer( Player player ) {
        return players.contains(player);
    }

    /**
     * @return The "started" message. Will be showed when your game already started
     */
    public String getStartedState() {
        return startedState;
    }

    /**
     * Set the "started" message.
     * Will be showed when your game already started
     * @param startedState The "started" message. Will be showed when your game already started
     */
    public void setStartedState( String startedState ) {
        this.startedState = startedState;
    }

    /**
     * @return The "waiting" message. Will be showed when your game wait more players
     */
    public String getWaitingState() {
        return waitingState;
    }

    /**
     * Set the "waiting" message. W
     * ill be showed when your game wait more players
     * @param waitingState The "waiting" message. Will be showed when your game wait more players
     */
    public void setWaitingState( String waitingState ) {
        this.waitingState = waitingState;
    }

    /**
     * @return The "unavailable" message. Will be showed when your game is not available
     */
    public String getUnavailableState() {
        return unavailableState;
    }

    /**
     * Set the "unavailable" message.
     * Will be showed when your game is not available
     * @param unavailableState The "unavailable" message. Will be showed when your game is not available
     */
    public void setUnavailableState( String unavailableState ) {
        this.unavailableState = unavailableState;
    }

    /**
     * @return The current state of your game
     */
    public Integer getCurrentState() {
        return currentState;
    }

    /**
     * Set a new state to your game
     * @param currentState The new state of your game
     */
    public void setCurrentState( Integer currentState ) {
        this.currentState = currentState;
    }

    /**
     * @return The world of your game
     */
    public World getWorld() {
        return world;
    }

    /**
     * Set the world of your game
     * @param world The world of your game
     */
    public void setWorld( World world ) {
        this.world = world;
    }

    /**
     * @return The "join" message for all players. Will be showed to all players in the game
     * when a new player join your game.
     */
    public String getJoinMessageAllPlayers() {
        return joinMessageAllPlayers;
    }

    /**
     * Set the "join" message for all players.
     * Will be showed to all players in the game when a new player join your game.
     * Variables:
     *     ${player} : the name of the player
     *     ${game} : the name of the game
     * @param joinMessage The "join" message. Will be showed when a new player join your game.
     */
    public void setJoinMessageAllPlayers( String joinMessage ) {
        this.joinMessageAllPlayers = joinMessage;
    }

    /**
     * @return The "leave" message for all players. Will be showed to all players in the game
     * when a player leave your game.
     */
    public String getLeaveMessageAllPlayers() {
        return leaveMessageAllPlayers;
    }

    /**
     * Set the "leave" message for all players.
     * Will be showed when a new player leave your game.
     * Variables:
     * ${player} : the name of the player
     * ${game} : the name of the game
     *
     * @param leaveMessage The "leave" message. Will be showed when a player leave your game.
     */
    public void setLeaveMessageAllPlayers( String leaveMessage ) {
        this.leaveMessageAllPlayers = leaveMessage;
    }

    /**
     * @return The "join" message for the player. Will be showed to the player in the game
     * when he joins your game.
     */
    public String getJoinMessagePlayer() {
        return joinMessagePlayer;
    }

    /**
     * Set the "join" message for the player.
     * Will be showed when the player joins your game.
     * Variables:
     * ${player} : the name of the player
     * ${game} : the name of the game
     *
     * @param joinMessagePlayer The "join" message. Will be showed when the player joins your game.
     */
    public void setJoinMessagePlayer( String joinMessagePlayer ) {
        this.joinMessagePlayer = joinMessagePlayer;
    }

    /**
     * @return The "leave" message for the player. Will be showed to the player in the game
     * when he leaves your game.
     */
    public String getLeaveMessagePlayer() {
        return leaveMessagePlayer;
    }

    /**
     * Set the "leave" message for the player.
     * Will be showed when the player leaves your game.
     * Variables:
     * ${player} : the name of the player
     * ${game} : the name of the game
     *
     * @param leaveMessagePlayer The "leave" message. Will be showed when the player leaves your game.
     */
    public void setLeaveMessagePlayer( String leaveMessagePlayer ) {
        this.leaveMessagePlayer = leaveMessagePlayer;
    }

    /**
     * @return The "win point" message. Will be showed when a player has scored a point
     */
    public String getWinPointMessageAllPlayers() {
        return winPointMessageAllPlayers;
    }

    /**
     * Set the "win point" message for all players.
     * Will be showed when a player has scored a point.
     * Variables:
     * ${player} : the name of the player
     * ${game} : the name of the game
     * ${team} : the name of the team
     *
     * @param winPointMessageAllPlayers The "win point" message. Will be showed when a player has scored a point
     */
    public void setWinPointMessageAllPlayers( String winPointMessageAllPlayers ) {
        this.winPointMessageAllPlayers = winPointMessageAllPlayers;
    }

    /**
     * @return The "win point" message. Will be showed when the player score a point
     */
    public String getWinPointMessagePlayer() {
        return winPointMessagePlayer;
    }

    /**
     * Set the "win point" message for the player.
     * Will be showed when the player score a point.
     * Variables:
     * ${player} : the name of the player
     * ${game} : the name of the game
     * ${team} : the name of the team
     *
     * @param winPointMessagePlayer The "win point" message. Will be showed when the player score a point
     */
    public void setWinPointMessagePlayer( String winPointMessagePlayer ) {
        this.winPointMessagePlayer = winPointMessagePlayer;
    }

    /**
     * @return The "lose point" message. Will be showed when a player lose a point
     */
    public String getLosePointMessageAllPlayers() {
        return losePointMessageAllPlayers;
    }

    /**
     * Set the "lose point" message for all players.
     * Will be showed when a player lose a point.
     * Variables:
     * ${player} : the name of the player
     * ${game} : the name of the game
     * ${team} : the name of the team
     *
     * @param losePointMessageAllPlayers The "win point" message. Will be showed when a player lose a point
     */
    public void setLosePointMessageAllPlayers( String losePointMessageAllPlayers ) {
        this.losePointMessageAllPlayers = losePointMessageAllPlayers;
    }

    /**
     * @return The "lose point" message. Will be showed when the player lose a point
     */
    public String getLosePointMessagePlayer() {
        return losePointMessagePlayer;
    }

    /**
     * Set the "lose point" message for the player.
     * Will be showed when the player lose a point.
     * Variables:
     * ${player} : the name of the player
     * ${game} : the name of the game
     * ${team} : the name of the team
     *
     * @param losePointMessagePlayer The "win point" message. Will be showed when the player lose a point
     */
    public void setLosePointMessagePlayer( String losePointMessagePlayer ) {
        this.losePointMessagePlayer = losePointMessagePlayer;
    }

    /**
     * @return The maximum points to score to finish the game.
     */
    public Integer getMaxPoints() {
        return maxPoints;
    }

    /**
     * Set the amount of points to finish the game.
     *
     * @param maxPoints Amount of points to finish the game.
     */
    public void setMaxPoints( Integer maxPoints ) {
        this.maxPoints = maxPoints;
    }
}
