package com.olyno.gamet.events;

import java.nio.file.Path;
import java.util.HashMap;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.bukkit.GameCanStartEvent;
import com.olyno.gamet.events.bukkit.GameCreatedEvent;
import com.olyno.gamet.events.bukkit.GameDeletedEvent;
import com.olyno.gamet.events.bukkit.GameLoadedEvent;
import com.olyno.gamet.events.bukkit.GameReadyEvent;
import com.olyno.gamet.events.bukkit.GameSavedEvent;
import com.olyno.gamet.events.bukkit.GameSessionCreated;
import com.olyno.gamet.events.bukkit.GameSessionDeleted;
import com.olyno.gamet.events.bukkit.GameStartedEvent;
import com.olyno.gamet.events.bukkit.GameStoppedEvent;
import com.olyno.gamet.events.bukkit.PlayerJoinGameEvent;
import com.olyno.gamet.events.bukkit.PlayerLeaveGameEvent;
import com.olyno.gamet.events.bukkit.SpectatorJoinEvent;
import com.olyno.gamet.events.bukkit.SpectatorLeaveEvent;
import com.olyno.gamet.events.bukkit.TeamAddedEvent;
import com.olyno.gamet.events.bukkit.TeamRemovedEvent;
import com.olyno.gamet.util.GameRunner;
import com.olyno.gami.enums.FileFormat;
import com.olyno.gami.enums.GameMessageTarget;
import com.olyno.gami.enums.GameMessageType;
import com.olyno.gami.enums.GameState;
import com.olyno.gami.listeners.GameListener;
import com.olyno.gami.models.Game;
import com.olyno.gami.models.GameMessage;
import com.olyno.gami.models.Team;

import org.bukkit.entity.Player;

public class GameEvents implements GameListener {

    private HashMap<Game, GameRunner> runningGames = new HashMap<>();

    @Override
    public void onGameCreated(Game game) {
        new GameCreatedEvent(game);
    }

    @Override
    public void onGameDeleted(Game game) {
        new GameDeletedEvent(game);
    }

    @Override
    public void onSessionCreated(Game game) {
        new GameSessionCreated(game);
    }

    @Override
    public void onSessionDeleted(Game game) {
        new GameSessionDeleted(game);
    }

    @Override
    public void onGameLoaded(Game game, Path gamePath, FileFormat format) {
        Gamet.info("Game '" + game.getName() + "' loaded with success");
        new GameLoadedEvent(game, gamePath, format);
    }

    @Override
    public void onGameSaved(Game game, Path gamePath, FileFormat format) {
        Gamet.info("Game '" + game.getName() + "' saved as " + format.name().toLowerCase() + " file");
        new GameSavedEvent(game, gamePath, format);
    }

    @Override
    public void onGameCanStart(Game game) {
        new GameCanStartEvent(game);
    }

    @Override
    public void onGameReady(Game game) {
        new GameReadyEvent(game);
    }

    @Override
    public void onGameStarted(Game game) {
        GameRunner runner = new GameRunner(game);
        game.setState(GameState.START);
        runner.start();
        runningGames.put(game, runner);
        new GameStartedEvent(game);
    }

    @Override
    public void onGameStopped(Game game) {
        game.setState(GameState.ENDED);
        new GameStoppedEvent(game);
    }

    @Override
    public void onPlayerJoin(Game game, Object p) {
        Player player = (Player) p;
        if (Gamet.messages) {
            String displayName = game.getDisplayName();
            for (GameMessage message : game.getMessages(GameMessageType.JOIN, GameMessageTarget.GLOBAL)) {
                for (Object playerObjectInGame : game.getPlayers()) {
                    Player playerInGame = (Player) playerObjectInGame;
                    if (player.getUniqueId() != playerInGame.getUniqueId()) {
                        playerInGame.sendMessage(displayName + this.formatMessage(message.getMessage(), game, player));
                    }
                }
            }
            for (GameMessage message : game.getMessages(GameMessageType.JOIN, GameMessageTarget.GLOBAL)) {
                player.sendMessage(displayName + this.formatMessage(message.getMessage(), game, player));
            }
        }
        new PlayerJoinGameEvent(game, (Player) player);
    }

    @Override
    public void onPlayerLeave(Game game, Object p) {
        Player player = (Player) p;
        if (Gamet.messages) {
            String displayName = game.getDisplayName();
            for (GameMessage message : game.getMessages(GameMessageType.LEAVE, GameMessageTarget.GLOBAL)) {
                for (Object playerObjectInGame : game.getPlayers()) {
                    Player playerInGame = (Player) playerObjectInGame;
                    if (player.getUniqueId() != playerInGame.getUniqueId()) {
                        playerInGame.sendMessage(displayName + this.formatMessage(message.getMessage(), game, player));
                    }
                }
            }
            for (GameMessage message : game.getMessages(GameMessageType.LEAVE, GameMessageTarget.GLOBAL)) {
                player.sendMessage(displayName + this.formatMessage(message.getMessage(), game, player));
            }
        }
        new PlayerLeaveGameEvent(game, (Player) player);
    }

    @Override
    public void onSpectatorJoin(Game game, Object player) {
        new SpectatorJoinEvent(game, (Player) player);
    }

    @Override
    public void onSpectatorLeave(Game game, Object player) {
        new SpectatorLeaveEvent(game, (Player) player);
    }

    @Override
    public void onTeamAdded(Game game, Team team) {
        new TeamAddedEvent(game, team);
    }

    @Override
    public void onTeamRemoved(Game game, Team team) {
        new TeamRemovedEvent(game, team);
    }

    private String formatMessage(String entryMessage, Game game, Player player) {
        entryMessage = entryMessage.replaceAll("\\$\\{player}", player.getDisplayName());
        entryMessage = entryMessage.replaceAll("\\$\\{game}", game.getDisplayName());
        return entryMessage;
    }

}
