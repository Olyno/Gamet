package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

import org.bukkit.command.CommandSender;

public class CmdGameMinimumPlayer extends GameCommand {

    public CmdGameMinimumPlayer() {
        super();
        this.setDescription("Define the minimum of player before be able to start the game.");
        this.setPattern("(\\w+) set minimum player ([0-9]+)");
        this.setUsage("<game name> set minimum player <integer>");
        this.addPermission("game.set.players.minimum");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(0).toLowerCase();
        if (Gami.getGames().containsKey(gameName)) {
            Gami.getGames().get(gameName).setMinPlayer(Integer.valueOf(args.get(1)));
        } else {
            this.fail("The game '" + gameName + "' has not been found.", sender);
        }
    }
    
}