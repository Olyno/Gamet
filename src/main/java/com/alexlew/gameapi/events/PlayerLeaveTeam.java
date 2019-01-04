package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.PlayerLeaveTeamEvent;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLeaveTeam implements Listener {

    public PlayerLeaveTeam( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public PlayerLeaveTeam( Player player ) {
        Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveTeamEvent(player));
    }

    @EventHandler
    public void onPlayerLeaveTeam( PlayerLeaveTeamEvent event ) {
        Game game = event.getGame();
        Team team = event.getTeam();
        Player player = event.getPlayer();

        if (GameAPI.manageAutomatically) {
            if (game.getSpawn() != null) {
                player.teleport(game.getSpawn());
            }

            String displayName = game.getDisplayName();
            String joinMessageAllPlayers = game.getJoinMessageAllPlayers();
            String joinMessagePlayer = game.getJoinMessagePlayer();
            joinMessageAllPlayers = joinMessageAllPlayers.replaceAll("\\$\\{player}", player.getDisplayName());
            joinMessageAllPlayers = joinMessageAllPlayers.replaceAll("\\$\\{game}", game.getDisplayName());
            joinMessageAllPlayers = joinMessageAllPlayers.replaceAll("\\$\\{team}", team.getDisplayName());
            joinMessagePlayer = joinMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
            joinMessagePlayer = joinMessagePlayer.replaceAll("\\$\\{game}", game.getDisplayName());
            joinMessagePlayer = joinMessagePlayer.replaceAll("\\$\\{team}", team.getDisplayName());

            for (Player playerInGame : game.getPlayers()) {
                if (player.getAddress() != playerInGame.getAddress()) {
                    playerInGame.sendMessage(displayName + joinMessageAllPlayers);
                }
            }
            player.sendMessage(displayName + joinMessagePlayer);
        }
    }

}
