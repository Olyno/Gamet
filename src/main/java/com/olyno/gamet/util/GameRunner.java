package com.olyno.gamet.util;

import java.util.ArrayList;
import java.util.Optional;

import com.olyno.gamet.Gamet;
import com.olyno.gami.Gami;
import com.olyno.gami.enums.GameMessageTarget;
import com.olyno.gami.enums.GameMessageType;
import com.olyno.gami.enums.GameState;
import com.olyno.gami.models.Game;
import com.olyno.gami.models.GameMessage;
import com.olyno.gami.models.GameTimerMessage;
import com.olyno.gami.models.Team;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class GameRunner extends Thread {

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

		while (game.getState() == GameState.STARTED) {
			for (Team team : game.getTeams().values()) {
				if (team.getTotalPoints().equals(team.getGoal())) {
					game.setState(GameState.ENDED);
				}
			}
		}

		getWinner();

		for (Team team : game.getTeams().values()) {
			for (Object playerObject : team.getPlayers()) {
				Player player = (Player) playerObject;
				player.teleport((Location) game.getLobby());
				for (GameMessage endMessage : game.getMessages(GameMessageType.END, GameMessageTarget.PLAYER)) {
					player.sendMessage(game.getDisplayName() + endMessage.getMessage()
						.replaceAll("\\$\\{winner}", winner.getDisplayName())
						.replaceAll("\\$\\{game}", game.getDisplayName())
						.replaceAll("\\$\\{team}", team.getDisplayName())
						.replaceAll("\\$\\{winnerPoints}", winner.getTotalPoints().toString())
						.replaceAll("\\$\\{teamPoints}", team.getTotalPoints().toString())
					);
				}
			}
		}

		game.setState(GameState.WAITING);
		game.setWinner(null);

	}

	private void launch() throws InterruptedException {
		ArrayList<GameTimerMessage> messages = game.getMessages(GameMessageType.TIMER, GameMessageTarget.GLOBAL);
		for (Integer index = game.getTimer(); index > 0; index --) {
			final Integer i = index;
			if (game.getState() == GameState.START) { // Check if we continue to launch the game
				if (Gamet.messages) { // If user let us manage messages
					GameTimerMessage timerMessage = messages
						.stream()
						.filter(message -> message.getTime() == i)
						.findFirst()
						.get();
					if (timerMessage != null) { // If a message at this time is set
						String message = timerMessage.getMessage();
						message = message.replaceAll("\\$\\{time}", i.toString());
						for (Team team : game.getTeams().values()) {
							for (Object playerObject : team.getPlayers()) {

								Player player = (Player) playerObject;

								// Check with which form we send the message
								switch (game.getTimerMessageAs()) {
									case TITLE:
										BountifulAPI.sendTitle(player, 10, 20, 10, message, null);
										break;
									case SUBTITLE:
										BountifulAPI.sendTitle(player, 10, 20, 10, null, message);
										break;
									case ACTION_BAR:
										BountifulAPI.sendActionBar(player, message, 1000);
										break;
									case MESSAGE:
										player.sendMessage(message);
										break;
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
			for (Object playerObject : team.getPlayers()) {
				Player player = (Player) playerObject;
				player.teleport((Location) team.getSpawn());
			}
		}
		game.setState(GameState.STARTED);
	}

	private Team getWinner() {
		if (game.getWinner() == null) {
			for (Team team : game.getTeams().values()) {
				if (winner == null) {
					winner = team;
				} else if (winner.getTotalPoints() < team.getTotalPoints()) {
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
		Player player = event.getPlayer();
		Optional<Team> team = Gami.getTeamOfPlayer(player);
		if (team.isPresent()) {
			if (game.getState() == GameState.STARTED) {
				event.setRespawnLocation(team.get().getSpawn());
			}
		}
	}
}