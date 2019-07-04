package com.olyno.events;

import com.olyno.GameAPI;
import com.olyno.events.bukkit.TeamCreatedEvent;
import com.olyno.events.bukkit.TeamDeletedEvent;
import com.olyno.types.Game;
import com.olyno.types.Team;
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
