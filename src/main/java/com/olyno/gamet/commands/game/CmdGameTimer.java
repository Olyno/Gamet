package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

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
        Integer timer = Integer.valueOf(args.get(1));
        Gami.getGameByName(gameName).ifPresentOrElse(gameFound -> {
            gameFound.setTimer(timer);
        }, () -> {
            this.fail("The game '" + gameName + "' has not been found.", sender);
        });
    }
    
}