package com.alexlew.gameapi.util;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.GameStarted;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.regex.Pattern;

public class GameRunner extends Thread implements Listener {

	private Game game;
	private Team winner;

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

		while (game.getState() == 2) {
			for (Team team : game.getTeams().values()) {
				if (team.getPoints().getPoints().equals(team.getObjective())) {
					game.setState(3);
				}
			}
		}

		getWinner();

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

		game.setState(0);
		game.setWinner(null);

	}

	private void launch() throws InterruptedException {
		for (Integer index = game.getTimer(); index > 0; index--) {
			if (game.getState() == 1) { // Check if we continue to launch the game
				if (GameAPI.messages) { // If user let us manage messages
					if (game.getTimerMessages().containsKey(index)) { // If a message at this time is set
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
				}
				Thread.sleep(1000);
			} else {
				return;
			}
		}
		for (Team team : game.getTeams().values()) {
			for (Player player : team.getPlayers()) {
				player.teleport(team.getSpawn());
			}
		}
		game.setState(2);
		new GameStarted(game);
		GameAPI.getInstance().getServer().getPluginManager().registerEvents(this, GameAPI.getInstance());
	}

	private Team getWinner() {
		if (game.getWinner() == null) {
			for (Team team : game.getTeams().values()) {
				if (winner == null) {
					winner = team;
				} else if (winner.getPoints().getPoints() < team.getPoints().getPoints()) {
					winner = team;
				}
			}
			return winner;
		} else {
			return game.getWinner();
		}

	}

	@EventHandler
	public void onDeath(PlayerRespawnEvent event) {
		if (game.getState() == 2) {
			event.setRespawnLocation(Game.getTeamOfPlayer(event.getPlayer()).getSpawn());
		}
	}
}