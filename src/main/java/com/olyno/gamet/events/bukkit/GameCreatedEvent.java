package com.olyno.gamet.events.bukkit;

import com.olyno.gamet.types.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameCreatedEvent extends Event {

    public static final HandlerList handlers = new HandlerList();

    private Game game;

    public GameCreatedEvent( Game game ) {
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
