package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdGameLobby extends GameCommand {

    public CmdGameLobby() {
        super();
        this.setDescription("Define the lobby of the game.");
        this.setPattern("(\\w+) set lobby");
        this.setUsage("<game name> set lobby");
        this.addPermission("game.set.lobby");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(0).toLowerCase();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Gami.getGames().containsKey(gameName)) {
                Gami.getGames().get(gameName).setLobby(player.getLocation());
            } else {
                this.fail("The game '" + gameName + "' has not been found.", sender);
            }
        } else {
            this.fail("Can't execute this command in the console.", sender);
        }
    }
    
}