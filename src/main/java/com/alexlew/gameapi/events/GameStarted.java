package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.GameDeletedEvent;
import com.alexlew.gameapi.events.bukkit.GameStartedEvent;
import com.alexlew.gameapi.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStarted implements Listener {

    public GameStarted( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GameStarted( Game game ) {
        Bukkit.getServer().getPluginManager().callEvent(new GameStartedEvent(game));
    }

    @EventHandler
    public void onGameStarted( GameStartedEvent event ) {
        Game game = event.getGame();
    }

}
