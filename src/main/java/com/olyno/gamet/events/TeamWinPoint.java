package com.olyno.gamet.events;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.bukkit.TeamWinPointEvent;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.types.Point;
import com.olyno.gamet.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamWinPoint implements Listener {

    public TeamWinPoint( Gamet plugin ) {
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
		String winPointMessageGlobal = team.getWinPointMessage().get("global");
		String winPointMessagePlayer = team.getWinPointMessage().get("player");
        if (player != null) {
			winPointMessageGlobal = winPointMessageGlobal.replaceAll("\\$\\{player}", player.getDisplayName());
            winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
		}
		if (team != null) {
			winPointMessageGlobal = winPointMessageGlobal.replaceAll("\\$\\{team}", team.getDisplayName());
			winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{team}", team.getDisplayName());
        }
        if (points != null) {
			winPointMessageGlobal = winPointMessageGlobal.replaceAll("\\$\\{points}", points.toString());
            winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{points}", points.toString());
        }

		winPointMessageGlobal = winPointMessageGlobal.replaceAll("\\$\\{player}", "");
		winPointMessageGlobal = winPointMessageGlobal.replaceAll("\\$\\{points}", "");
		winPointMessageGlobal = winPointMessageGlobal.replaceAll("\\$\\{team}", "");
		winPointMessageGlobal = winPointMessageGlobal.replaceAll("\\$\\{game}", game.getDisplayName());

        winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{player}", "");
        winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{points}", "");
        winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{team}", "");
        winPointMessagePlayer = winPointMessagePlayer.replaceAll("\\$\\{game}", game.getDisplayName());

        for (Player playerInGame : game.getPlayers()) {
            if (player != null) {
                if (player.getAddress() != playerInGame.getAddress()) {
					playerInGame.sendMessage(displayName + winPointMessageGlobal);
                }
            } else {
				playerInGame.sendMessage(displayName + winPointMessageGlobal);
            }

        }
        if (player != null) {
            player.sendMessage(displayName + winPointMessagePlayer);
        }
    }

}
