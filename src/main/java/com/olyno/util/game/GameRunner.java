package com.olyno.util.game;

import com.olyno.GameAPI;
import com.olyno.events.GameStarted;
import com.olyno.events.GameStopped;
import com.olyno.types.Game;
import com.olyno.types.Team;
import com.olyno.util.BountifulAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.regex.Pattern;

public class GameRunner extends Thread implements Listener {

	private Game game;

	public GameRunner(Game game) {
		this.game = game;
	}

	@Override
	public void run() {

		try {
			launch();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (game.getState() == GameStatus.PROCESSING) {
			for (Team team : game.getTeams().values()) {
				if (team.getPoints().getPoints().equals(team.getObjective())) {
					new GameStopped(game);
				}
			}
		}

		Team winner = game.getWinner();

		for (Team team : game.getTeams().values()) {
			for (Player player : team.getPlayers()) {
				player.teleport(game.getLobby());
				player.sendMessage(game.getDisplayName() + game.getEndMessage().get("global")
						.replaceAll("\\$\\{winner}", winner.getDisplayName())
						.replaceAll("\\$\\{game}", game.getDisplayName())
						.replaceAll("\\$\\{team}", team.getDisplayName())
						.replaceAll("\\$\\{winnerPoints}", winner.getPoints().getPoints().toString())
						.replaceAll("\\$\\{teamPoints}", team.getPoints().getPoints().toString())
				);
			}
		}

		game.setWinner(null);
		game.setState(GameStatus.WAITING);

	}

	private void launch() throws InterruptedException {
		for (Integer index = game.getTimer(); index > 0; index--) {
			if (game.getState() == GameStatus.STARTED) { // Check if we continue to launch the game
				if (game.getTimerMessages().containsKey(index)) { // If a message at this time is set
					if (GameAPI.messages) { // If user let us manage messages
						String message = game.getTimerMessages().get(index);
						message = message.replaceAll("\\$\\{time}", index.toString());
						for (Team team : game.getTeams().values()) {
							for (Player player : team.getPlayers()) {

								// Check with which form we send the message
								if (Pattern.compile("message").matcher(game.getTimerMessageAs()).find()) {
									player.sendMessage(message);
								} else if (Pattern.compile("action[_ ]bar").matcher(game.getTimerMessageAs()).find()) {
									BountifulAPI.sendActionBar(player, message, 1000);
								} else if (Pattern.compile("title").matcher(game.getTimerMessageAs()).find()) {
									BountifulAPI.sendTitle(player, 10, 20, 10, message, null);
								}

							}
						}
					}

					if (GameAPI.sounds) { // If user let us manage sounds
						GameSound sound = game.getTimerSounds().get(index);
						for (Team team : game.getTeams().values()) {
							for (Player player : team.getPlayers()) {
								player.playSound(player.getLocation(), sound.getSound(), sound.getVolume(), sound.getPitch());
							}
						}
					}

				}
				Thread.sleep(1000);
			} else {
				new GameStopped(game);
				game.setState(GameStatus.WAITING);
				return;
			}
		}
		for (Team team : game.getTeams().values()) {
			for (Player player : team.getPlayers()) {
				player.teleport(team.getSpawn());
			}
		}
		new GameStarted(game);
		GameAPI.getInstance().getServer().getPluginManager().registerEvents(this, GameAPI.getInstance());
	}

	@EventHandler
	public void onDeath(PlayerRespawnEvent event) {
		if (game.getState() == GameStatus.PROCESSING) {
			event.setRespawnLocation(Game.getTeamOfPlayer(event.getPlayer()).getSpawn());
		}
	}
}