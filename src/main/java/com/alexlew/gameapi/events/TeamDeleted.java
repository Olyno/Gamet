package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.TeamCreatedEvent;
import com.alexlew.gameapi.events.bukkit.TeamDeletedEvent;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamDeleted implements Listener {

    public TeamDeleted( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public TeamDeleted( Team team ) {
        Bukkit.getServer().getPluginManager().callEvent(new TeamDeletedEvent(team));
    }

    @EventHandler
    public void onTeamDeleted( TeamCreatedEvent event ) {
        Game game = event.getGame();
        Team team = event.getTeam();
    }

}
