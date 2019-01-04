package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.TeamCreatedEvent;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamCreated implements Listener {

    public TeamCreated( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public TeamCreated( Team team ) {
        Bukkit.getServer().getPluginManager().callEvent(new TeamCreatedEvent(team));
    }

    @EventHandler
    public void onTeamCreated( TeamCreatedEvent event ) {
        Game game = event.getGame();
        Team team = event.getTeam();
    }

}
