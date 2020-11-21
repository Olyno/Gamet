package com.olyno.gamet.util.skript;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public abstract class MultiplePropertyExpression<F, T> extends SimpleExpression<T> {

    private Expression<? extends F> expr;
    private List<Expression<?>> expressions;
    protected List<?> usedExpressions;

    protected static <T> void register(final Class<? extends Expression<T>> c, final Class<T> type, final String property, final String fromType) {
        Skript.registerExpression(c, type, ExpressionType.SIMPLE,
            "[all] [the] %" + fromType + "%'[s] " + property,
            "[all] [the] " + property + " of %" + fromType + "%"
        );
    }

    public abstract Class<? extends T> getReturnType();
    protected abstract String getPropertyName();
    protected abstract T[] convert(F context);

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        this.expr = (Expression<? extends F>) expr[0];
        this.expressions = Arrays.asList(expr);
        this.expressions.remove(matchedPattern);
        return true;
    }

    @Override
    protected T[] get(Event e) {
        usedExpressions = this.expressions
            .stream()
            .map(expr -> expr.getSingle(e))
            .collect(Collectors.toList());
        return convert(expr.getSingle(e));
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "all the " + getPropertyName() + " of " + expr.toString(e, debug);
    }

    protected Expression<? extends F> getExpr() {
        return expr;
    }

}