package com.olyno.events;

import com.olyno.GameAPI;
import com.olyno.events.bukkit.GameCreatedEvent;
import com.olyno.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameCreated implements Listener {

    public GameCreated( GameAPI plugin ) {
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
