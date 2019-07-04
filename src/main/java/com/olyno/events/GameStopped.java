package com.olyno.events;

import com.olyno.GameAPI;
import com.olyno.events.bukkit.GameDeletedEvent;
import com.olyno.types.Game;
import com.olyno.util.game.GameStatus;
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
    public void onGameStopped(GameDeletedEvent event) {
        Game game = event.getGame();
        game.setState(GameStatus.ENDED);
    }

}
