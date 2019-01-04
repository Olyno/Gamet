/*
 * This class has been created by AlexLew. Don't do shit with it or you will get a tiger very angry against you!
 *
 *                     ___......----:'"":--....(\
 *                 .-':'"":   :  :  :   :  :  :.(\.`-.
 *               .'`.  `.  :  :  :   :   : : : : : :  .';
 *              :-`. :   .  : :  `.  :   : :.   : :`.`. ';
 *              : ;-. `-.-._.  :  :   :  ::. .' `. `., =  ;
 *              :-:.` .-. _-.,  :  :  : ::,.'.-' ;-. ,'''"
 *            .'.' ;`. .-' `-.:  :  : : :;.-'.-.'   `-'
 *     :.   .'.'.-' .'`-.' -._;..:---'''"~;._.-;
 *     :`--'.'  : :'     ;`-.;            :.`.-'`.
 *      `'"`    : :      ;`.;             :=; `.-'`.
 *              : '.    :  ;              :-:   `._-`.
 *               `'"'    `. `.            `--'     `._;
 *                         `'"'
 */

package com.alexlew.gameapi.events.bukkit;

import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameDeletedEvent extends Event {

    public static final HandlerList handlers = new HandlerList();

    private Game game;

    public GameDeletedEvent( Game game ) {
        this.game = game;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Game getGame() {
        return game;
    }
}
