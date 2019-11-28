package com.olyno.gamet.events;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.bukkit.GameDeletedEvent;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.util.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStopped implements Listener {

    public GameStopped( Gamet plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GameStopped( Game game ) {
        Bukkit.getServer().getPluginManager().callEvent(new GameDeletedEvent(game));
    }

    @EventHandler
    public void onGameStopped(GameDeletedEvent event) {
        Game game = event.getGame();
        game.setState(GameStatus.ENDED);
    }

}
