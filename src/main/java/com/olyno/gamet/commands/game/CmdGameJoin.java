package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

public class CmdGameJoin extends GameCommand {

    public CmdGameJoin() {
        super();
        this.setDescription("Join an existing game.");
        this.setPattern("(\\w+) join");
        this.setUsage("<game name> join");
        this.addPermission("game.join");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(0).toLowerCase();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Gami.getGameByName(gameName).ifPresentOrElse(gameFound -> {
                gameFound.addPlayer(player);
                player.teleport((Location) gameFound.getSpawn());
            }, () -> {
                this.fail("The game '" + gameName + "' has not been found.", sender);
            });
        } else {
            this.fail("Can't execute this command in the console.", sender);
        }
    }
    
}