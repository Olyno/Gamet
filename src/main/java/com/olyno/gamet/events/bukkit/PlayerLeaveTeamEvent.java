package com.olyno.gamet.events.bukkit;

import com.olyno.gami.models.Team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class PlayerLeaveTeamEvent extends Event implements Listener {

    private static final HandlerList handlers = new HandlerList();

    private Team team;
    private Player player;

    public PlayerLeaveTeamEvent() { }

	public PlayerLeaveTeamEvent(Team team, Player player) {
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

    public Team getTeam() {
        return team;
    }

    public Player getPlayer() {
        return player;
    }
    
}
