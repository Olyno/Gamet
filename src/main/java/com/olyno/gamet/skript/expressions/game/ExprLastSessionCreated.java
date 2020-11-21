package com.olyno.gamet.skript.expressions.game;

import com.olyno.gamet.skript.effects.game.EffCreateSession;
import com.olyno.gami.models.Game;

import org.bukkit.event.Event;

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

@Name("Last Game Session Created")
@Description("Returns the last game session created.")
@Examples({
    "command session:" +
    "\ttrigger:" +
    "\t\tset {_session} to last session created" +
    "\t\tbroadcast \"The name of the session is %{_session}%\""
})
@Since("3.0.0")

public class ExprLastSessionCreated extends SimpleExpression<Game> {

    static {
        Skript.registerExpression(ExprLastSessionCreated.class, Game.class, ExpressionType.SIMPLE,
            "[the] last [[mini(-| )]game] session created"
        );
    }

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        return true;
    }

    @Override
    protected Game[] get( Event e ) {
        return new Game[]{EffCreateSession.lastCreatedSession};
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
    public String toString( Event e, boolean debug ) {
        return "last game session created";
    }

}
