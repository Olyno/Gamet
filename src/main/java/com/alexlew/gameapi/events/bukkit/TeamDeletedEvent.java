package com.alexlew.gameapi.events.bukkit;

import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamDeletedEvent extends Event {

    public static final HandlerList handlers = new HandlerList();

    private Game game;
    private Team team;

    public TeamDeletedEvent( Team team ) {
        this.game = team.getGame();
        this.team = team;
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

    public Team getTeam() {
        return team;
    }
}
