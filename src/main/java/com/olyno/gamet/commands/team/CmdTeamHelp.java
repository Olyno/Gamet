package com.olyno.gamet.commands.team;

import java.util.ArrayList;

import com.olyno.gamet.util.commands.GameCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CmdTeamHelp extends GameCommand {

    public CmdTeamHelp() {
        super();
        this.setDescription("Show team commands");
        this.setPattern("help");
        this.setUsage("help");
        this.addPermission("team.help");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        Bukkit.dispatchCommand(sender, "gamet help 3");
    }
    
}