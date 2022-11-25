package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

public class CmdGameSpawn extends GameCommand {

    public CmdGameSpawn() {
        super();
        this.setDescription("Define the spawn of the game.");
        this.setPattern("(\\w+) set spawn");
        this.setUsage("<game name> set spawn");
        this.addPermission("game.set.spawn");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(0).toLowerCase();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Gami.getGameByName(gameName).ifPresentOrElse(gameFound -> {
                gameFound.setSpawn(player.getLocation());
            }, () -> {
                this.fail("The game '" + gameName + "' has not been found.", sender);
            });
        } else {
            this.fail("Can't execute this command in the console.", sender);
        }
    }
    
}