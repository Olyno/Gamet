package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

public class CmdGameDelete extends GameCommand {

    public CmdGameDelete() {
        super();
        this.setDescription("Delete an existing game.");
        this.setPattern("delete (\\w+)");
        this.setUsage("delete <game name>");
        this.addPermission("game.delete");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(0).toLowerCase();
        Gami.getGameByName(gameName).ifPresent(gameFound -> {
            gameFound.delete();
        });
    }
    
}