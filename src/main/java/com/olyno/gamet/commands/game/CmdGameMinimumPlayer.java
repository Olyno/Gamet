package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

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
        Integer minPlayer = Integer.valueOf(args.get(1));
        Gami.getGameByName(gameName).ifPresentOrElse(gameFound -> {
            gameFound.setMinPlayer(minPlayer);
        }, () -> {
            this.fail("The game '" + gameName + "' has not been found.", sender);
        });
    }
    
}