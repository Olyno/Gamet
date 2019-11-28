package com.olyno.gamet.skript.expressions.game;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.olyno.gamet.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;

@Name("World of game")
@Description("Returns world of a game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tset {_game} to game \"test\"",
        "\t\tset world of {_game} to world(\"world\")",
        "\t\tbroadcast \"The game %{_game}% is in world: %world of {_game}%\""
})
@Since("1.0")

public class ExprWorldOfGame extends SimplePropertyExpression<Game, World> {

    static {
        register(ExprWorldOfGame.class, World.class,
                "[the] world", "game");
    }

    @Override
    public World convert( Game game ) {
        return game.getWorld();
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
                mode == Changer.ChangeMode.DELETE) {
            return new Class[]{Game.class};
        }
        return null;
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
        for (Game game : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    game.setWorld((World) delta[0]);
                    break;
                case RESET:
                    game.setWorld(Bukkit.getWorld("world"));
                    break;
                case DELETE:
                    game.setWorld(null);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "world";
    }


    @Override
    public Class<? extends World> getReturnType() {
        return World.class;
    }
}
