package com.olyno.gamet.util.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.event.Event;

public abstract class MultiplePropertyExpressions<F, T> extends SimpleExpression<T> {

    private Expression<? extends F> type;
    private List<Expression<?>> expressions;
    protected ArrayList<?> usedExpressions;

    protected static <T> void register(final Class<? extends Expression<T>> c, final Class<T> type, final String property, final String fromType) {
        Skript.registerExpression(c, type, ExpressionType.SIMPLE,
            "[all] [the] %" + fromType + "%'[s] " + property,
            "[all] [the] " + property + " of %" + fromType + "%"
        );
    }

    public abstract Class<? extends T> getReturnType();
    protected abstract String getPropertyName();

    protected abstract T[] convert(F type);

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        this.type = (Expression<? extends F>) expr[matchedPattern];
        this.expressions = Arrays.asList(expr);
        this.expressions.remove(matchedPattern);
        return true;
    }

    @Override
    protected T[] get(Event e) {
        List<?> expressions = this.expressions
            .stream()
            .map(expr -> expr.getSingle(e))
            .collect(Collectors.toList());
        usedExpressions = new ArrayList<>(expressions);
        return convert(type.getSingle(e));
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        String property = getPropertyName();
        for (int i = 0; i < this.expressions.size(); i ++) {
            property = property.replaceAll("\\$" + (i + 1), this.expressions.get(i).toString(e, debug));
        }
        return "all the " + property + " of " + type.toString(e, debug);
    }

    protected Expression<? extends F> getExpr() {
        return type;
    }

}