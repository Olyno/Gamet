package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.TeamLosePointEvent;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Point;
import com.alexlew.gameapi.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamLosePoint implements Listener {

	public TeamLosePoint(GameAPI plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public TeamLosePoint(Point points) {
		Bukkit.getServer().getPluginManager().callEvent(new TeamLosePointEvent(points));
	}

	@EventHandler
	public void onTeamLosePoint(TeamLosePointEvent event) {
		Game game = event.getGame();
		Team team = event.getTeam();
		Player player = event.getPlayer();
		Integer points = event.getPoints();

		String displayName = game.getDisplayName();
		String losePointMessageGlobal = team.getLosePointMessage().get("global");
		String losePointMessagePlayer = team.getLosePointMessage().get("player");
		if (player != null) {
			losePointMessageGlobal = losePointMessageGlobal.replaceAll("\\$\\{player}", player.getDisplayName());
			losePointMessagePlayer = losePointMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
		}
		if (team != null) {
			losePointMessageGlobal = losePointMessageGlobal.replaceAll("\\$\\{team}", team.getDisplayName());
			losePointMessagePlayer = losePointMessagePlayer.replaceAll("\\$\\{team}", team.getDisplayName());
		}
		if (points != null) {
			losePointMessageGlobal = losePointMessageGlobal.replaceAll("\\$\\{points}", points.toString());
			losePointMessagePlayer = losePointMessagePlayer.replaceAll("\\$\\{points}", points.toString());
		}

		losePointMessageGlobal = losePointMessageGlobal.replaceAll("\\$\\{player}", "");
		losePointMessageGlobal = losePointMessageGlobal.replaceAll("\\$\\{points}", "");
		losePointMessageGlobal = losePointMessageGlobal.replaceAll("\\$\\{team}", "");
		losePointMessageGlobal = losePointMessageGlobal.replaceAll("\\$\\{game}", game.getDisplayName());

		losePointMessagePlayer = losePointMessagePlayer.replaceAll("\\$\\{player}", "");
		losePointMessagePlayer = losePointMessagePlayer.replaceAll("\\$\\{points}", "");
		losePointMessagePlayer = losePointMessagePlayer.replaceAll("\\$\\{team}", "");
		losePointMessagePlayer = losePointMessagePlayer.replaceAll("\\$\\{game}", game.getDisplayName());

		for (Player playerInGame : game.getPlayers()) {
			if (player != null) {
				if (player.getAddress() != playerInGame.getAddress()) {
					playerInGame.sendMessage(displayName + losePointMessageGlobal);
				}
			} else {
				playerInGame.sendMessage(displayName + losePointMessageGlobal);
			}

		}
		if (player != null) {
			player.sendMessage(displayName + losePointMessagePlayer);
		}
	}

}
