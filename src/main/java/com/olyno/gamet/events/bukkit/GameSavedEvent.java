package com.olyno.gamet.events.bukkit;

import java.nio.file.Path;

import com.olyno.gami.enums.FileFormat;
import com.olyno.gami.models.Game;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class GameSavedEvent extends Event implements Listener {

    public static final HandlerList handlers = new HandlerList();

    private Game game;
    private Path gamePath;
    private FileFormat format;

    public GameSavedEvent() { }

    public GameSavedEvent( Game game, Path gamePath, FileFormat format ) {
        this.game = game;
        this.gamePath = gamePath;
        this.format = format;
        Bukkit.getServer().getPluginManager().callEvent(this);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Game getGame() {
        return game;
    }

    public Path getGamePath() {
        return gamePath;
    }

    public FileFormat getFileFomat() {
        return format;
    }
    
}
