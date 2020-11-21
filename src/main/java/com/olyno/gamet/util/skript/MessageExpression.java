package com.olyno.gamet.util.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.olyno.gami.enums.GameMessageTarget;
import com.olyno.gami.enums.GameMessageType;
import com.olyno.gami.models.Game;
import com.olyno.gami.models.GameMessage;
import com.olyno.gami.models.Team;

import org.bukkit.event.Event;

public abstract class MessageExpression<T extends GameMessage> extends SimpleExpression<T> {

    private Expression<?> context;
    private GameMessageTarget target;
    private Boolean isSingle;

    protected static <T> void register(final Class<? extends Expression<T>> c, final Class<T> type, final String property, final String fromType) {
        List<GameMessageTarget> targets = Arrays.asList(GameMessageTarget.values());
        List<String> parsingTargets = IntStream
            .range(0, targets.size())
            .mapToObj(index -> index + "¦" + targets.get(index).name().toLowerCase().replaceAll("_", ""))
            .collect(Collectors.toList());
        String targetsList = String.join("|", parsingTargets);
        Skript.registerExpression(c, type, ExpressionType.SIMPLE,
            "[all] [the] %" + fromType + "%'[s] " + "(" + targetsList + ") " + property + " messages",
            "[all] [the] (" + targetsList + ") " + property + "messages of %" + fromType + "%",
            "[all] [the] %" + fromType + "%'[s] " + "(" + targetsList + ") " + property + " message",
            "[all] [the] (" + targetsList + ") " + property + "message of %" + fromType + "%"
        );
    }
    protected abstract String getPropertyName();
    protected abstract GameMessageType getGameMessageType();

    @Override
    public boolean init(final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        this.context = (Expression<?>) expr[0];
        this.target = GameMessageTarget.values()[parseResult.mark];
        this.isSingle = matchedPattern > 1;
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T[] get(Event e) {
        Object ctx = context.getSingle(e);
        ArrayList<T> messages = new ArrayList<>();
        if (ctx instanceof Game) {
            Game game = (Game) ctx;
            messages = (ArrayList<T>) game.getMessages(getGameMessageType(), target);
        } else if (ctx instanceof Team) {
            Team team = (Team) ctx;
            messages = (ArrayList<T>) team.getMessages(getGameMessageType(), target);
        }
        return (T[]) Arrays.copyOfRange(messages.toArray(), 0, messages.size());
    }

    @Override
    public Class<?>[] acceptChange( final Changer.ChangeMode mode ) {
        switch (mode) {
            case SET:
            case ADD:
            case REMOVE:
            case DELETE:
                return new Class[]{getReturnType()};
            default:
                return new Class[0];
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void change( Event e, Object[] delta, Changer.ChangeMode mode ) {
        Object ctx = context.getSingle(e);
        T message = (T) delta[0];
        switch (mode) {
            case SET:
                if (ctx instanceof Game) {
                    Game game = (Game) ctx;
                    game.getMessages(getGameMessageType(), getTarget()).clear();
                } else if (ctx instanceof Team) {
                    Team team = (Team) ctx;
                    team.getMessages(getGameMessageType(), getTarget()).clear();
                }
            case ADD:
                if (ctx instanceof Game) {
                    Game game = (Game) ctx;
                    game.getMessages(getGameMessageType(), getTarget()).add(message);
                } else if (ctx instanceof Team) {
                    Team team = (Team) ctx;
                    team.getMessages(getGameMessageType(), getTarget()).add(message);
                }
                break;
            case REMOVE:
                if (ctx instanceof Game) {
                    Game game = (Game) ctx;
                    game.getMessages(getGameMessageType(), getTarget()).remove(message);
                } else if (ctx instanceof Team) {
                    Team team = (Team) ctx;
                    team.getMessages(getGameMessageType(), getTarget()).remove(message);
                }
                break;
            case DELETE:
                if (ctx instanceof Game) {
                    Game game = (Game) ctx;
                    game.getMessages(getGameMessageType(), getTarget()).clear();
                } else if (ctx instanceof Team) {
                    Team team = (Team) ctx;
                    team.getMessages(getGameMessageType(), getTarget()).clear();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isSingle() {
        return isSingle;
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "all the " + target.name().toLowerCase().replaceAll("_", " ") + " " + getPropertyName() + " message" + (isSingle ? "" : "s") + " of " + context.toString(e, debug);
    }

    protected GameMessageTarget getTarget() {
        return target;
    }

}