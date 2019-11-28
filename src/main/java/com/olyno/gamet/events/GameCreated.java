package com.olyno.gamet.events;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.bukkit.GameCreatedEvent;
import com.olyno.gamet.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameCreated implements Listener {

    public GameCreated( Gamet plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GameCreated( Game game ) {
		Bukkit.getServer().getPluginManager().callEvent(new GameCreatedEvent(game));
    }

    @EventHandler
	public void onGameCreated(GameCreatedEvent event) {
        Game game = event.getGame();
    }

}
