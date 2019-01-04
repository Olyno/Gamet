package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
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
        if (GameAPI.manageAutomatically) {
            for (String gameName : Game.games.keySet()) {
                if (Game.games.get(gameName).hasPlayer(player)) {
                    Game.games.get(gameName).removePlayer(player);
                }
                for (Team team : Game.games.get(gameName).getTeams()) {
                    if (team.hasPlayer(player)) {
                        team.removePlayer(player);
                    }
                }
            }
        }
    }

}
