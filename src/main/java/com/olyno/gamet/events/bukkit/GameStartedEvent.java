package com.olyno.gamet.events.bukkit;

import com.olyno.gami.models.Game;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class GameStartedEvent extends Event implements Listener {

    public static final HandlerList handlers = new HandlerList();

    private Game game;

    public GameStartedEvent() { }

    public GameStartedEvent( Game game ) {
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
    
}
