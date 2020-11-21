package com.olyno.gamet.events.bukkit;

import com.olyno.gami.models.Game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class PlayerLeaveGameEvent extends Event implements Listener {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Game game;

    public PlayerLeaveGameEvent() { }

	public PlayerLeaveGameEvent(Game game, Player player) {
        this.player = player;
		this.game = game;
        Bukkit.getServer().getPluginManager().callEvent(this);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

}
