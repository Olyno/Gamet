package com.olyno.gamet.events.bukkit;

import com.olyno.gami.models.Team;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class TeamDeletedEvent extends Event implements Listener {

    public static final HandlerList handlers = new HandlerList();

    private Team team;

    public TeamDeletedEvent() { }

    public TeamDeletedEvent( Team team ) {
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

    public Team getTeam() {
        return team;
    }
    
}