package com.olyno.gamet.events;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.bukkit.GameReadyEvent;
import com.olyno.gamet.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameCanStart implements Listener {

    public GameCanStart( Gamet plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GameCanStart( Game game ) {
        Bukkit.getServer().getPluginManager().callEvent(new GameReadyEvent(game));
    }

    @EventHandler
    public void onGameReady( GameReadyEvent event ) {
        Game game = event.getGame();
    }

}
