package com.olyno.gamet.events;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    public PlayerQuit( Gamet plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        Player player = event.getPlayer();
		if (Gamet.manage_automatically) {
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
