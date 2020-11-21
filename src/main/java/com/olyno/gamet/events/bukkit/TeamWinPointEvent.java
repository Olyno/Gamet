package com.olyno.gamet.events.bukkit;

import com.olyno.gami.models.Point;
import com.olyno.gami.models.Team;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class TeamWinPointEvent extends Event implements Listener {

    private static final HandlerList handlers = new HandlerList();

    private Team team;
    private Point points;

    public TeamWinPointEvent() { }

    public TeamWinPointEvent( Team team, Point points ) {
        this.team = team;
        this.points = points;
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

    public Point getPoints() {
        return points;
    }

}
