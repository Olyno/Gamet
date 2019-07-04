package com.olyno.events;

import com.olyno.GameAPI;
import com.olyno.events.bukkit.GameReadyEvent;
import com.olyno.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameReady implements Listener {

    public GameReady( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GameReady( Game game ) {
        Bukkit.getServer().getPluginManager().callEvent(new GameReadyEvent(game));
    }

    @EventHandler
    public void onGameReady( GameReadyEvent event ) {
        Game game = event.getGame();
    }

}
