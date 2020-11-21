package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

import org.bukkit.command.CommandSender;

public class CmdGameTimer extends GameCommand {

    public CmdGameTimer() {
        super();
        this.setDescription("Define the timer of the game.");
        this.setPattern("(\\w+) set timer ([0-9]+)");
        this.setUsage("<game name> set timer <time>");
        this.addPermission("game.set.timer");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(0).toLowerCase();
        if (Gami.getGames().containsKey(gameName)) {
            Gami.getGames().get(gameName).setTimer(Integer.valueOf(args.get(1)));
        } else {
            this.fail("The game '" + gameName + "' has not been found.", sender);
        }
    }
    
}