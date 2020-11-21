package com.olyno.gamet.events.bukkit;

import com.olyno.gamet.Gamet;
import com.olyno.gami.Gami;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    public PlayerQuit( ) { }

    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        Player player = event.getPlayer();
		if (Gamet.manage_automatically) {
            Gami.getGameOfPlayer(player).ifPresent(game -> game.removePlayer(player));
            Gami.getTeamOfPlayer(player).ifPresent(team -> team.removePlayer(player));
        }
    }

}
