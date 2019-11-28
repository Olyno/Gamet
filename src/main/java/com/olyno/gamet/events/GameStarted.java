package com.olyno.gamet.events;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.bukkit.GameStartedEvent;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.util.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStarted implements Listener {

    public GameStarted( Gamet plugin ) {
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
