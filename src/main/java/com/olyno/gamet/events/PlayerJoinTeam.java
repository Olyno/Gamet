package com.olyno.gamet.events;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.bukkit.PlayerJoinTeamEvent;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinTeam implements Listener {

    public PlayerJoinTeam( Gamet plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public PlayerJoinTeam( Team team, Player player ) {
        Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinTeamEvent(team, player));
    }

    @EventHandler
    public void onPlayerJoinTeam( PlayerJoinTeamEvent event ) {
        Game game = event.getGame();
        Team team = event.getTeam();
        Player player = event.getPlayer();

        if (Gamet.messages) {
            String displayName = game.getDisplayName();
            String joinMessageGlobal = game.getJoinMessage().get("global");
            String joinMessagePlayer = game.getJoinMessage().get("player");
            joinMessageGlobal = joinMessageGlobal.replaceAll("\\$\\{player}", player.getDisplayName());
            joinMessageGlobal = joinMessageGlobal.replaceAll("\\$\\{game}", game.getDisplayName());
            joinMessageGlobal = joinMessageGlobal.replaceAll("\\$\\{team}", team.getDisplayName());
            joinMessagePlayer = joinMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
            joinMessagePlayer = joinMessagePlayer.replaceAll("\\$\\{game}", game.getDisplayName());
            joinMessagePlayer = joinMessagePlayer.replaceAll("\\$\\{team}", team.getDisplayName());

            for (Player playerInGame : game.getPlayers()) {
                if (player.getAddress() != playerInGame.getAddress()) {
                    playerInGame.sendMessage(displayName + joinMessageGlobal);
                }
            }
            player.sendMessage(displayName + joinMessagePlayer);
        }

        if (Gamet.manage_automatically) {
            player.teleport(team.getLobby());
            boolean start = true;
            for (Team currentTeam : team.getGame().getTeams().values()) {
                if (currentTeam.getPlayers().size() < currentTeam.getMinPlayer()) {
                    start = false;
                }
            }
            if (start) {
                team.getGame().start();
            }
        }
    }

}
