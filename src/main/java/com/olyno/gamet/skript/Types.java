package com.olyno.gamet.skript;

import com.olyno.gami.Gami;
import com.olyno.gami.models.Game;
import com.olyno.gami.models.GameMessage;
import com.olyno.gami.models.Point;
import com.olyno.gami.models.Team;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class Types {

	static {
		// Game type
		Classes.registerClass(new ClassInfo<>(Game.class, "game")
			.defaultExpression(new EventValueExpression<>(Game.class))
			.user("(mini(-| )?)?game")
			.name("Game")
			.description("The current game")
			.examples(
				"on player join game:",
				"\tbroadcast \"%event-player% joined the game %event-game%!\""
			)
			.since("2.0")
			.parser(new Parser<Game>() {

				@Override
				public String getVariableNamePattern() {
					return ".+";
				}

				@Override
				public Game parse(String gameName, ParseContext arg1) {
					return Gami.getGames().get(gameName);
				}

				@Override
				public String toString(Game game, int arg1) {
					return game.getName();
				}

				@Override
				public String toVariableNameString(Game game) {
					return game.getName();
				}

			}));

		// Team type
		Classes.registerClass(new ClassInfo<>(Team.class, "team")
			.defaultExpression(new EventValueExpression<>(Team.class))
			.user("team")
			.name("Team")
			.description("The current team")
			.examples(
				"on player join team:",
				"\tbroadcast \"%event-player% joined the team %event-team%!\""
			)
			.since("2.0")
			.parser(new Parser<Team>() {

				@Override
				public String getVariableNamePattern() {
					return ".+";
				}

				@Override
				public Team parse(String teamName, ParseContext arg1) {
					for (Game game : Gami.getGames().values()) {
						for (Team team : game.getTeams().values()) {
							if (team.getName() == teamName) {
								return team;
							}
						}
					}
					return null;
				}

				@Override
				public String toString(Team team, int arg1) {
					return team.getName();
				}

				@Override
				public String toVariableNameString(Team team) {
					return team.getName();
				}

			}));

		// Point type
		Classes.registerClass(new ClassInfo<>(Point.class, "point")
			.defaultExpression(new EventValueExpression<>(Point.class))
			.user("points?")
			.name("Points")
			.description("A point type with information like the player, game, number of points and more.")
			.examples(
				"on team win points:",
				"\tif raw event-points > 10:",
				"\t\tbroadcast \"WOW this team won a lot of points!\""
			)
			.since("2.0")
			.parser(new Parser<Point>() {

				@Override
				public String getVariableNamePattern() {
					return ".+";
				}

				@Override
				public Point parse(String arg0, ParseContext arg1) {
					try {
						return new Point(Integer.parseInt(arg0));
					} catch (NumberFormatException e1) {
						return null;
					}
				}

				@Override
				public String toString(Point point, int arg1) {
					return point.getPoints().toString();
				}

				@Override
				public String toVariableNameString(Point point) {
					return point.getPoints().toString();
				}

			}));


		// GameMessage type
		Classes.registerClass(new ClassInfo<>(GameMessage.class, "gamemessage")
			.defaultExpression(new EventValueExpression<>(GameMessage.class))
			.user("game(-|_| )?message")
			.name("Game Message")
			.description("A game message type.")
			.since("3.0.0")
			.parser(new Parser<GameMessage>() {

				@Override
				public String getVariableNamePattern() {
					return ".+";
				}

				@Override
				public GameMessage parse(String arg0, ParseContext arg1) {
					return null;
				}

				@Override
				public String toString(GameMessage message, int arg1) {
					return message.getMessage();
				}

				@Override
				public String toVariableNameString(GameMessage message) {
					return message.getMessage();
				}

			}));

	}

}
