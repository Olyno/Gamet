package com.olyno.gamet.events.bukkit;

import com.olyno.gami.models.Game;
import com.olyno.gami.models.Team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class SpectatorJoinEvent extends Event implements Listener {

    private static final HandlerList handlers = new HandlerList();

    private Game game;
    private Team team;
    private Player player;

    public SpectatorJoinEvent() { }

    public SpectatorJoinEvent( Game game, Player player ) {
        this.game = game;
        this.player = player;
        Bukkit.getServer().getPluginManager().callEvent(this);
    }

    public SpectatorJoinEvent( Team team, Player player ) {
        this.game = team.getGame().get();
        this.team = team;
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }

}
