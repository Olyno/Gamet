package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

public class CmdGameDisplayName extends GameCommand {

    public CmdGameDisplayName() {
        super();
        this.setDescription("Define the display name of the game.");
        this.setPattern("(\\w+) set display(_|-| )?name (\\w+)");
        this.setUsage("<game name> set display name (\\w+)");
        this.addPermission("game.set.display.name");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(0).toLowerCase();
        Gami.getGameByName(gameName).ifPresent(gameFound -> {
            gameFound.setDisplayName(args.get(2));
        });
    }
    
}