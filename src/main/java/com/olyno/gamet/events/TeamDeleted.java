package com.olyno.gamet.events;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.bukkit.TeamCreatedEvent;
import com.olyno.gamet.events.bukkit.TeamDeletedEvent;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamDeleted implements Listener {

    public TeamDeleted( Gamet plugin ) {
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
