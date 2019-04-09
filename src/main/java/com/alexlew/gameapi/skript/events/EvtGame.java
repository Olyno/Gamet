package com.alexlew.gameapi.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.alexlew.gameapi.events.bukkit.*;
import com.alexlew.gameapi.types.Game;

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
