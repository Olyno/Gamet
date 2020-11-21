package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

import org.bukkit.command.CommandSender;

public class CmdGameMaximumPlayer extends GameCommand {

    public CmdGameMaximumPlayer() {
        super();
        this.setDescription("Define the maximum of player before start the game.");
        this.setPattern("(\\w+) set maximum player ([0-9]+)");
        this.setUsage("<game name> set maximum player <integer>");
        this.addPermission("game.set.players.maximum");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(0).toLowerCase();
        if (Gami.getGames().containsKey(gameName)) {
            Gami.getGames().get(gameName).setMaxPlayer(Integer.valueOf(args.get(1)));
        } else {
            this.fail("The game '" + gameName + "' has not been found.", sender);
        }
    }
    
}