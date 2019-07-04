package com.olyno.events;

import com.olyno.GameAPI;
import com.olyno.types.Game;
import com.olyno.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    public PlayerQuit( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        Player player = event.getPlayer();
		if (GameAPI.manage_automatically) {
			for (Game game : Game.getGames().values()) {
				for (Team team : game.getTeams().values()) {
                    if (team.hasPlayer(player)) {
                        team.removePlayer(player);
                    }
                }
				if (game.hasPlayer(player)) {
					game.removePlayer(player);
				}
            }
        }
    }

}
