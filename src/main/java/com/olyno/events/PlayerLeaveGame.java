package com.olyno.events;

import com.olyno.GameAPI;
import com.olyno.events.bukkit.PlayerLeaveGameEvent;
import com.olyno.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLeaveGame implements Listener {

    public PlayerLeaveGame( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

	public PlayerLeaveGame(Game game, Player player) {
		Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveGameEvent(game, player));
    }

    @EventHandler
    public void onPlayerLeaveGame( PlayerLeaveGameEvent event ) {
        Player player = event.getPlayer();
        Game game = event.getGame();

		if (GameAPI.messages) {
            String displayName = game.getDisplayName();
			String leaveMessageGlobal = game.getLeaveMessage().get("global");
			String leaveMessagePlayer = game.getLeaveMessage().get("player");
			leaveMessageGlobal = leaveMessageGlobal.replaceAll("\\$\\{player}", player.getDisplayName());
			leaveMessageGlobal = leaveMessageGlobal.replaceAll("\\$\\{game}", displayName);
            leaveMessagePlayer = leaveMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
            leaveMessagePlayer = leaveMessagePlayer.replaceAll("\\$\\{game}", displayName);

            for (Player playerInGame : game.getPlayers()) {
                if (player.getAddress() != playerInGame.getAddress()) {
					playerInGame.sendMessage(displayName + leaveMessageGlobal);
                }
            }
            player.sendMessage(displayName + leaveMessagePlayer);
        }
    }
}
