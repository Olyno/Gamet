package com.olyno.gamet.events;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.bukkit.TeamCreatedEvent;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamCreated implements Listener {

    public TeamCreated( Gamet plugin ) {
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
