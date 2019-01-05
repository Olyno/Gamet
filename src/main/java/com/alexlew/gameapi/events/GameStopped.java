package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.GameDeletedEvent;
import com.alexlew.gameapi.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStopped implements Listener {

    public GameStopped( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GameStopped( Game game ) {
        Bukkit.getServer().getPluginManager().callEvent(new GameDeletedEvent(game));
    }

    @EventHandler
    public void onGameCreated( GameDeletedEvent event ) {
        Game game = event.getGame();
    }

}
