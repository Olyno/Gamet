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

package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.events.bukkit.GameDeletedEvent;
import com.alexlew.gameapi.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameDeleted implements Listener {

    public GameDeleted( GameAPI plugin ) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GameDeleted( Game game ) {
        Bukkit.getServer().getPluginManager().callEvent(new GameDeletedEvent(game));
    }

    @EventHandler
    public void onDeleted( GameDeletedEvent event ) {
        Game game = event.getGame();
    }

}
