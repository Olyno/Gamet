package com.alexlew.gameapi.events.bukkit;

import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeaveTeamEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Game game;
    private Team team;
    private Player player;

    /**
     * @param player Which player ?
     */
	public PlayerLeaveTeamEvent(Team team, Player player) {
		this.game = team.getGame();
		this.team = team;
        this.player = player;
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
