package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.PlayerLeaveGameEvent;
import com.alexlew.gameapi.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLeaveGame implements Listener {

    public PlayerLeaveGame( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public PlayerLeaveGame( Player player ) {
        Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveGameEvent(player));
    }

    @EventHandler
    public void onPlayerLeaveGame( PlayerLeaveGameEvent event ) {
        Player player = event.getPlayer();
        Game game = event.getGame();

        if (GameAPI.manageAutomatically) {
            if (game.getLobby() != null) {
                player.teleport(game.getLobby());
            }

            String displayName = game.getDisplayName();
            String leaveMessageAllPlayers = game.getLeaveMessageAllPlayers();
            String leaveMessagePlayer = game.getLeaveMessagePlayer();
            leaveMessageAllPlayers = leaveMessageAllPlayers.replaceAll("\\$\\{player}", player.getDisplayName());
            leaveMessageAllPlayers = leaveMessageAllPlayers.replaceAll("\\$\\{game}", displayName);
            leaveMessagePlayer = leaveMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
            leaveMessagePlayer = leaveMessagePlayer.replaceAll("\\$\\{game}", displayName);

            for (Player playerInGame : game.getPlayers()) {
                if (player.getAddress() != playerInGame.getAddress()) {
                    playerInGame.sendMessage(displayName + leaveMessageAllPlayers);
                }
            }
            player.sendMessage(displayName + leaveMessagePlayer);
        }
    }
}
