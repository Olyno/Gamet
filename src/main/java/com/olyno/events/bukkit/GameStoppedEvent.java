package com.olyno.events.bukkit;

import com.olyno.types.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStoppedEvent extends Event {

    public static final HandlerList handlers = new HandlerList();

    private Game game;

    public GameStoppedEvent( Game game ) {
        this.game = game;
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
