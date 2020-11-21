package com.olyno.gamet.events.bukkit;

import com.olyno.gami.models.Game;
import com.olyno.gami.models.Team;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class TeamAddedEvent extends Event implements Listener {

    private static final HandlerList handlers = new HandlerList();

    private Game game;
    private Team team;

    public TeamAddedEvent() { }

    public TeamAddedEvent( Game game, Team team ) {
        this.game = game;
        this.team = team;
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

    public Team getTeam() {
        return team;
    }

}
