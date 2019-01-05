package com.alexlew.gameapi.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import org.bukkit.entity.Player;

public class Point {

    static {
        Classes.registerClass(new ClassInfo<>(Point.class, "points")
                .defaultExpression(new EventValueExpression<>(Point.class))
                .user("point(s)?")
                .name("Points")
                .description("A point type which join the player, game, number of points and more.")
                .examples(
                        "on team win points:",
                        "\tif event-points > 10:",
                        "\t\tbroadcast \"WOW this team won a lot of points!\""
                )
                .since("2.0")
                .parser(new Parser<Point>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public Point parse( String arg0, ParseContext arg1 ) {
                        return null;
                    }

                    @Override
                    public String toString( Point arg0, int arg1 ) {
                        return arg0.getPoints().toString();
                    }

                    @Override
                    public String toVariableNameString( Point arg0 ) {
                        return arg0.getPoints().toString();
                    }

                }));
    }

    private Integer points;
    private Integer advantage;
    private Player who;
    private Game game;
    private Team team;

    public Point( Integer points ) {
        this.points = points;
        this.advantage = points;
    }

    public Point( Integer points, Player who ) {
        this.points = points;
        this.advantage = points;
        this.who = who;
        this.game = Game.getGameOfPlayer(who);
        this.team = game.getTeamOfPlayer(who);
    }

    /**
     * @return Points of the team
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * @param points Points of the new "points" type
     */
    public void setPoints( Integer points ) {
        this.points = points;
    }

    /**
     * Return the advantage that the team will win with this number of point
     *
     * @return The advantage with these points
     */
    public Integer getAdvantage() {
        return advantage;
    }

    /**
     * Set the advantage that the team will win with this number of point
     *
     * @param advantage The advantage that the team will win with this number of point
     */
    public void setAdvantage( Integer advantage ) {
        this.advantage = advantage;
    }

    public Player getWho() {
        return who;
    }

    public void setWho( Player who ) {
        this.who = who;
    }

    public Game getGame() {
        return game;
    }

    public void setGame( Game game ) {
        this.game = game;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam( Team team ) {
        this.team = team;
    }
}
