package com.olyno.gamet.skript.events;

import com.olyno.gamet.events.bukkit.GameCanStartEvent;
import com.olyno.gamet.events.bukkit.GameCreatedEvent;
import com.olyno.gamet.events.bukkit.GameDeletedEvent;
import com.olyno.gamet.events.bukkit.GameReadyEvent;
import com.olyno.gamet.events.bukkit.GameSessionCreated;
import com.olyno.gamet.events.bukkit.GameSessionDeleted;
import com.olyno.gamet.events.bukkit.GameStartedEvent;
import com.olyno.gamet.events.bukkit.GameStoppedEvent;
import com.olyno.gami.models.Game;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

public class EvtGame {

    static {
        // Command Ready Event
        Skript.registerEvent("Game Ready Event", SimpleEvent.class, GameReadyEvent.class,
                "[mini[(-| )]]game ready [to start]"
        );
        EventValues.registerEventValue(GameReadyEvent.class, Game.class, new Getter<Game, GameReadyEvent>() {

            @Override
            public Game get( GameReadyEvent e ) {
                return e.getGame();
            }
        }, 0);

        // Command Can Start Event
        Skript.registerEvent("Game Can Start Event", SimpleEvent.class, GameCanStartEvent.class,
                "[mini[(-| )]]game can start"
        );
        EventValues.registerEventValue(GameCanStartEvent.class, Game.class, new Getter<Game, GameCanStartEvent>() {

            @Override
            public Game get( GameCanStartEvent e ) {
                return e.getGame();
            }
        }, 0);

        // Command Created Event
		Skript.registerEvent("Game Created Event", SimpleEvent.class, GameCreatedEvent.class,
                "[mini[(-| )]]game create[d]"
        );
        EventValues.registerEventValue(GameCreatedEvent.class, Game.class, new Getter<Game, GameCreatedEvent>() {

            @Override
            public Game get( GameCreatedEvent e ) {
                return e.getGame();
            }
        }, 0);

        // Command Deleted Event
		Skript.registerEvent("Game Deleted Event", SimpleEvent.class, GameDeletedEvent.class,
                "[mini[(-| )]]game delete[d]"
        );
        EventValues.registerEventValue(GameDeletedEvent.class, Game.class, new Getter<Game, GameDeletedEvent>() {

            @Override
            public Game get( GameDeletedEvent e ) {
                return e.getGame();
            }
        }, 0);

        // Command Created Event
		Skript.registerEvent("Session Created Event", SimpleEvent.class, GameSessionCreated.class,
            "[[mini[(-| )]]game] session create[d]"
        );
        EventValues.registerEventValue(GameSessionCreated.class, Game.class, new Getter<Game, GameSessionCreated>() {

            @Override
            public Game get( GameSessionCreated e ) {
                return e.getGame();
            }
        }, 0);

        // Command Deleted Event
		Skript.registerEvent("Session Deleted Event", SimpleEvent.class, GameSessionDeleted.class,
            "[[mini[(-| )]]game] session delete[d]"
        );
        EventValues.registerEventValue(GameSessionDeleted.class, Game.class, new Getter<Game, GameSessionDeleted>() {

            @Override
            public Game get( GameSessionDeleted e ) {
                return e.getGame();
            }
        }, 0);

        // Command Started Event
        Skript.registerEvent("Game Started Event", SimpleEvent.class, GameStartedEvent.class,
                "[mini[(-| )]]game start[ed]"
        );
        EventValues.registerEventValue(GameStartedEvent.class, Game.class, new Getter<Game, GameStartedEvent>() {

            @Override
            public Game get( GameStartedEvent e ) {
                return e.getGame();
            }
        }, 0);

        // Command Stopped Event
        Skript.registerEvent("Game Stopped Event", SimpleEvent.class, GameStoppedEvent.class,
                "[mini[(-| )]]game stop[ped]"
        );
        EventValues.registerEventValue(GameStoppedEvent.class, Game.class, new Getter<Game, GameStoppedEvent>() {

            @Override
            public Game get( GameStoppedEvent e ) {
                return e.getGame();
            }
        }, 0);
    }
}
