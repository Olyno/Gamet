package com.alexlew.gameapi.events.bukkit;

import com.alexlew.gameapi.types.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeaveGameEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Game game;

	public PlayerLeaveGameEvent(Game game, Player player) {
        this.player = player;
		this.game = game;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }
}
