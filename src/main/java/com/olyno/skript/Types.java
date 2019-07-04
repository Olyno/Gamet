package com.olyno.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.olyno.types.Game;
import com.olyno.types.Point;
import com.olyno.types.Team;

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
					public Game parse(String arg0, ParseContext arg1) {
						return null;
					}

					@Override
					public String toString(Game arg0, int arg1) {
						return arg0.getName();
					}

					@Override
					public String toVariableNameString(Game arg0) {
						return arg0.getName();
					}

				}));

		// Team type
		Classes.registerClass(new ClassInfo<>(Team.class, "team")
				.defaultExpression(new EventValueExpression<>(Team.class))
				.user("team")
				.name("team")
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
					public Team parse(String arg0, ParseContext arg1) {
						return null;
					}

					@Override
					public String toString(Team arg0, int arg1) {
						return arg0.getName();
					}

					@Override
					public String toVariableNameString(Team arg0) {
						return arg0.getName();
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
					public String toString(Point arg0, int arg1) {
						return arg0.getPoints().toString();
					}

					@Override
					public String toVariableNameString(Point arg0) {
						return arg0.getPoints().toString();
					}

				}));

	}

}
