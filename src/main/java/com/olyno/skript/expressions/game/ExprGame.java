package com.olyno.skript.expressions.game;

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
import com.olyno.GameAPI;
import com.olyno.skript.effects.sections.SecStartGame;
import com.olyno.skript.effects.sections.SecStopGame;
import com.olyno.types.Game;
import com.olyno.util.EffectSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Game Expression")
@Description("Return a game from its name (the game must to exist)")
@Examples({
        "command game:",
        "\ttrigger:",
        "\t\tdelete game \"test\"",
        "\t\tsend \"Game \"\"test\"\" deleted!\""
})
@Since("2.0")

public class ExprGame extends SimpleExpression<Game> {

    static {
        Skript.registerExpression(ExprGame.class, Game.class, ExpressionType.SIMPLE,
                "[the] [mini[(-| )]]game [%-string%]"
        );
    }

    public static Game lastGame;

    private Boolean scope = false;
    private Expression<String> gameName;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        gameName = (Expression<String>) expr[0];
        scope = EffectSection.isCurrentSection(SecStartGame.class)
                || EffectSection.isCurrentSection(SecStopGame.class);
        if (gameName == null) {
            return scope;
        }
        return true;
    }

    @Override
    protected Game[] get( Event e ) {
		String currentGame = scope ? lastGame.getName() : gameName.getSingle(e);
		if (!currentGame.replaceAll(" ", "").isEmpty()) {
			if (Game.getGames().containsKey(currentGame)) {
				return new Game[]{Game.getGames().get(currentGame)};
            } else {
                return null;
            }
        } else {
			GameAPI.error("A game can't have a empty name (Current name: \"" + currentGame + "\")");
            return null;
        }
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
            return new Class[]{Player.class};
        }
        return null;
    }

    @Override
    public void change( Event e, Object[] delta, Changer.ChangeMode mode) {
		String currentGame = scope ? lastGame.getName() : gameName.getSingle(e);
		if (Game.getGames().containsKey(currentGame)) {
			Game game = Game.getGames().get(currentGame);
            for (Object obj : delta) {
                if (obj instanceof Player) {
                    Player player = (Player) obj;
                    switch (mode) {
                        case SET:
                            if (!game.hasPlayer(player)) {
                                game.addPlayer(player);
                            }
                            break;
                        case ADD:
							if (game.getMaxPlayer() > game.getPlayers().size()) {
                                if (!game.hasPlayer(player)) {
                                    game.addPlayer(player);
                                }
                            } else {
								GameAPI.error("The game " + game.getName() + " can't add more players, you have already " + game.getPlayers().size() + " players in this game and the maximum of players is " + game.getMaxPlayer());
                            }
                            break;
                        case REMOVE:
                            game.removePlayer(player);
                            break;
                        default:
                            break;
                    }
                } else {
                    GameAPI.error("Only players can be added in a game, but you used a " + obj.getClass());
                }
            }
        }
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
        return "the game " + gameName.getSingle(e);
    }
}
