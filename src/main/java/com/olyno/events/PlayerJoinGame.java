package com.olyno.events;

import com.olyno.GameAPI;
import com.olyno.events.bukkit.PlayerJoinGameEvent;
import com.olyno.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinGame implements Listener {

	public PlayerJoinGame(GameAPI plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public PlayerJoinGame(Game game, Player player) {
		Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinGameEvent(game, player));
	}

	@EventHandler
	public void onPlayerJoinGame(PlayerJoinGameEvent event) {
		Player player = event.getPlayer();
		Game game = event.getGame();

		if (GameAPI.messages) {
			String displayName = game.getDisplayName();
			String joinMessageGlobal = game.getJoinMessage().get("global");
			String joinMessagePlayer = game.getJoinMessage().get("player");
			joinMessageGlobal = joinMessageGlobal.replaceAll("\\$\\{player}", player.getDisplayName());
			joinMessageGlobal = joinMessageGlobal.replaceAll("\\$\\{game}", game.getDisplayName());
			joinMessagePlayer = joinMessagePlayer.replaceAll("\\$\\{player}", player.getDisplayName());
			joinMessagePlayer = joinMessagePlayer.replaceAll("\\$\\{game}", game.getDisplayName());

			for (Player playerInGame : game.getPlayers()) {
				if (player.getAddress() != playerInGame.getAddress()) {
					playerInGame.sendMessage(displayName + joinMessageGlobal);
				}
			}
			player.sendMessage(displayName + joinMessagePlayer);
		}
		if (game.getPlayers().size() == game.getMinPlayer()) {
			new GameCanStart(game);
		}
		if (game.getPlayers().size() == game.getMaxPlayer()) {
			new GameReady(game);
		}
	}

}
