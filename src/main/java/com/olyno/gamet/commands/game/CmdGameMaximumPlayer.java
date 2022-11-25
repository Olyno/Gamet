package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

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
        Integer maxPlayer = Integer.valueOf(args.get(1));
        Gami.getGameByName(gameName).ifPresentOrElse(gameFound -> {
            gameFound.setMaxPlayer(maxPlayer);
        }, () -> {
            this.fail("The game '" + gameName + "' has not been found.", sender);
        });
    }
    
}