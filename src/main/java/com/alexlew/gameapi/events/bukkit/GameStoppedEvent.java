package com.alexlew.gameapi.events.bukkit;

import com.alexlew.gameapi.types.Game;
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
