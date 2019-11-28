package com.olyno.gamet.skript.expressions.game;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.olyno.gamet.types.Game;
import org.bukkit.event.Event;

@Name("Amount Of Session Expression")
@Description("Return amount session from a game. Can be set.")
@Examples({
        "command session:\n" +
                "\ttrigger:\n" +
                "\t\tset number of session in game \"test\" to 5\n" +
                "\t\tsend \"Number of session has been defined to 5!\""
})
@Since("2.0.4")

public class ExprAmountOfSessions extends SimpleExpression<Integer> {

    static {
        Skript.registerExpression(ExprAmountOfSessions.class, Integer.class, ExpressionType.SIMPLE,
                "[the] (number|amount) of session (of|from|in) %game%"
        );
    }

    private Expression<Game> game;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        game = (Expression<Game>) expr[0];
        return true;
    }

    @Override
    protected Integer[] get(Event e) {
        return new Integer[]{game.getSingle(e).getSessionsAmount()};
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class[]{Integer.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Game currentGame = game.getSingle(e);
        switch (mode) {
            case SET:
                currentGame.setSessionsAmount((Integer) delta[0]);
                break;
            case RESET:
                currentGame.setSessionsAmount(1);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "amount of session from " + game.toString(e, debug);
    }
}
