package com.olyno.gamet.commands.game;

import java.util.ArrayList;

import com.olyno.gamet.util.commands.GameCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CmdGameHelp extends GameCommand {

    public CmdGameHelp() {
        super();
        this.setDescription("Show game commands");
        this.setPattern("help");
        this.setUsage("help");
        this.addPermission("game.help");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        Bukkit.dispatchCommand(sender, "gamet help 2");
    }
    
}