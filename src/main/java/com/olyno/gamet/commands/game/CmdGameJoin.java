package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            if (Gami.getGames().containsKey(gameName)) {
                Gami.getGames().get(gameName).addPlayer(player);
                player.teleport((Location) Gami.getGames().get(gameName).getSpawn());
            } else {
                this.fail("The game '" + gameName + "' has not been found.", sender);
            }
        } else {
            this.fail("Can't execute this command in the console.", sender);
        }
    }
    
}