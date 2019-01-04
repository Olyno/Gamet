package com.alexlew.gameapi.skript.expressions.team;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.gameapi.types.Point;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Points of team")
@Description("Returns points of a team. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_team} to team \"red\" from \"test\"",
        "\t\tadd 1 to points of {_team}",
        "\t\tbroadcast \"The team %{_team}% has %points of {_team}% points!\" "
})
@Since("1.0")

public class ExprPoints extends SimpleExpression<Point> {

    static {
        Skript.registerExpression(ExprPoints.class, Point.class, ExpressionType.SIMPLE,
                "%integer% point[s] [from %-player%]"
        );
    }

    private Expression<Integer> points;
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        points = (Expression<Integer>) expr[0];
        player = (Expression<Player>) expr[1];
        return true;
    }

    @Override
    protected Point[] get( Event e ) {
        Point point = new Point(points.getSingle(e));
        if (player.getSingle(e) != null) {
            point.setWho(player.getSingle(e));
        }
        return new Point[]{point};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Point> getReturnType() {
        return Point.class;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        String back = points.getSingle(e).toString() + " points";
        if (player.getSingle(e) != null) {
            back = back + " from player \"" + player.getSingle(e).getDisplayName() + "\"";
        }
        return back;
    }
}
