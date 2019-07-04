package com.olyno.events.bukkit;

import com.olyno.types.Game;
import com.olyno.types.Point;
import com.olyno.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamLosePointEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Game game;
    private Team team;
    private Player player;
    private Point points;

    public TeamLosePointEvent( Point points ) {
        this.game = points.getGame();
        this.team = points.getTeam();
        this.player = points.getWho();
        this.points = points;
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

    public Integer getPoints() {
        return points.getPoints();
    }

    public Player getPlayer() {
        return player;
    }
}
