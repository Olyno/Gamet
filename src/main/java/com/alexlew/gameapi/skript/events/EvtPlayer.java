package com.alexlew.gameapi.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.alexlew.gameapi.events.bukkit.*;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.entity.Player;

public class EvtPlayer {

    static {
        // Player Join CommandGameSpigot Event
        Skript.registerEvent("Player Join Game Event", SimpleEvent.class, PlayerJoinGameEvent.class,
                "player join [a] [mini[(-| )]]game"
        );
        EventValues.registerEventValue(PlayerJoinGameEvent.class, Player.class, new Getter<Player, PlayerJoinGameEvent>() {
            @Override
            public Player get( PlayerJoinGameEvent e ) {
                return e.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(PlayerJoinGameEvent.class, Game.class, new Getter<Game, PlayerJoinGameEvent>() {
            @Override
            public Game get( PlayerJoinGameEvent e ) {
                return e.getGame();
            }
        }, 0);

        // Player Leave CommandGameSpigot Event
        Skript.registerEvent("Player Leave Game Event", SimpleEvent.class, PlayerLeaveGameEvent.class,
                "player leave [a] [mini[(-| )]]game"
        );
        EventValues.registerEventValue(PlayerLeaveGameEvent.class, Player.class, new Getter<Player, PlayerLeaveGameEvent>() {
            @Override
            public Player get( PlayerLeaveGameEvent e ) {
                return e.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(PlayerLeaveGameEvent.class, Game.class, new Getter<Game, PlayerLeaveGameEvent>() {
            @Override
            public Game get( PlayerLeaveGameEvent e ) {
                return e.getGame();
            }
        }, 0);

        // Player Join Team Event
        Skript.registerEvent("Player Join Team Event", SimpleEvent.class, PlayerJoinTeamEvent.class,
                "player join [a] team"
        );
        EventValues.registerEventValue(PlayerJoinTeamEvent.class, Player.class, new Getter<Player, PlayerJoinTeamEvent>() {
            @Override
            public Player get( PlayerJoinTeamEvent e ) {
                return e.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(PlayerJoinTeamEvent.class, Game.class, new Getter<Game, PlayerJoinTeamEvent>() {
            @Override
            public Game get( PlayerJoinTeamEvent e ) {
                return e.getGame();
            }
        }, 0);
        EventValues.registerEventValue(PlayerJoinTeamEvent.class, Team.class, new Getter<Team, PlayerJoinTeamEvent>() {
            @Override
            public Team get( PlayerJoinTeamEvent e ) {
                return e.getTeam();
            }
        }, 0);

        // Player Leave Team Event
        Skript.registerEvent("Player Leave Team Event", SimpleEvent.class, PlayerLeaveTeamEvent.class,
                "player leave [a] team"
        );
        EventValues.registerEventValue(PlayerLeaveTeamEvent.class, Player.class, new Getter<Player, PlayerLeaveTeamEvent>() {
            @Override
            public Player get( PlayerLeaveTeamEvent e ) {
                return e.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(PlayerLeaveTeamEvent.class, Game.class, new Getter<Game, PlayerLeaveTeamEvent>() {
            @Override
            public Game get( PlayerLeaveTeamEvent e ) {
                return e.getGame();
            }
        }, 0);
        EventValues.registerEventValue(PlayerLeaveTeamEvent.class, Team.class, new Getter<Team, PlayerLeaveTeamEvent>() {
            @Override
            public Team get( PlayerLeaveTeamEvent e ) {
                return e.getTeam();
            }
        }, 0);

        // Player Score Point Event
        Skript.registerEvent("Player Score Point Event", SimpleEvent.class, TeamWinPointEvent.class,
                "player (win|score) [a] point[s]"
        );
        EventValues.registerEventValue(TeamWinPointEvent.class, Game.class, new Getter<Game, TeamWinPointEvent>() {

            @Override
            public Game get( TeamWinPointEvent e ) {
                return e.getGame();
            }
        }, 0);
        EventValues.registerEventValue(TeamWinPointEvent.class, Player.class, new Getter<Player, TeamWinPointEvent>() {

            @Override
            public Player get( TeamWinPointEvent e ) {
                return e.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(TeamWinPointEvent.class, Integer.class, new Getter<Integer, TeamWinPointEvent>() {

            @Override
            public Integer get( TeamWinPointEvent e ) {
                return e.getPoints();
            }
        }, 0);
        EventValues.registerEventValue(TeamWinPointEvent.class, Integer.class, new Getter<Integer, TeamWinPointEvent>() {

            @Override
            public Integer get( TeamWinPointEvent e ) {
                return e.getPoints();
            }
        }, 0);

        // Player Lose Point Event
        Skript.registerEvent("Player Lose Point Event", SimpleEvent.class, TeamLosePointEvent.class,
                "player lose [a] point[s]"
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
        EventValues.registerEventValue(TeamLosePointEvent.class, Player.class, new Getter<Player, TeamLosePointEvent>() {

            @Override
            public Player get( TeamLosePointEvent e ) {
                return e.getPlayer();
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
