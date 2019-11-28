package com.olyno.gamet.types;

import org.bukkit.entity.Player;

public class Point {

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
		this.team = Game.getTeamOfPlayer(who);
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
