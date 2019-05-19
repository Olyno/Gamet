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

	public PlayerLeaveTeam(Team team, Player player) {
		Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveTeamEvent(team, player));
    }

    @EventHandler
    public void onPlayerLeaveTeam( PlayerLeaveTeamEvent event ) {
        Game game = event.getGame();
        Team team = event.getTeam();
        Player player = event.getPlayer();

		if (GameAPI.messages) {
            if (game.getSpawn() != null) {
                player.teleport(game.getSpawn());
            }

            String displayName = game.getDisplayName();
			String leaveMessageGlobal = game.getLeaveMessage().get("global");
			String leaveMessagePlayer = game.getLeaveMessage().get("player");
			leaveMessageGlobal = leaveMessageGlobal.replaceAll("\\$\\{player}", player.getDisplayName());
			leaveMessageGlobal = leaveMessageGlobal.replaceAll("\\$\\{game}", game.getDisplayName());
			leaveMessageGlobal = leaveMessageGlobal.replaceAll("\\$\\{team}", team.getDisplayName());
            leaveMessagePlayer = leaveMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
            leaveMessagePlayer = leaveMessagePlayer.replaceAll("\\$\\{game}", game.getDisplayName());
            leaveMessagePlayer = leaveMessagePlayer.replaceAll("\\$\\{team}", team.getDisplayName());

            for (Player playerInGame : game.getPlayers()) {
                if (player.getAddress() != playerInGame.getAddress()) {
					playerInGame.sendMessage(displayName + leaveMessageGlobal);
                }
            }
            player.sendMessage(displayName + leaveMessagePlayer);
        }

		if (GameAPI.manage_automatically) {
			boolean stop = false;
			for (Team currentTeam : team.getGame().getTeams().values()) {
				if (currentTeam.getPlayers().size() < currentTeam.getMinPlayer()) {
					stop = true;
				}
			}
			if (stop) {
				team.getGame().stop();
			}
		}
    }

}
