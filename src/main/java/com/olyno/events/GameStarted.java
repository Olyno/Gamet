package com.olyno.events;

import com.olyno.GameAPI;
import com.olyno.events.bukkit.GameStartedEvent;
import com.olyno.types.Game;
import com.olyno.util.game.GameStatus;
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
        game.setState(GameStatus.PROCESSING);
    }

}
