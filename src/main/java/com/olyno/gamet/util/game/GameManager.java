package com.olyno.gamet.util.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class GameManager {

	/**
	 * @return The name of the Game/Team
	 */
	public abstract String getName();

	/**
	 * Set the name of your Game/Team
	 *
	 * @param name The name of your Game/Team
	 */
	public abstract void setName(String name);

	/**
	 * @return The display name of your Game/Team
	 */
	public abstract String getDisplayName();

	/**
	 * Set the display name of your Game/Team
	 *
	 * @param displayName The display name of your Game/Team
	 */
	public abstract void setDisplayName(String displayName);

	/**
	 * @return The minimum of player that your Game/Team require before start
	 */
	public abstract Integer getMinPlayer();

	/**
	 * Set the minimum of player that your Game/Team require before start
	 *
	 * @param minPlayer The minimum of player that your Game/Team require before start
	 */
	public abstract void setMinPlayer(Integer minPlayer);

	/**
	 * @return The maximum of player that your Game/Team require before start
	 */
	public abstract Integer getMaxPlayer();

	/**
	 * Set the maximum of player that your Game/Team require before start
	 *
	 * @param maxPlayer The maximum of player that your Game/Team require before start
	 */
	public abstract void setMaxPlayer(Integer maxPlayer);

	/**
	 * @return Lobby of your Game/Team
	 */
	public abstract Location getLobby();

	/**
	 * Set the location of the lobby of your Game/Team.
	 * It's where players join your Game/Team.
	 *
	 * @param lobby Lobby of your Game/Team
	 */
	public abstract void setLobby(Location lobby);

	/**
	 * @return The spawn of your Game/Team
	 */
	public abstract Location getSpawn();

	/**
	 * Set the location of the spawn of your Game/Team.
	 * It's where players will be teleported when they will join your Game/Team.
	 *
	 * @param spawn The spawn of your Game/Team
	 */
	public abstract void setSpawn(Location spawn);

	/**
	 * @return Players in your Game/Team
	 */
	public abstract List<Player> getPlayers();

	/**
	 * Add a player to your Game/Team
	 *
	 * @param player The player who will join the Game/Team
	 */
	public abstract void addPlayer(Player player);

	/**
	 * Remove a player from your Game/Team
	 *
	 * @param player The player who will leave the Game/Team
	 */
	public abstract void removePlayer(Player player);

	/**
	 * @param player The player to check if he is in the Game/Team
	 * @return If the player is in your Game/Team or not
	 */
	public abstract Boolean hasPlayer(Player player);

	/**
	 * Delete your Game/Team
	 */
	public abstract <T> T delete();

}
