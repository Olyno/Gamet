package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.PlayerJoinGameEvent;
import com.alexlew.gameapi.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinGame implements Listener {

    public PlayerJoinGame( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public PlayerJoinGame( Player player ) {
        Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinGameEvent(player));
    }

    @EventHandler
    public void onPlayerJoinGame( PlayerJoinGameEvent event ) {
        Player player = event.getPlayer();
        Game game = event.getGame();

        if (GameAPI.manageAutomatically) {
            if (game.getSpawn() != null) {
                player.teleport(game.getSpawn());
            }

            String displayName = game.getDisplayName();
            String joinMessageAllPlayers = game.getJoinMessageAllPlayers();
            String joinMessagePlayer = game.getJoinMessagePlayer();
            joinMessageAllPlayers = joinMessageAllPlayers.replaceAll("\\$\\{player}", player.getDisplayName());
            joinMessageAllPlayers = joinMessageAllPlayers.replaceAll("\\$\\{game}", game.getDisplayName());
            joinMessagePlayer = joinMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
            joinMessagePlayer = joinMessagePlayer.replaceAll("\\$\\{game}", game.getDisplayName());

            for (Player playerInGame : game.getPlayers()) {
                if (player.getAddress() != playerInGame.getAddress()) {
                    playerInGame.sendMessage(displayName + joinMessageAllPlayers);
                }
            }
            player.sendMessage(displayName + joinMessagePlayer);

            if (game.getPlayers().length == game.getMinPlayer()) {
                new GameCanStart(game);
            }
            if (game.getPlayers().length == game.getMaxPlayer()) {
                new GameReady(game);
            }
        }
    }

}
