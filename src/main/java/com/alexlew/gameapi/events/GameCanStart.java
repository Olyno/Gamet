package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.GameReadyEvent;
import com.alexlew.gameapi.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameCanStart implements Listener {

    public GameCanStart( GameAPI plugin ) {
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
