package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.TeamWinPointEvent;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Point;
import com.alexlew.gameapi.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamWinPoint implements Listener {

    public TeamWinPoint( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public TeamWinPoint( Point points ) {
        Bukkit.getServer().getPluginManager().callEvent(new TeamWinPointEvent(points));
    }

    @EventHandler
    public void onTeamWinPoint( TeamWinPointEvent event ) {
        Game game = event.getGame();
        Team team = event.getTeam();
        Player player = event.getPlayer();
        Integer points = event.getPoints();

        String displayName = game.getDisplayName();
        String winPointMessageAllPlayers = game.getWinPointMessageAllPlayers();
        String winPointMessagePlayer = game.getWinPointMessagePlayer();
        if (player != null) {
            winPointMessageAllPlayers = winPointMessageAllPlayers.replaceAll("\\$\\{player}", player.getDisplayName());
            winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
            if (team != null) {
                winPointMessageAllPlayers = winPointMessageAllPlayers.replaceAll("\\$\\{team}", team.getDisplayName());
                winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{team}", team.getDisplayName());
            }
        }
        if (points != null) {
            winPointMessageAllPlayers = winPointMessageAllPlayers.replaceAll("\\$\\{points}", points.toString());
            winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{points}", points.toString());
        }

        winPointMessageAllPlayers = winPointMessageAllPlayers.replaceAll("\\$\\{player}", "");
        winPointMessageAllPlayers = winPointMessageAllPlayers.replaceAll("\\$\\{points}", "");
        winPointMessageAllPlayers = winPointMessageAllPlayers.replaceAll("\\$\\{team}", "");
        winPointMessageAllPlayers = winPointMessageAllPlayers.replaceAll("\\$\\{game}", game.getDisplayName());

        winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{player}", "");
        winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{points}", "");
        winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{team}", "");
        winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{game}", game.getDisplayName());

        for (Player playerInGame : game.getPlayers()) {
            if (player != null) {
                if (player.getAddress() != playerInGame.getAddress()) {
                    playerInGame.sendMessage(displayName + winPointMessageAllPlayers);
                }
            } else {
                playerInGame.sendMessage(displayName + winPointMessageAllPlayers);
            }

        }
        if (player != null) {
            player.sendMessage(displayName + winPointMessagePlayer);
        }
    }

}
