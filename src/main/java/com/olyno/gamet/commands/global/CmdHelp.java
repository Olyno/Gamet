package com.olyno.gamet.commands.global;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.commands.CommandSpigot;
import com.olyno.gamet.util.commands.CommandType;
import com.olyno.gamet.util.commands.GameCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdHelp extends GameCommand {

    public CmdHelp() {
        super();
        this.setDescription("Show all game commands of Gamet.");
        this.setPattern("help( \\w)?");
        this.setUsage("help [1,2,3]");
        this.addPermission("show_help");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameCommands = CommandSpigot.COMMANDS
            .get(CommandType.GAME)
            .stream()
            .map(command -> ChatColor.GOLD + "/game " + command.getUsage() + ChatColor.GRAY + " - " + ChatColor.WHITE + command.getDescription() + "\n")
            .collect(Collectors.joining("\n"));
        String teamCommands = CommandSpigot.COMMANDS
            .get(CommandType.TEAM)
            .stream()
            .map(command -> ChatColor.GOLD + "/team " + command.getUsage() + ChatColor.GRAY + " - " + ChatColor.WHITE + command.getDescription() + "\n")
            .collect(Collectors.joining("\n"));
        String globalCommands = CommandSpigot.COMMANDS
            .get(CommandType.GLOBAL)
            .stream()
            .map(command -> ChatColor.GOLD + "/" + command.getUsage() + ChatColor.GRAY + " - " + ChatColor.WHITE + command.getDescription() + "\n")
            .collect(Collectors.joining("\n"));
        if (args.size() == 0) args.add("1");
        if (args.get(0).equals("1")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "=============== Gamet - Help (1/3) ===============\n\n"
                + ChatColor.DARK_AQUA + ChatColor.BOLD + ChatColor.UNDERLINE + "Global:\n\n" + ChatColor.RESET
                + globalCommands + "\n\n"
                + ChatColor.GOLD + "Current Version: " + ChatColor.WHITE + Gamet.getPlugin(Gamet.class).getDescription().getVersion() + "\n"
                + ChatColor.GOLD + "Author: " + ChatColor.WHITE + Gamet.getPlugin(Gamet.class).getDescription().getAuthors().toArray()[0] + "\n"
            );
        } else if (args.get(0).equals("2")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "=============== Gamet - Help (2/3) ===============\n\n"
                + ChatColor.DARK_AQUA + ChatColor.BOLD + ChatColor.UNDERLINE + "Game:\n\n" + ChatColor.RESET
                + gameCommands + "\n"
            );
        } else if (args.get(0).equals("3")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "=============== Gamet - Help (3/3) ===============\n\n"
                + ChatColor.DARK_AQUA + ChatColor.BOLD + ChatColor.UNDERLINE + "Team:\n\n" + ChatColor.RESET
                + teamCommands + "\n"
            );
        } else {
            Bukkit.dispatchCommand(sender, args.get(0).toLowerCase() + " help");
        }

    }
    
}