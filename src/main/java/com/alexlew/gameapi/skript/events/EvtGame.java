package com.alexlew.gameapi.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.alexlew.gameapi.events.bukkit.GameCanStartEvent;
import com.alexlew.gameapi.events.bukkit.GameCreatedEvent;
import com.alexlew.gameapi.events.bukkit.GameDeletedEvent;
import com.alexlew.gameapi.events.bukkit.GameReadyEvent;
import com.alexlew.gameapi.types.Game;

public class EvtGame {

    static {
        // CommandGameSpigot Ready Event
        Skript.registerEvent("Game Ready Event", SimpleEvent.class, GameReadyEvent.class,
                "[mini[(-| )]]game ready [to start]"
        );
        EventValues.registerEventValue(GameReadyEvent.class, Game.class, new Getter<Game, GameReadyEvent>() {

            @Override
            public Game get( GameReadyEvent e ) {
                return e.getGame();
            }
        }, 0);

        // CommandGameSpigot Can Start Event
        Skript.registerEvent("Game Can Start Event", SimpleEvent.class, GameCanStartEvent.class,
                "[mini[(-| )]]game can start"
        );
        EventValues.registerEventValue(GameCanStartEvent.class, Game.class, new Getter<Game, GameCanStartEvent>() {

            @Override
            public Game get( GameCanStartEvent e ) {
                return e.getGame();
            }
        }, 0);

        // CommandGameSpigot Created Event
        Skript.registerEvent("Game Created Event", SimpleEvent.class, GameReadyEvent.class,
                "[mini[(-| )]]game created"
        );
        EventValues.registerEventValue(GameCreatedEvent.class, Game.class, new Getter<Game, GameCreatedEvent>() {

            @Override
            public Game get( GameCreatedEvent e ) {
                return e.getGame();
            }
        }, 0);

        // CommandGameSpigot Deleted Event
        Skript.registerEvent("Game Deleted Event", SimpleEvent.class, GameReadyEvent.class,
                "[mini[(-| )]]game deleted"
        );
        EventValues.registerEventValue(GameDeletedEvent.class, Game.class, new Getter<Game, GameDeletedEvent>() {

            @Override
            public Game get( GameDeletedEvent e ) {
                return e.getGame();
            }
        }, 0);
    }
}
