package com.olyno.gamet.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.bukkit.PlayerJoinTeamEvent;
import com.olyno.gamet.events.bukkit.PlayerLeaveTeamEvent;
import com.olyno.gamet.events.bukkit.SpectatorJoinEvent;
import com.olyno.gamet.events.bukkit.SpectatorLeaveEvent;
import com.olyno.gamet.events.bukkit.TeamCreatedEvent;
import com.olyno.gamet.events.bukkit.TeamDeletedEvent;
import com.olyno.gamet.events.bukkit.TeamLosePointEvent;
import com.olyno.gamet.events.bukkit.TeamReadyEvent;
import com.olyno.gamet.events.bukkit.TeamWinEvent;
import com.olyno.gamet.events.bukkit.TeamWinPointEvent;
import com.olyno.gami.enums.GameMessageTarget;
import com.olyno.gami.enums.GameMessageType;
import com.olyno.gami.listeners.TeamListener;
import com.olyno.gami.models.Game;
import com.olyno.gami.models.GameMessage;
import com.olyno.gami.models.Point;
import com.olyno.gami.models.Team;

public class TeamEvents implements TeamListener {

    @Override
    public void onTeamCreated(Team team) {
        new TeamCreatedEvent(team);
    }

    @Override
    public void onTeamDeleted(Team team) {
        new TeamDeletedEvent(team);
    }

    @Override
    public void onTeamReady(Team team) {
        new TeamReadyEvent(team);
    }

    @Override
    public void onPointWin(Team team, Point points) {
        Game game = team.getGame().get();
        Player player = (Player) points.getAuthor();
        if (Gamet.messages) {
            String displayName = game.getDisplayName();
            for (GameMessage message : team.getMessages(GameMessageType.LOSE_POINT, GameMessageTarget.GLOBAL)) {
                for (Object playerObjectInGame : game.getPlayers()) {
                    Player playerInGame = (Player) playerObjectInGame;
                    if (player.getUniqueId() != playerInGame.getUniqueId()) {
                        playerInGame.sendMessage(displayName + this.formatMessage(message.getMessage(), game, team, playerInGame, points));
                    }
                }
            }
            for (GameMessage message : team.getMessages(GameMessageType.LOSE_POINT, GameMessageTarget.PLAYER)) {
                if (player != null) {
                    player.sendMessage(displayName + this.formatMessage(message.getMessage(), game, team, player, points));
                }
            }
        }
        new TeamWinPointEvent(team, points);
    }

    @Override
    public void onPointLose(Team team, Point points) {
        Player player = (Player) points.getAuthor();
        Game game = team.getGame().get();
        String displayName = game.getDisplayName();
        if (Gamet.messages) {
            for (GameMessage message : team.getMessages(GameMessageType.LOSE_POINT, GameMessageTarget.GLOBAL)) {
                for (Object playerObjectInGame : game.getPlayers()) {
                    Player playerInGame = (Player) playerObjectInGame;
                    if (player.getUniqueId() != playerInGame.getUniqueId()) {
                        playerInGame.sendMessage(displayName + this.formatMessage(message.getMessage(), game, team, playerInGame, null));
                    }
                }
            }
            for (GameMessage message : team.getMessages(GameMessageType.LOSE_POINT, GameMessageTarget.PLAYER)) {
                if (player != null) {
                    player.sendMessage(displayName + this.formatMessage(message.getMessage(), game, team, player, null));
                }
            }
        }
        new TeamLosePointEvent(team, points);
    }

    @Override
    public void onPlayerJoin(Team team, Object p) {
        Game game = team.getGame().get();
        Player player = (Player) p;

        if (Gamet.messages) {
            String displayName = game.getDisplayName();
            for (GameMessage message : team.getMessages(GameMessageType.JOIN, GameMessageTarget.GLOBAL)) {
                for (Object playerObjectInGame : game.getPlayers()) {
                    Player playerInGame = (Player) playerObjectInGame;
                    if (player.getUniqueId() != playerInGame.getUniqueId()) {
                        playerInGame.sendMessage(displayName + this.formatMessage(message.getMessage(), game, team, playerInGame, null));
                    }
                }
            }
            for (GameMessage message : team.getMessages(GameMessageType.JOIN, GameMessageTarget.PLAYER)) {
                player.sendMessage(displayName + this.formatMessage(message.getMessage(), game, team, player, null));
            }
        }

        if (Gamet.manage_automatically) {
            player.teleport((Location) team.getLobby());
            boolean start = true;
            for (Team currentTeam : game.getTeams()) {
                if (currentTeam.getPlayers().size() < currentTeam.getMinPlayer()) {
                    start = false;
                }
            }
            if (start) game.start();
        }
        new PlayerJoinTeamEvent(team, player);
    }

    @Override
    public void onPlayerLeave(Team team, Object p) {
        Game game = team.getGame().get();
        Player player = (Player) p;
        if (Gamet.messages) {
            if (Gamet.manage_automatically) {
                if (game.getSpawn() != null) {
                    player.teleport((Location) game.getSpawn());
                }
            }
            String displayName = game.getDisplayName();
            for (GameMessage message : team.getMessages(GameMessageType.LEAVE, GameMessageTarget.GLOBAL)) {
                for (Object playerObjectInGame : game.getPlayers()) {
                    Player playerInGame = (Player) playerObjectInGame;
                    if (player.getUniqueId() != playerInGame.getUniqueId()) {
                        playerInGame.sendMessage(displayName + this.formatMessage(message.getMessage(), game, team, playerInGame, null));
                    }
                }
            }
            for (GameMessage message : team.getMessages(GameMessageType.LEAVE, GameMessageTarget.PLAYER)) {
                player.sendMessage(displayName + this.formatMessage(message.getMessage(), game, team, player, null));
            }
        }
		if (Gamet.manage_automatically) {
			boolean stop = false;
			for (Team currentTeam : game.getTeams()) {
				if (currentTeam.getPlayers().size() < currentTeam.getMinPlayer()) {
					stop = true;
				}
			}
			if (stop) game.stop();
        }
        new PlayerLeaveTeamEvent(team, player);
    }

    @Override
    public void onSpectatorJoin(Team team, Object player) {
        new SpectatorJoinEvent(team, (Player) player);
    }

    @Override
    public void onSpectatorLeave(Team team, Object player) {
        new SpectatorLeaveEvent(team, (Player) player);
    }

    @Override
    public void onWin(Team team) {
        new TeamWinEvent(team);
    }

    private String formatMessage(String entryMessage, Game game, Team team, Player player, Point point) {
        if (player != null)
            entryMessage = entryMessage.replaceAll("\\$\\{player}", player.getDisplayName());
        if (game != null)
            entryMessage = entryMessage.replaceAll("\\$\\{game}", game.getDisplayName());
        if (team != null)
            entryMessage = entryMessage.replaceAll("\\$\\{team}", team.getDisplayName());
        if (point != null)
            entryMessage = entryMessage.replaceAll("\\$\\{point}", "" + point.getPoints());
        return entryMessage
            .replaceAll("\\$\\{player}", "")
            .replaceAll("\\$\\{game}", "")
            .replaceAll("\\$\\{team}", "")
            .replaceAll("\\$\\{point}", "");
    }
    
}
