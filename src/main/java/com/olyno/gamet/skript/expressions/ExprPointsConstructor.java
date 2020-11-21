package com.olyno.gamet.skript.expressions;

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

import com.olyno.gami.models.Point;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Points")
@Description("Create a point.")
@Examples({
    "command test:" +
    "\ttrigger:" + 
    "\t\tset {_team} to team \"red\" from \"test\"" +
    "\t\tadd 1 point to {_team}" +
    "\t\tbroadcast \"The team %{_team}% has %points of {_team}% points!\""
})
@Since("1.0")

public class ExprPointsConstructor extends SimpleExpression<Point> {

    static {
        Skript.registerExpression(ExprPointsConstructor.class, Point.class, ExpressionType.SIMPLE,
            "%integer% point[s] [(from|of|with [author]) %-player%]"
        );
    }

    private Expression<Integer> pointsAmount;
    private Expression<Player> pointsAuthor;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        pointsAmount = (Expression<Integer>) expr[0];
        pointsAuthor = (Expression<Player>) expr[1];
        return true;
    }

    @Override
    protected Point[] get(Event e) {
        Integer amount = pointsAmount.getSingle(e);
        Player author = pointsAuthor.getSingle(e);
        return new Point[]{ new Point(amount, author) };
    }

    @Override
    public Class<? extends Point> getReturnType() {
        return Point.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return pointsAmount.toString(e, debug) + " points" + (pointsAuthor != null ? " from " + pointsAuthor.toString(e, debug) : "");
    }

}
