package com.olyno.gamet.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.olyno.gamet.events.bukkit.TeamCreatedEvent;
import com.olyno.gamet.events.bukkit.TeamDeletedEvent;
import com.olyno.gamet.events.bukkit.TeamLosePointEvent;
import com.olyno.gamet.events.bukkit.TeamWinPointEvent;
import com.olyno.gamet.types.Game;
import com.olyno.gamet.types.Team;

public class EvtTeam {

    static {
        // Team Created Event
        Skript.registerEvent("Team Created Event", SimpleEvent.class, TeamCreatedEvent.class,
                "team created"
        );
        EventValues.registerEventValue(TeamCreatedEvent.class, Game.class, new Getter<Game, TeamCreatedEvent>() {

            @Override
            public Game get( TeamCreatedEvent e ) {
                return e.getGame();
            }
        }, 0);
        EventValues.registerEventValue(TeamCreatedEvent.class, Team.class, new Getter<Team, TeamCreatedEvent>() {

            @Override
            public Team get( TeamCreatedEvent e ) {
                return e.getTeam();
            }
        }, 0);

        // Team Deleted Event
        Skript.registerEvent("Team Deleted Event", SimpleEvent.class, TeamDeletedEvent.class,
                "team deleted"
        );
        EventValues.registerEventValue(TeamDeletedEvent.class, Game.class, new Getter<Game, TeamDeletedEvent>() {

            @Override
            public Game get( TeamDeletedEvent e ) {
                return e.getGame();
            }
        }, 0);
        EventValues.registerEventValue(TeamDeletedEvent.class, Team.class, new Getter<Team, TeamDeletedEvent>() {

            @Override
            public Team get( TeamDeletedEvent e ) {
                return e.getTeam();
            }
        }, 0);

        // Team Score Point Event
        Skript.registerEvent("Team Score Point Event", SimpleEvent.class, TeamWinPointEvent.class,
                "team (win|score) [a] point[s]"
        );
        EventValues.registerEventValue(TeamWinPointEvent.class, Game.class, new Getter<Game, TeamWinPointEvent>() {

            @Override
            public Game get( TeamWinPointEvent e ) {
                return e.getGame();
            }
        }, 0);
        EventValues.registerEventValue(TeamWinPointEvent.class, Team.class, new Getter<Team, TeamWinPointEvent>() {

            @Override
            public Team get( TeamWinPointEvent e ) {
                return e.getTeam();
            }
        }, 0);
        EventValues.registerEventValue(TeamWinPointEvent.class, Integer.class, new Getter<Integer, TeamWinPointEvent>() {

            @Override
            public Integer get( TeamWinPointEvent e ) {
                return e.getPoints();
            }
        }, 0);

        // Team Lose Point Event
        Skript.registerEvent("Team Lose Point Event", SimpleEvent.class, TeamLosePointEvent.class,
                "team lose [a] point[s]"
        );
        EventValues.registerEventValue(TeamLosePointEvent.class, Game.class, new Getter<Game, TeamLosePointEvent>() {

            @Override
            public Game get( TeamLosePointEvent e ) {
                return e.getGame();
            }
        }, 0);
        EventValues.registerEventValue(TeamLosePointEvent.class, Team.class, new Getter<Team, TeamLosePointEvent>() {

            @Override
            public Team get( TeamLosePointEvent e ) {
                return e.getTeam();
            }
        }, 0);
        EventValues.registerEventValue(TeamLosePointEvent.class, Integer.class, new Getter<Integer, TeamLosePointEvent>() {

            @Override
            public Integer get( TeamLosePointEvent e ) {
                return e.getPoints();
            }
        }, 0);

    }
}
