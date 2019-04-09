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
            String leaveMessageAllPlayers = game.getLeaveMessageAllPlayers();
            String leaveMessagePlayer = game.getLeaveMessagePlayer();
            leaveMessageAllPlayers = leaveMessageAllPlayers.replaceAll("\\$\\{player}", player.getDisplayName());
            leaveMessageAllPlayers = leaveMessageAllPlayers.replaceAll("\\$\\{game}", game.getDisplayName());
            leaveMessageAllPlayers = leaveMessageAllPlayers.replaceAll("\\$\\{team}", team.getDisplayName());
            leaveMessagePlayer = leaveMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
            leaveMessagePlayer = leaveMessagePlayer.replaceAll("\\$\\{game}", game.getDisplayName());
            leaveMessagePlayer = leaveMessagePlayer.replaceAll("\\$\\{team}", team.getDisplayName());

            for (Player playerInGame : game.getPlayers()) {
                if (player.getAddress() != playerInGame.getAddress()) {
                    playerInGame.sendMessage(displayName + leaveMessageAllPlayers);
                }
            }
            player.sendMessage(displayName + leaveMessagePlayer);
        }
    }

}
