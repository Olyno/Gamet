package com.olyno.skript.expressions.game;

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
import com.olyno.skript.effects.game.EffDeleteGame;
import com.olyno.types.Game;
import org.bukkit.event.Event;

@Name("Last game deleted")
@Description("Returns the last game deleted.")
@Examples({
        "command game:",
        "\ttrigger:",
        "\t\tdelete game \"Test\"",
        "\t\tset {_game} to last game deleted",
        "\t\tbroadcast \"You deleted the game named %gameapi name of {_game}%\""
})
@Since("1.0")

public class ExprLastGameDeleted extends SimpleExpression<Game> {

    static {
        Skript.registerExpression(ExprLastGameDeleted.class, Game.class, ExpressionType.SIMPLE,
                "[the] last [mini(-| )]game deleted"
        );
    }

    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        return true;
    }

    @Override
    protected Game[] get( Event e ) {
        return new Game[]{EffDeleteGame.lastDeletedGame};
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
        return "the last game deleted \"" + EffDeleteGame.lastDeletedGame.getName() + "\"";
    }

}
