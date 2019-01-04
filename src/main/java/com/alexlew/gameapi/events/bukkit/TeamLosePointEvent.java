package com.alexlew.gameapi.events.bukkit;

import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Point;
import com.alexlew.gameapi.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamLosePointEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Game game;
    private Team team;
    private Player player;
    private Integer points;

    public TeamLosePointEvent( Point points ) {
        this.game = points.getGame();
        this.team = points.getTeam();
        this.player = points.getWho();
        this.points = points.getPoints();
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
        return points;
    }

    public Player getPlayer() {
        return player;
    }
}
