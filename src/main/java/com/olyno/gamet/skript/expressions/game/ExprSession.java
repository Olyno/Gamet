package com.olyno.gamet.skript.expressions.game;

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
import com.olyno.gamet.types.Game;
import org.bukkit.event.Event;

@Name("Session Expression")
@Description("Return a session from a game (the game must to exist)")
@Examples({
        "command start:\n" +
                "\ttrigger:\n" +
                "\t\tstart session 3 of game \"test\"\n" +
                "\t\tsend \"&aStarted\""
})
@Since("2.0.4")

public class ExprSession extends SimpleExpression<Game> {

    static {
        Skript.registerExpression(ExprSession.class, Game.class, ExpressionType.SIMPLE,
                "[the] session %integer% of %game%",
                "[the] %game%'s session %integer%"
        );
    }

    private Expression<Integer> session;
    private Expression<Game> game;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        session = matchedPattern == 0 ? (Expression<Integer>) expr[0] : (Expression<Integer>) expr[1];
        game = matchedPattern == 0 ? (Expression<Game>) expr[1] : (Expression<Game>) expr[0];
        return true;
    }

    @Override
    protected Game[] get(Event e) {
        return new Game[]{game.getSingle(e).getSession(session.getSingle(e))};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Game> getReturnType() {
        return Game.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "session " + session.toString(e, debug) + " of " + game.toString(e, debug);
    }
}
