package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDie implements Listener {

	public PlayerDie(GameAPI plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerQuit(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Game game = Game.getGameOfPlayer(player);
		Team team = game.getTeamOfPlayer(player);

		if (GameAPI.manageAutomatically) {
			if (team != null) {
				player.teleport(team.getSpawn());
			} else {
				player.teleport(game.getSpawn());
			}
		}

	}

}
